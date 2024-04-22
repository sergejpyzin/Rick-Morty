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

    /**
     * Получает всех персонажей из внешнего API.
     *
     * @return Объект типа Characters, содержащий информацию о всех персонажах.
     */
    @Override
    public Characters getAllCharacters() {

        // Получаем URL API из переменных окружения
        String path = environment.getProperty("CHARACTER_API");

        // Отправляем запрос на получение данных о персонажах по указанному URL
        ResponseEntity<Characters> responseEntity = getResponseEntity(path);

        // Возвращаем тело ответа, содержащее информацию о всех персонажах
        return responseEntity.getBody();
    }


    /**
     * Получает информацию о персонаже по указанному идентификатору.
     *
     * @param id Идентификатор персонажа
     * @return Результат выполнения запроса с информацией о персонаже
     */
    @Override
    public Result getCharacterById(Integer id) {

        // Устанавливаем заголовки для принятия JSON данных
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        // Создаем HTTP-сущность с установленными заголовками
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // Формируем путь к API для получения информации о персонаже по идентификатору
        String pathToId = Objects.requireNonNull(environment.getProperty("CHARACTER_API")).concat("/").concat(String.valueOf(id));

        // Выполняем HTTP-запрос GET для получения информации о персонаже
        ResponseEntity<Result> responseEntity = template.exchange(pathToId, HttpMethod.GET, httpEntity, Result.class);

        // Возвращаем результат выполнения запроса, содержащий информацию о персонаже
        return responseEntity.getBody();
    }


    /**
     * Получает список персонажей, отсортированных по имени.
     *
     * @return Отсортированный список персонажей.
     */
    @Override
    public Characters getSortedByName() {

        // Получение пути к API персонажей из настроек
        String path = environment.getProperty("CHARACTER_API");

        // Отправка запроса к API и получение ответа
        ResponseEntity<Characters> responseEntity = getResponseEntity(path);

        // Получение тела ответа и его обработка
        Characters characters = Objects.requireNonNull(responseEntity.getBody());
        // Сортировка списка персонажей по имени с использованием компаратора compareByName
        characters.setResults(responseEntity.getBody().getResults().stream().sorted(compareByName).toList());

        // Возврат отсортированного списка персонажей
        return characters;
    }


    /**
     * Получает список персонажей, отсортированных по полу.
     *
     * @return Отсортированный список персонажей.
     */
    public Characters getSortedByGender() {

        // Получение пути к API персонажей из конфигурации
        String path = environment.getProperty("CHARACTER_API");

        // Выполнение запроса к API для получения списка персонажей
        ResponseEntity<Characters> responseEntity = getResponseEntity(path);

        // Отсортировать список персонажей по полу с использованием компаратора compareByGender
        Objects.requireNonNull(responseEntity.getBody()).setResults(responseEntity.getBody().getResults().stream().sorted(compareByGender).toList());

        // Вернуть отсортированный список персонажей
        return responseEntity.getBody();
    }


    /**
     * Получает список персонажей, отсортированных по дате создания.
     *
     * @return Отсортированный список персонажей.
     */
    public Characters getSortedByCreated() {

        // Получаем путь к API персонажей из настроек
        String path = environment.getProperty("CHARACTER_API");

        // Получаем ответ от API
        ResponseEntity<Characters> responseEntity = getResponseEntity(path);

        // Сортируем список персонажей по дате создания с использованием компаратора compareByCreated
        Objects.requireNonNull(responseEntity.getBody()).setResults(responseEntity.getBody().getResults().stream().sorted(compareByCreated).toList());

        // Возвращаем отсортированный список персонажей
        return responseEntity.getBody();
    }


    /**
     * Получает ответ от сервера в формате ResponseEntity с данными о персонажах.
     *
     * @param path Путь к API, откуда требуется получить данные о персонажах.
     * @return Объект ResponseEntity с данными о персонажах.
     */
    private ResponseEntity<Characters> getResponseEntity(String path) {

        // Установка заголовков запроса для принятия JSON данных
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        // Создание HttpEntity с установленными заголовками
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Отправка GET запроса и получение ответа от сервера
        return template.exchange(path, HttpMethod.GET, entity, Characters.class);
    }


    /**
     * Компаратор для сравнения результатов по имени.
     */
    public static Comparator<Result> compareByName = new Comparator<Result>() {
        /**
         * Сравнивает два объекта результата по имени.
         *
         * @param o1 Первый объект результата.
         * @param o2 Второй объект результата.
         * @return Значение &lt; 0, если o1 меньше o2; значение &gt; 0, если o1 больше o2; 0, если они равны.
         */
        @Override
        public int compare(Result o1, Result o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };


    /**
     * Компаратор для сравнения результатов по полу персонажа.
     */
    public static Comparator<Result> compareByGender = new Comparator<Result>() {
        /**
         * Сравнивает два объекта результата по полу персонажа.
         *
         * @param o1 Первый объект результата.
         * @param o2 Второй объект результата.
         * @return Значение &lt; 0, если o1 меньше o2; значение &gt; 0, если o1 больше o2; 0, если они равны.
         */
        @Override
        public int compare(Result o1, Result o2) {
            return o1.getGender().compareTo(o2.getGender());
        }
    };

    /**
     * Компаратор для сравнения результатов по дате создания персонажа.
     */
    public static Comparator<Result> compareByCreated = new Comparator<Result>() {
        /**
         * Сравнивает два объекта результата по дате создания персонажа.
         *
         * @param o1 Первый объект результата.
         * @param o2 Второй объект результата.
         * @return Значение &lt; 0, если o1 меньше o2; значение &gt; 0, если o1 больше o2; 0, если они равны.
         */
        @Override
        public int compare(Result o1, Result o2) {
            return o1.getCreated().compareTo(o2.getCreated());
        }
    };

}
