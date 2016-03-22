package sg.edu.nus.iss.se24_2ft.unit1.ca.util;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public abstract class OkCancelDialog extends JDialog {

    public OkCancelDialog (JFrame parent, String title) {
        super (parent, title);
        add ("Center", createFormPanel());
        add ("South",  createButtonPanel());
        setVisible(true);
        setSize(200, 200);
    }

    public OkCancelDialog (JFrame parent) {
        this (parent, "");
    }

    private JPanel createButtonPanel () {
        JPanel p = new JPanel ();

        JButton b;
        ActionListener l;

        b = new JButton ("OK");
        l = new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                boolean success = performOkAction ();
                if (success) {
                    destroyDialog ();
                }
            }
        };
        b.addActionListener (l);
        p.add (b);

        b = new JButton ("Cancel");
        l = new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	boolean success =performCancel();
                destroyDialog ();
            }
        };
        b.addActionListener (l);
        p.add (b);

        return p;
    }

    private void destroyDialog () {
        setVisible (false);
        dispose();
    }

    protected abstract JPanel createFormPanel () ;

    protected abstract boolean performOkAction () ;
    
    protected abstract boolean performCancel();

}