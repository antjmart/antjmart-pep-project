package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;

    public SocialMediaController() {
        accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::userRegistration);
        return app;
    }

    // handler that covers new user registration
    private void userRegistration(Context ctx) throws JsonProcessingException {
        ObjectMapper omap = new ObjectMapper();
        Account registrationInfo = omap.readValue(ctx.body(), Account.class);
        // get back result of attempting to register the account
        Account newAccount = accountService.createAccount(registrationInfo);

        if (newAccount == null)
            ctx.status(400);  // failure
        else
            ctx.json(newAccount).status(200);  // success
    }

}