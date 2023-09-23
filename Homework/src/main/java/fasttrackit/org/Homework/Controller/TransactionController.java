package fasttrackit.org.Homework.Controller;


import fasttrackit.org.Homework.Domain.Transaction;
import fasttrackit.org.Homework.Domain.TransactionDTO;
import fasttrackit.org.Homework.Domain.TransactionPatchDTO;
import fasttrackit.org.Homework.Domain.Type;
import fasttrackit.org.Homework.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("transactions")
public class TransactionController {
    private TransactionService transactionService;


    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAllTransactions(@RequestParam(name = "type", required = false) Type type,
                                                @RequestParam(name = "minAmount", required = false, defaultValue = "-1") int minAmount,
                                                @RequestParam(name = "maxAmount", required = false, defaultValue = "-1") int maxAmount){
        return this.transactionService.getAllTransactions(type, minAmount, maxAmount);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable(name = "id") int id){
        return this.transactionService.getTransactionById(id);
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction){
        return this.transactionService.addTransaction(transaction);
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable(name = "id") int id, @RequestBody TransactionDTO transactionDTO){
        return this.transactionService.updateTransaction(id, transactionDTO);
    }

    @PatchMapping("/{id}")
    public Transaction patchTransaction(@PathVariable(name = "id") int id, @RequestBody TransactionPatchDTO transactionPatchDTO){
        return this.transactionService.patchTransaction(id, transactionPatchDTO.getProduct(), transactionPatchDTO.getAmount());
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable(name = "id") int id){
        this.transactionService.deleteTransaction(id);
    }

    @GetMapping("/reports/type")
    public Map<Type, Double> getReportType(){
        return this.transactionService.getReportType();
    }

    @GetMapping("/reports/product")
    public Map<String, Double> getReportProduct(@RequestParam(name = "product", required = true) String product){
        return this.transactionService.getReportProduct(product);
    }
}
