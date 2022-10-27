package dev.koicreek.bokasafn.mimirs.users;

import dev.koicreek.bokasafn.mimirs.users.contracts.UserRegistrationCM;
import dev.koicreek.bokasafn.mimirs.users.contracts.UserCreationResponseCM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UsersServiceImpl implements UsersService {

    UsersDAO usersDAO;

    PasswordEncoder passwordEncoder;

    //#region Constructors -----------------------------------------------

    @Autowired
    public UsersServiceImpl(UsersDAO usersDAO,
                            PasswordEncoder bCryptPasswordEncoder) {
        this.usersDAO = usersDAO;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    //#endregion

    @Override
    public UserCreationResponseCM createUser(UserRegistrationCM userRequest) {
        UserEntity userEntity = new UserEntity(userRequest);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userRequest.getPassword()));
        this.usersDAO.save(userEntity);

        // System.out.printf("UUID: %s (%d)\n", userEntity.getPublicId(), userEntity.getPublicId().length());

        return new UserCreationResponseCM(userEntity);
    }

    @Override
    public UserRegistrationCM getUserInfoByUsername(String username) {
        UserEntity userEntity = usersDAO.findByUsername(username);

        if(userEntity == null) throw new UsernameNotFoundException(username);

        return new UserRegistrationCM(userEntity);
    }

    //#region SpringSecurity

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = usersDAO.findByUsername(username);

        if(user == null) throw new UsernameNotFoundException(username);

        return new User(user.getUsername(), user.getEncryptedPassword(), new ArrayList<>());
    }

    //#endregion

}
