/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.visualization;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Sebastian
 */
public abstract class Curve{
    final int radius=20;
    protected ArrayList<vec3> points;
    static Graph g;
    JFrame jf;
    protected int[] parts=new int[2];
    protected String name;
    
    public static void test(String[] args){
        vec3[] vlist={new vec3(10, 10, 0),new vec3(200, 150, 0),new vec3(30, 350, 0), new vec3(100, 300, 0)};
        Spline b=new Spline(vlist,50);
        b.graph(2);
    }

    protected static vec3 lerp(vec3 P0,vec3 P1,float t){
        return P0.plus(P1.minus(P0).times(t));//P0+(P1-P0)*t
    }
    abstract vec3 lerp(vec3[] pList,float[] time);
    abstract vec3 lerp(ArrayList<vec3> pList,float[] time);
    private ArrayList<vec2> create2DCurve(){
        ArrayList<vec2> out=new ArrayList<>();
        float dt=1f/parts[1];
        for(int i=0;i<=parts[0];i++)
            for(int u=0;u<parts[1];u++){
                float[] t={i,dt*u};
                vec3 temp=lerp(points,t);
                out.add(new vec2(temp.x,temp.y));
            }
        return out;
    }
    private ArrayList<vec2> create3DCurve(){
        ArrayList<vec2> out=new ArrayList<>();
        float dt=1f/parts[1];
        for(int i=0;i<=parts[0];i++)
            for(int u=0;u<parts[1];u++)
                out.add(
                        lerp(points,new float[]{i,dt*u}).toVec2(10000)
                );
        return out;
    }
    private ArrayList<vec2> create2DControls(){
        ArrayList<vec2> out=new ArrayList<>();
        for(int i=0;i<points.size();i++){
            out.add(new vec2(points.get(i).x,points.get(i).y));
        }
        return out;
    }
    private ArrayList<vec2> create3DControls(){
        ArrayList<vec2> out=new ArrayList<>();
        for(int i=0;i<points.size();i++){
            out.add(points.get(i).toVec2(10000));
        }
        return out;
    }
    private float distance(float x, float y){
        return (float)Math.sqrt(x*x+y*y);
    }
    protected int selectedPoint(float x,float y, ArrayList<vec3> vlist){
        for(int i=0;i<vlist.size();i++)
            if(distance(vlist.get(i).x-x, vlist.get(i).y-y)<=radius)
                return i;
        return -1;
    }
    public static class mouseCom{
        boolean pressed=false;
        int selected=-1;
    }
    private void addMouseListener(){
        Curve me=this;
        jf.addMouseListener(new MouseInputListener() {
            mouseCom c=new mouseCom();
            public void mousePressed(MouseEvent e) {
                //System.out.println("pressed");
                c.pressed=true;
                c.selected=me.selectedPoint(e.getXOnScreen(), e.getYOnScreen(), points);
                if(c.selected==-1){
                    c.pressed=false;
                    return ;
                }
            }
            public void mouseReleased(MouseEvent e) {
                //System.out.println(c.selected);
                if(c.pressed&&c.selected!=-1){
                    vec3 update=new vec3(e.getXOnScreen(),e.getYOnScreen(),0);
                    points.set(c.selected, update);
                    Spline b=new Spline(points,50);
                    b.regraph(2);
                }
                c.selected=-1;
                c.pressed=false;
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
            public void mouseMoved(MouseEvent e) {}
            public void mouseDragged(MouseEvent e) {}
        });
    }
    protected void graph(int dim){
        if(dim!=2&&dim!=3)
            return;
        jf=new JFrame(name);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setSize(400, 300);
        addMouseListener();
        Container c=jf.getContentPane();
        ArrayList<vec2> curve,controls;
        if(dim==2){
            controls=create2DControls();
            curve=create2DCurve();
        }
        else{
            controls=create3DControls();
            curve=create3DCurve();
        }
        g=new Graph(curve,controls,radius);
        c.add(g);
    }
    protected void regraph(int dim){
        if(dim!=2&&dim!=3)
            return;
        ArrayList<vec2> curve,controls;
        if(dim==2){
            controls=create2DControls();
            curve=create2DCurve();
        }
        else{
            controls=create3DControls();
            curve=create3DCurve();
        }
        g.regraph(curve,controls);
    }
}
