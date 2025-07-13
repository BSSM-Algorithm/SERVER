package bssm.bssm_algorithm.domain.user.domain.exception;

import bssm.bssm_algorithm.global.exception.entity.DuplicateEntityException;

public class DuplicateUsernameException extends DuplicateEntityException {
    public DuplicateUsernameException(String username) {
        super("유저명이 " + username + "인 플레이어가 이미 존재합니다.");
    }
}
