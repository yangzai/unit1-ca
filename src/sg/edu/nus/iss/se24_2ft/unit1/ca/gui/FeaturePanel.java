package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by yangzai on 12/3/16.
 */
public class FeaturePanel extends JPanel {
    private List<ActionListener> backActionListenerList;

    public FeaturePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        backActionListenerList = new ArrayList<>();
    }

    public FeaturePanel(LayoutManager layout) {
        super(layout);
        backActionListenerList = new ArrayList<>();
    }

    public FeaturePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        backActionListenerList = new ArrayList<>();
    }

    public FeaturePanel() {
        backActionListenerList = new ArrayList<>();
    }

    public void addBackActionListener(ActionListener l) {
        backActionListenerList.add(l);
    }

    public void backActionPerformed(ActionEvent e) {
        backActionListenerList.forEach(l -> l.actionPerformed(e));
    }
}
