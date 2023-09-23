package fasttrackit.org.Homework.Repository;

import fasttrackit.org.Homework.Domain.Transaction;
import fasttrackit.org.Homework.Domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


    List<Transaction> findByType(Type type);

    List<Transaction> findByAmountGreaterThan(double amount);

    List<Transaction> findByAmountLessThan(double amount);

    List<Transaction> findByTypeAndAmountGreaterThan(Type type, double amount);
    List<Transaction> findByTypeAndAmountLessThan(Type type, double amount);
    List<Transaction> findByAmountGreaterThanAndAmountLessThan(double minAmount, double maxAmount);
    List<Transaction> findByTypeAndAmountGreaterThanAndAmountLessThan(Type type, double minAmount, double maxAmount);


}
