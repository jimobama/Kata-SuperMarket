/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.dialog.DialogButtons;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import structures.InterfaceObserver;

/**
 *
 * @author Obaro
 */
public interface IView extends InterfaceObserver {

    public static final int INFORMATION = 0;
    public static final int WARNING = 1;
    public static final int ERROR = 2;

    void setModel(Object model);

    void show();

    static void report(GUIScreen screen, int type, String message) {
        switch (type) {
            case IView.ERROR:
                MessageBox.showMessageBox(screen, "Error", message, DialogButtons.OK);
                break;
            case IView.WARNING:
                MessageBox.showMessageBox(screen, "Warning", message, DialogButtons.OK);
                break;
            case IView.INFORMATION:
            default:
                MessageBox.showMessageBox(screen, "Information", message, DialogButtons.OK);
                break;

        }
    }

}
