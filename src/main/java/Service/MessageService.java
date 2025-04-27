package Service;

import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;

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
}
