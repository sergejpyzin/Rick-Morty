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
        return responseEntity.getBody();
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
        String path = environment.getProperty("CHARACTER_API");
        ResponseEntity<Characters> responseEntity = getResponseEntity(path);
        Objects.requireNonNull(responseEntity.getBody()).setResults(responseEntity.getBody().getResults().stream().sorted(compareByName).toList());
        return responseEntity.getBody();
    }

    public Characters getSortedByGender() {
        String path = environment.getProperty("CHARACTER_API");
        ResponseEntity<Characters> responseEntity = getResponseEntity(path);
        Objects.requireNonNull(responseEntity.getBody()).setResults(responseEntity.getBody().getResults().stream().sorted(compareByGender).toList());
        return responseEntity.getBody();
    }

    public Characters getSortedByCreated() {
        String path = environment.getProperty("CHARACTER_API");
        ResponseEntity<Characters> responseEntity = getResponseEntity(path);
        Objects.requireNonNull(responseEntity.getBody()).setResults(responseEntity.getBody().getResults().stream().sorted(compareByCreated).toList());
        return responseEntity.getBody();
    }

    private ResponseEntity<Characters> getResponseEntity(String path) {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return template.exchange(path, HttpMethod.GET, entity, Characters.class);


    }

    public static Comparator<Result> compareByName = new Comparator<Result>() {
        @Override
        public int compare(Result o1, Result o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    public static Comparator<Result> compareByGender = new Comparator<Result>() {
        @Override
        public int compare(Result o1, Result o2) {
            return o1.getGender().compareTo(o2.getGender());
        }
    };

    public static Comparator<Result> compareByCreated = new Comparator<Result>() {
        @Override
        public int compare(Result o1, Result o2) {
            return o1.getCreated().compareTo(o2.getCreated());
        }
    };
}
