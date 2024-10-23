package come.dawnlight.chronicle_dawnlight.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username; // 用户名
    private String password; // 加密后的密码
    private String email; // 邮箱，唯一
}
