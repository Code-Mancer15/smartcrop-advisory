package com.smartcrop.controller;

import com.smartcrop.util.FileUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLogin(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              org.springframework.ui.Model model) {
        if (FileUtil.validateUser(email, password)) {
            session.setAttribute("user", email);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }
    }

    @GetMapping("/signup")
    public String showSignup(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               HttpSession session) {

        FileUtil.saveUser(name, email, password);
        session.setAttribute("user", email);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}