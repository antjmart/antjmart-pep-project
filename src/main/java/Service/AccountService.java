package Service;

import DAO.AccountDAO;
import Model.Account;


public class AccountService {
    private AccountDAO accountDAO;

    // default no args constructor
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    // create service object from already constructed DAO object
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // method for creating a new account for a user
    public Account createAccount(Account account) {
        String username = account.getUsername();
        if (username.isEmpty() || account.getPassword().length() < 4)
            return null;  // failed conditions for username and/or password
        if (accountDAO.getAccountByUsername(username) != null)
            return null;  // username already exists
        
        return accountDAO.insertAccount(account);
    }

    // method for attempting a user login into the database
    public Account login(String username, String password) {
        return accountDAO.findAccountWithCredentials(username, password);
    }
}
