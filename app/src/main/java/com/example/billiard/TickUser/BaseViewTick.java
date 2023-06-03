package com.example.billiard.TickUser;

import android.content.Context;
import android.view.View;


import com.example.billiard.BasicType.Vector2;
import com.example.billiard.BasicType.physicsScene;

public class BaseViewTick extends View implements ITick{
    public Vector2 position;
    public float mass;
    public Vector2 velocity;

    public BaseViewTick(Context context) {
        super(context);

    }

    //physicsScene
    public Vector2 gravity=new Vector2(0.0f,0.0f);
    public float dt=1.0f/60.0f;


    @Override
    public void onTick(long l) {


    }

    public void simulate(float dt,Vector2 gravity) {

    }


}
