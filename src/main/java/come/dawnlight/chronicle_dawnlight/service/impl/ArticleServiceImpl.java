package come.dawnlight.chronicle_dawnlight.service.impl;

import come.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import come.dawnlight.chronicle_dawnlight.mapper.ArticleMapper;
import come.dawnlight.chronicle_dawnlight.pojo.dto.ArticleDTO;
import come.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.ArticleVO;
import come.dawnlight.chronicle_dawnlight.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public int createArticle(ArticleDTO articleDTO, String id) {
        ArticlePO article = new ArticlePO();
        article.setUserId(id);
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setIsPrivate(articleDTO.getIsPrivate());
        article.setCategoryId(articleDTO.getCategoryId()); // 设置分类ID
        article.setCreatedAt(LocalDateTime.now()); // 设置创建时间
        article.setUpdatedAt(LocalDateTime.now()); // 设置更新时间
        return articleMapper.insert(article);
    }

    @Override
    public void updateArticle(Long id, ArticleDTO articleDTO, String userId) {
        // 权限检查后更新文章
        ArticlePO article = articleMapper.selectByIdAndUserId(id, userId);
        if (article != null) {
            article.setTitle(articleDTO.getTitle());
            article.setContent(articleDTO.getContent());
            article.setIsPrivate(articleDTO.getIsPrivate());
            article.setCategoryId(articleDTO.getCategoryId());
            article.setCreatedAt(articleDTO.getCreatedAt());
            article.setUpdatedAt(articleDTO.getUpdatedAt());
            articleMapper.update(article);
        }
    }

    @Override
    public void deleteArticle(Long id, String userID) {
        ArticlePO article = articleMapper.selectByIdAndUserId(id, userID);
        if (article != null) {
            articleMapper.delete(id);  // 删除文章
        }
    }

    public void deleteArticles(List<Long> ids, String userID) {
        // 执行批量删除逻辑
        for (Long id : ids) {
            // 检查权限等逻辑
            // 删除文章
            articleMapper.delete(id);
        }
    }

    @Override
    public List<ArticlePO> getArticlesByUser(String userID) {
        return articleMapper.selectByUserId(userID);
    }

    @Override
    public List<ArticlePO> getArticlesByUserAndCategory(String userId, Long categoryId) {
        return articleMapper.selectByUserIdAndCategoryId(userId, categoryId);
    }

    @Override
    public ArticlePO getArticleById(Long id, String userID) {
        return articleMapper.selectByIdAndUserId(id, userID);// 文章不存在时返回空
    }

    @Override
    public List<ArticlePO> getArticlesByCategoryId(Long categoryId) {
        return articleMapper.selectByUserIdAndCategoryId(BaseContext.getCurrentThreadId().toString(),categoryId);
    }

    @Override
    public void updateCategoryByIds(List<Long> ids, Long categoryId) {
        articleMapper.updateCategoryIdByIds(ids, categoryId);
    }

    @Override
    public List<ArticlePO> getPublicArticles() {
        return articleMapper.publicArticle();
    }

    @Override
    public ArticleVO getPublicArticleByArticleID(Long id) {
        return articleMapper.publicArticleByArticleID(id);
    }
}
