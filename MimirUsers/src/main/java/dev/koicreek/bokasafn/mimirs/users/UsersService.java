package dev.koicreek.bokasafn.mimirs.users;

import dev.koicreek.bokasafn.mimirs.users.contracts.UserRegistrationCM;
import dev.koicreek.bokasafn.mimirs.users.contracts.UserCreationResponseCM;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    UserCreationResponseCM createUser(UserRegistrationCM userRegistrationCM);
    UserRegistrationCM getUserInfoByUsername(String username);
}
