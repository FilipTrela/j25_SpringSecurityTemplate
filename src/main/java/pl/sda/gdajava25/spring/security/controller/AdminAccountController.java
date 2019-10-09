package pl.sda.gdajava25.spring.security.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.gdajava25.spring.security.model.Account;
import pl.sda.gdajava25.spring.security.service.AccountService;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/admin/account/")
@PreAuthorize(value = "hasRole('ADMIN')")
public class AdminAccountController {
    private final AccountService accountService;

    @GetMapping("/list")
    public String getUserList(Model model) {
        model.addAttribute("users", accountService.getAllAccount());

        return "user-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(Model model, @PathVariable(name = "id") Long id) {
        accountService.deleteById(id);
        return "redirect:/admin/account/list";
    }

    @GetMapping("/disable/{id}")
    public String disable(Model model, @PathVariable(name = "id") Long id) {
        accountService.disable(id);
        return "redirect:/admin/account/list";
    }

    @GetMapping("/enable/{id}")
    public String enable(Model model, @PathVariable(name = "id") Long id) {
        accountService.enable(id);
        return "redirect:/admin/account/list";
    }

    @GetMapping("/reset/{id}")
    public String reset(Model model, @PathVariable(name = "id") Long id) {
        Optional<Account> optionalAccount = accountService.getAccountById(id);
        if (optionalAccount.isPresent()) {
              model.addAttribute("account",optionalAccount.get());
              return "account-password";
        }
        return "redirect:/admin/account/list";
    }
}
