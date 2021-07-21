package com.spnsolo.controller;

import com.spnsolo.model.ScooterResponse;
import com.spnsolo.service.ScooterCRUD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScooterControllerTest {
    private MockMvc mvc;

    private ScooterCRUD scooterCRUD;

    @BeforeEach
    void setUp() {
        scooterCRUD = mock(ScooterCRUD.class);
        mvc = MockMvcBuilders
                .standaloneSetup(new ScooterController(scooterCRUD))
                .build();
    }

    @Test
    void testGetScooterById() throws Exception {
        long id = 1;
        int charge = 54;
        boolean available = true;
        double longitude = 45.1;
        double latitude = 36.2;
        ScooterResponse response = new ScooterResponse(id,charge,available,longitude,latitude);

        when(scooterCRUD.getById(id)).thenReturn(Optional.of(response));

        String expectedJson = "{\"id\":" + id + ",\"charge\":" + charge + ",\"available\":" + available + "," +
                "\"longitude\":"+ longitude +",\"latitude\":"+ latitude +"}";

        mvc.perform(get("/scooters/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(scooterCRUD, only()).getById(id);
    }

    @Test
    void testGetScooterByAbsentId() throws Exception {
        long id = 1;

        when(scooterCRUD.getById(id)).thenReturn(Optional.empty());

        mvc.perform(get("/scooters/" + id))
                .andExpect(status().isNotFound());

        verify(scooterCRUD, only()).getById(id);
    }

    @Test
    void testDeleteMessage() throws Exception {

        long id = 1;
        int charge = 54;
        boolean available = true;
        double longitude = 45.1;
        double latitude = 36.2;
        ScooterResponse response = new ScooterResponse(id,charge,available,longitude,latitude);

        String expectedJson = "{\"id\":" + id + ",\"charge\":" + charge + ",\"available\":" + true + "," +
                "\"longitude\":"+ longitude +",\"latitude\":"+ latitude +"}";

        when(scooterCRUD.deleteById(id))
                .thenReturn(Optional.of(response))
                .thenReturn(Optional.empty());

        mvc.perform(delete("/scooters/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        mvc.perform(delete("/scooters/{id}", id))
                .andExpect(status().isNotFound());

        verify(scooterCRUD, times(2)).deleteById(id);
        verifyNoMoreInteractions(scooterCRUD);
    }
}
