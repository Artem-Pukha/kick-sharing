package com.spnsolo.cache;

import com.spnsolo.botapi.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserCache {
    private final Map<Long, BotState> usersBotState= new HashMap<>();

    public void setUsersCurrentBotState(long userId, BotState botState) {
        usersBotState.put(userId,botState);
    }

    public BotState getUsersCurrentBotState(long userId) {
        BotState botState = usersBotState.get(userId);
        if (botState == null)botState = BotState.ASK_LOCATION;
        return botState;
    }
}
