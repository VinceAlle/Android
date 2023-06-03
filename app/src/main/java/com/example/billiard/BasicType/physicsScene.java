package com.example.billiard.BasicType;

import com.example.billiard.TickUser.BaseViewTick;

import java.util.ArrayList;

public class physicsScene {

    public static Vector2 gravity=new Vector2(0.0f,0.0f);
    public static float dt=1.0f/60.0f;
    public static Vector2 worldSize=new Vector2(0.0f,0.0f);
    public static boolean paused=true;
    public static ArrayList<BaseViewTick> dynamicSim=new ArrayList<>();
    public static float restitution=1.0f;

    public static void setWorldSize(float x,float y){
        worldSize.x=x;worldSize.y=y;
    }

}
