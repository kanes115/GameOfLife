package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Kanes on 24.10.2016.
 */
public class MainPanel extends JPanel implements MouseListener, KeyListener {

    private int WIDTH=310, HEIGHT=280; //jako ilość pól
    private int FIELDWIDTH=3, FIELDHEIGHT=3; //jako ilość pikseli, najlepiej, żeby były równe

    Field field = new Field(WIDTH, HEIGHT, FIELDWIDTH, FIELDHEIGHT);

    int counter=0;
    JLabel counterLabel = new JLabel("0");

    int delayTime=5;

    private boolean mouseOverPanel=false;

    public MainPanel(){
        super();

        setFocusable(true);

//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int width = (int) screenSize.getWidth();
//        int height = (int) screenSize.getHeight();
//        WIDTH=width/FIELDWIDTH-width/10;
//        HEIGHT=height/FIELDHEIGHT-height/40;


        setPreferredSize(new Dimension(WIDTH*FIELDWIDTH+10, HEIGHT*FIELDHEIGHT+10));

        setVisible(true);

        setDoubleBuffered(true);

        addMouseListener(this);
        addKeyListener(this);

        counterLabel.setFont(new Font("Italic", Font.BOLD, 20));
        add(counterLabel);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        field.paint(g);
    }

    public void counterInc(){
        counter++;
        counterLabel.setText(Integer.toString(counter));
    }


    public void setWIDTH(int width){WIDTH = width;}
    public void setHEIGHT(int height){HEIGHT = height;}
    public void setFieldSize(int size){
        FIELDHEIGHT=size;
        FIELDHEIGHT=size;
    }
    public void setDelayTime(int delayTime){this.delayTime = delayTime;}








    class GenerationMotor extends Thread{

        private int delayTime;

        public GenerationMotor(int delayTime){
            this.delayTime = delayTime;
        }

        private void waitDelayTime(){
            try {
                sleep(delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void run(){      //tu algorytm gry zmieniający tablicę fields[][] i repaintujący
            while(true) {
                field.nextGeneration();
                counterInc();
                updateUI();
                repaint();
                waitDelayTime();
            }
        }
    }









    //mouselistener:
    //metoda wywoływana, gdy następuje kliknięcie, czyli wciśnięcie i zwolnienie przycisku myszy, ale uwaga, obie te operacje muszą zajść w jednym położeniu.
    public void mouseClicked(MouseEvent event){
        int x = event.getX();
        int y = event.getY();
        if(!field.isTrue(field.getTabInd(x, y).x,field.getTabInd(x, y).y)) field.setLivingPoint(x, y);
        else field.setDeadPoint(x, y);
        updateUI();
        repaint();
    }

    //metoda wywoływana, gdy zostaje wciśnięty przycisk myszy\
    public void mousePressed(MouseEvent event){}

    //metoda wywoływana, gdy następuje zwolnienie przycisku myszy
    public void mouseReleased(MouseEvent event){}

    //metoda wywoływana, gdy kursor pojawia się w obszarze nasłuchującym na zdarzenia, na przykład panelu
    public void mouseEntered(MouseEvent event){mouseOverPanel=true; System.out.print(mouseOverPanel);}

    //metoda wywoływana, gdy kursor opuszcza obszar nasłuchujący zdarzenia
    public void mouseExited(MouseEvent event){mouseOverPanel=false; System.out.print(mouseOverPanel);}


    //Keylistener:
    public void keyTyped(KeyEvent e){}

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            new Thread(new GenerationMotor(delayTime)).start();
        }
        if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            field.nextGeneration();
            counterInc();
            updateUI();
            repaint();
        }
        if(e.getKeyCode()==KeyEvent.VK_B){
            int x = (int) getMousePosition().getX();
            int y = (int) getMousePosition().getY();
            if(mouseOverPanel) field.addBird(field.convertPxToTabInd(x, y).x, field.convertPxToTabInd(x, y).y);
            // /field.addBird(WIDTH/5, HEIGHT/4);
            updateUI();
            repaint();
        }
        if(e.getKeyCode()==KeyEvent.VK_G){
            int x = (int) getMousePosition().getX();
            int y = (int) getMousePosition().getY();
            if(mouseOverPanel) field.addGlider(field.convertPxToTabInd(x, y).x, field.convertPxToTabInd(x, y).y);
            updateUI();
            repaint();
        }
        if(e.getKeyCode()==KeyEvent.VK_Z){
            int x = (int) getMousePosition().getX();
            int y = (int) getMousePosition().getY();
            if(mouseOverPanel) field.addZoladz(field.convertPxToTabInd(x, y).x, field.convertPxToTabInd(x, y).y);
            updateUI();
            repaint();
        }
        if(e.getKeyCode()==KeyEvent.VK_L){
            int x = (int) getMousePosition().getX();
            int y = (int) getMousePosition().getY();
            if(mouseOverPanel) field.addNSLine(field.convertPxToTabInd(x, y).x, field.convertPxToTabInd(x, y).y);
            updateUI();
            repaint();
        }
        if(e.getKeyCode()==KeyEvent.VK_N){
            int x = (int) getMousePosition().getX();
            int y = (int) getMousePosition().getY();
            if(mouseOverPanel) field.addNS5(field.convertPxToTabInd(x, y).x, field.convertPxToTabInd(x, y).y);
            updateUI();
            repaint();
        }

    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE) System.exit(0);
    }

}
