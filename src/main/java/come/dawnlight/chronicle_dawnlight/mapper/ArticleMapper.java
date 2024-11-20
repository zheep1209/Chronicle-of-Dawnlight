package come.dawnlight.chronicle_dawnlight.mapper;

import come.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.ArticleVO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.PublicArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface ArticleMapper {
    // 插入新文章
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ArticlePO article);

    // 更新文章
    void update(ArticlePO article);

    // 根据文章ID和用户ID查询文章（确保用户只能操作自己的文章）
    ArticlePO selectByIdAndUserId(@Param("id") Long id, @Param("userId") String userId);

    // 根据用户 ID 和分类 ID 查询文章列表
    List<ArticlePO> selectByUserIdAndCategoryId(@Param("userId") String userId, @Param("categoryId") Long categoryId);

    // 删除文章
    void delete(@Param("id") Long id);

    // 根据用户ID查询所有文章
    List<ArticlePO> selectByUserId(@Param("userId") String userId);

    // 根据分类 ID 查询文章（弃用）
    List<ArticlePO> selectByCategoryId(Long categoryId);

    // 根据文章ID列表，批量修改文件的分类属性
    void updateCategoryIdByIds(@Param("ids") List<Long> ids, @Param("categoryId") Long categoryId);
    // 获取全部公开文章
    List<PublicArticleVO> publicArticle();

    ArticleVO publicArticleByArticleID(Long id);
}
