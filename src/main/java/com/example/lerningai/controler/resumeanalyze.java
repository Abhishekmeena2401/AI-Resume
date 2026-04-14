
package com.example.lerningai.controler;

import com.example.lerningai.entity.resumedata;
import com.example.lerningai.repository.Resumerepo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.lerningai.service.CerebrasAPIcall;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@CrossOrigin(origins = "*")
public class resumeanalyze {


    @Autowired
    private Resumerepo repo;

    @Autowired
    private CerebrasAPIcall callCerebras;

    @PostMapping("/analyze")
    public String getDatafromAPI(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "email", defaultValue = "") String userEmail,
            @RequestParam(value = "username", defaultValue = "") String userName
    ) {
        try {
            String filename = file.getOriginalFilename();

            //  Only allow DOC and DOCX
            if (filename == null ||
                    (!filename.toLowerCase().endsWith(".doc") && !filename.toLowerCase().endsWith(".docx"))) {
                return "{\"error\": \"Only DOC or DOCX files are allowed. PDF is not supported.\"}";
            }
            //  Extract text
            String text = "";
            InputStream inputStream = file.getInputStream();

            if (filename.toLowerCase().endsWith(".docx")) {
                XWPFDocument docx = new XWPFDocument(inputStream);
                XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
                text = extractor.getText();
                extractor.close();
                docx.close();
            } else {
                HWPFDocument doc = new HWPFDocument(inputStream);
                WordExtractor extractor = new WordExtractor(doc);
                text = extractor.getText();
                extractor.close();
                doc.close();
            }

            if (text == null || text.trim().isEmpty()) {
                return "{\"error\": \"Could not extract text from the file.\"}";
            }
            //""  Call AI ( returns clean JSON )
            String content = callCerebras.callCerebras(text);
            System.out.println("AI CONTENT: " + content);
            // Clean content (safety)
            content = content.trim();

            if (content.startsWith("```")) {
                content = content.replaceAll("```json", "").replaceAll("```", "").trim();
            }


            // Parse JSON
            JsonObject data = JsonParser.parseString(content).getAsJsonObject();

            // Extract ONLY required fields
            int atsScore = data.has("atsScore") ? data.get("atsScore").getAsInt() : 0;
            String overallFeedback = data.has("overallFeedback") ? data.get("overallFeedback").getAsString() : "";

           // Save minimal data
            resumedata resumeobj = new resumedata();
            resumeobj.setUserEmail(userEmail);
            resumeobj.setUserName(userName);
            resumeobj.setAtsScore(atsScore);
            resumeobj.setOverallFeedback(overallFeedback);

            repo.save(resumeobj);

            // Return full JSON to frontend (unchanged)

            return content;

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Something went wrong. Please try again.\"}";
        }
    }
}






