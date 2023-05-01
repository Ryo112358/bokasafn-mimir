package dev.koicreek.bokasafn.mimirs.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDAO extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByPublicId(String publicId);
}
