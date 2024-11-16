package come.dawnlight.chronicle_dawnlight.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String username; // 用户名
    private String email; // 邮箱，唯一
    private String avatar;
    private Integer role;
    private Integer articleCount;
    private Integer categoryCount;
}