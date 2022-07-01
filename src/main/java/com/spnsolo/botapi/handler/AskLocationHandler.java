package com.spnsolo.botapi.handler;

import com.spnsolo.botapi.BotState;
import com.spnsolo.botapi.InputMessageHandler;
import com.spnsolo.cache.UserCache;
import com.spnsolo.service.bot.ButtonLocationMessageService;
import com.spnsolo.service.bot.ReplyMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AskLocationHandler implements InputMessageHandler {
    private final ReplyMessageService messageService;
    private final UserCache botUserCache;

    private static final BotState NAME = BotState.ASK_LOCATION;
    private static String CONFIRM = "";

    public AskLocationHandler(UserCache botUserCache,
                              ReplyMessageService messageService,
                              ButtonLocationMessageService buttonLocationMessageService){
        this.botUserCache = botUserCache;
        this.messageService = messageService;

        CONFIRM = buttonLocationMessageService.getConfirmRequestLocationMassage();
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {return NAME;}

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();
        long userId = inputMsg.getFrom().getId();
        SendMessage replyToUser = messageService.getReplyMessage(chatId,"reply.askLocation");
        replyToUser.setReplyMarkup(getReplyKeyboardButtons());
        botUserCache.setUsersCurrentBotState(userId,BotState.FINDING_SCOOTERS);
        return replyToUser;
    }
    private ReplyKeyboardMarkup getReplyKeyboardButtons(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();

        
        KeyboardButton buttonConfirm = new KeyboardButton(CONFIRM);
        buttonConfirm.setRequestLocation(true);

        firstRow.add(buttonConfirm);

        keyboard.add(firstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
