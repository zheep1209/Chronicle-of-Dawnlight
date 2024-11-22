package come.dawnlight.chronicle_dawnlight.service.impl;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import come.dawnlight.chronicle_dawnlight.mapper.TransactionMapper;
import come.dawnlight.chronicle_dawnlight.pojo.dto.TransactionDTO;
import come.dawnlight.chronicle_dawnlight.pojo.po.TransactionPO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.TransactionVO;
import come.dawnlight.chronicle_dawnlight.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;
    @Override
    public Result<String> create(TransactionDTO transactionDTO) {
        TransactionPO transactionPO = new TransactionPO();
        transactionPO.setId(UUID.randomUUID().toString());
        transactionPO.setUserId(transactionDTO.getUserId());
        transactionPO.setAmount(transactionDTO.getAmount());
        transactionPO.setCategoryId(transactionDTO.getCategoryId());
        transactionPO.setType(transactionDTO.getType());
        transactionPO.setDescription(transactionDTO.getDescription());
        transactionPO.setDate(transactionDTO.getDate());
        int result = transactionMapper.create(transactionPO);
        if (result == 0)
            return Result.error("添加失败");
        return Result.success("添加成功");
    }

    @Override
    public Result<String> update(TransactionVO transactionVO) {
        TransactionPO transactionPO = new TransactionPO();
        transactionPO.setId(transactionVO.getId());
        transactionPO.setAmount(transactionVO.getAmount());
        transactionPO.setType(transactionVO.getType());
        //分类ID使用数据库查询分类名
        transactionPO.setDescription(transactionVO.getDescription());
        transactionPO.setDate(transactionVO.getDate());
        int result = transactionMapper.update(transactionPO,transactionVO.getCategory());
        return Result.success("修改成功");
    }

    @Override
    public Result<TransactionVO> get(String id) {
        TransactionVO transactionVO = transactionMapper.get(id, BaseContext.getCurrentThreadId().toString());
        if (transactionVO==null){
            return Result.error("找不到该交易，或你不是当前交易的用户");
        }
        return Result.success(transactionVO);
    }

    @Override
    public Result<String> delete(String id) {
        int result = transactionMapper.delete(id);
        return result==0?Result.error("删除失败"):Result.success("删除成功");
    }
}
