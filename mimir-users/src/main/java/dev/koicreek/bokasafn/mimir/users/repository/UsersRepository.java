package dev.koicreek.bokasafn.mimir.users.repository;

import dev.koicreek.bokasafn.mimir.users.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
