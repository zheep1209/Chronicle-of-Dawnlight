package come.dawnlight.chronicle_dawnlight;

import come.dawnlight.chronicle_dawnlight.mapper.RoleMapper;
import come.dawnlight.chronicle_dawnlight.mapper.UserMapper;
import come.dawnlight.chronicle_dawnlight.pojo.po.RolePO;
import come.dawnlight.chronicle_dawnlight.pojo.po.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChronicleDawnlightApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    void emailTest() {

    }
}
