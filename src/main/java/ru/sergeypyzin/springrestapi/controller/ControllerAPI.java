package ru.sergeypyzin.springrestapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sergeypyzin.springrestapi.service.ServiceApi;

import java.util.List;

@Controller
@AllArgsConstructor
public class ControllerAPI {

    private final ServiceApi serviceApi;

    @GetMapping("/characters")
    public String getAllCharacters (Model model){
        model.addAttribute("characters", serviceApi.getAllCharacters().getResults());
        return "characters";
    }

    @GetMapping("/characters/sortbyname")
    public String sortByName(Model model) {
        model.addAttribute("characters", serviceApi.getSortedByName().getResults());
        return "characters";
    }


    @GetMapping("/characters/sortbygender")
    public String sortByGender (Model model){
        model.addAttribute("characters", serviceApi.getSortedByGender().getResults());
        return "characters";
    }

    @GetMapping("/characters/sortbycreated")
    public String sortByCreated (Model model){
        model.addAttribute("characters", serviceApi.getSortedByCreated().getResults());
        return "characters";
    }

    @GetMapping("/character/{id}")
    public String getCharacter (Model model, @PathVariable Integer id){
        model.addAttribute("character", serviceApi.getCharacterById(id));
        return "character";
    }
}
