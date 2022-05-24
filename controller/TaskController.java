/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.connectionFactory;

/**
 *
 * @author Thiago Martins
 */
public class TaskController {

    public void save(Task task) {

        String sql = "INSERT INTO tasks (idProject, name, description,"
                + "completed, notes, deadline, createdAt, updatedAt)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAd().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAd().getTime()));
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa "
                    + ex.getMessage(), ex);
        } finally {
            connectionFactory.closeConnection(connection, statement);

        }

    }

    public void update(Task task) {

        String sql = "UPDATE tasks SET"
                + " idProject = ? "
                + "name = ? "
                + "description = ? "
                + "completed = ? "
                + "notes = ? "
                + "deadline = ? "
                + "createdAt = ? "
                + "updatedAt = ? "
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o Banco de dados.
            connection = connectionFactory.getConnection();
            
            // Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando so valores no statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAd().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAd().getTime()));
            statement.setInt(9, task.getId());
            
            //Executando a query
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar uma tarefa"
                    + ex.getMessage(), ex);
        }
    }

    public void removeById(int taskId){

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Criação da conexão com o Banco de dados
            connection = connectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            // Setando os valores
            statement.setInt(1, taskId);
            
            //Executando a query
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar uma tarefa"
                    + ex.getMessage(), ex);
        } finally {
            connectionFactory.closeConnection(connection, statement);
        }

    }

    public List<Task> getAll(int idProject) {

        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        //Lisat de tarefas que será devolvida quando a chamada do metodo acontecer
        List<Task> tasks = new ArrayList<Task>();

        try {
            
            // Conexão com o Banco de dados
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            // Setando um valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);
            
            // Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            // enquanto houverem valores a serem percorridos no resulSet
            while (resultSet.next()) {

                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAd(resultSet.getDate("createdAt"));
                task.setUpdatedAd(resultSet.getDate("updatedAt"));
                tasks.add(task);

            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir a tarefa"
                    + ex.getMessage(), ex);
        } finally {
            connectionFactory.closeConnection(connection, statement, resultSet);
        }

        //Lista de tarefas que foi criada e carregada do banco de dados
        return tasks;
    }
}
