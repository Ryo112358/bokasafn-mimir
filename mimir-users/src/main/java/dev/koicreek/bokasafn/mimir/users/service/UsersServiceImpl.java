package dev.koicreek.bokasafn.mimir.users.service;

import dev.koicreek.bokasafn.mimir.users.model.UserCM;
import dev.koicreek.bokasafn.mimir.users.model.UserCreationResponseCM;
import dev.koicreek.bokasafn.mimir.users.model.UserEntity;
import dev.koicreek.bokasafn.mimir.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    UsersRepository usersRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    //#region Constructors -----------------------------------------------

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //#endRegion

    @Override
    public UserCreationResponseCM createUser(UserCM userRequest) {
        UserEntity userEntity = new UserEntity(userRequest);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        this.usersRepository.save(userEntity);

        // System.out.printf("UUID: %s (%d)\n", userEntity.getPublicId(), userEntity.getPublicId().length());

        return new UserCreationResponseCM(userEntity);
    }
}
