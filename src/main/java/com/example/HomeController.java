package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Returns the string "index" when the root endpoint ("/") is accessed via a GET request.
     *
     * @return  the string "index"
    */
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
