package come.dawnlight.chronicle_dawnlight.controller;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.pojo.dto.TransactionDTO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.TransactionVO;
import come.dawnlight.chronicle_dawnlight.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    /**
     * 创建交易记录
     *
     * @param transactionDTO 交易记录
     * @return Result
     */
    @PostMapping("/create")
    public Result<String> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.create(transactionDTO);
    }

    /**
     * @param transactionVO 交易记录
     * @return Result
     */
    @PutMapping("/update")
    public Result<String> updateTransaction(@RequestBody TransactionVO transactionVO) {
        return transactionService.update(transactionVO);
    }

    @GetMapping("/get")
    public Result<TransactionVO> getTransaction(@RequestParam(value = "id") String id) {
        return transactionService.get(id);
    }

    @DeleteMapping("/delete")
    public Result deleteTransaction(@RequestParam(value = "id") String id) {
        return transactionService.delete(id);
    }
}
