package ru.sergeypyzin.springrestapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sergeypyzin.springrestapi.service.ServiceApi;


@Controller
@AllArgsConstructor
public class ControllerAPI {

    private final ServiceApi serviceApi;

    /**
     * Получает список всех персонажей мультсериала.
     *
     * @param model Модель для передачи данных в представление.
     * @return Название представления, отображающего список персонажей.
     */
    @GetMapping("/characters")
    public String getAllCharacters(Model model) {
        model.addAttribute("characters", serviceApi.getAllCharacters().getResults());
        return "characters";
    }

    /**
     * Метод обрабатывает GET-запрос на сортировку персонажей по имени.
     * Получает отсортированный список персонажей по имени из сервисного слоя
     * и добавляет его в модель для передачи в представление.
     *
     * @param model объект модели, используемый для передачи данных в представление
     * @return имя представления "characters" с отсортированным списком персонажей по имени
     */
    @GetMapping("/characters/sortbyname")
    public String sortByName(Model model) {
        model.addAttribute("characters", serviceApi.getSortedByName().getResults());
        return "characters";
    }

    /**
     * Метод обрабатывает GET-запрос на сортировку персонажей по полу.
     * Получает отсортированный список персонажей по полу из сервисного слоя
     * и добавляет его в модель для передачи в представление.
     *
     * @param model объект модели для передачи атрибутов в представление
     * @return имя представления для отображения списка отсортированных персонажей
     */
    @GetMapping("/characters/sortbygender")
    public String sortByGender(Model model) {
        model.addAttribute("characters", serviceApi.getSortedByGender().getResults());
        return "characters";
    }

    /**
     * Метод обрабатывает GET-запрос на сортировку персонажей по дате создания.
     * Получает отсортированный список персонажей по дате создания из сервисного слоя
     * и добавляет его в модель для передачи в представление.
     *
     * @param model объект модели для передачи данных в представление
     * @return строку, указывающую на имя представления, где отображается отсортированный список персонажей
     */
    @GetMapping("/characters/sortbycreated")
    public String sortByCreated(Model model) {
        model.addAttribute("characters", serviceApi.getSortedByCreated().getResults());
        return "characters";
    }

    /**
     * Метод контроллера для получения информации о персонаже по его идентификатору.
     *
     * @param model объект Model для передачи данных в представление
     * @param id    идентификатор персонажа
     * @return строка, представляющая имя представления, которое должно отобразить информацию о персонаже
     */
    @GetMapping("/character/{id}")
    public String getCharacter(Model model, @PathVariable Integer id) {
        model.addAttribute("character", serviceApi.getCharacterById(id));
        return "character";
    }

}
