package com.developer.hcmsserver.verification;

import com.developer.hcmsserver.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/verify")
public class VerificationController {

    @Autowired
    UserService userService;

    @GetMapping("/email-verification/{token}")
    public String verifyEmailToken(@PathVariable String token,Model model) {
        boolean isVerified = userService.verifyEmailToken(token);
        if(isVerified) model.addAttribute("successMessage","Email Verified Successfully!");
        else model.addAttribute("errorMessage","Email Verification link has expired!");
        return "emailStatus";
    }
}
