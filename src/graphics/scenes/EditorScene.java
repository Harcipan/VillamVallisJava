package graphics.scenes;

import javax.swing.*;

public class EditorScene extends JPanel {

    public EditorScene() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel jl = new JLabel("This is the editor scene!");
        add(jl);
    }

}
