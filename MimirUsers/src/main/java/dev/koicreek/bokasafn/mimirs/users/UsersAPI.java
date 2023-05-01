package dev.koicreek.bokasafn.mimirs.users;

import dev.koicreek.bokasafn.mimirs.users.contracts.UserRegistrationCM;
import dev.koicreek.bokasafn.mimirs.users.contracts.response.GetUserResponseCM;
import dev.koicreek.bokasafn.mimirs.users.contracts.response.UserCreationResponseCM;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/port")
    public String portStatus() {
        return "Users service running on port: " + env.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<UserCreationResponseCM> createUser(@Valid @RequestBody UserRegistrationCM userRegistrationCM) {
        final UserCreationResponseCM response = usersService.createUser(userRegistrationCM);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserResponseCM> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(usersService.getUserByPublicId(userId));
    }
}
