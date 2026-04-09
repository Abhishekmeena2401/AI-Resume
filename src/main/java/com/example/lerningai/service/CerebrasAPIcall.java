


package com.example.lerningai.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class CerebrasAPIcall {

    public static String callCerebras(String resumeText) {

        String apiKey = "csk-ky4ekr58e94hyvhrt8n8cvxm4td6kvmhv5dcfkdw9y6prn58";
        String url = "https://api.cerebras.ai/v1/chat/completions";

        String prompt = "You are an expert resume coach and career advisor. Analyze the following resume thoroughly and return ONLY a valid JSON object (no extra text, no markdown, no explanation outside the JSON).\n\n" +
                "IMPORTANT RULES:\n" +
                "- Return ONLY JSON\n" +
                "- Do NOT break JSON\n" +
                "- Close all brackets properly\n" +
                "- Do NOT truncate response\n\n" +

                "Return this exact JSON structure:\n" +
                "{\n" +
                "  \"atsScore\": number,\n" +
                "  \"atsExplanation\": \"string\",\n" +
                "  \"predictedJobRole\": \"string\",\n" +
                "  \"jobRoleReasoning\": \"string\",\n" +
                "  \"skillsToAdd\": [\n" +
                "    { \"skill\": \"string\", \"reason\": \"string\" }\n" +
                "  ],\n" +
                "  \"recommendedCourses\": [\n" +
                "    { \"title\": \"string\", \"platform\": \"string\", \"reason\": \"string\" }\n" +
                "  ],\n" +
                "  \"resumeTips\": [\"string\"],\n" +
                "  \"interviewTips\": [\"string\"],\n" +
                "  \"improvementIdeas\": [\n" +
                "    { \"section\": \"string\", \"suggestion\": \"string\" }\n" +
                "  ],\n" +
                "  \"videoSuggestions\": [\n" +
                "    { \"title\": \"string\", \"url\": \"string\", \"reason\": \"string\" }\n" +
                "  ],\n" +
                "  \"overallFeedback\": \"string\",\n" +
                "  \"topSkills\": [\"string\"]\n" +
                "}\n\n" +

                "Make response detailed and complete.\n\n" +
                "Resume Text:\n" + resumeText;

        // Create request body
        JsonObject request = new JsonObject();
        request.addProperty("model", "llama3.1-8b");

        JsonArray messages = new JsonArray();
        JsonObject msg = new JsonObject();
        msg.addProperty("role", "user");
        msg.addProperty("content", prompt);
        messages.add(msg);

        request.add("messages", messages);
        request.addProperty("max_tokens", 2048);

        String body = request.toString();

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            String responseBody = response.getBody();

            //  STEP 1: Parse full API response
            JsonObject fullJson = JsonParser.parseString(responseBody).getAsJsonObject();

            //  STEP 2: Extract only AI content
            String content = fullJson
                    .getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content")
                    .getAsString();

            //  STEP 3: Clean content
            content = content.trim();

            //  STEP 4: Extract only JSON part
            int start = content.indexOf("{");
            int end = content.lastIndexOf("}");

            if (start != -1 && end != -1) {
                content = content.substring(start, end + 1);
            }

            return content;

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"AI response parsing failed\"}";
        }
    }
}