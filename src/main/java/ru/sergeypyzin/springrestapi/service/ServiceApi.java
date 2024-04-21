package ru.sergeypyzin.springrestapi.service;


import ru.sergeypyzin.springrestapi.domain.Characters;
import ru.sergeypyzin.springrestapi.domain.Result;

public interface ServiceApi {
    public Characters getAllCharacters();

    public Result getCharacterById(Integer id);

    public Characters getSortedByName();
    public Characters getSortedByGender();
    public Characters getSortedByCreated();

}
