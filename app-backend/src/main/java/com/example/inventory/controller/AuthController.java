// controller/AuthController.java
package com.example.inventory.controller;

import com.example.inventory.entity.User;
import com.example.inventory.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // login.html
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {

        return userService.authenticate(username, password)
            .map(user -> {
                session.setAttribute("LOGIN_USER_ID", user.getId());
                session.setAttribute("LOGIN_USERNAME", user.getUsername());
                session.setAttribute("LOGIN_ROLE", user.getRole());

                System.out.println("[LOGIN_OK] redirect to /dashboard"
                + " sessionId=" + session.getId()
                + " username=" + user.getUsername());

                return "redirect:/dashboard";
            })
            .orElseGet(() -> {
                System.out.println("[LOGIN_NG] back to /login username=" + username);
                model.addAttribute("error", "ユーザー名またはパスワードが違います。");
                return "login";
            });
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
