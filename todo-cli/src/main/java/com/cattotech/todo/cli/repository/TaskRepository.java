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
import java.util.List;

public class TaskRepository implements ITaskRepository {

    // relative route from SQL database
    private final String URL = "jdbc:sqlite:catto.db";

    // Methods here
    // initializacion method for the db, must be used in main
    @Override
    public void initializeDataBase() {
        String createDatabase = """
                    CREATE TABLE IF NOT EXISTS tasks (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL UNIQUE,
                        description TEXT,
                        priority TEXT,
                        status TEXT,
                        limit_date TEXT
                    );
                                """;

        try (Connection cnn = DriverManager.getConnection(URL); Statement stmt = cnn.createStatement()) {

            stmt.execute(createDatabase);

        } catch (SQLException ex) {
            System.out.println("Somenthing go wrong starting DB, try later");
            System.err.println(ex.getMessage());
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
                    LocalDate limitDate = Utils.getDate(rs.getString("limit_date"));
                    result.setDateLimit(limitDate);
                    
                } else {
                    // just for the moment, check later
                    
                    return null;
                }
            } catch (SQLException ex) {
                // don't forget the catch
                System.err.println(ex.getMessage());
            }

        } catch (SQLException ex) {
            // don't forget the catch
            System.err.println(ex.getMessage());
        }
        
        
        return result;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        String sql = "SELECT * FROM tasks";
        ArrayList results = new ArrayList<Task>();
        
        try (Connection cnn = DriverManager.getConnection(URL);
            Statement stmt = cnn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                do {
                    //create task and load it
                    String name = rs.getString("name");
                    Long id = rs.getLong("id");
                    String description= rs.getString("description");
                    Status st = Status.valueOf(rs.getString("status"));
                    Priority pr = Priority.valueOf(rs.getString("priority"));
                    LocalDate date = Utils.getDate(rs.getString("limit_date"));

                    // create instance of task and set id
                    Task taskToList = new Task(name, description, pr, st, date);
                    taskToList.setId(id);
                    
                    results.add(taskToList);
                    
                } while(rs.next());
            } else {
                return null;
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        
        return results;
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
    public void insertTask(Task task) {
        // the null date must be inserted in DB like none
        String sql = "INSERT INTO tasks(name, description, priority, status, limit_date) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection cnn = DriverManager.getConnection(URL);
            PreparedStatement stmt = cnn.prepareStatement(sql)) {
            
            // Get the values from the object and complete the query
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getPriority().name());
            stmt.setString(4, task.getStatus().name());
            
            
            String dateToSave = (task.getDateLimit() != null) ? Utils.formatDate(task.getDateLimit()) : "none";
            stmt.setString(5, dateToSave);
            
            // execute query, no expected return
            stmt.execute();
            
        } catch (SQLException ex) {
            // TODO
            System.err.println(ex.getMessage());
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
