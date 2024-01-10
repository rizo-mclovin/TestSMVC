package org.example.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
   private int id;

   @NotEmpty(message = "Name should not be empty!")
   @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters!")
   private String name;

   @NotEmpty(message = "Email should not be empty!")
   @Email(message = "Email should be valid!")
   private String email;


   @NotEmpty(message = "Address should not be empty!")
   private String address;

   public Person(int id, String name, String email, String address) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.address = address;
   }

   public Person() {
   }



   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }


   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }
}
