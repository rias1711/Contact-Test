package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.User;
import com.codegym.service.CategoryService;
import com.codegym.service.GenericService;
import com.codegym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }


    @GetMapping("/users")
    public ModelAndView listUsers() {
        Iterable<User> users = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("/user/list");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/create-user")
    public ModelAndView showCreateUser() {
        ModelAndView modelAndView = new ModelAndView("/user/create");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/create-user")
    public ModelAndView saveUser(@ModelAttribute("user")User user) {
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/user/create");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("message", "New user created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-user/{id}")
    public ModelAndView showEditUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("/user/edit");
            modelAndView.addObject("user", user);
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit-user")
    public ModelAndView updateUser(@ModelAttribute("user")User user) {
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/user/edit");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "User updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-user/{id}")
    public ModelAndView showDeleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("/user/delete");
            modelAndView.addObject("user", user);
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/delete-user")
    public String deleteUser(@ModelAttribute("user")User user) {
        userService.remove(user.getId());
        return "redirect:users";
    }
}
