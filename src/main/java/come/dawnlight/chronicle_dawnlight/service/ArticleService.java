package come.dawnlight.chronicle_dawnlight.service;

import come.dawnlight.chronicle_dawnlight.pojo.dto.ArticleDTO;
import come.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;

import java.util.List;

public interface ArticleService {
    int createArticle(ArticleDTO articleDTO, String username);

    void updateArticle(Long id, ArticleDTO articleDTO, String username);

    void deleteArticle(Long id, String username);

    void deleteArticles(List<Long> ids, String userID);

    List<ArticlePO> getArticlesByUser(String username);

    List<ArticlePO> getArticlesByUserAndCategory(String userId, Long categoryId);

    ArticlePO getArticleById(Long id, String username);

    List<ArticlePO> getArticlesByCategoryId(Long categoryId);

    void updateCategoryByIds(List<Long> ids, Long categoryId);
}
