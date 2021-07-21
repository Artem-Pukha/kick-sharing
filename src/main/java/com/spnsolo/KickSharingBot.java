package com.spnsolo;

import com.spnsolo.botapi.TelegramFacade;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class KickSharingBot extends TelegramWebhookBot {
    private String botPath;
    private String botUsername;
    private String botToken;

    private final TelegramFacade facade;

    public KickSharingBot(TelegramFacade facade){this.facade = facade;}
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) { return facade.handleUpdate(update); }

    public void setBotPath(String botPath) { this.botPath = botPath; }

    public void setBotUsername(String botUsername) { this.botUsername = botUsername; }

    public void setBotToken(String botToken) { this.botToken = botToken; }
}
