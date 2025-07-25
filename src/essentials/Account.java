package essentials;

public class Account implements IKonto {
    private double balance;
    private String accountNumber;
    private Customer owner;

    @Override
    public void deposit(double amount) {
        if(amount > 0) {
            this.setBalance(this.getBalance() + amount);
        }
    }

    @Override
    public Boolean withdraw(double amount) {
        if (amount > 0 && amount <= this.getBalance()) {
            this.setBalance(this.getBalance() - amount);
            return true;
        }
        return false;
    }

    @Override
    public void transfer(IKonto targetAccount, double amount) {
        if(this.withdraw(amount)) {
            targetAccount.deposit(amount);
        }
    }

    public Account(String accountNumber, Customer owner) {
        this.setAccountNumber(accountNumber);
        this.setOwner(owner);
        this.setBalance(0.0);
    }

    @Override
    public String getAccountNumber() {
        return this.accountNumber;
    }

    @Override
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getOwner() {
        return this.owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", accountNumber='" + accountNumber + '\'' +
                ", owner=" + owner +
                '}';
    }

}
