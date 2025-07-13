package bssm.bssm_algorithm.domain.user.domain.exception;

import bssm.bssm_algorithm.global.exception.entity.EntityNotFoundException;

public class UsernameNotFoundException extends EntityNotFoundException {
    public UsernameNotFoundException(String username) {
        super("유저명이 " + username + "인 플레이어가 존재하지 않습니다.");
    }
}
