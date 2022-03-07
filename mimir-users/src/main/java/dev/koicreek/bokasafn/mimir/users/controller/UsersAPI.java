package dev.koicreek.bokasafn.mimir.users.controller;

import dev.koicreek.bokasafn.mimir.users.model.UserCM;
import dev.koicreek.bokasafn.mimir.users.model.UserCreationResponseCM;
import dev.koicreek.bokasafn.mimir.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
public class UsersAPI {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<UserCreationResponseCM> createUser(@Valid @RequestBody UserCM userCM) {
        return new ResponseEntity<>(usersService.createUser(userCM), HttpStatus.CREATED);
    }
}
