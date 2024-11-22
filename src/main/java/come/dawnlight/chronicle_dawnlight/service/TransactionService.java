package come.dawnlight.chronicle_dawnlight.service;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.pojo.dto.TransactionDTO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.TransactionVO;

public interface TransactionService {
    Result<String> create(TransactionDTO transactionDTO);

    Result<String> update(TransactionVO transactionVO);

    Result<TransactionVO> get(String id);

    Result<String> delete(String id);
}
