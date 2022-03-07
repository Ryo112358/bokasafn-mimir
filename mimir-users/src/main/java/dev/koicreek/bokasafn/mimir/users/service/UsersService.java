package dev.koicreek.bokasafn.mimir.users.service;

import dev.koicreek.bokasafn.mimir.users.model.UserCM;
import dev.koicreek.bokasafn.mimir.users.model.UserCreationResponseCM;

public interface UsersService {
    UserCreationResponseCM createUser(UserCM userCM);
}
