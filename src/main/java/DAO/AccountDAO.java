package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account getAccountByUsername(String username) {
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "Select * FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
    public Account addAccount(Account account) {
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO account(username, password) VALUES(?, ?);";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                
                return account;
            }
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Account> getAllAccounts() {
        List<Account> allAccounts = new ArrayList<>();
        try (Connection con = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM account;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                allAccounts.add(account);
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return allAccounts;
    }
    public Account getAccountByID(int id) {
        try (Connection con = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
