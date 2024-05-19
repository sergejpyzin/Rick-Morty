package ru.sergeypyzin.springrestapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ru.sergeypyzin.springrestapi.domain.Characters;
import ru.sergeypyzin.springrestapi.domain.Result;
import ru.sergeypyzin.springrestapi.service.ServiceApi;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerAPITest {

    @Mock
    private ServiceApi serviceApi;

    @Mock
    private Model model;

    @InjectMocks
    private ControllerAPI controllerAPI;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCharacters() {
        Characters characters = new Characters();
        characters.setResults(List.of(new Result(), new Result()));

        when(serviceApi.getAllCharacters()).thenReturn(characters);

        String viewName = controllerAPI.getAllCharacters(model);

        assertEquals("characters", viewName);
        verify(model, times(1)).addAttribute(eq("characters"), any());
        verify(serviceApi, times(1)).getAllCharacters();
    }

    @Test
    void getCharacter() {
        Result character = new Result();
        character.setId(1);

        when(serviceApi.getCharacterById(1)).thenReturn(character);

        String viewName = controllerAPI.getCharacter(model, 1);

        assertEquals("character", viewName);
        verify(model, times(1)).addAttribute(eq("character"), any());
        verify(serviceApi, times(1)).getCharacterById(1);
    }

    @Test
    void sortByName() {
        Characters characters = new Characters();
        characters.setResults(List.of(new Result(), new Result()));

        when(serviceApi.getSortedByName()).thenReturn(characters);

        String viewName = controllerAPI.sortByName(model);

        assertEquals("characters", viewName);
        verify(model, times(1)).addAttribute(eq("characters"), any());
        verify(serviceApi, times(1)).getSortedByName();
    }

    @Test
    void sortByGender() {
        Characters characters = new Characters();
        characters.setResults(List.of(new Result(), new Result()));

        when(serviceApi.getSortedByGender()).thenReturn(characters);

        String viewName = controllerAPI.sortByGender(model);

        assertEquals("characters", viewName);
        verify(model, times(1)).addAttribute(eq("characters"), any());
        verify(serviceApi, times(1)).getSortedByGender();
    }

    @Test
    void sortByCreated() {
        Characters characters = new Characters();
        characters.setResults(List.of(new Result(), new Result()));

        when(serviceApi.getSortedByCreated()).thenReturn(characters);

        String viewName = controllerAPI.sortByCreated(model);

        assertEquals("characters", viewName);
        verify(model, times(1)).addAttribute(eq("characters"), any());
        verify(serviceApi, times(1)).getSortedByCreated();
    }
}
