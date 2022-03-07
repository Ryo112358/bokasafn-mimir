package dev.koicreek.bokasafn.mimir.users.service;

import dev.koicreek.bokasafn.mimir.users.model.UserCM;
import dev.koicreek.bokasafn.mimir.users.model.UserEntity;
import dev.koicreek.bokasafn.mimir.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserCM createUser(UserCM userRequest) {
        UserEntity userEntity = new UserEntity(userRequest);
        userEntity.setEncryptedPassword("encrypted?");
        this.usersRepository.save(userEntity);

        userRequest.setUuid(userEntity.getUuid());
        userRequest.setPassword(null);
        return userRequest;
    }
}
