package com.spnsolo.botapi.handler;

import com.spnsolo.botapi.BotState;
import com.spnsolo.botapi.InputMessageHandler;
import com.spnsolo.cache.UserCache;
import com.spnsolo.model.ScooterResponse;
import com.spnsolo.service.bot.GeocoderService;
import com.spnsolo.service.bot.ReplyMessageService;
import com.spnsolo.service.impl.ScooterService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class FindScooterHandler implements InputMessageHandler {
    private final UserCache botUserCache;
    private final GeocoderService geocoderService;
    private final ReplyMessageService messageService;
    private final ScooterService scooterService;

    private static final BotState NAME = BotState.FINDING_SCOOTERS;
    private static final float RADIUS_OF_SEARCHING = 1.0F;//Kilometres

    public FindScooterHandler(UserCache botUserCache,
                              GeocoderService geocoderService,
                              ScooterService scooterService,
                              ReplyMessageService messageService){
        this.botUserCache = botUserCache;
        this.geocoderService = geocoderService;
        this.messageService = messageService;
        this.scooterService = scooterService;
    }

    @Override
    public BotState getHandlerName() { return NAME; }

    @Override
    public SendMessage handle(Message message) {
        return handleLocation(message);
    }

    private SendMessage handleLocation(Message message){
        long chatId = message.getChatId();
        long userId = message.getFrom().getId();
        Location userLocation = message.getLocation();
        botUserCache.setUsersCurrentBotState(userId,BotState.ASK_LOCATION);
        SendMessage sendMessage = messageService.getReplyMessage(chatId,"reply.scooters.not.found");

        StringBuilder reply = findNearScooters(userLocation);
        if(!reply.isEmpty()){
            sendMessage = new SendMessage(String.valueOf(chatId),String.valueOf(reply));
        }

        return sendMessage;
    }


    private StringBuilder findNearScooters(Location location){
        List<ScooterResponse> scooters = scooterService.getAllAvailableNearCoordinates(location.getLongitude(),
                location.getLatitude(), RADIUS_OF_SEARCHING);
        StringBuilder output = new StringBuilder();
        if(!scooters.isEmpty()){
            output.append("Самокатов найдено: ").append(scooters.size()).append('\n');
            output.append("-----\n");
            for(ScooterResponse response: scooters){
                output.append("Адресс: ").append(geocoderService.coordinatesToAddress(response.getLatitude(),
                        response.getLongitude())).append('\n');
                output.append("Заряд: ").append(response.getCharge()).append("%\n");
                output.append("-----\n");
            }
        }
        return output;
    }



}
