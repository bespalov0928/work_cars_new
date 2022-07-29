package ru.work.cars.controller;

import org.apache.catalina.LifecycleState;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.work.cars.model.*;
import ru.work.cars.persistence.EngineStore;
import ru.work.cars.persistence.TransmissionStore;
import ru.work.cars.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private final MarkService markService;
    private final PostService postService;
    private final BodyService bodyService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;
    private final UserService userService;

    public IndexController(MarkService markService,
                           PostService postService,
                           BodyService bodyService,
                           EngineService engineService,
                           TransmissionService transmissionService,
                           UserService userService) {
        this.markService = markService;
        this.postService = postService;
        this.bodyService = bodyService;
        this.engineService = engineService;
        this.transmissionService = transmissionService;
        this.userService = userService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("marks", markService.findAll());
        model.addAttribute("posts", postService.findAll());
        return "index";
    }

    @GetMapping("/addPost")
    public String addPost(Model model) {
        model.addAttribute("marks", markService.findAll());
        model.addAttribute("bodies", bodyService.findAll());
        model.addAttribute("transmission", transmissionService.findAll());
        model.addAttribute("engines", engineService.findAll());
        return "addPost";
    }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute Post post,
                           @RequestParam(value = "mark_id", required = false) int idMark,
                           @RequestParam(value = "body_id", required = false) int idBody,
                           @RequestParam(value = "transmission_id", required = false) int idTransmission,
                           @RequestParam(value = "engine_id", required = false) int idEngine
    ) throws IOException {
        org.springframework.security.core.userdetails.User userContext = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByName(userContext.getUsername());
        Mark mark = markService.findById(idMark);
        Body body = bodyService.findById(idBody);
        Transmission transmission= transmissionService.findById(idTransmission);
        Engine engine = engineService.findById(idEngine);
        post.setUser(user);
        post.setMark(mark);
        post.setBody(body);
        post.setTransmission(transmission);
        post.setEngine(engine);
        postService.savePost(post);
        return "redirect:/index";
    }
}
