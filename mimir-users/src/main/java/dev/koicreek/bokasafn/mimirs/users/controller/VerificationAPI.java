package dev.koicreek.bokasafn.mimirs.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify/users")
public class VerificationAPI {

    @Autowired
    private Environment env;

    @GetMapping("/routing")
    public String portStatus() {
        return "Users service running on port " + env.getProperty("local.server.port");
    }
}
