package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.company.MainPanel;

/**
 * Created by Kanes on 24.10.2016.
 */
public class MainWindow extends JFrame {

    MainPanel screen = new MainPanel();
    ConfigPanel config = new ConfigPanel();

    public MainWindow() {
        super();

        add(screen);
        add(config);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        setLocation(50, 50);


        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }








    public class ConfigPanel extends JPanel {

        JLabel info = new JLabel("Z = zaladz");
        JLabel info2 = new JLabel("B = bird");
        JLabel info3 = new JLabel("N = niesmiertelny 5x5");
        JLabel info4 = new JLabel("G = glider");
        JLabel info5 = new JLabel("L = line niesmiertelny");


        public ConfigPanel(){
            super();

            setFocusable(true);

            setVisible(true);

            setDoubleBuffered(true);

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(info);
            add(info2);
            add(info3);
            add(info4);
            add(info5);

        }


    }



}
