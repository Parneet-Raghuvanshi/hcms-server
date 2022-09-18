package com.developer.hcmsserver.verification;

import com.developer.hcmsserver.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/verify")
public class VerificationController {

    @Autowired
    UserService userService;

    @GetMapping("/email-verification/{token}")
    public String verifyEmailToken(@PathVariable String token,Model model) {
        boolean isVerified = userService.verifyEmailToken(token);
        model.addAttribute("name","EMAIL VERIFICATION");
        if(isVerified) model.addAttribute("successMessage","Email Verified Successfully!");
        else model.addAttribute("errorMessage","Email Verification link has expired!");
        return "status";
    }

    @GetMapping("/password-reset/{token}")
    public String resetPassword(@PathVariable String token,Model model) {
        model.addAttribute("token",token);
        model.addAttribute("password",new Password());
        return "resetPassword";
    }

    @PostMapping("/password-reset/{token}")
    public String resetPassword(@PathVariable String token,Model model,@ModelAttribute("password") Password password) {
        if(password.getPasswordOne().isEmpty() || password.getPasswordTwo().isEmpty()) {
            model.addAttribute("errorMessage","Password can't be empty!");
            model.addAttribute("token",token);
            return "resetPassword";
        }
        if(!password.getPasswordOne().equals(password.getPasswordTwo())) {
            model.addAttribute("errorMessage","Both the password must be same!");
            model.addAttribute("token",token);
            return "resetPassword";
        }

        boolean operationResult = userService.resetPassword(token,password.getPasswordOne());
        if(operationResult) {
            // Password Done
            model.addAttribute("successMessage","Password reset successfully, please login again!");
        } else {
            // Password Cancel
            model.addAttribute("errorMessage","Password reset link has expired!");
        }
        return "status";
    }
}
