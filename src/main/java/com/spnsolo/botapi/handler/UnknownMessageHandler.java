package com.spnsolo.botapi.handler;

import com.spnsolo.botapi.BotState;
import com.spnsolo.botapi.InputMessageHandler;
import com.spnsolo.cache.UserCache;
import com.spnsolo.service.bot.ReplyMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class UnknownMessageHandler implements InputMessageHandler {
    private final ReplyMessageService messageService;
    private final UserCache botUserCache;

    private static final BotState NAME = BotState.UNKNOWN_MESSAGE;

    public UnknownMessageHandler(ReplyMessageService messageService,
                                 UserCache botUserCache){
        this.botUserCache = botUserCache;
        this.messageService = messageService;
    }
    @Override
    public BotState getHandlerName() {
        return NAME;
    }

    @Override
    public SendMessage handle(Message message) {
        return handleUnknownMessage(message);
    }

    private SendMessage handleUnknownMessage(Message message){
        long chatId = message.getChatId();
        long userId = message.getFrom().getId();
        botUserCache.setUsersCurrentBotState(userId,BotState.ASK_LOCATION);
        return messageService.getReplyMessage(chatId,"reply.unknown");
    }
}
