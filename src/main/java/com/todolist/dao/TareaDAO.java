/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todolist.dao;

import com.todolist.model.Tarea;
import com.todolist.model.Usuario;
import como.todolist.database.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jzuniga
 */
public class TareaDAO {
    public ArrayList<Tarea> buscar(int idUsuario) {
        ArrayList<Tarea> tareas = new ArrayList<Tarea>();
        Connection conn = null;
        ConnectionDB db = new ConnectionDB();
        
        try {
            conn = db.getConnection();
            String query = "SELECT * FROM " + Tarea.TABLE + " WHERE usuario_id = ?";
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, idUsuario);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                Tarea tarea = new Tarea(rs.getInt("id"), rs.getString("tarea"), rs.getBoolean("status"), rs.getInt("usuario_id"));
                tareas.add(tarea);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                db.close();
            } catch (SQLException ex) {
                Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tareas;
    }
    
    public Tarea buscarPorID(int id) {
        Tarea tarea = null;
        Connection conn = null;
        ConnectionDB db = new ConnectionDB();
        
        try {
            conn = db.getConnection();
            String query = "SELECT * FROM " + Tarea.TABLE + " WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) {
                tarea = new Tarea(rs.getInt("id"), rs.getString("tarea"), rs.getBoolean("status"), rs.getInt("usuario_id"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                db.close();
            } catch (SQLException ex) {
                Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tarea;
    }
    
    public Tarea guardar(String tarea, int usuarioId) {
        Tarea tareaCreada = null;
        Connection conn = null;
        ConnectionDB db = new ConnectionDB();
        try {
            conn = db.getConnection();
            String query = "INSERT INTO " + Tarea.TABLE + "(tarea, usuario_id) VALUE(?, ?)";
            PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, tarea);
            pstm.setInt(2, usuarioId);
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if(rs != null && rs.next()) {
                tareaCreada = this.buscarPorID(rs.getInt(1));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                db.close();
            } catch (SQLException ex) {
                Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tareaCreada;
    }
    
    public int actualizarStatus(boolean status, int id) {
        int result = 0;
        Connection conn = null;
        ConnectionDB db = new ConnectionDB();
        try {
            conn = db.getConnection();
            String query = "UPDATE " + Tarea.TABLE + " SET status = ? WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setBoolean(1, status);
            pstm.setInt(2, id);
            result = pstm.executeUpdate();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                db.close();
            } catch (SQLException ex) {
                Logger.getLogger(TareaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
}
