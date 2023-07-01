package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TokenForm extends JFrame {
    private JPanel rootPanel;
    private JTextField appField;
    private JTextField tokenField;
    private JButton saveButton;


    public TokenForm(String name) {
        super(name);

        setContentPane(rootPanel);
        setSize(200, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);

        saveButton.addActionListener(new SaveListener(this));
    }

    private class SaveListener implements ActionListener {
        private final JFrame jFrame;

        public SaveListener(JFrame jFrame) {
            this.jFrame = jFrame;
        }
        @Override
        public void actionPerformed(ActionEvent e) {

            /*
            try {
                System.out.println(Paths.get("env.json").toAbsolutePath());
                FileReader fr = new FileReader("/home/prom/IdeaProjects/vkParser/src/main/env.json");
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
             */

            Path path = Paths.get("env.json").toAbsolutePath();

            try (FileReader fr = new FileReader(path.toFile())) {
                System.out.println(fr);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            jFrame.dispose();
        }
    }

}
