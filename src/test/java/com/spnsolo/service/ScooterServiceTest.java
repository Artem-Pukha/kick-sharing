package com.spnsolo.service;

import com.spnsolo.entity.Coordinates;
import com.spnsolo.entity.Scooter;
import com.spnsolo.model.SaveScooter;
import com.spnsolo.model.ScooterResponse;
import com.spnsolo.repository.ScooterRepository;
import com.spnsolo.service.impl.ScooterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

public class ScooterServiceTest {
    private ScooterService service;
    private ScooterRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(ScooterRepository.class);
        service = new ScooterService(repository);
    }


    @Test
    void testGetScooterById() {
        long absentId = 23;
        long presentId = 1;
        Scooter scooter = new Scooter(presentId,54,true,new Coordinates(45.1,36.2));

        when(repository.getById(absentId)).thenReturn(Optional.empty());
        when(repository.getById(presentId)).thenReturn(Optional.of(scooter));

        Optional<ScooterResponse> absentResponse = service.getById(absentId);

        assertThat(absentResponse).isEmpty();
        verify(repository).getById(absentId);

        Optional<ScooterResponse> presentResponse = service.getById(presentId);

        assertThat(presentResponse).hasValueSatisfying(scooterResponse ->
                assertScooterMatchesResponse(scooter, scooterResponse));
        verify(repository).getById(presentId);

        verifyNoMoreInteractions(repository);
    }

    @Test
    void testUpdateMessage() {
        long absentId = 23;
        long presentId = 1;

        Scooter scooter = new Scooter(presentId,54,true,new Coordinates(45.1,36.2));

        SaveScooter saveScooter = new SaveScooter(100,true,45.1,36.2);

        when(repository.getById(absentId)).thenReturn(Optional.empty());
        when(repository.getById(presentId)).thenReturn(Optional.of(scooter));
        when(repository.update(scooter)).thenReturn(scooter);

        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> service.update(absentId, saveScooter))
                .satisfies(e -> assertThat(e.getStatus()).isSameAs(HttpStatus.NOT_FOUND));

        verify(repository).getById(absentId);

        service.update(presentId, saveScooter);

        assertThat(saveScooter.getCharge()).isEqualTo(scooter.getCharge());
        assertThat(saveScooter.isAvailable()).isEqualTo(scooter.isAvailable());
        assertThat(saveScooter.getLatitude()).isEqualTo(scooter.getCoordinates().getLatitude());
        assertThat(saveScooter.getLongitude()).isEqualTo(scooter.getCoordinates().getLongitude());

        verify(repository).getById(presentId);
        verify(repository).update(scooter);

        verifyNoMoreInteractions(repository);
    }

    @Test
    void testDeleteMessage() {
        long absentId = 23;
        long presentId = 1;
        Scooter scooter = new Scooter(presentId,54,true,new Coordinates(45.1,36.2));

        when(repository.getById(absentId)).thenReturn(Optional.empty());
        when(repository.getById(presentId)).thenReturn(Optional.of(scooter));

        Optional<ScooterResponse> absentResponse = service.deleteById(absentId);

        assertThat(absentResponse).isEmpty();
        verify(repository).getById(absentId);

        Optional<ScooterResponse> presentResponse = service.deleteById(presentId);

        assertThat(presentResponse).hasValueSatisfying(messageResponse ->
                assertScooterMatchesResponse(scooter, messageResponse));

        verify(repository).getById(presentId);
        verify(repository).deleteById(presentId);

        verifyNoMoreInteractions(repository);
    }

    @Test
    void testCreateMessage() {
        SaveScooter saveScooter = new SaveScooter(100,true,45.1,36.2);
        long scooterId = 1;

        when(repository.create(notNull())).thenAnswer(invocation -> {
            Scooter entity = invocation.getArgument(0);
            assertThat(entity.getId()).isNull();
            assertThat(entity.getCharge()).isEqualTo(saveScooter.getCharge());
            assertThat(entity.getCoordinates().getLatitude()).isEqualTo(saveScooter.getLatitude());
            assertThat(entity.getCoordinates().getLongitude()).isEqualTo(saveScooter.getLongitude());
            entity.setId(scooterId);
            return entity;
        });

        ScooterResponse response = service.create(saveScooter);

        assertThat(response.getId()).isEqualTo(scooterId);
        assertThat(response.getCharge()).isEqualTo(saveScooter.getCharge());
        assertThat(response.isAvailable()).isEqualTo(saveScooter.isAvailable());
        assertThat(response.getLongitude()).isEqualTo(saveScooter.getLongitude());
        assertThat(response.getLatitude()).isEqualTo(saveScooter.getLatitude());

        verify(repository, only()).create(notNull());
    }

    private static void assertScooterMatchesResponse(Scooter scooter, ScooterResponse scooterResponse) {
        assertThat(scooterResponse.getId()).isEqualTo(scooter.getId());
        assertThat(scooterResponse.getCharge()).isEqualTo(scooter.getCharge());
        assertThat(scooterResponse.isAvailable()).isEqualTo(scooter.isAvailable());
        assertThat(scooterResponse.getLatitude()).isEqualTo(scooter.getCoordinates().getLatitude());
        assertThat(scooterResponse.getLongitude()).isEqualTo(scooter.getCoordinates().getLongitude());
    }
}
