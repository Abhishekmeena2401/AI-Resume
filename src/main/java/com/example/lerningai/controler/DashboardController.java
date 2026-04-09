package com.example.lerningai.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// NOTE: This is a @Controller (NOT @RestController)
// because we want to return an HTML page, not JSON data.
@Controller
public class DashboardController {

    // When someone visits http://localhost:8080/ or http://localhost:8080
    // they will be shown dashboard.html automatically
    @GetMapping("/")
    public String home() {
        return "forward:/dashboard.html";
    }
}
