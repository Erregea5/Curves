/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.visualization;
import java.util.*;
import java.lang.Math;
/**
 *
 * @author Sebastian
 */
public class MathEvaluator {

    public static void test(String[] args){
        MathEvaluator e=new MathEvaluator();
        String tr="";
        int[] g={2,4,6,8,10,11,12};
        int[] t={14, 15,17,19,21,23,27};
        for(int i=0;i<30;i++){
            int numi=(int)(Math.random()*5000);
            double num=(double)numi/100.;
            int op=(int)Math.round(Math.random()*5);
            tr+=num;
            for(int u:t)if(u==i)tr+=")";
            tr+=op==0?"+":op==1?"-":op==2?"*":op==3?"/":op==4?"^":"%";
            for(int u:g)if(u==i)tr+="(";
            
        }
        System.out.println(e.calculate(tr.substring(0,tr.length()-2)));
        //System.out.println((1^3^1^2^3^1^3));
    }
    
    public float calculate(String expression) {
        String[] express=expression.split(" ");
        expression="";
        for(String in:express)
            expression+=in;
        if(expression.equals(""))return 0;
        
        String nums="0123456789";
        float output=0.0f;
        ArrayList<Float> numlist=new ArrayList<Float>();
        ArrayList<String> functs=new ArrayList<String>();
        float div=1,
               cur=0;
        boolean deci=false,
                zero=false,
                neg=false;
        if(expression.startsWith("-")){
            expression=expression.substring(1);
            neg=true;
        }
        
        for (int i = 0; i < expression.length(); i++) {
            //System.out.println("  "+expression+" ");
            if (expression.startsWith("(", i)) {
                
                int bcount =1,ecount=0;
                int in=i+1;
                
                while(bcount!=ecount){
                    bcount+=expression.startsWith("(", i+1)?1:0;
                    ecount+=expression.startsWith(")", i+1)?1:0;
                    i++;
                }
                if(neg)
                    numlist.add(-calculate(expression.substring(in, i)));
                else
                    numlist.add(calculate(expression.substring(in, i)));
                //System.out.println("dgh"+expression.substring(in, i));
            }
            else if(expression.startsWith(".", i))
                deci=true;
            else if(nums.contains(expression.substring(i, i+1))){
                int next=Integer.parseInt(expression.substring(i, i+1));
                if(next==0)zero=true;
                if(deci){
                    div*=10;
                    cur= Math.abs(cur)+(float)(next/div);
                }
                else 
                    cur=Math.abs((cur*10))+next;
                if(neg)
                    cur=-cur;
                if(i==expression.length()-1)
                    numlist.add(cur);
            }
            else{
                deci=false;
                neg=false;
                div=1;
                if(cur!=0||zero)
                    numlist.add(cur);
                zero=false;
                cur=0;
                
                //dealing with expressions
                String fir=expression.substring(i, i+1);
                String sec=expression.substring(i+1, i+2);
                if(fir.equals("+")&&sec.equals("+"))
                    functs.add("+");
                else if(fir.equals("-")&&sec.equals("-"))
                    functs.add("+");
                else if(fir.equals("+")&&sec.equals("-"))
                    functs.add("-");
                else if(fir.equals("-")&&sec.equals("+"))
                    functs.add("-");
                else if(fir.equals("*")&&sec.equals("-")){
                    functs.add("*");
                    neg=true;
                }
                else if(fir.equals("/")&&sec.equals("-")){
                    functs.add("/");
                    neg=true;
                }
                else if(fir.equals("^")&&sec.equals("-")){
                    functs.add("^");
                    neg=true;
                }
                else if(fir.equals("%")&&sec.equals("-")){
                    functs.add("%");
                    neg=true;
                }
                else{ 
                    functs.add(expression.substring(i, i+1));
                    i--;
                }
                i++;
            }
        }
        //System.out.println("      "+functs+"/"+numlist);
        for(int i=0;i<functs.size();i++){
            float fir=numlist.get(i);
            float sec=numlist.get(i+1);
            if(functs.get(i).equals("^")){
                functs.remove(i);
                numlist.remove(i);
                numlist.remove(i);
                numlist.add(i, (float)Math.pow(fir, sec));
                i--;
            }
        }
        for(int i=0;i<functs.size();i++){
            float fir=numlist.get(i);
            float sec=numlist.get(i+1);
            if(functs.get(i).equals("*")){
                functs.remove(i);
                numlist.remove(i);
                numlist.remove(i);
                numlist.add(i, fir*sec);
                i--;
            }
            else if(functs.get(i).equals("/")){
                functs.remove(i);
                numlist.remove(i);
                numlist.remove(i);
                numlist.add(i, fir/sec);
                i--;
            }
            else if(functs.get(i).equals("%")){
                functs.remove(i);
                numlist.remove(i);
                numlist.remove(i);
                numlist.add(i, fir%sec);
                i--;
            }
        }
        output=numlist.remove(0);
        for(int i=0;i<functs.size();i++){
            float sec=numlist.remove(0);
            output+=functs.get(i).equals("+")?sec:-sec;
        }
        return output;
    }
}