package org.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
   @GetMapping("/hello")
   public String sayHello(){
      return "hello_world";
   }

   @GetMapping("/bye")
   public String sayBye(){
      return "bye";
   }

   @GetMapping("/exit")
   public String exit(){
      return "exit";
   }
}