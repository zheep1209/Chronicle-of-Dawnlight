package com.dawnlight.chronicle_dawnlight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.dawnlight.chronicle_dawnlight.common.Result;
import com.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import com.dawnlight.chronicle_dawnlight.pojo.dto.ArticleDTO;
import com.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;
import com.dawnlight.chronicle_dawnlight.pojo.vo.ArticleVO;
import com.dawnlight.chronicle_dawnlight.pojo.vo.PublicArticleVO;
import com.dawnlight.chronicle_dawnlight.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 根据用户名查找用户ID，创建文章
     *
     * @param articleDTO 文章信息
     * @return 创建的文章ID
     */
    @PostMapping("/article/create")
    public Result<Integer> createArticle(@RequestBody ArticleDTO articleDTO) {
        String id = BaseContext.getCurrentThreadId().toString();
        int articleId = articleService.createArticle(articleDTO, id);
        return Result.success(articleId);
    }

    /**
     * 根据用户名和文章ID进行权限检查，并修改文章
     *
     * @param id         文章ID
     * @param articleDTO 文章信息
     * @return result
     */
    @PutMapping("/article/update/{id}")
    public Result<String> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        String userID = BaseContext.getCurrentThreadId().toString();
        articleService.updateArticle(id, articleDTO, userID);
        return Result.success("文章更新成功");
    }

    /**
     * 根据用户ID和文章类型ID查询并返回文章列表
     *
     * @param categoryId 文章类型ID
     * @return result
     */
    @GetMapping("/byUserAndCategory")
    public Result<List<ArticlePO>> getArticlesByUserAndCategory(@RequestParam Long categoryId) {
        String userID = BaseContext.getCurrentThreadId().toString();
        List<ArticlePO> articles = articleService.getArticlesByUserAndCategory(userID, categoryId);
        return Result.success(articles);
    }

    /**
     * 根据用户名和文章ID进行权限检查，并删除文章
     *
     * @param ids 文章ID集合
     * @return result
     */
    @DeleteMapping("/article/delete")
    public Result<String> deleteArticles(@RequestBody List<Long> ids) {
        String userID = BaseContext.getCurrentThreadId().toString();
        articleService.deleteArticles(ids, userID);
        return Result.success("文章删除成功");
    }

    /**
     * 根据用户名查找并返回用户的所有文章
     *
     * @return result
     */
    @GetMapping("/article/list")
    public Result<List<ArticlePO>> listArticles() throws JsonProcessingException {
        String userID = BaseContext.getCurrentThreadId().toString();
        List<ArticlePO> articles = articleService.getArticlesByUser(userID);
        return Result.success(articles);
    }

    /**
     * 根据用户名和文章ID进行权限检查，并返回文章详情
     *
     * @param id 文章ID
     * @return result
     */
    @GetMapping("/article/{id}")
    public Result<ArticlePO> getArticle(@PathVariable Long id) {
        String userID = BaseContext.getCurrentThreadId().toString();
        ArticlePO article = articleService.getArticleById(id, userID);
        return Result.success(article);
    }

    /**
     * 获取全部公开文章
     *
     * @return 文章列表
     */
    @GetMapping("/publicArticle")
    public Result<List<PublicArticleVO>> getPublicArticle() {
        List<PublicArticleVO> articles = articleService.getPublicArticles();
        return Result.success(articles);
    }

    /**
     * @param id 文章ID
     * @return result
     */
    @GetMapping("/publicArticleByArticleID/{id}")
    public Result<ArticleVO> getPublicArticleByArticleID(@PathVariable Long id) {
        ArticleVO articles = articleService.getPublicArticleByArticleID(id);
        return Result.success(articles);
    }

    /**
     * 根据文章ID列表，批量修改文件的分类属性
     *
     * @param ids        文章ID集合
     * @param categoryId 分类ID
     * @return result
     */
    @PutMapping("/updateCategoryByIds")
    public Result<String> updateCategoryByIds(@RequestParam List<Long> ids, @RequestParam(value = "categoryId", required = false) Long categoryId) {
        articleService.updateCategoryByIds(ids, categoryId);
        return Result.success("分类更新成功");
    }
}
