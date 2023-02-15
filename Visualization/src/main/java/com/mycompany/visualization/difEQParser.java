package com.mycompany.visualization;

import javax.swing.JFrame;
import java.util.ArrayList;

public class difEQParser {
    private String difEQ;
    private int order;
    private JFrame jf;
    private static Graph g;
    public int sampleSize,wsize=300,woffset=200;
    public float dx;
    public float[] initialVals;

    public static void test(String[] args){
        float[] initial={0,1};
        difEQParser d=new difEQParser("y'=y^2+x^3",initial,50,.01f);
        d.graph();
    }
    
    public difEQParser(){
        difEQ="y=x";
        order=0;
        sampleSize=2;
        dx=.1f;
        initialVals=new float[]{0f,0f};
    }
    public difEQParser(String eq,float[] vals, int sample,float dx){
        difEQ=eq;
        sampleSize=sample;
        this.dx=dx;
        order=vals.length;
        initialVals=new float[order+1];
        for(int i=0;i<order;i++)
            initialVals[i]=vals[i];
        initialVals[order]=0;
        System.out.println(eq);
    }
    private void createFrame(){
        jf=new JFrame("differential equations");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setSize(400, 300);
    }
    public ArrayList<vec2> normalize(ArrayList<vec2> vlist){
        float sumx=0,sumy=0;
        for(vec2 v:vlist){
            sumx+=(v.x*v.x);
            sumy+=(v.y*v.y);
        }
        float normx=(float)Math.sqrt(sumx),
        normy=(float)Math.sqrt(sumy);
        for(int i=0;i<vlist.size();i++){
            vec2 normalized=new vec2((vlist.get(i).x*wsize/normx)+woffset,-(vlist.get(i).y*wsize/normy)+woffset);
            System.out.println(normalized.x+" , "+normalized.y);
            vlist.set(i, normalized);
        }
        return vlist;
    }
    public void graph(){
        graph(solve());
    }
    public void graph(ArrayList<vec2> solution){
        createFrame();
        solution=normalize(solution);
        g=new Graph(solution);
        jf.getContentPane().add(g);
    }
    public String plug(){
        return plug(difEQ,initialVals);
    }
    public String plug(String eq,float[] vals){
        String rhs="";
        int len=eq.length(),i=0;
        char c=' ';
        while(c!='='){
            c=eq.charAt(i);
            i++;
        }
        for(;i<len;i++){
            c=eq.charAt(i);
            if(c=='y'){
                int cnt=1;
                for(i=i+1;i<len;i++){
                    c=eq.charAt(i);
                    if(c=='\'')
                        cnt++;
                    else{
                        i--;
                        break;
                    }
                }
                rhs+=Math.abs(vals[cnt])>.001?vals[cnt]:0;
            }
            else if(c=='x')
                rhs+=Math.abs(vals[0])>.001?vals[0]:0;
            else rhs+=c;
        }
        System.out.println("rhs: "+rhs);
        return rhs; 
    }
    public ArrayList<vec2> solve(){
        return solve(difEQ,initialVals);
    }
    public ArrayList<vec2> solve(String eq,float[] vals){
        ArrayList<vec2> out=new ArrayList<>();
        MathEvaluator m=new MathEvaluator();
        for(int i=0;i<sampleSize;i++){
            vals[order]=m.calculate(plug(eq,vals));
            for(int u=vals.length-2;u>0;u--)
                vals[u]+=vals[u+1]*dx;
            vals[0]+=dx;
            vec2 point=new vec2(vals[0],vals[1]);
            out.add(i, point);
        }
        return out;
    }
}
