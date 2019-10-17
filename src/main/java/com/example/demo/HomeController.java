package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

@Controller
public class HomeController {
    // Added for ToDo
    @Autowired
    TodoRepository todoRepository;

    // Added for ToDo
    @RequestMapping("/")
    public String listTasks(Model model){
        model.addAttribute("todos", todoRepository.findAll());
        return "list";
    }

    // Added for ADMIN Role in ToDo
    @RequestMapping("/showusers")
    public String listUsers(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "showusers";
    }

    // Added for ToDo
    @GetMapping("/add")
    public String todoForm(Model model){
        model.addAttribute("todo", new Todo());
        return "todoform";
    }

    // Added for ToDo
    @PostMapping("/process")
    public String processForm(@Valid Todo todo, BindingResult result){
        if (result.hasErrors()){
            return "todoform";
        }
        todoRepository.save(todo);
        return "redirect:/";
    }

    @PostMapping("/processprofile")
    public String processProfile(@Valid
              @ModelAttribute("user") User user, BindingResult result,
              Model model){
        model.addAttribute("user", user);

        if(result.hasErrors()){
            return "updateprofile";
        }
        else {
            if (user.getRoles().toString().contains("USER")){
                userService.saveUser(user);
            }
            else if (user.getRoles().toString().contains("ADMIN")){
                userService.saveAdmin(user);
            } if (user.getRoles().toString().contains("SUPERVISOR")){
                userService.saveSupervisor(user);
            }
            model.addAttribute("message", "User Account Updated");
        }
        //return "redirect:/";
        return "showusers";
    }

//    @PostMapping("/register")
//    public String processRegistrationPage(@Valid
//              @ModelAttribute("user") User user, BindingResult result,
//              Model model){
//        model.addAttribute("user", user);
//
//        if(result.hasErrors()){
//            return "registration";
//        }
//        else {
//            userService.saveUser(user);
//            model.addAttribute("message", "User Account Created");
//        }
//        //return "redirect:/";
//        return "list";
////        return "index";
//    }

    // Added for ToDo
    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("todo", todoRepository.findById(id).get());
        return "show";
    }

    // Added for ToDo
    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("todo", todoRepository.findById(id).get());
        return "todoform";
    }

    // see process_profile  use that
//    @RequestMapping("/update_profile/{id}")
//    public String updateProfile(@PathVariable("id") long id, Model model){
//        model.addAttribute("user", userRepository.findById(id).get());
//        model.addAttribute("roles", roleRepository.findAll());
//        return "userprofile";
//    }

//    @PostMapping("/register")
//    public String processRegistrationPage(@Valid
//                  @ModelAttribute("user") User user, BindingResult result,
//                  Model model){
//        model.addAttribute("user", user);
//
//        if(result.hasErrors()){
//            return "registration";
//        }
//        else {
//            userService.saveUser(user);
//            model.addAttribute("message", "User Account Created");
//        }
//        //return "redirect:/";
//        return "list";
////        return "index";
//    }


    // Added for ToDo
    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        todoRepository.deleteById(id);
        return "redirect:/";
    }

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
        return "list";
//        return "index";
    }

//    @RequestMapping("/")
//    public String index(){
//        return "index";
//    }

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
