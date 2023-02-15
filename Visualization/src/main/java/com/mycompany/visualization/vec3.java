/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.visualization;

/**
 *
 * @author Sebastian
 */
public class vec3 extends vec2{
    float z;
    public vec3(){
        x=0f;
        y=0f;
        z=0f;
    }
    public vec3(float _x,float _y,float _z){
        x=_x;
        y=_y;
        z=_z;
    }
    public vec3 plus(vec3 right){
        return new vec3(x+right.x,y+right.y,z+right.z);
    }
    public vec3 negate(){
        return new vec3(-x,-y,-z);
    }
    public vec3 minus(vec3 right){
        return plus(right.negate());
    }
    public vec3 times(float mult){
        return new vec3(x*mult,y*mult,z*mult);
    }
    public vec3 over(float div){
        return times(1/div);
    }
    public vec2 toVec2(float d){
        if(d==0||d==z)
            return new vec2();
        return new vec2(x/(1-z/d),y/(1-z/d));
    }
}