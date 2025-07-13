package bssm.bssm_algorithm.domain.refreshtoken.domain.repository;

import bssm.bssm_algorithm.domain.refreshtoken.domain.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
