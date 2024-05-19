package ru.sergeypyzin.springrestapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.sergeypyzin.springrestapi.domain.Characters;
import ru.sergeypyzin.springrestapi.domain.Result;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceApiImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpHeaders headers;

    @Mock
    private Environment environment;

    @InjectMocks
    private ServiceApiImpl serviceApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCharacters() {
        String apiUrl = "http://example.com/api/characters";
        when(environment.getProperty("CHARACTER_API")).thenReturn(apiUrl);

        Characters characters = new Characters();
        characters.setResults(List.of(new Result(), new Result()));

        ResponseEntity<Characters> responseEntity = new ResponseEntity<>(characters, HttpStatus.OK);
        when(restTemplate.exchange(eq(apiUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Characters.class)))
                .thenReturn(responseEntity);

        Characters result = serviceApi.getAllCharacters();

        assertNotNull(result);
        assertEquals(2, result.getResults().size());
        verify(restTemplate, times(1)).exchange(eq(apiUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Characters.class));
    }

    @Test
    void getCharacterById() {
        String apiUrl = "http://example.com/api/characters/1";
        when(environment.getProperty("CHARACTER_API")).thenReturn("http://example.com/api/characters");

        Result character = new Result();
        character.setId(1);

        ResponseEntity<Result> responseEntity = new ResponseEntity<>(character, HttpStatus.OK);
        when(restTemplate.exchange(eq(apiUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Result.class)))
                .thenReturn(responseEntity);

        Result result = serviceApi.getCharacterById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(restTemplate, times(1)).exchange(eq(apiUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Result.class));
    }

    // Additional tests for sorting methods...

    @Test
    void getSortedByName() {
        String apiUrl = "http://example.com/api/characters";
        when(environment.getProperty("CHARACTER_API")).thenReturn(apiUrl);

        Characters characters = new Characters();
        Result result1 = new Result();
        result1.setName("B");
        Result result2 = new Result();
        result2.setName("A");
        characters.setResults(List.of(result1, result2));

        ResponseEntity<Characters> responseEntity = new ResponseEntity<>(characters, HttpStatus.OK);
        when(restTemplate.exchange(eq(apiUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Characters.class)))
                .thenReturn(responseEntity);

        Characters result = serviceApi.getSortedByName();

        assertNotNull(result);
        assertEquals("A", result.getResults().get(0).getName());
        assertEquals("B", result.getResults().get(1).getName());
        verify(restTemplate, times(1)).exchange(eq(apiUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Characters.class));
    }
}
