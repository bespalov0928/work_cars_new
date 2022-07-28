package ru.work.cars.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.work.cars.model.User;
import ru.work.cars.service.UserService;

@Controller
public class RegControl {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegControl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/reg")
    public String reqSave(@ModelAttribute User user, Model model) {
        User userFind = userService.findByName(user.getUsername());
        String errorMessage = null;
        if (userFind != null) {
            errorMessage = "User: "+user.getUsername()+" exists!";
            model.addAttribute("errorMessage", errorMessage);
            return "registration";
        }

        user.setEnabled(true);
//        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "login";

    }

    @GetMapping("/reg")
    public String regPage(){
        return "registration";
    }


}
