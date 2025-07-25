package essentials;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Account> accounts = new ArrayList<Account>();
    private String name;
    private Customer[] customers;

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public void addAccount(Account account) {
        if (account != null && !this.getAccounts().contains(account)) {
            this.accounts.add(account);
        }
    }

    public void removeAccount(Account account) {
        if (account != null && this.getAccounts().contains(account)) {
            this.accounts.remove(account);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bank (String name) {
        this.setName(name);
    }

    public Account findAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "accounts=" + accounts +
                ", name='" + name + '\'' +
                '}';
    }

}
