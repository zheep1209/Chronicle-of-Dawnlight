package come.dawnlight.chronicle_dawnlight.service.impl;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import come.dawnlight.chronicle_dawnlight.mapper.TransactionCategoryMapper;
import come.dawnlight.chronicle_dawnlight.pojo.po.TransactionCategoryPO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.TransactionCategoryVO;
import come.dawnlight.chronicle_dawnlight.service.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCategoryServiceImpl implements TransactionCategoryService {
    @Autowired
    TransactionCategoryMapper transactionCategoryMapper;

    @Override
    public Result<List<TransactionCategoryVO>> getAllCategory() {
        return Result.success(transactionCategoryMapper.getAllCategory(BaseContext.getCurrentThreadId().toString()));
    }

    @Override
    public Result<String> delete(Long id) {
        int delete = transactionCategoryMapper.delete(id);
        if (delete == 0)
            return Result.error("删除失败");
        return Result.success("删除成功");
    }

    @Override
    public Result<String> create(String name) {
        TransactionCategoryPO transactionCategoryPO = new TransactionCategoryPO();
        transactionCategoryPO.setName(name);
        transactionCategoryPO.setUserId(BaseContext.getCurrentThreadId().toString());
        if (transactionCategoryMapper.create(transactionCategoryPO) == 0){
            return Result.error("创建失败");
        }
        return Result.success("创建成功");
    }
}
