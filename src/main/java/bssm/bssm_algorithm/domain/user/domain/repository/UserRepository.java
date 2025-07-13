package bssm.bssm_algorithm.domain.user.domain.repository;

import bssm.bssm_algorithm.domain.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query("update UserEntity u set u.password = :password where u.username = :username")
    @Modifying
    void updatePasswordByUsername(String password, String username);
}
