package ru.sergeypyzin.springrestapi.service;


import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sergeypyzin.springrestapi.domain.Characters;
import ru.sergeypyzin.springrestapi.domain.Result;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ServiceApiImpl implements ServiceApi {

    private final RestTemplate template;
    private final HttpHeaders headers;
    private final Environment environment;

    @Override
    public Characters getAllCharacters() {
        String path = environment.getProperty("CHARACTER_API");
        ResponseEntity<Characters> responseEntity = getResponseEntity(path);
        return responseEntity != null ? responseEntity.getBody() : null;
    }

    @Override
    public Result getCharacterById(Integer id) {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String pathToId = Objects.requireNonNull(environment.getProperty("CHARACTER_API")).concat("/").concat(String.valueOf(id));
        ResponseEntity<Result> responseEntity = template.exchange(pathToId, HttpMethod.GET, httpEntity, Result.class);
        return responseEntity.getBody();
    }

    @Override
    public Characters getSortedByName() {
        Characters characters = getAllCharacters();
        if (characters != null) {
            characters.getResults().sort(Comparator.comparing(Result::getName));
        }
        return characters;
    }

    @Override
    public Characters getSortedByGender() {
        Characters characters = getAllCharacters();
        if (characters != null) {
            characters.getResults().sort(Comparator.comparing(Result::getGender));
        }
        return characters;
    }

    @Override
    public Characters getSortedByCreated() {
        Characters characters = getAllCharacters();
        if (characters != null) {
            characters.getResults().sort(Comparator.comparing(Result::getCreated));
        }
        return characters;
    }

    private <T> ResponseEntity<T> getResponseEntity(String path) {
        if (path != null) {
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return template.exchange(path, HttpMethod.GET, entity, (Class<T>) Characters.class);
        }
        return null;
    }
}
