/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todolist;

import com.todolist.controller.LoginController;



/**
 *
 * @author jzuniga
 */
public class TodoList {
    public static void main(String[] args) {
        LoginController loginController = new LoginController();
        loginController.init();
    }
}
