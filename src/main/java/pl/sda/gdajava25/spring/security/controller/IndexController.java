package pl.sda.gdajava25.spring.security.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/")
public class IndexController {


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @GetMapping("/tylkoDlaKozakow")
    public String tylkoDlaKozakow() {
        return "index-koazk";
    }

    @GetMapping("/adminIndex")
    public String adminIndex() {
        return "index-admin";
    }
}
