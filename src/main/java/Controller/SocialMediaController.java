package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;
    ObjectMapper mapper;


    public SocialMediaController() {
        this.messageService = new MessageService();
        this.accountService = new AccountService();
        this.mapper = new ObjectMapper();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages", this::getAllMessages);
        app.get("/accounts", this::getAllAccounts);
        app.post("/messages", this::createMessage);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.get("/messages/{message_id}", this::getMessageByMessageId);
        app.get("/accounts/{account_id}/messages", this::getMessagesOfUser);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.post("/register", this::registerAccount);
        app.post("/login", this::loginToSite);


        return app;
    }
    // List<Message> messages = messageService.getAllMessages();

    //     ctx.json(messages).status(200);
    private void getAllAccounts(Context ctx) {
        List<Account> accounts = accountService.getAllAccounts();
        ctx.json(accounts).status(200);

    }
    private void loginToSite(Context ctx) throws JsonMappingException, JsonProcessingException {
        
        Account account = mapper.readValue(ctx.body().toString(), Account.class);
        Account verifiedAccount = accountService.verifyAccountLogin(account);
        if (verifiedAccount == null) {
            ctx.status(401);
        } else {
            ctx.json(verifiedAccount).status(200);
        }
    }
    private void registerAccount(Context ctx) throws JsonMappingException, JsonProcessingException {
        
        Account account = mapper.readValue(ctx.body().toString(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount == null) {
            ctx.status(400);
        } else {
            ctx.json(addedAccount).status(200);
        }
        

    }
    private void updateMessage(Context ctx) throws JsonMappingException, JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByMessageId(messageId);
        if (message == null) {
            ctx.status(400);
            return;
        }
        Message newMessage = mapper.readValue(ctx.body(), Message.class);
        newMessage.setMessage_id(message.message_id);
        newMessage.setPosted_by(message.getPosted_by());
        newMessage.setTime_posted_epoch(message.getTime_posted_epoch());
        if (newMessage.getMessage_text().isEmpty()) {
            ctx.status(400);
            return;
        }
        if (newMessage.getMessage_text().length() > 255) {
            ctx.status(400);
            return;
        }
        messageService.updateMessage(newMessage);
        ctx.json(newMessage).status(200);

        

    }
    private void getMessageByMessageId(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByMessageId(messageId);
        if (message == null) { 
            ctx.status(200);
        } else {
            ctx.json(message).status(200);
        }
    }
    private void getMessagesOfUser(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesOfUser(accountId);
        ctx.json(messages).status(200);
    }
    private void deleteMessage(Context ctx) {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageID);
        
        if (deletedMessage != null) {
            ctx.json(deletedMessage).status(200);
        }
        ctx.status(200);
        

    }
    private void createMessage(Context ctx) throws JsonProcessingException {
        Message message = mapper.readValue(ctx.body(), Message.class);
        
        if (message.getMessage_text().length() > 255) {
            ctx.status(400).result("");
            return;
        }
        if (message.getMessage_text() == "") {
            ctx.status(400).result("");
            return;
        }
        int userId = message.getPosted_by();
        Account act = accountService.getAccountById(userId);
        if (act == null) {
            ctx.status(400).result("");
            return;
        }
        

        Message createdMessage = messageService.addMessage(message);
        
        if (createdMessage != null) {
            ctx.json(createdMessage).status(200);
            System.out.println("I *********** RAN!!!!!");
       }


    }

    private void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();

        ctx.json(messages).status(200);
        // ctx.result("this worked");
    }



}