package come.dawnlight.chronicle_dawnlight.mapper;

import come.dawnlight.chronicle_dawnlight.pojo.po.RolePO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {
    int insertRole(RolePO role);
    int deleteRoleById(Integer roleId);
    RolePO findRoleById(Integer roleId);
    List<RolePO> findAllRoles();
    int updateRole(RolePO role);
}
