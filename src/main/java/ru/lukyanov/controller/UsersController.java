package ru.lukyanov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.lukyanov.entity.User;
import ru.lukyanov.service.UserService;

import java.util.List;

@Controller
public class UsersController {
    private final UserService userService;
    private final List<User> userList;

    public UsersController(UserService userService) {
        this.userService = userService;
        this.userList = userService.findAll();
    }

    @GetMapping("/users")
    public String main(Model model) {
        model.addAttribute("message", "Users List From DB:");
        model.addAttribute("users", userList);
        return "users";
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public User returnUser(@PathVariable long id) {
        return userService.findById(id).orElseThrow();
    }
}