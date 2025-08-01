package com.cattotech.todo.cli.repository;

import com.cattotech.todo.cli.model.Task;
import com.cattotech.todo.cli.utils.Priority;
import com.cattotech.todo.cli.utils.Status;
import com.cattotech.todo.cli.utils.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskRepository implements ITaskRepository {

    // relative route from SQL database
    private final String URL = "jdbc:sqlite:catto.db";

    // Methods here
    // initializacion method for the db, must be used in main
    @Override
    public void initializeDataBase() {
        String createDatabase = """
                    CREATE TABLE IF NOT EXITS tasks (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL UNIQUE,
                        descritption TEXT,
                        priority TEXT,
                        status TEXT,
                        limit TEXT
                    );
                                """;

        try (Connection cnn = DriverManager.getConnection(URL); Statement stmt = cnn.createStatement()) {

            stmt.execute(createDatabase);

        } catch (SQLException ex) {
            System.out.println("Somenthing go wrong starting DB, try later");
            // nothing for now
        }
    }

    @Override
    public Task getTaskByName(String name) {

        String sql = "SELECT * FROM tasks WHERE name = ?";
        Task result = new Task(name);

        try (Connection cnn = DriverManager.getConnection(URL); PreparedStatement stmt = cnn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    
                    // pass the data to constructor
                    result.setId(rs.getLong("id"));
                    result.setDescription(rs.getString("description"));
                    result.setStatus(Status.valueOf(rs.getString("status")));
                    result.setPriority(Priority.valueOf(rs.getString("priority")));
                    
                    // convert to LocalDate and asing to Task
                    LocalDate limitDate = Utils.getDate(rs.getString("limit"));
                    result.setDateLimit(limitDate);
                    
                } else {
                    // just for the moment, check later
                    
                    return null;
                }
            } catch (SQLException ex) {
                // don't forget the catch
            }

        } catch (SQLException ex) {
            // don't forget the catch
        }
        
        
        return result;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteByName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // the text must be saved as TEXT
    @Override
    public void isertTask(Task task) {
        // the null date must be inserted in DB like none
        String sql = "INSERT INTO tasks(name, description, priority, status, limit) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection cnn = DriverManager.getConnection(URL);
            PreparedStatement stmt = cnn.prepareStatement(sql)) {
            
            // Get the values from the object and complete the query
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getPriority().name());
            stmt.setString(4, task.getStatus().name());
            
            String dateToSave = (task.getDateLimit() != null) ? task.getDateLimit().toString() : "none";
            stmt.setString(5, dateToSave);
            
            // execute query, no expected return
            boolean check = stmt.execute();
            
            if (!check) {
                System.out.println("Something go wrong saving the task");
            }
            
        } catch (SQLException ex) {
            // TODO            
        }
    }

    @Override
    public void updateTask(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Task getTaskById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public TaskRepository() {
    }
    
}
