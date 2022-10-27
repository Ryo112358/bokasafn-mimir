package dev.koicreek.bokasafn.mimirs.users.contracts;

import dev.koicreek.bokasafn.mimirs.users.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class UserRegistrationCM {

    /** User UUID */
    private String publicId;

    @NotBlank
    @Size(min=4, max=32)
    private String username;

    private String displayName;

    @NotBlank
    @Size(min=8, max=32)
    private String password;

    @NotNull
    @Email
    private String email;

    //#region Constructors -----------------------------------------------

    public UserRegistrationCM(UserEntity user) {
        this.publicId = user.getPublicId();
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
    }

    //#endregion
}
