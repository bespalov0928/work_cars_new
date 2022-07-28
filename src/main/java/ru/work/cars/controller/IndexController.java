package ru.work.cars.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.work.cars.model.User;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index(Model model) {
//        User user = null;
        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        return "index";
    }
}
