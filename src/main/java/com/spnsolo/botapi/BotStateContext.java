package com.spnsolo.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> handlers){
        handlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState state, Message message){
        InputMessageHandler handler = findMessageHandler(state);
        return handler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        InputMessageHandler handler;
        switch (currentState){
            case FINDING_SCOOTERS -> handler = messageHandlers.get(BotState.FINDING_SCOOTERS);
            case UNKNOWN_MESSAGE -> handler = messageHandlers.get(BotState.UNKNOWN_MESSAGE);
            default -> handler = messageHandlers.get(BotState.ASK_LOCATION);
        }
        return handler;
    }
}
