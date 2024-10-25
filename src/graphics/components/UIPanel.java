package graphics.components;

import graphics.GameFrame;

import javax.swing.*;
import java.awt.*;

public class UIPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public UIPanel() {
        JLabel jl = new JLabel("This is the game scene!");
        add(jl);
        this.setBounds(0, 0, GameFrame.getInstance().getWidth(), GameFrame.getInstance().getHeight());
        this.setBackground(new Color(0,0,0,0));
        this.setOpaque(false); //without this the UIpanel is covering the game because it isn't truly transparent
    }

}
