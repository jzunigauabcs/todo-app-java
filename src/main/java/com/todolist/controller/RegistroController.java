/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todolist.controller;

import com.todolist.dao.UsuarioDAO;
import com.todolist.view.RegistroView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author jzuniga
 */
public class RegistroController {

    private RegistroView registroV;
            
    public RegistroController() {
    }
    
    public void init() {
        this.registroV = new RegistroView();
        this.registroV.setVisible(true);
        this.registroV.btnRegistrarUsuarioClick(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                registro();
            }
        
        });
    }
    
    public void registro() {
        String nombre = this.registroV.getJtfNombre().getText();
        String primerApellido = this.registroV.getJtfPrimerApellido().getText();
        String segundoApellido = this.registroV.getJtfSegundoApelldio().getText();
        String email = this.registroV.getJtfEmail().getText();
        String password = String.valueOf(this.registroV.getJpwfPassword().getPassword());
        String repeatPassword = String.valueOf(this.registroV.getJpwfRepetirPassword().getPassword());
        
        if(!password.equals(repeatPassword)) {
            JOptionPane.showMessageDialog(this.registroV, "Las contraseñas no coinciden", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            UsuarioDAO usuarioDao = new UsuarioDAO();
            if(usuarioDao.guardar(nombre, primerApellido, segundoApellido, email, password) == 1) {
                JOptionPane.showMessageDialog(this.registroV, "Registro exitoso", "Success", JOptionPane.INFORMATION_MESSAGE);
                LoginController loginController = new LoginController();
                loginController.init();
                this.registroV.dispose();
            } else {
                JOptionPane.showMessageDialog(this.registroV, "Ocurrió un error al intentar registrar los datos del usuario", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
}
