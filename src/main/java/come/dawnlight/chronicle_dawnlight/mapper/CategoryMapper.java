package come.dawnlight.chronicle_dawnlight.mapper;

import come.dawnlight.chronicle_dawnlight.pojo.po.CategoryPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void insert(CategoryPO category);
    void update(CategoryPO category);
    void delete(Long id);
    List<CategoryPO> selectByUserId(String userId);
}
