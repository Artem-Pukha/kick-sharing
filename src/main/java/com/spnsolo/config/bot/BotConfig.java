package com.spnsolo.config.bot;

import com.spnsolo.KickSharingBot;
import com.spnsolo.botapi.TelegramFacade;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String botName;
    private String botToken;
    private String webHookPath;

    @Bean
    public KickSharingBot kickSharingBot(TelegramFacade facade){
        KickSharingBot bot = new KickSharingBot(facade);
        bot.setBotPath(webHookPath);
        bot.setBotUsername(botName);
        bot.setBotToken(botToken);
        return bot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getWebHookPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }
}
