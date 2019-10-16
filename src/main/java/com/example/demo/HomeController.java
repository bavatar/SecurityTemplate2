package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {
    // Added for 4.05
    @Autowired
    UserRepository userRepository;

    // Added for 4.06
    @Autowired
    UserService userService;

    // Added for 4.06
    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    // Added for 4.06
    @PostMapping("/register")
    public String processRegistrationPage(@Valid
               @ModelAttribute("user") User user, BindingResult result,
               Model model){
        model.addAttribute("user", user);

        if(result.hasErrors()){
            return "registration";
        }
        else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        //return "redirect:/";
        return "index";
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    // Modified for 4.05
    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username = principal.getName();
//        System.out.println("secure: username= " + username);
//
//        User user = userRepository.findByUsername(username);
//        System.out.println("secure: email= " + user.getEmail());
//        System.out.println("secure: Password= " + user.getPassword());
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }
//
//    @RequestMapping("/admin")
//    public String admin(){
//        return "admin";
//    }
//
//    @RequestMapping("/student")
//    public String Student(){
//        return "student";
//    }
//
//    @RequestMapping("/course")
//    public String Course(){
//        return "course";
//    }
//
//    @RequestMapping("/teacher")
//    public String Teacher(){
//        return "teacher";
//    }
}
