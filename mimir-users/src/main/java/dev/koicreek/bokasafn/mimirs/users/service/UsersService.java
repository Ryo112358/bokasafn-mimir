package dev.koicreek.bokasafn.mimirs.users.service;

import dev.koicreek.bokasafn.mimirs.users.model.UserCM;
import dev.koicreek.bokasafn.mimirs.users.model.UserCreationResponseCM;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    UserCreationResponseCM createUser(UserCM userCM);
    UserCM getUserInfoByUsername(String username);
}
