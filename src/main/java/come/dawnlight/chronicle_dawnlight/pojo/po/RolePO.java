package come.dawnlight.chronicle_dawnlight.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePO {
    private int id; // 角色ID，自增
    private String name; // 角色名称
}
