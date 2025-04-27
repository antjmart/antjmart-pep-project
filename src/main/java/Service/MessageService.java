package Service;

import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;
import java.util.List;


public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    // default no args constructor
    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    // method for verifying and creating a new message
    public Message createMessage(Message message) {
        String msgText = message.getMessage_text();
        if (msgText.isEmpty() || msgText.length() > 255)
            return null;  // invalid message
        if (accountDAO.getAccountByID(message.getPosted_by()) == null)
            return null;  // user posting the message does not exist
        
        return messageDAO.insertMessage(message);
    }

    // method for retrieving all messages from database
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // method for getting a message by its message_id
    public Message getMessageByID(int id) {
        return messageDAO.getMessageByID(id);
    }
}
