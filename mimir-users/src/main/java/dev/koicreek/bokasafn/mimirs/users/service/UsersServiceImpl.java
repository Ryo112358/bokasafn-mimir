package dev.koicreek.bokasafn.mimirs.users.service;

import dev.koicreek.bokasafn.mimirs.users.model.UserCM;
import dev.koicreek.bokasafn.mimirs.users.model.UserCreationResponseCM;
import dev.koicreek.bokasafn.mimirs.users.model.UserEntity;
import dev.koicreek.bokasafn.mimirs.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UsersServiceImpl implements UsersService {

    UsersRepository usersRepository;

    PasswordEncoder passwordEncoder;

    //#region Constructors -----------------------------------------------

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,
                            PasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    //#endRegion

    @Override
    public UserCreationResponseCM createUser(UserCM userRequest) {
        UserEntity userEntity = new UserEntity(userRequest);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userRequest.getPassword()));
        this.usersRepository.save(userEntity);

        // System.out.printf("UUID: %s (%d)\n", userEntity.getPublicId(), userEntity.getPublicId().length());

        return new UserCreationResponseCM(userEntity);
    }

    @Override
    public UserCM getUserInfoByUsername(String username) {
        UserEntity userEntity = usersRepository.findByUsername(username);

        if(userEntity == null) throw new UsernameNotFoundException(username);

        return new UserCM(userEntity);
    }

    //#region SpringSecurity

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = usersRepository.findByUsername(username);

        if(user == null) throw new UsernameNotFoundException(username);

        return new User(user.getUsername(), user.getEncryptedPassword(), new ArrayList<>());
    }

    //#endRegion

}
