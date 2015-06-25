/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;

/**
 *
 * @author Obaro
 * @param <T>
 */
public interface InterfaceModel<T> {
    void add(T t);
    void remove(T t);
    void update(T t);
    ArrayList<T>  getList();
    boolean isExist(T t); 
    T getItemById(String id);

}
