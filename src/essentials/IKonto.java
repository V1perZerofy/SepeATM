package essentials;

public interface IKonto {
    void deposit(double amount);
    Boolean withdraw(double amount);
    void transfer(IKonto targetAccount, double amount);
    String getAccountNumber();
    void setAccountNumber(String accountNumber);
    double getBalance();
    void setBalance(double balance);
}
