package come.dawnlight.chronicle_dawnlight.service;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.pojo.vo.TransactionCategoryVO;

import java.util.List;

public interface TransactionCategoryService {

    Result<List<TransactionCategoryVO>> getAllCategory();

    Result<String> delete(Long id);

    Result<String> create(String name);
}
