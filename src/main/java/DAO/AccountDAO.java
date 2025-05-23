package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;


public class AccountDAO {
    
    // inserts a new record into the Account table
    public Account insertAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        String sqlLine = "INSERT INTO account (username, password) VALUES (?, ?);";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlLine, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.executeUpdate();

            ResultSet assignedPKey = stmt.getGeneratedKeys();
            if (assignedPKey.next()) {
                // this means the insertion was successful and got back valid primary key
                int assignedID = assignedPKey.getInt(1);
                return new Account(assignedID, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // insertion failed
        return null;
    }

    // retrieves row record for the account with the given username
    public Account getAccountByUsername(String username) {
        Connection conn = ConnectionUtil.getConnection();
        String sqlLine = "SELECT * FROM account WHERE username = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlLine);
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                // this means an account record with the given username was found
                int account_id = result.getInt("account_id");
                String password = result.getString("password");
                return new Account(account_id, username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // no record with the username was found
        return null;
    }

    // retrives record of an account that has a matching username and password
    public Account findAccountWithCredentials(String username, String password) {
        Connection conn = ConnectionUtil.getConnection();
        String sqlLine = "SELECT * FROM account WHERE username = ? AND password = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlLine);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                // this means a matching account was successfully found
                int account_id = result.getInt("account_id");
                return new Account(account_id, username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // credentials are invalid, did not match an account
        return null;
    }

    // retrieves row record for the account with the given id
    public Account getAccountByID(int id) {
        Connection conn = ConnectionUtil.getConnection();
        String sqlLine = "SELECT * FROM account WHERE account_id = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlLine);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                // this means an account record with the given account id was found
                String username = result.getString("username");
                String password = result.getString("password");
                return new Account(id, username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // no record with the account id was found
        return null;
    }
}
