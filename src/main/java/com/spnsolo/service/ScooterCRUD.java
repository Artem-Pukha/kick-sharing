package com.spnsolo.service;


import com.spnsolo.model.SaveScooter;
import com.spnsolo.model.ScooterResponse;

import java.util.List;
import java.util.Optional;

public interface ScooterCRUD {
    ScooterResponse create(SaveScooter request);

    Optional<ScooterResponse> getById(long id);

    void update(long id,SaveScooter request);

    Optional<ScooterResponse> deleteById(long id);

    List<ScooterResponse> getAllAvailable();
}
