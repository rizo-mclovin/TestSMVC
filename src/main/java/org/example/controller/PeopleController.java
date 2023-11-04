package org.example.controller;


import org.example.model.Person;
import org.example.model.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
   private final PersonDAO personDAO;

   @Autowired
   public PeopleController(PersonDAO personDAO) {
      this.personDAO = personDAO;
   }

   @GetMapping
   public String allPeople(Model model){
      model.addAttribute("people", personDAO.getAll());
      return "people/all";
   }

   @GetMapping("/{id}")
   public String index(@PathVariable("id") int id, Model model){
      model.addAttribute("person", personDAO.getOne(id));
      return "people/index";
   }

   @GetMapping("/new")
   public String newPerson(Model model){
      model.addAttribute("person", new Person());
      return "people/new";
   }

   @PostMapping
   public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
      if (bindingResult.hasErrors()){
         return "people/new";
      }
      personDAO.save(person);
      return "redirect:/people";
   }

   @GetMapping("/{id}/edit")
   public String edit(@PathVariable int id, Model model){
      model.addAttribute("person", personDAO.getOne(id));
      return "people/edit";
   }

   @PatchMapping("/{id}")
   public String update(@PathVariable int id,
                        @ModelAttribute("person") @Valid Person person,
                        BindingResult bindingResult){

      if(bindingResult.hasErrors()){
         return "people/edit";
      }

      personDAO.update(id, person);
      return "redirect:/people";
   }

   @DeleteMapping("/delete/{id}")
   public String deletePerson(@PathVariable int id){
      personDAO.delete(id);
      return "redirect:/people";
   }

}
