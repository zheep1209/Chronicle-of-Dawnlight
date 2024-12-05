package com.dawnlight.chronicle_dawnlight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.dawnlight.chronicle_dawnlight.pojo.dto.ArticleDTO;
import com.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;
import com.dawnlight.chronicle_dawnlight.pojo.vo.ArticleVO;
import com.dawnlight.chronicle_dawnlight.pojo.vo.PublicArticleVO;

import java.util.List;

public interface ArticleService {
    int createArticle(ArticleDTO articleDTO, String username);

    void updateArticle(Long id, ArticleDTO articleDTO, String username);

//    void deleteArticle(Long id, String username);

    void deleteArticles(List<Long> ids, String userID);

    List<ArticlePO> getArticlesByUser(String username) throws JsonProcessingException;

    List<ArticlePO> getArticlesByUserAndCategory(String userId, Long categoryId);

    ArticlePO getArticleById(Long id, String username);

//    List<ArticlePO> getArticlesByCategoryId(Long categoryId);

    void updateCategoryByIds(List<Long> ids, Long categoryId);

    List<PublicArticleVO> getPublicArticles();

    ArticleVO getPublicArticleByArticleID(Long id);
}
