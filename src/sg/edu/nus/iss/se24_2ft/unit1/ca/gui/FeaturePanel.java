package sg.edu.nus.iss.se24_2ft.unit1.ca.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yangzai on 12/3/16.
 */
public abstract class FeaturePanel extends JPanel {

    public FeaturePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public FeaturePanel(LayoutManager layout) {
        super(layout);
    }

    public FeaturePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public FeaturePanel() {}
}
