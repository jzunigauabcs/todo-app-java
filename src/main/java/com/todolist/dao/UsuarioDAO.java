/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todolist.dao;

import com.todolist.model.Usuario;
import como.todolist.database.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jzuniga
 */
public class UsuarioDAO {
    public Usuario buscarEmailPassword(String email, String password) {
        Usuario usuario = null;
        Connection conn = null;
        ConnectionDB db = new ConnectionDB();
        try {
            conn = db.getConnection();
            String query = "SELECT * FROM " + Usuario.TABLE + " WHERE email = ? AND password=SHA1(?)";
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, email);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPrimerApellido(rs.getString("primer_apellido"));
                usuario.setSegundoApellido(rs.getString("segundo_apellido"));
                usuario.setEmail(rs.getString("email"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usuario;
    }
    
    public int guardar(String nombre, String primerApellido, String segundoApellido, String email, String password) {
        int result = 0;
        ConnectionDB db = new ConnectionDB();
        Connection conn = null;
        try {
            conn = db.getConnection();
            String query = "INSERT INTO " + Usuario.TABLE + "(nombre, primer_apellido, segundo_apellido, email, password) VALUE(?, ?, ?, ?, SHA1(?))";
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, nombre);
            pstm.setString(2, primerApellido);
            pstm.setString(3, segundoApellido);
            pstm.setString(4, email);
            pstm.setString(5, password);
            
            result = pstm.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}