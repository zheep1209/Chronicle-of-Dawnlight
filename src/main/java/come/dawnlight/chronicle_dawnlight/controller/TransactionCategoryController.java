package come.dawnlight.chronicle_dawnlight.controller;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.pojo.vo.TransactionCategoryVO;
import come.dawnlight.chronicle_dawnlight.service.TransactionCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactionCategory")
public class TransactionCategoryController {
    private static final Logger log = LoggerFactory.getLogger(TransactionCategoryController.class);
    @Autowired
    TransactionCategoryService transactionCategoryService;

    /**
     * 获取所有分类
     *
     * @return Return
     */
    @GetMapping("/getAllCategory")
    public Result<List<TransactionCategoryVO>> createArticle() {
        return transactionCategoryService.getAllCategory();
    }

    /**
     * 删除分类
     *
     * @param id 分类id
     * @return Result
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable("id") Long id) {
        return transactionCategoryService.delete(id);
    }

    /**
     * 新增分类
     *
     * @param name
     * @return
     */
    @PostMapping("/create")
    public Result<String> create(@RequestParam String name) {
        log.info("Creating new category: {}", name);
        if (name == null)
            return Result.error("分类名不能为空");
        return transactionCategoryService.create(name);
    }
}
