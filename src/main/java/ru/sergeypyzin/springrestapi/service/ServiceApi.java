package ru.sergeypyzin.springrestapi.service;


import ru.sergeypyzin.springrestapi.domain.Characters;
import ru.sergeypyzin.springrestapi.domain.Result;

public interface ServiceApi {
    /**
     * Получает всех персонажей.
     *
     * @return объект класса Characters, содержащий информацию о всех персонажах
     */
    public Characters getAllCharacters();

    /**
     * Получает информацию о персонаже по его идентификатору.
     *
     * @param id идентификатор персонажа
     * @return объект класса Result, содержащий информацию о персонаже
     */
    public Result getCharacterById(Integer id);

    /**
     * Получает отсортированный список персонажей по имени.
     *
     * @return объект класса Characters, содержащий отсортированный список персонажей по имени
     */
    public Characters getSortedByName();

    /**
     * Получает отсортированный список персонажей по полу.
     *
     * @return объект класса Characters, содержащий отсортированный список персонажей по полу
     */
    public Characters getSortedByGender();

    /**
     * Получает отсортированный список персонажей по дате создания.
     *
     * @return объект класса Characters, содержащий отсортированный список персонажей по дате создания
     */
    public Characters getSortedByCreated();


}
