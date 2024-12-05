package com.dawnlight.chronicle_dawnlight.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import com.dawnlight.chronicle_dawnlight.common.utils.RedisUtil;
import com.dawnlight.chronicle_dawnlight.mapper.ArticleMapper;
import com.dawnlight.chronicle_dawnlight.pojo.dto.ArticleDTO;
import com.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;
import com.dawnlight.chronicle_dawnlight.pojo.vo.ArticleVO;
import com.dawnlight.chronicle_dawnlight.pojo.vo.PublicArticleVO;
import com.dawnlight.chronicle_dawnlight.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisUtil redisUtil;

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
        redisUtil.delete("selectByUserIdAndCategoryId" + articleDTO.getCategoryId());
        redisUtil.delete("getArticlesByUser:" + id);
        redisUtil.delete("selectByUserIdAndCategoryId:" + -1);
        if (!articleDTO.getIsPrivate()) {
            redisUtil.delete("publicArticle");
        }
        return articleMapper.insert(article);
    }

    @Override
    public void updateArticle(Long id, ArticleDTO articleDTO, String userId) {
            redisUtil.deleteFolder("selectByUserIdAndCategoryId");
            redisUtil.delete("selectByIdAndUserId:" + id);
            redisUtil.delete("getArticlesByUser:" + userId);
        // 权限检查后更新文章
        ArticlePO article = articleMapper.selectByIdAndUserId(id, userId);
        if (!articleDTO.getIsPrivate()) {
            redisUtil.delete("publicArticle");
            redisUtil.delete("publicArticle:" + id);
        }
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

//    @Override
//    public void deleteArticle(Long id, String userID) {
//        redisUtil.delete("selectByIdAndUserId" + id);
//        ArticlePO article = articleMapper.selectByIdAndUserId(id, userID);
//        if (article != null) {
//            articleMapper.delete(id);  // 删除文章
//            redisUtil.delete("publicArticle:" + id);
//        }
//    }

    public void deleteArticles(List<Long> ids, String userID) {
        // 执行批量删除逻辑
        redisUtil.deleteFolder("selectByUserIdAndCategoryId");
        for (Long id : ids) {
            // 检查权限等逻辑
            // 删除文章
            redisUtil.delete("getArticlesByUser:" + userID);
            redisUtil.delete("selectByIdAndUserId" + id);
            articleMapper.delete(id);
            redisUtil.delete("publicArticle:" + id);
        }
    }

    //    根据uid获取全部文章
    @Override
    public List<ArticlePO> getArticlesByUser(String userID) {
        TypeReference<List<ArticlePO>> typeReference = new TypeReference<>() {
        };
        List<ArticlePO> cachedArticles = redisUtil.get("getArticlesByUser:" + userID, typeReference);
        if (cachedArticles == null) {
            List<ArticlePO> articlePO = articleMapper.selectByUserId(userID);
            redisUtil.set("getArticlesByUser:" + userID, articlePO, 1440);
            return articlePO; // 直接返回新数据
        }
        return cachedArticles;
    }

    @Override
    public List<ArticlePO> getArticlesByUserAndCategory(String userId, Long categoryId) {
        TypeReference<List<ArticlePO>> typeReference = new TypeReference<>() {
        };
        List<ArticlePO> articlePOS = redisUtil.get("selectByUserIdAndCategoryId:" + categoryId, typeReference);
        if (articlePOS == null) {
            articlePOS = articleMapper.selectByUserIdAndCategoryId(userId, categoryId);
            redisUtil.set("selectByUserIdAndCategoryId:" + categoryId, articleMapper.selectByUserIdAndCategoryId(userId, categoryId), 1440);
            return articlePOS;
        }
        return articlePOS;
    }

    @Override
    public ArticlePO getArticleById(Long id, String userID) {
        TypeReference<ArticlePO> typeReference = new TypeReference<>() {
        };
        ArticlePO articlePO = redisUtil.get("selectByIdAndUserId:" + id, typeReference);
        if (articlePO == null) {
            articlePO = articleMapper.selectByIdAndUserId(id, userID);
            redisUtil.set("selectByIdAndUserId:" + id, articlePO, 1440);
            return articlePO;
        }
        return articlePO;
    }

//    //-----------------------
//    @Override
//    public List<ArticlePO> getArticlesByCategoryId(Long categoryId) {
//        return articleMapper.selectByUserIdAndCategoryId(BaseContext.getCurrentThreadId().toString(), categoryId);
//    }
//
//    //-----------------------
    @Override
    public void updateCategoryByIds(List<Long> ids, Long categoryId) {
        if (categoryId==0){
            redisUtil.delete("getArticlesByUser:" + BaseContext.getCurrentThreadId());
            return;
        }
        redisUtil.deleteFolder("selectByUserIdAndCategoryId");
        redisUtil.delete("getArticlesByUser:" + BaseContext.getCurrentThreadId());
        ids.forEach(id -> redisUtil.delete("selectByIdAndUserId" + id));
        articleMapper.updateCategoryIdByIds(ids, categoryId);
    }

    @Override
    public List<PublicArticleVO> getPublicArticles() {
        TypeReference<List<PublicArticleVO>> typeReference = new TypeReference<>() {
        };
        List<PublicArticleVO> publicArticleVOS = redisUtil.get("publicArticle", typeReference);
        if (publicArticleVOS == null) {
            publicArticleVOS = articleMapper.publicArticle();
            redisUtil.set("publicArticle", publicArticleVOS, 1440);
            return publicArticleVOS;
        }
        return publicArticleVOS;
    }

    @Override
    public ArticleVO getPublicArticleByArticleID(Long id) {
        TypeReference<ArticleVO> typeReference = new TypeReference<>() {
        };
        ArticleVO articleVO = redisUtil.get("publicArticle:" + id, typeReference);
        if (articleVO == null) {
            articleVO = articleMapper.publicArticleByArticleID(id);
            redisUtil.set("publicArticle:" + id, articleVO, 1440);
            return articleVO;
        }
        return articleVO;
    }
}
