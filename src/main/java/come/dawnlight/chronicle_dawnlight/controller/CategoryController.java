package come.dawnlight.chronicle_dawnlight.controller;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import come.dawnlight.chronicle_dawnlight.pojo.dto.CategoryDTO;
import come.dawnlight.chronicle_dawnlight.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")  // 基本路径为 "/categories"
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 创建分类
     * URL: /categories (POST)
     * 请求体示例:
     * {
     *     "name": "你的分类名称"
     * }
     * 响应:
     * 成功时返回 "分类创建成功"
     */
    @PostMapping
    public Result createCategory(@RequestBody CategoryDTO categoryDTO) {
        String userId = BaseContext.getCurrentThreadId().toString();  // 从 BaseContext 获取当前用户 ID
        categoryService.createCategory(categoryDTO, userId);
        return Result.success("分类创建成功");
    }

    /**
     * 更新分类
     * URL: /categories (PUT)
     * 请求体示例:
     * {
     *     "id": 分类ID,
     *     "name": "更新后的分类名称"
     * }
     * 响应:
     * 成功时返回 "分类更新成功"
     */
    @PutMapping
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return Result.success("分类更新成功");
    }

    /**
     * 删除分类
     * URL: /categories/{id} (DELETE)
     * 路径参数: 分类ID (id)
     * 示例: /categories/1
     * 响应:
     * 成功时返回 "分类删除成功"
     */
    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable Long id) {
        String userId = BaseContext.getCurrentThreadId().toString();  // 从 BaseContext 获取当前用户 ID
        categoryService.deleteCategory(id, userId);
        return Result.success("分类删除成功");
    }
    /**
     * 获取当前用户的所有分类
     * URL: /categories (GET)
     * 请求参数: 无
     * 响应: 返回分类列表的 JSON 数组
     */
    @GetMapping
    public Result getCategories() {
        String userId = BaseContext.getCurrentThreadId().toString();  // 从 BaseContext 获取当前用户 ID
        List<CategoryDTO> categories = categoryService.getCategoriesByUser(userId);
        return Result.success(categories);
    }
}
