package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    } 
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
    public List<Message> getAllMessages() {
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }
    public Message addMessage(Message message) {
        return messageDAO.insertMessage(message);
    }
    public Message deleteMessage(int messageID) {
        Message message = messageDAO.getMessageById(messageID);
        if (message != null) {
            messageDAO.deleteMessage(messageID);
            return message; // Return the deleted message
        }
        return null;
    }
    public Message getMessageByMessageId(int messageId) {
        return messageDAO.getMessageById(messageId);
    }
    public List<Message> getMessagesOfUser(int account_id) {
        return messageDAO.getMessagesOfUser(account_id);
    }
    public Message updateMessage(Message message) {
        
        return messageDAO.updateMessage(message);
    }
    
}
