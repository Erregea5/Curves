/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.visualization;

import static com.mycompany.visualization.Curve.lerp;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Sebastian
 */
public class BezierCurve extends Curve{
    public BezierCurve(int p){
        points= new ArrayList<>();
        name="Bezier";
        parts[0]=0;
        parts[1]=p;
    }
    public BezierCurve(ArrayList<vec3> pList,int p){
        points=pList;
        name="Bezier";
        parts[0]=0;
        parts[1]=p;
    }
    public BezierCurve(vec3[] pList,int p){
        points=new ArrayList<vec3>(Arrays.asList(pList));
        name="Bezier";
        parts[0]=0;
        parts[1]=p;
    }
    public vec3 lerp(vec3[] pList,float[] time){
        return recursivelerp(pList, time[1]);
    }
    @Override
    public vec3 lerp(ArrayList<vec3> pList,float[] time){
        return recursivelerp(pList, time[1]);
    }
    public vec3 recursivelerp(vec3[] pList,float t){
        return recursivelerp(new ArrayList<vec3>(Arrays.asList(pList)), t);
    }
    public vec3 recursivelerp(ArrayList<vec3> pList,float t){
        if(pList.size()==1)
            return pList.get(0);
        vec3[] newPList=new vec3[pList.size()-1];
        for(int i=0;i<newPList.length;i++){
            newPList[i]=lerp(pList.get(i), pList.get(i+1), t);
        }
        return recursivelerp(newPList, t);
    }
    public vec3 bernsteinPolynomial(vec3[] pList,float t){
        return bernsteinPolynomial(new ArrayList<vec3>(Arrays.asList(pList)), t);
    }
    public vec3 bernsteinPolynomial(ArrayList<vec3> pList,float t){
        float omt=1-t;
        int len=pList.size()-1;
        vec3 sum=new vec3();
        for(int i=0;i<=len;i++){
            sum=sum.plus(
                pList.get(i).times(
                    choose(len,i)*(float)Math.pow(omt, len-i)*(float)Math.pow(t, i)
                    )
                );
        }
        return sum;
    }
    static float factorial(int n){
        float f=1f;
        for(int i=n;i>1;i--){
            f*=i;
        }
        return f;
    }
    static float choose(int n,int k){
        return factorial(n)/(factorial(k)*factorial(n-k));
    }
}
