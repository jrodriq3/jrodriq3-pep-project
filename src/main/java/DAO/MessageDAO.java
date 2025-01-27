package DAO;

import java.sql.Statement;

import static org.mockito.Mockito.calls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public boolean deleteMessage(int messageId) {
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            preparedStatement.executeUpdate();
            return true;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }
    public Message updateMessage(Message message) {
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setLong(2, message.getMessage_id());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return message;
            } 


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("**********************************");
            System.out.println("**********************************");
            System.out.println("**********************************");
            System.out.println("**********************************");
            System.out.println("**********************************");
            System.out.println("**********************************");
            System.out.println("**********************************");
        }
        return null;
    }
    public Message insertMessage(Message message) {
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?);";
            PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                message.setMessage_id(resultSet.getInt(1));
                return message;
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getMessagesOfUser(int account_id) {
        List<Message> messages = new ArrayList<>();
        try (Connection con = ConnectionUtil.getConnection()) {
            
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);

            }
            return messages;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public Message getMessageById(int id) {
        try (Connection con = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public List<Message> getAllMessages() {
        List<Message> allMessages = new ArrayList<>();
        try (Connection con = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                allMessages.add(message);
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return allMessages;
    }
}
