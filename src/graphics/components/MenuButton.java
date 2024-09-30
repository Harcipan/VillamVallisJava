package graphics.components;

import java.awt.Color;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MenuButton extends JButton {
	public MenuButton(String s)
	{
		super(s);
		this.setBackground(new Color(255,255,255));
	}
}
