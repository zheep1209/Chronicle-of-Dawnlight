package come.dawnlight.chronicle_dawnlight.mapper;

import come.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {

    // 插入新文章
    void insert(ArticlePO article);

    // 更新文章
    void update(ArticlePO article);

    // 根据文章ID和用户ID查询文章（确保用户只能操作自己的文章）
    ArticlePO selectByIdAndUserId(@Param("id") Long id, @Param("userId") String userId);

    // 删除文章
    void delete(@Param("id") Long id);

    // 根据用户ID查询所有文章
    List<ArticlePO> selectByUserId(@Param("userId") String userId);
}
