package come.dawnlight.chronicle_dawnlight.service.impl;

import come.dawnlight.chronicle_dawnlight.mapper.ArticleMapper;
import come.dawnlight.chronicle_dawnlight.mapper.UserMapper;
import come.dawnlight.chronicle_dawnlight.pojo.dto.ArticleDTO;
import come.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;
import come.dawnlight.chronicle_dawnlight.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
private UserMapper userMapper;
@Autowired
private ArticleMapper articleMapper;
    @Override
    public void createArticle(ArticleDTO articleDTO, String id) {
        // 根据用户名找到用户，并保存文章
        ArticlePO article = new ArticlePO();
        article.setUserId(id);
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setIsPrivate(articleDTO.getIsPrivate());
        article.setCreatedAt(articleDTO.getCreatedAt());
        article.setUpdatedAt(articleDTO.getUpdatedAt());
        articleMapper.insert(article);
    }

    @Override
    public void updateArticle(Long id, ArticleDTO articleDTO, String userId) {
        // 权限检查后更新文章
        ArticlePO article = articleMapper.selectByIdAndUserId(id, userId);
        if (article != null) {
            article.setTitle(articleDTO.getTitle());
            article.setContent(articleDTO.getContent());
            article.setIsPrivate(articleDTO.getIsPrivate());
            article.setCreatedAt(articleDTO.getCreatedAt());
            article.setUpdatedAt(articleDTO.getUpdatedAt());
            log.info("数据检验：{}",articleDTO);
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

    @Override
    public List<ArticlePO> getArticlesByUser(String userID) {
        return articleMapper.selectByUserId(userID);
    }

    @Override
    public ArticlePO getArticleById(Long id, String userID) {
        ArticlePO article = articleMapper.selectByIdAndUserId(id, userID);
        log.info("111111111111:{}",article);
        if (article != null) {
            return article;
        }
        return null;  // 文章不存在时返回空
    }

}
