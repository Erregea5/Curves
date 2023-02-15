/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Sebastian
 */
public class Graph extends JPanel{
    ArrayList<vec2> points;
    ArrayList<vec2> controls;
    int radius;
    public Graph(ArrayList<vec2> pList,ArrayList<vec2> cList,int r){
        points=pList;
        controls=cList;
        radius=r;
    }
    public Graph(ArrayList<vec2> pList){
        points=pList;
        controls=new ArrayList<>();
        radius=2;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int[] x=new int[points.size()*2];
        int[] y=new int[points.size()*2];
        for(int i=0;i<controls.size();i++){
            g.fillOval((int)controls.get(i).x-radius, (int)controls.get(i).y-radius, radius, radius);
        }
        for(int i=0;i<points.size()-1;i++){
            g.drawLine((int)points.get(i).x,(int) points.get(i).y, (int)points.get(i+1).x, (int)points.get(i+1).y);
            /*x[i]=(int)points.get(i).x;
            y[i]=(int) points.get(i).y;
            x[x.length-i-1]=(int)points.get(i).x-5;
            y[y.length-i-1]=(int)points.get(i).y+10;*/
        }
        //g.fillPolygon(x, y, points.size()*2);
    }
    public void regraph(ArrayList<vec2> pList,ArrayList<vec2> cList){
        points=pList;
        controls=cList;
        repaint();
    }
    public void regraph(ArrayList<vec2> pList){
        points=pList;
        repaint();
    }
}
