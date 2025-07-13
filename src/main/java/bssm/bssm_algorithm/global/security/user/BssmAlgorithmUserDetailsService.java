package bssm.bssm_algorithm.global.security.user;

import bssm.bssm_algorithm.domain.user.domain.entity.UserEntity;
import bssm.bssm_algorithm.domain.user.domain.exception.UsernameNotFoundException;
import bssm.bssm_algorithm.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BssmAlgorithmUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public BssmAlgorithmUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new BssmAlgorithmUserDetails(userEntity);
    }
}
