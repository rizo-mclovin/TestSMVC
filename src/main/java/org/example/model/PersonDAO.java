package org.example.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
@SuppressWarnings("all")
public class PersonDAO {

   private final JdbcTemplate jdbcTemplate;


   @Autowired
   public PersonDAO(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

   public List<Person> getAll(){
      return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
   }

   public Person getOne(int id){
      return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
              .stream().findAny().orElse(null);
   }

   public void save(Person person){
      jdbcTemplate.update("INSERT INTO Person(name, email, address) VALUES(?, ?, ?)",
              person.getName(), person.getEmail(), person.getAddress());
   }

   public void update(int id, Person updatedPerson){
      jdbcTemplate.update("UPDATE Person SET name=?, email=?, address=? WHERE id=?",
              updatedPerson.getName(), updatedPerson.getEmail(), updatedPerson.getAddress(), id);
   }

   public void delete(int id){
      jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
   }

   public void testBatchUpdate() {
      List<Person> people = create1000People();
      long before = System.currentTimeMillis();

      jdbcTemplate.batchUpdate("INSERT INTO Person(id, name, email) VALUES (?, ?, ?)",
              new BatchPreparedStatementSetter() {
                 @Override
                 public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, people.get(i).getId());
                    ps.setString(2, people.get(i).getName());
                    ps.setString(3, people.get(i).getEmail());
                 }

                 @Override
                 public int getBatchSize() {
                    return people.size();
                 }
              }
      );


      long after = System.currentTimeMillis();
      System.out.println(after-before);
   }

   public void testMultiUpdate() {
      List<Person> people = create1000People();
      long before = System.currentTimeMillis();
      for (Person person: people) {
         jdbcTemplate.update("INSERT INTO Person(id, name, email) VALUES (?, ?, ?)",
                 person.getId(), person.getName(), person.getEmail());
      }
      long after = System.currentTimeMillis();

      System.out.println(after-before);

   }

   private List<Person> create1000People() {
      List<Person> personList = new ArrayList<>();
      for (int i = 0; i < 1000; i++) {
         personList.add(new Person(i, "Name" + i, "test" + i + "@gmail.com",  "Some Address"));
      }
      return personList;
   }


}
