package come.dawnlight.chronicle_dawnlight.controller;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import come.dawnlight.chronicle_dawnlight.pojo.dto.ArticleDTO;
import come.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;
import come.dawnlight.chronicle_dawnlight.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class ArticleController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private ArticleService articleService;

    /**
     * 根据用户名查找用户ID，保存文章
     *
     * @param articleDTO
     * @return
     */
    @PostMapping("/article/create")
    public Result createArticle(@RequestBody ArticleDTO articleDTO) {
        String id = BaseContext.getCurrentThreadId().toString();
        articleService.createArticle(articleDTO, id);
        return Result.success("文章创建成功");
    }


    /**
     * 根据用户名和文章ID进行权限检查，并修改文章
     *
     * @param id
     * @param articleDTO
     * @return
     */
    @PutMapping("/article/update/{id}")
    public Result updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        log.info("提交的内容{}", articleDTO);
        String userID = BaseContext.getCurrentThreadId().toString();
        articleService.updateArticle(id, articleDTO, userID);
        return Result.success("文章更新成功");
    }

    /**
     * 根据用户ID和文章类型ID查询并返回文章列表
     * @param categoryId
     * @return
     */
    @GetMapping("/byUserAndCategory")
    public Result getArticlesByUserAndCategory(@RequestParam Long categoryId) {
        String userID = BaseContext.getCurrentThreadId().toString();
        List<ArticlePO> articles = articleService.getArticlesByUserAndCategory(userID, categoryId);
        return Result.success(articles);
    }
    /**
     * 根据用户名和文章ID进行权限检查，并删除文章
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/article/delete")
    public Result deleteArticles(@RequestBody List<Long> ids) {
        String userID = BaseContext.getCurrentThreadId().toString();
        articleService.deleteArticles(ids, userID);
        return Result.success("文章删除成功");
    }

    /**
     * 根据用户名查找并返回用户的所有文章
     *
     * @return
     */
    @GetMapping("/article/list")
    public Result listArticles() {
        String userID = BaseContext.getCurrentThreadId().toString();
        List<ArticlePO> articles = articleService.getArticlesByUser(userID);
        return Result.success(articles);
    }

    /**
     * 根据用户名和文章ID进行权限检查，并返回文章详情
     *
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    public Result getArticle(@PathVariable Long id) {
        String userID = BaseContext.getCurrentThreadId().toString();
        ArticlePO article = articleService.getArticleById(id, userID);
        log.info("返回列表：{}", article);
        return Result.success(article);
    }

    /**
     * 根据分类ID获取文章列表
     * @param categoryId
     * @return
     */
    @GetMapping("/articlesByCategoryId")
    public Result getArticlesByCategoryId(@RequestParam Long categoryId) {
        List<ArticlePO> articles = articleService.getArticlesByCategoryId(categoryId);
        return Result.success(articles);
    }

    /**
     * 根据文章ID列表，批量修改文件的分类属性
     * @param ids
     * @param categoryId
     * @return
     */
    @PutMapping("/updateCategoryByIds")
    public Result updateCategoryByIds(@RequestParam List<Long> ids, @RequestParam Long categoryId) {
        articleService.updateCategoryByIds(ids, categoryId);
        return Result.success("分类更新成功");
    }
}
