/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todolist.controller;

import com.todolist.dao.TareaDAO;
import com.todolist.model.Tarea;
import com.todolist.model.Usuario;
import com.todolist.view.TodoListView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jzuniga
 */
public class TodoListController {
    private Usuario usuario;
    private TodoListView todoListV;
    private TareaDAO tareaDao;
    
    public TodoListController() {
        this.tareaDao = new TareaDAO();
    }
    
    
    
    public void init(Usuario usuario) {
        this.usuario = usuario;
        this.todoListV = new TodoListView();
        this.todoListV.setVisible(true);
        this.todoListV.jbtnGuardarTareaClick(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                guardarTarea(todoListV.getJtfTarea().getText(), usuario.getId());
            }
        });
        this.todoListV.jtblTareasCheckListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                 final int COLUMNA_ID = 0;
                 final int COLUMNA_TAREA = 1;
                final int COLUMNA_STATUS = 2;
                int fila = tme.getFirstRow();
                int columna = tme.getColumn();
                
                if(columna == COLUMNA_STATUS) {
                    DefaultTableModel model = (DefaultTableModel) tme.getSource();
                    Boolean estaCompletada = (Boolean) model.getValueAt(fila, columna);
                    int id = (int) model.getValueAt(fila, COLUMNA_ID);
                    String tarea = (String) model.getValueAt(fila, COLUMNA_TAREA);
                    tareaDao.actualizarStatus(estaCompletada, id); 
                    
                    //model.setValueAt(todoListV.alternarTarea(estaCompletada, tarea), fila, COLUMNA_TAREA);
                    
                    
                }
            }
        });
        this.cargarTareas();
    }
    
    public void cargarTareas() {
        
        ArrayList<Tarea> tareas = tareaDao.buscar(this.usuario.getId());
        
        for(Tarea tarea : tareas) {
            this.agregarTareaALista(tarea);            
        }
    }
    
    public void agregarTareaALista(Tarea tarea) {
        DefaultTableModel tableModel = (DefaultTableModel)this.todoListV.getJtblTareas().getModel();
        Object row[] = new Object[3];
        row[0] = tarea.getId();
        row[1] = tarea.getTarea();
        row[2] = tarea.getStatus();
        tableModel.addRow(row);
    }
    
    public void guardarTarea(String tarea, int usuarioId) {
        Tarea nuevaTarea = this.tareaDao.guardar(tarea, usuarioId);
        if(nuevaTarea == null) {
            JOptionPane.showMessageDialog(this.todoListV, "Ocurrió un error al intentar guardar la tarea", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            this.agregarTareaALista(nuevaTarea);
            this.todoListV.limpiarJtbfTarea();
        }
        
    }
}
