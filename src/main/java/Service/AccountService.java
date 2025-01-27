package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;
    public AccountService() {
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
    public Account getAccountById(int id) {
        return accountDAO.getAccountByID(id);
    }
    public Account verifyAccountLogin(Account account) {
        String usernameToCheck = account.getUsername();
        String passwordToCheck = account.getPassword();
        Account loggedInAccount = accountDAO.getAccountByUsername(usernameToCheck);
        if (loggedInAccount == null) {
            return null;
        }
        if (!loggedInAccount.getPassword().equals(passwordToCheck)) {
            return null;
        }
        return loggedInAccount;
    }
    public Account addAccount(Account account) {
        if (account.username.isBlank()) {
            return null;
        }
        if (account.password.length() < 5) {
            return null;
        }
        Account accountCheck = accountDAO.getAccountByUsername(account.username);
        if (accountCheck != null) {
            return null;
        }
        Account accountAdded = accountDAO.addAccount(account);
        Account actWithId = accountDAO.getAccountByUsername(accountAdded.getUsername());
        return actWithId;
    }

}
