package dev.koicreek.bokasafn.mimirs.users;

import dev.koicreek.bokasafn.mimirs.users.contracts.UserRegistrationCM;
import dev.koicreek.bokasafn.mimirs.users.contracts.UserCreationResponseCM;
import dev.koicreek.bokasafn.mimirs.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
public class UsersAPI {

    @Autowired
    private Environment env;

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<UserCreationResponseCM> createUser(@Valid @RequestBody UserRegistrationCM userRegistrationCM) {
        return new ResponseEntity<>(usersService.createUser(userRegistrationCM), HttpStatus.CREATED);
    }

    @GetMapping("/port")
    public String portStatus() {
        return "Users service running on port: " + env.getProperty("local.server.port");
    }
}
