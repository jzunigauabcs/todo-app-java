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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jzuniga
 */
public class TodoListController {
    private TodoListView todoListV;
    private TareaDAO tareaDao;
    private Usuario usuario;

    public TodoListController() {
        
    }
    
    public void init(Usuario usuario) {
        this.usuario = usuario;
        this.tareaDao = new TareaDAO();
        this.todoListV = new TodoListView();
        this.todoListV.jbtnGuardarTareaClick(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String tarea = todoListV.getJtflTarea().getText();
                guardarTarea(tarea, usuario.getId());
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
                    DefaultTableModel tableModel = (DefaultTableModel) tme.getSource();
                    Boolean estaCompleta = (Boolean)tableModel.getValueAt(fila, columna);
                    int id = (Integer)tableModel.getValueAt(fila, COLUMNA_ID);
                    tareaDao.actualizarStatus(estaCompleta, id);
                }
            }
        });
        this.cargarTareas();
        this.todoListV.setVisible(true);
    }
    
    public void guardarTarea(String tarea, int usuarioId) {
        Tarea tareaNueva = this.tareaDao.guardar(tarea, usuarioId);
        
        if(tareaNueva == null) {
            JOptionPane.showMessageDialog(this.todoListV, "Ocurrió un error al intentar guardar la tarea", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            this.todoListV.limpiarJtflTarea();
            DefaultTableModel tableModel = (DefaultTableModel)this.todoListV.getJtblTodoList().getModel();
            this.agregarTareaFila(tableModel, crearFila(tareaNueva));
        }
    }
    
    public void cargarTareas() {
        ArrayList<Tarea> tareas = this.tareaDao.buscarTodasPorUsuario(usuario.getId());
       DefaultTableModel tableModel = (DefaultTableModel)this.todoListV.getJtblTodoList().getModel();
       
        for(Tarea tarea : tareas) {
           this.agregarTareaFila(tableModel, crearFila(tarea));
        }
    }
    
    public Object[] crearFila(Tarea tarea) {
        Object fila[] = new Object[3];
        fila[0] = tarea.getId();
        fila[1] = tarea.getTarea();
        fila[2] = tarea.getStatus();
        return fila;
    }
    
    public void agregarTareaFila(DefaultTableModel tableModel, Object fila[]) {
          tableModel.addRow(fila);
    }
   
}
