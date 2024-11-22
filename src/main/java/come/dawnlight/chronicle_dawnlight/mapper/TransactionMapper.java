package come.dawnlight.chronicle_dawnlight.mapper;

import come.dawnlight.chronicle_dawnlight.pojo.po.TransactionPO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.TransactionVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper {
    int create(TransactionPO transactionPO);

    int update(TransactionPO transactionPO,String categoryName);

    TransactionVO get(String id, String string);

    int delete(String id);
}
