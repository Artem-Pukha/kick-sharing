package com.spnsolo.service.impl;

import com.spnsolo.exception.ScooterExceptions;
import com.spnsolo.model.ScooterResponse;
import com.spnsolo.model.SaveScooter;
import com.spnsolo.entity.Scooter;
import com.spnsolo.repository.ScooterRepository;
import com.spnsolo.service.ScooterCRUD;
import com.spnsolo.util.CalculatorCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScooterService implements ScooterCRUD {


    private final ScooterRepository repository;

    public ScooterService(ScooterRepository repository){
        this.repository = repository;
    }

    @Override
    public ScooterResponse create(final SaveScooter request) {
        Scooter scooter = request.toScooter();
        return ScooterResponse.fromScooter(repository.create(scooter));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScooterResponse> getById(long id) {
        return repository.getById(id).map(ScooterResponse::fromScooter);
    }

    @Override
    public void update(long id, SaveScooter request) {
        Scooter scooter = repository.getById(id).orElseThrow(() -> ScooterExceptions.scooterNotFound(id));
        scooter.getCoordinates().setLatitude(request.getLatitude());
        scooter.getCoordinates().setLongitude(request.getLongitude());
        scooter.setAvailable(request.isAvailable());
        scooter.setCharge(request.getCharge());
        repository.update(scooter);
    }

    @Override
    public Optional<ScooterResponse> deleteById(long id) {
        Optional<Scooter> message = repository.getById(id);
        if(message.isPresent())repository.deleteById(id);
        return message.map(ScooterResponse::fromScooter);
    }

    public List<ScooterResponse> getAllAvailableNearCoordinates(double longitude, double latitude, float radius){
        List<Scooter> all = repository.getAllAvailable();
        double distance;

        Iterator<Scooter> iterator = all.iterator();

        while (iterator.hasNext()) {
            Scooter element = iterator.next();
            distance = CalculatorCoordinates.distanceBetweenCoordinates(element.getCoordinates().getLatitude(),
                    latitude,element.getCoordinates().getLongitude(),longitude);
            if(distance > radius)iterator.remove();
        }
        return ScooterResponse.fromScooter(all);
    }
}
