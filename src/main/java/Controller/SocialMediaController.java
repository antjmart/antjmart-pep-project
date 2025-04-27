package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;
    private ObjectMapper omap;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
        omap = new ObjectMapper();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::userRegistration);
        app.post("login", this::userLogin);
        app.post("messages", this::messageCreation);
        return app;
    }

    // handler that covers new user registration
    private void userRegistration(Context ctx) throws JsonProcessingException {
        Account registrationInfo = omap.readValue(ctx.body(), Account.class);
        // get back result of attempting to register the account
        Account newAccount = accountService.createAccount(registrationInfo);

        if (newAccount == null)
            ctx.status(400);  // failure
        else
            ctx.json(newAccount).status(200);  // success
    }

    // handler that covers user logins
    private void userLogin(Context ctx) throws JsonProcessingException {
        Account loginInfo = omap.readValue(ctx.body(), Account.class);
        // get back login result
        Account loggedIn = accountService.login(loginInfo.getUsername(), loginInfo.getPassword());

        if (loggedIn == null)
            ctx.status(401);  // failure
        else
            ctx.json(loggedIn).status(200);  // success
    }

    // handler that covers creating new messages
    private void messageCreation(Context ctx) throws JsonProcessingException {
        Message message = omap.readValue(ctx.body(), Message.class);
        // get back result from creating the message
        Message newMessage = messageService.createMessage(message);

        if (newMessage == null)
            ctx.status(400);  // failure
        else
            ctx.json(newMessage).status(200);  // success
    }
}