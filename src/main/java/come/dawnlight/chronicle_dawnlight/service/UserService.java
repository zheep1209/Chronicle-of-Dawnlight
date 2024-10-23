package come.dawnlight.chronicle_dawnlight.service;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.exception.BaseException;
import come.dawnlight.chronicle_dawnlight.pojo.dto.UserDTO;

public interface UserService {
    Integer registerUser(UserDTO userDTO, String code) throws BaseException;

    Result loginByPassword(String identifier, String password);

    Result loginByEmail(String identifier, String code) throws BaseException;

    void updateAvatar(String id, String avatar);

    void updateUserName(String id, String username) throws BaseException;

    void updatePassward(String id, String password,String code) throws BaseException;
}
