package come.dawnlight.chronicle_dawnlight.mapper;

import come.dawnlight.chronicle_dawnlight.pojo.po.TransactionCategoryPO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.TransactionCategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransactionCategoryMapper {

    void batchInsert(List<TransactionCategoryPO> categories);

    List<TransactionCategoryVO> getAllCategory(String userId);

    int delete(Long id);

    int create(TransactionCategoryPO transactionCategoryPO);
}
