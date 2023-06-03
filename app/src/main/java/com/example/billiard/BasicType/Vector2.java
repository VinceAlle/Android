package com.example.billiard.BasicType;

import androidx.annotation.NonNull;

public class Vector2 {
    public float x,y;

    public Vector2(){
        this.x=0.0f;
        this.y=0.0f;
    }
    public Vector2(float x, float y){
        this.x=x;
        this.y=y;
    }

    public Vector2 clone(){
        return new Vector2(this.x,this.y);
    }

    public void add(Vector2 v){
        this.x+=v.x;
        this.y+=v.y;
    }

    public void add(Vector2 v,float s){
        this.x+=v.x*s;
        this.y+=v.y*s;
    }

    public Vector2 addVectors(Vector2 a,Vector2 b){
        this.x=a.x+b.x;
        this.y=a.y+b.y;
        return this;
    }

    public void sub(Vector2 v){
        this.x-=v.x;
        this.y-=v.y;
    }

    public void sub(Vector2 v,float s){
        this.x-=v.x*s;
        this.y-=v.y*s;
    }

    public void subtractVectors(Vector2 a,Vector2 b){
        this.x=a.x-b.x;
        this.y=a.y-b.y;
    }

    public float length(){
        return (float) Math.sqrt(this.x*this.x+this.y*this.y);
    }

    public void scale(float s){
        this.x*=s;
        this.y*=s;
    }

    public float dot(Vector2 v){
        return this.x*v.x+this.y*v.y;
    }

    @NonNull
    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
