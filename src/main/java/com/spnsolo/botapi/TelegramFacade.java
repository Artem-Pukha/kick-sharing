package com.spnsolo.botapi;

import com.spnsolo.cache.UserCache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final UserCache botUserCache;

    public TelegramFacade(BotStateContext botStateContext,
                          UserCache botUserCache){
        this.botStateContext = botStateContext;
        this.botUserCache = botUserCache;
    }

    public BotApiMethod<?> handleUpdate(Update update){
        SendMessage replyMessage = new SendMessage();

        Message message = update.getMessage();
        if (message != null && (message.hasText() || message.hasLocation())) replyMessage = handleInputMessage(message);

        return replyMessage;
    }
    private SendMessage handleInputMessage(Message message){
        long userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        if (message.hasText()&&"/start".equals(message.getText()))botState = BotState.ASK_LOCATION;
        if(message.hasLocation())botState = BotState.FINDING_SCOOTERS;
        else botState = BotState.UNKNOWN_MESSAGE;

        botUserCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }
}
