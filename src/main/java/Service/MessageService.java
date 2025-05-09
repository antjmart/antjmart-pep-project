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

    // method for checking if a message with a given id exists, then deleting it
    public Message deleteMessageWithID(int id) {
        Message message = messageDAO.getMessageByID(id);
        if (message != null) {
            // if message exists, then delete it
            messageDAO.deleteMessageWithID(id);
        }
        return message;
    }

    // method for checking if message exists, updates message with valid new text
    public Message updateMessageWithID(int id, String newText) {
        if (newText.isEmpty() || newText.length() > 255)
            return null;  // invalid new message
        
        Message message = messageDAO.getMessageByID(id);
        if (message == null)
            return null;  // the message meant to be updated does not exist
        
        messageDAO.updateMessageWithID(id, newText);
        message.setMessage_text(newText);
        return message;
    }

    // method for getting list of messages posted by given user id
    public List<Message> getMessagesFromUserID(int account_id) {
        return messageDAO.getMessagesFromUserID(account_id);
    }
}
