package com.spnsolo.service.bot;

import org.springframework.stereotype.Service;

@Service
public class ButtonLocationMessageService {
    private final LocaleMessageService localeMessageService;

    public ButtonLocationMessageService(LocaleMessageService localeMessageService){
        this.localeMessageService = localeMessageService;
    }

    public String getConfirmRequestLocationMassage(){ return localeMessageService.getMessage("button.confirmLocation"); }

    public String getRejectRequestLocationMassage(){
        return localeMessageService.getMessage("button.rejectLocation");
    }
}
