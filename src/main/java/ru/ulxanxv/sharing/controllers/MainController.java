package ru.ulxanxv.sharing.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MainController {

    @GetMapping
    public String publicGetSomeMessage() {
        return "Hello, [PRIVATE]";
    }

    @GetMapping("/{id}")
    public String privateGetSomeMessage(@PathVariable Long id) {
        return "Hello, [ PRIVATE: " + id + " ]";
    }

}
