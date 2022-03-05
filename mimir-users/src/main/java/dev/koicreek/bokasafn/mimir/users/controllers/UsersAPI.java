package dev.koicreek.bokasafn.mimir.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UsersAPI {

    @Autowired
    private Environment env;

    @GetMapping("/verifyRouting")
    public String status() {
        return "Users service running on port " + env.getProperty("local.server.port");
    }
}
