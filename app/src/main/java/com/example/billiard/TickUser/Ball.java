package com.example.billiard.TickUser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.billiard.BasicType.Vector2;

public class Ball extends BaseViewTick{
    public Ball(Context context) {
        super(context);
    }

    public float radius;

    public Ball(Context context, float radius, float mass, Vector2 pos,Vector2 vel){
        super(context);
        this.radius=radius;
        this.mass=mass;
        this.position=pos.clone();
        this.velocity=vel.clone();
    }

    @Override
    public void simulate(float dt, Vector2 gravity) {
        super.simulate(dt, gravity);
        this.velocity.add(gravity,dt);
        this.position.add(this.velocity,dt);
        //Log.d("Ball",velocity.toString());
        //Log.d("Ball",position.toString());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        canvas.drawCircle(position.x,position.y,radius,p);
    }
}
