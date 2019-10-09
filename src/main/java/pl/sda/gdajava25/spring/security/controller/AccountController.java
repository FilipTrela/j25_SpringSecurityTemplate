package pl.sda.gdajava25.spring.security.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.gdajava25.spring.security.model.Account;
import pl.sda.gdajava25.spring.security.service.AccountService;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/user/")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/register")
    public String registrationForm(Model model, Account account) {
        model.addAttribute("newAccount", account);
        return "register-form";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid Account account,BindingResult result, String passwordConfirm) {
        if (result.hasErrors()) {
            return registrationError(model, account, result.getFieldError().getDefaultMessage());
        }
        if (!account.getPassword().equals(passwordConfirm)) {
            return registrationError(model, account, "Passwords do nat match");
        }
        if (!accountService.register(account)) {
            return registrationError(model, account, "User with given username already exist.");
        }
        return "redirect:/login";
    }

    private String registrationError(Model model, Account account, String s) {
        model.addAttribute("newAccount", account);
        model.addAttribute("errorMessage", s);
        return "register-form";
    }


}
