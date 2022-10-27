package dev.koicreek.bokasafn.mimirs.users.contracts;

import dev.koicreek.bokasafn.mimirs.users.UserEntity;
import lombok.Data;


@Data
public class UserCreationResponseCM {
    private String publicId;
    private String username;
    private String displayName;
    private String email;

    //#region Constructors -----------------------------------------------

    public UserCreationResponseCM(UserEntity user) {
        this.publicId = user.getPublicId();
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
    }

    //#endregion
}
