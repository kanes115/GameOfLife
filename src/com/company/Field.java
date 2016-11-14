package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.*;

/**
 * Created by Kanes on 24.10.2016.
 */
public class Field {
    public final int WIDTH, HEIGHT; //jako ilość pól
    public final int FIELDWIDTH, FIELDHEIGHT; //jako ilość pikseli, najlepiej, żeby były równe

    private boolean [][]fields; //true to żywe pole

    private boolean [][]tmp;


    public Field(int width, int height, int fieldWidth, int fieldHeight){
        this.WIDTH = width;
        this.HEIGHT=height;
        this.FIELDHEIGHT=fieldHeight;
        this.FIELDWIDTH=fieldWidth;
        fields = new boolean[WIDTH][HEIGHT];
        for(int i=0; i<WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                fields[i][j] = false;
            }
        }
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for(int i=0; i<HEIGHT; i++){
            for(int j=0; j<WIDTH; j++){
                if(fields[j][i]){ g.fillRect(j*FIELDWIDTH, i*FIELDHEIGHT, FIELDWIDTH, FIELDHEIGHT);}
                else g.drawRect(j*FIELDWIDTH, i*FIELDHEIGHT, FIELDWIDTH, FIELDHEIGHT);

            }
        }

    }

    private boolean outOfScope(int i, int j){
        if(i<0 || j<0) return true;
        if(i>=WIDTH || j>=HEIGHT) return true;
        return false;
    }

    private int amountOfNeighbours(int i, int j){
        int neighbours=0;
        for(int k=i-1; k<=i+1; k++){
            for(int l=j-1; l<=j+1; l++){
                if(!outOfScope(k, l) && fields[k][l] && (k!=i || j!=l)) neighbours++;
            }
        }
        return neighbours;
    }

    public void nextGeneration(){

        tmp = new boolean [WIDTH][HEIGHT];
        for(int i=0; i<WIDTH; i++)
            for(int j=0; j<HEIGHT; j++) tmp[i][j]=false;

        for(int i=0; i<WIDTH; i++){
            for(int j=0; j<HEIGHT; j++){
                if(amountOfNeighbours(i, j)<2 || amountOfNeighbours(i, j)>3) tmp[i][j] = false;
                if(amountOfNeighbours(i, j)==3) tmp[i][j] = true;
                if(amountOfNeighbours(i, j)==2) tmp[i][j]=fields[i][j];
            }
        }
        fields = tmp;
    }


    public void showTableAsPlainText(){
        for(int i=0; i<WIDTH; i++){
            System.out.println("");
            for(int j=0; j<HEIGHT; j++) System.out.print(fields[i][j] + "|");
        }
    }

    public TabInd convertPxToTabInd(int x, int y){
        return new TabInd(x/FIELDWIDTH, y/FIELDHEIGHT);
    }

    public void setLivingPoint(int xx, int yy){
        int x = convertPxToTabInd(xx, yy).x;
        int y = convertPxToTabInd(xx, yy).y;
        fields[x][y] = true;
    }

    public void setDeadPoint(int xx, int yy){
        int x = convertPxToTabInd(xx, yy).x;
        int y = convertPxToTabInd(xx, yy).y;
        fields[x][y] = false;
    }

    public TabInd getTabInd(int x, int y){  //argumenty są pozycją w pikselach
        return new TabInd(convertPxToTabInd(x, y).x, convertPxToTabInd(x, y).y);
    }

    public boolean isTrue(int x, int y){    //argumenty są pozycja w tablicy
        return fields[x][y];
    }


    public void addBird(int x, int y){  //argumenty są pozycją w tablicy
        fields[x][y]=true;
        fields[x-1][y]=true;
        fields[x-2][y]=true;
        fields[x-3][y]=true;
        fields[x][y+1]=true;
        fields[x][y+2]=true;
        fields[x-1][y+3]=true;
        fields[x-4][y+3]=true;
        fields[x-4][y+1]=true;
    }

    public void addGlider(int x, int y){
        fields[x][y]=true;
        fields[x-1][y]=true;
        fields[x-2][y]=true;
        fields[x-2][y+1]=true;
        fields[x-1][y+2]=true;

    }

    public void addZoladz(int x, int y){
        fields[x][y]=true;
        fields[x][y+2]=true;
        fields[x-1][y+2]=true;
        fields[x+2][y+1]=true;
        fields[x+3][y+2]=true;
        fields[x+4][y+2]=true;
        fields[x+5][y+2]=true;
    }

    public void addNS5(int x, int y){
        fields[x][y]=true;
        fields[x+1][y]=true;
        fields[x+2][y]=true;
        fields[x][y+1]=true;
        fields[x+4][y]=true;
        fields[x+4][y+2]=true;
        fields[x+4][y+3]=true;
        fields[x+4][y+4]=true;
        fields[x+3][y+2]=true;
        fields[x+2][y+3]=true;
        fields[x+2][y+3]=true;
        fields[x+1][y+3]=true;
        fields[x][y+4]=true;
        fields[x+2][y+4]=true;
    }

    public void addNSLine(int x, int y){
        fields[x][y]=true;
        for(int i=0; i<14 && i!=8; i++) fields[x+i][y]=true;
        for(int i=17; i<20; i++) fields[x+i][y]=true;
        for(int i=26; i<38 && i!=33; i++) fields[x+i][y]=true;
    }

}
