package graphics.components;

import java.awt.Color;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MenuButton extends JButton {
	public MenuButton(String s)
	{
		super(s);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setFont(this.getFont().deriveFont(20.0f));
		/*this.setOpaque(true);
		this.setBackground(Color.GRAY);*/
		this.setBorder(null);
	}
}
