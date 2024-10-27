package come.dawnlight.chronicle_dawnlight.service;

import come.dawnlight.chronicle_dawnlight.pojo.dto.ArticleDTO;
import come.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;

import java.util.List;

public interface ArticleService {
    void createArticle(ArticleDTO articleDTO, String username);

    void updateArticle(Long id, ArticleDTO articleDTO, String username);

    void deleteArticle(Long id, String username);

    List<ArticlePO> getArticlesByUser(String username);

    ArticlePO getArticleById(Long id, String username);
}
