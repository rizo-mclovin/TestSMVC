package org.example.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {
   private static int PEOPLE_COUNT;
   private List<Person> peopleDB;

   {
      peopleDB = new ArrayList<>();

      peopleDB.add(new Person(++PEOPLE_COUNT, "Alex", 20, "alex@mail.ru"));
      peopleDB.add(new Person(++PEOPLE_COUNT, "Katy", 18, "katy@gmail.com"));
      peopleDB.add(new Person(++PEOPLE_COUNT, "John", 33, "john@proton.me"));
      peopleDB.add(new Person(++PEOPLE_COUNT, "Bob", 21, "bob@gmail.com"));
      peopleDB.add(new Person(++PEOPLE_COUNT, "Tom", 43, "tom@gmail.com"));
   }

   public List<Person> getAll(){
      return peopleDB;
   }

   public Person getOne(int id){
      return peopleDB.stream().filter(person -> person.getId()==id).findAny().orElse(null);
   }

   public void save(Person person){
      person.setId(++PEOPLE_COUNT);
      peopleDB.add(person);
   }

   public void update(int id, Person updatedPerson){
      Person personToUpdate = getOne(id);

      personToUpdate.setName(updatedPerson.getName());
      personToUpdate.setAge(updatedPerson.getAge());
      personToUpdate.setEmail(updatedPerson.getEmail());
   }

   public void delete(int id){
      peopleDB.removeIf(p -> p.getId()==id);
   }

}
