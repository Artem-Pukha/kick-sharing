package com.spnsolo.controller;

import com.spnsolo.exception.ScooterExceptions;
import com.spnsolo.model.SaveScooter;
import com.spnsolo.model.ScooterResponse;
import com.spnsolo.service.ScooterCRUD;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/scooters")
public class ScooterController {
    private final ScooterCRUD scooterService;

    public ScooterController(ScooterCRUD scooterService) {
        this.scooterService = scooterService;
    }

    @GetMapping("/{id}")
    public ScooterResponse get(@PathVariable long id) {
        return scooterService.getById(id).orElseThrow(() -> ScooterExceptions.scooterNotFound(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ScooterResponse create(@Valid @RequestBody SaveScooter request) {
        return scooterService.create(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable long id, @Valid @RequestBody SaveScooter request) {
        scooterService.update(id ,request);
    }

    @DeleteMapping("/{id}")
    public ScooterResponse delete(@PathVariable long id) {
        return scooterService.deleteById(id)
                .orElseThrow(() -> ScooterExceptions.scooterNotFound(id));
    }

}
