/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todolist.model;

/**
 *
 * @author jzuniga
 */
public class Tarea {
    private int id;
    private String tarea;
    private boolean status;
    private int usuario_id;
    
    public final static String TABLE = "tareas";

    public Tarea() {
    }
    
    public Tarea(int id, String tarea, boolean status, int usuario_id) {
        this.id = id;
        this.tarea = tarea;
        this.status = status;
        this.usuario_id = usuario_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    
    
}
