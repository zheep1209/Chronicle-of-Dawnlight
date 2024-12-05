package com.dawnlight.chronicle_dawnlight.service;

import com.dawnlight.chronicle_dawnlight.common.Result;
import com.dawnlight.chronicle_dawnlight.common.exception.BaseException;
import com.dawnlight.chronicle_dawnlight.pojo.dto.UserDTO;

import java.util.UUID;

public interface UserService {
    Integer registerUser(UserDTO userDTO, String code) throws BaseException;

    Result loginByPassword(String identifier, String password);

    Result loginByEmail(String identifier, String code) throws BaseException;

    void updateAvatar(String id, String avatar);

    void updateUserName(String id, String username) throws BaseException;

    void updatePassward(String id, String password,String code) throws BaseException;

    Result getUser(UUID currentThreadId);
}
