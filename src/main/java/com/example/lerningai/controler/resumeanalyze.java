
package com.example.lerningai.controler;

import com.example.lerningai.entity.Userdata;
import com.example.lerningai.entity.resumedata;
import com.example.lerningai.repository.Resumerepo;

import com.example.lerningai.repository.UserRepo;
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
    private UserRepo userRepo;

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

            try {
                // Parse JSON
                JsonObject data = JsonParser.parseString(content).getAsJsonObject();
                // Extract fields
                int atsScore = 0;
                if (data.has("atsScore")) { atsScore = data.get("atsScore").getAsInt(); }

                String overallFeedback = data.has("overallFeedback") ? data.get("overallFeedback").getAsString() : "";

                resumedata resumeobj = new resumedata();
                Userdata user = userRepo.findByEmail(userEmail)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                resumeobj.setUserId(user.getId());
                resumeobj.setAtsScore(atsScore);
                resumeobj.setOverallFeedback(overallFeedback);
                repo.save(resumeobj);

                return content;

            } catch (Exception jsonEx) {
                System.err.println("JSON Parsing Error. Raw Content: " + content);
                return "{\"error\": \"AI generated an invalid response format. Please try again.\"}";
            }



        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Something went wrong. Please try again.\"}";
        }
    }
}






