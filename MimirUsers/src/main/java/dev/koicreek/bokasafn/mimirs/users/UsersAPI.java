package dev.koicreek.bokasafn.mimirs.users;

import dev.koicreek.bokasafn.mimirs.users.contracts.UserRegistrationCM;
import dev.koicreek.bokasafn.mimirs.users.contracts.UserCreationResponseCM;
import dev.koicreek.bokasafn.mimirs.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v0/users")
@RequiredArgsConstructor
public class UsersAPI {

    private final Environment env;
    private final UsersService usersService;

    @PostMapping
    public ResponseEntity<UserCreationResponseCM> createUser(@Valid @RequestBody UserRegistrationCM userRegistrationCM) {
        final UserCreationResponseCM response = usersService.createUser(userRegistrationCM);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/port")
    public String portStatus() {
        return "Users service running on port: " + env.getProperty("local.server.port");
    }
}
