package org.example.model;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {
   private static int PEOPLE_COUNT;


   private static final String URL = "jdbc:mysql://localhost:3306/springmvc_db";
   private static String USERNAME = "root";
   private static String PASSWORD = "zamandash2006";

   private static Connection connection;

   static {
      try {
         Class.forName("com.mysql.jdbc.Driver"); // postgresql - "org.postgresql.Driver"
      } catch (ClassNotFoundException e) {
         throw new RuntimeException(e);
      }

      try {
         connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public List<Person> getAll(){
      List<Person> people = new ArrayList<>();

      try {
         Statement statement = connection.createStatement();
         String SQL  = "SELECT * FROM Person";
         ResultSet resultSet = statement.executeQuery(SQL);

         while(resultSet.next()){
            Person person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));

            people.add(person);
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }


      return people;
   }

   public Person getOne(int id){
//      return peopleDB.stream().filter(person -> person.getId()==id).findAny().orElse(null);
      Person person;
      try {
         PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Person WHERE id=?");
         preparedStatement.setInt(1, id);

         ResultSet resultSet = preparedStatement.executeQuery();

         resultSet.next();

         person = new Person();

         person.setId(resultSet.getInt("id"));
         person.setName(resultSet.getString("name"));
         person.setAge(resultSet.getInt("age"));
         person.setEmail(resultSet.getString("email"));


      } catch (SQLException e) {
         throw new RuntimeException(e);
      }


      return person;
   }

   public void save(Person person){
      try {
         PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Person VALUES(1, ?, ?, ?)");
         preparedStatement.setString(1, person.getName());
         preparedStatement.setInt(2, person.getAge());
         preparedStatement.setString(3, person.getEmail());

         preparedStatement.executeUpdate();

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public void update(int id, Person updatedPerson){
      Person personToUpdate = getOne(id);

      personToUpdate.setName(updatedPerson.getName());
      personToUpdate.setAge(updatedPerson.getAge());
      personToUpdate.setEmail(updatedPerson.getEmail());
   }

   public void delete(int id){
      PreparedStatement preparedStatement = null;
      try {
         preparedStatement = connection.prepareStatement("DELETE FROM Person WHERE id=?");
         preparedStatement.setInt(1, id);
         preparedStatement.executeUpdate();

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

}
