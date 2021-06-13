/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todolist.controller;

import com.todolist.dao.UsuarioDAO;
import com.todolist.model.Usuario;
import com.todolist.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author jzuniga
 */
public class LoginController {
    private LoginView loginV;

    public LoginController() {
    }
    
    public void init() {
        this.loginV = new LoginView();
        this.loginV.setVisible(true);
        
        this.loginV.jbtnLoginClick(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                login();
            }
            
        });
    }
    
    public void login() {
        String email = this.loginV.getJtfEmail().getText();
        String password = String.valueOf(this.loginV.getJpwfPassword().getPassword());
        UsuarioDAO usuarioDao = new UsuarioDAO();
        Usuario usuario = usuarioDao.buscarEmailPassword(email, password);
        if(usuario == null) {
            JOptionPane.showMessageDialog(loginV, "Usuario o contraseña incorrecto", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(loginV, "Bienvenido " + usuario.getNombre(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
}
