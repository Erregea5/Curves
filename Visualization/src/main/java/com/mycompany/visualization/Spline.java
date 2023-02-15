/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.visualization;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.*;
import java.math.*;;
/**
 *
 * @author Sebastian
 */

public class Spline extends Curve{
    public Spline(int p){
        name="Spline";
        points=new ArrayList<>();
        parts[0]=0;
        parts[1]=p;
    }
    public Spline(ArrayList<vec3> pList,int p){
        name="Spline";
        points=pList;
        parts[1]=p;
        parts[0]=(pList.size()-4)/3;
    }
    public Spline(vec3[] pList,int p){
        name="Spline";
        points=new ArrayList<vec3>(Arrays.asList(pList));
        parts[1]=p;
        parts[0]=(pList.length-4)/3;
    }
    public vec3 lerp(vec3 P0,vec3 P1,vec3 P2, vec3 P3,float t){
        return lerp(
            lerp(P0, P1, t),
            lerp(P2, P3, t),
            t);
    }
    @Override
    public vec3 lerp(vec3[] pList,float[] time){
        return lerp(new ArrayList<vec3>(Arrays.asList(pList)), time);
    }
    @Override
    public vec3 lerp(ArrayList<vec3> pList,float[] time){
        int c=(int)time[0]*3;
        return lerp(pList.get(c), pList.get(c+1), pList.get(c+2), pList.get(c+3), time[1]);
    }
}

