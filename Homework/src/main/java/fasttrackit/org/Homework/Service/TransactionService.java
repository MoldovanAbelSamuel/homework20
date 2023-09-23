package fasttrackit.org.Homework.Service;

import fasttrackit.org.Homework.Domain.Transaction;
import fasttrackit.org.Homework.Domain.TransactionDTO;
import fasttrackit.org.Homework.Domain.Type;
import fasttrackit.org.Homework.Exceptions.EntityDoesntExistException;
import fasttrackit.org.Homework.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;


    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions(Type type, double minAmount, double maxAmount){
        if (type == null && minAmount == -1 && maxAmount == -1){
            return this.transactionRepository.findAll();
        }
        if (type != null){
            if(minAmount != -1){
                if(maxAmount != -1){
                    return this.transactionRepository.findByTypeAndAmountGreaterThanAndAmountLessThan(type, minAmount, maxAmount); // byTypeAndMinAndMax
                }
                return this.transactionRepository.findByTypeAndAmountGreaterThan(type, minAmount); // byTypeAndMin
            }
            if(maxAmount != -1){
                return this.transactionRepository.findByTypeAndAmountLessThan(type, maxAmount); // byTypeAndMax
            }
            return this.transactionRepository.findByType(type); // byType
        }
        if (minAmount != -1){
            if (maxAmount != -1){
                return this.transactionRepository.findByAmountGreaterThanAndAmountLessThan(minAmount, maxAmount); // byMinAndMax
            }
            return this.transactionRepository.findByAmountGreaterThan(minAmount); // byMinAmount
        }
        return this.transactionRepository.findByAmountLessThan(maxAmount); // byMaxAmount
    }

    public Transaction getTransactionById(int id){
        if(this.transactionRepository.findById(id).isPresent()){
            return this.transactionRepository.findById(id).get();
        }
        throw new EntityDoesntExistException("Transaction with the given id not found", id);
    }

    public Transaction addTransaction(Transaction transaction){
        return this.transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(int id, TransactionDTO transaction){
        Optional<Transaction> updateTransaction = this.transactionRepository.findById(id);
        if(updateTransaction.isPresent()){
            Transaction currentTransaction = updateTransaction.get();
            currentTransaction.setProduct(transaction.getProduct());
            currentTransaction.setAmount(transaction.getAmount());
            currentTransaction.setType(transaction.getType());
            this.transactionRepository.save(currentTransaction);
            return currentTransaction;
        }
        return this.transactionRepository.save(new Transaction(transaction.getProduct(), transaction.getType(), transaction.getAmount()));
    }

    public Transaction patchTransaction(int id, String product, double amount){
        Optional<Transaction> updateTransaction = this.transactionRepository.findById(id);
        if(updateTransaction.isPresent()){
            Transaction currentTransaction = updateTransaction.get();
            if(product == null){
                if (amount != -1){
                    currentTransaction.setAmount(amount);
                    this.transactionRepository.save(currentTransaction);
                    return currentTransaction;
                }
                this.transactionRepository.save(currentTransaction);
                return currentTransaction;
            }

            currentTransaction.setProduct(product);
            if (amount == -1){
                this.transactionRepository.save(currentTransaction);
                return currentTransaction;
            }
            currentTransaction.setAmount(amount);
            this.transactionRepository.save(currentTransaction);
            return currentTransaction;
        }
        throw new EntityDoesntExistException("Transaction with the given id not found", id);
    }

    public void deleteTransaction(int id){
        Optional<Transaction> deleteTransaction = this.transactionRepository.findById(id);
        if(deleteTransaction.isPresent()){
            this.transactionRepository.delete(deleteTransaction.get());
        } else {
            throw new EntityDoesntExistException("Transaction with the given id not found", id);
        }
    }

    public Map<Type, Double> getReportType(){
        double sumBuy = 0;
        double sumSell = 0;
        for(Transaction transaction : this.transactionRepository.findAll()){
            if(transaction.getType() == Type.BUY){
                sumBuy += transaction.getAmount();
            } else {
                sumSell += transaction.getAmount();
            }
        }
        Map<Type, Double> reportType = new HashMap<>();
        reportType.put(Type.SELL, sumSell);
        reportType.put(Type.BUY, sumBuy);
        return reportType;
    }

    public Map<String, Double> getReportProduct(String product){
        double sum = 0;
        for(Transaction transaction : this.transactionRepository.findAll()) {
            if (transaction.getProduct().equals(product)) {
                sum += transaction.getAmount();
            }
        }
        Map<String, Double> reportProduct = new HashMap<>();
        reportProduct.put(product, sum);
        return reportProduct;
    }
}
