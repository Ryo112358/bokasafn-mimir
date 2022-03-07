package dev.koicreek.bokasafn.mimir.users.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;


@Entity(name = "Users")
@Data
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private long id;

    @Column(name = "public_uuid", unique = true, nullable = false)
    private String publicId;

    @Column(length = 32, nullable = false)
    private String username;

    @Column(length = 32)
    private String displayName;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(length = 80, unique = true, nullable = false)
    private String email;

    //#region Constructors -----------------------------------------------

    public UserEntity() {
    }

    public UserEntity(UserCM user) {
        this.publicId = UUID.randomUUID().toString();
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
    }

    //#endRegion


}
