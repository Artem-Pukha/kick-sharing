package com.spnsolo.service.bot;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeocoderService {
    private final JOpenCageGeocoder jOpenCageGeocoder;
    private JOpenCageReverseRequest request;
    private JOpenCageResponse response;


    public GeocoderService(@Value("${jopencage.api.key}")String apiKey){
        jOpenCageGeocoder = new JOpenCageGeocoder(apiKey);
    }

    public String coordinatesToAddress(double latitude,double longitude){
        request = new JOpenCageReverseRequest(latitude,longitude);
        request.setLanguage("ru");
        request.setNoDedupe(true);
        request.setLimit(5);
        request.setNoAnnotations(true);
        request.setMinConfidence(3);

        response = jOpenCageGeocoder.reverse(request);
        return response.getResults().get(0).getFormatted();
    }
}
