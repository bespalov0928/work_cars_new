package ru.work.cars.controller;

import org.apache.catalina.LifecycleState;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.work.cars.model.*;
import ru.work.cars.persistence.EngineStore;
import ru.work.cars.persistence.PhotoStore;
import ru.work.cars.persistence.TransmissionStore;
import ru.work.cars.service.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.springframework.core.io.Resource;

//import javax.annotation.Resource;
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
    private final PhotoService photoService;

    public IndexController(MarkService markService,
                           PostService postService,
                           BodyService bodyService,
                           EngineService engineService,
                           TransmissionService transmissionService,
                           UserService userService,
                           PhotoService photoService) {
        this.markService = markService;
        this.postService = postService;
        this.bodyService = bodyService;
        this.engineService = engineService;
        this.transmissionService = transmissionService;
        this.userService = userService;
        this.photoService = photoService;
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
                           @RequestParam(value = "engine_id", required = false) int idEngine,
                           @RequestParam(value = "files") MultipartFile[] files
    ) throws IOException {
        org.springframework.security.core.userdetails.User userContext = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByName(userContext.getUsername());
        Mark mark = markService.findById(idMark);
        Body body = bodyService.findById(idBody);
        Transmission transmission = transmissionService.findById(idTransmission);
        Engine engine = engineService.findById(idEngine);
        post.setUser(user);
        post.setMark(mark);
        post.setBody(body);
        post.setTransmission(transmission);
        post.setEngine(engine);
        postService.savePost(post);
        for (MultipartFile file : files) {
            Photo ph = Photo.of(file.getBytes(), post);
            photoService.savePhoto(ph);
        }
        return "redirect:/index";
    }

    @GetMapping("/descr/{idPost}")
    public String getBodyService(Model model, @PathVariable("idPost") int idPost) {
        org.springframework.security.core.userdetails.User userContext = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByName(userContext.getUsername());
        Post post = postService.findById(idPost);
        List<Photo> photoList = post.getPhotos();
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("photos", photoList);
        return "descr";
    }

    @GetMapping("/postSale/{idPost}")
    public String postSale(@PathVariable("idPost") int idPost) {
        postService.postSale(idPost);
        return "redirect:/index";
    }

    @GetMapping("/postUpdate/{idPost}")
    public String postUpdate(Model model, @PathVariable("idPost") int idPost) {
        model.addAttribute("marks", markService.findAll());
        model.addAttribute("bodies", bodyService.findAll());
        model.addAttribute("transmissions", transmissionService.findAll());
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("post", postService.findById(idPost));
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post,
                             @RequestParam(value = "mark_id", required = false) int idMark,
                             @RequestParam(value = "body_id", required = false) int idBody,
                             @RequestParam(value = "transmission_id", required = false) int idTransmission,
                             @RequestParam(value = "engine_id", required = false) int idEngine,
                             @RequestParam(value = "files") MultipartFile[] files) throws IOException {

        org.springframework.security.core.userdetails.User userContext = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByName(userContext.getUsername());
        Mark mark = markService.findById(idMark);
        Body body = bodyService.findById(idBody);
        Transmission transmission = transmissionService.findById(idTransmission);
        Engine engine = engineService.findById(idEngine);
        post.setUser(user);
        post.setMark(mark);
        post.setBody(body);
        post.setTransmission(transmission);
        post.setEngine(engine);
        postService.postUpdate(post);
        post.getPhotos().clear();
        photoService.delPhoto(post.getId());
        for (MultipartFile file : files) {
            Photo ph = Photo.of(file.getBytes(), post);
            photoService.savePhoto(ph);
        }
        return "redirect:/index";
    }

    @GetMapping("/postDelete/{idPost}")
    public String postDelete(@PathVariable("idPost") int idPost){
        Post post = postService.findById(idPost);
        photoService.delPhoto(post.getId());
        postService.postDelete(idPost);
        return "redirect:/index";
    }

    @GetMapping("/getPhotoPostFirst/{idPost}")
    public ResponseEntity<Resource> photoFindFirst(@PathVariable("idPost") int idPost) {
        Post post = postService.findById(idPost);
        byte[] photo = post.getPhotos().get(0).getPhoto();
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(photo.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(photo));
    }

    @GetMapping("/getPhoto/{idPhoto}")
    public ResponseEntity<Resource> getPhotoPost(@PathVariable("idPhoto") int idPhoto) {
        byte[] photo = photoService.findById(idPhoto).getPhoto();
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(photo.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(photo));
    }

}
