package dev.koicreek.bokasafn.mimirs.users;

import dev.koicreek.bokasafn.mimirs.users.contracts.UserRegistrationCM;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import static dev.koicreek.bokasafn.mimirs.users.StringifyUtil.wrap;

@Entity(name = "Users")
@Data
public class UserEntity implements UserDetails, Serializable {

    @Id
    @GeneratedValue(generator="userIdSeq")
    @SequenceGenerator(name="userIdSeq", initialValue = 1_000_000_001, allocationSize = 5)
    @Column(name = "userId")
    private long id;

    @Column(name = "publicUuid", length = 36, nullable = false, unique = true)
    private String publicId;

    @Column(length = 32, nullable = false)
    private String username;

    @Column(length = 32)
    private String displayName;

    @Column(name="password", nullable = false)
    private String encryptedPassword;

    @Column(length = 64, nullable = false, unique = true)
    private String email;

    //#region Constructors -----------------------------------------------

    public UserEntity() {
    }

    public UserEntity(UserRegistrationCM user) {
        this.publicId = UUID.randomUUID().toString();
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
    }

    //#endRegion

    //#region UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of();
    }

    @Override
    public String getPassword() {
        return this.encryptedPassword;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //#endregion

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserEntity {");

        sb.append(String.format("\n\tid: %s", this.id));
        sb.append(String.format(",\n\tpublicUuid: %s", wrap(this.publicId)));
        sb.append(String.format(",\n\tusername: %s", wrap(this.username)));
        sb.append(String.format(",\n\temail: %s", wrap(this.email)));
        sb.append(String.format(",\n\tpassword: %s", wrap(this.encryptedPassword)));
        sb.append(String.format(",\n\tdisplayName: %s", wrap(this.displayName)));
        sb.append("\n}");

        return sb.toString();
    }

}
