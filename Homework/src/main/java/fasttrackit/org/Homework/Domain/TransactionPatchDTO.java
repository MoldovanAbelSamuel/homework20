package fasttrackit.org.Homework.Domain;

public class TransactionPatchDTO {
    private String product;
    private double amount;

    public TransactionPatchDTO(String product, double amount) {
        this.product = product;
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
