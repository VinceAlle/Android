package com.example.billiard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.billiard.BasicType.Vector2;
import com.example.billiard.BasicType.physicsScene;
import com.example.billiard.TickUser.ITick;
import com.example.billiard.TickUser.*;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public long timer=1000/60;

    void onEnter(){
        int numBalls=20;
        for (int i=0;i<numBalls;i++) {
            float radius=30f+(float) Math.random()*50f;
            float mass=(float) Math.PI*radius*radius;
            Vector2 pos=new Vector2((float) Math.random()*physicsScene.worldSize.x,(float) Math.random()*physicsScene.worldSize.y);
            //Vector2 pos=new Vector2(i*20.0f,i*20.0f);
            Vector2 vel=new Vector2(-100f+200f*(float)Math.random(),-100f+200f*(float)Math.random());
            //Vector2 vel=new Vector2(0.0f,0.0f);

            physicsScene.dynamicSim.add(new Ball(this,radius,mass,pos,vel));
            Log.d("onEnter", pos.toString());
        }
    }

    void Start(FrameLayout frame){
        onEnter();
        for (int i=0;i<physicsScene.dynamicSim.size();i++){
            frame.addView(physicsScene.dynamicSim.get(i));
        }
    }


    void handleBallCollision(Ball ball1,Ball ball2,float restitution){
        //Log.d("handleBallCollision", String.valueOf(ball1.radius));
        Vector2 dir=new Vector2();
        dir.subtractVectors(ball2.position,ball1.position);
        float d=dir.length()*1.03f;
        if(d==0.0||d>ball1.radius+ball2.radius)
            return;
        dir.scale(1.0f/d);

        float corr=(ball1.radius+ball2.radius-d)/2.0f;
        ball1.position.add(dir,-corr);
        ball2.position.add(dir,corr);

        float v1=ball1.velocity.dot(dir);
        float v2=ball2.velocity.dot(dir);

        float m1=ball1.mass;
        float m2=ball2.mass;

        float newV1=(m1*v1+m2*v2-m2*(v1-v2)*restitution)/(m1+m2);
        float newV2=(m1*v1+m2*v2-m1*(v2-v1)*restitution)/(m1+m2);

        ball1.velocity.add(dir,newV1-v1);
        ball2.velocity.add(dir,newV2-v2);
//        Log.d("handleBallCollision Ball1",ball1.velocity.toString());
//        Log.d("handleBallCollision Ball2",ball2.velocity.toString());
    }

    void handleWallCollision(Ball ball,Vector2 worldsize){
        if(ball.position.x<ball.radius){
            ball.position.x=ball.radius;
            ball.velocity.x=-ball.velocity.x;
        }
        if(ball.position.x>worldsize.x-ball.radius){
            ball.position.x=worldsize.x-ball.radius;
            ball.velocity.x=-ball.velocity.x;
        }
        if(ball.position.y<ball.radius){
            ball.position.y=ball.radius;
            ball.velocity.y=-ball.velocity.y;
        }

        if(ball.position.y>worldsize.y-ball.radius){
            ball.position.y=worldsize.y-ball.radius;
            ball.velocity.y=-ball.velocity.y;
        }
    }

    void simulate(){
        for (int i=0;i<physicsScene.dynamicSim.size();i++){
            BaseViewTick bvt = physicsScene.dynamicSim.get(i);
            bvt.simulate(physicsScene.dt,physicsScene.gravity);
            for (int j=i+1;j<physicsScene.dynamicSim.size();j++){
                BaseViewTick bvt2=physicsScene.dynamicSim.get(j);
                handleBallCollision((Ball) bvt,(Ball) bvt2,physicsScene.restitution);
            }
            handleWallCollision((Ball) bvt,physicsScene.worldSize);
            //Log.d("simulate",bvt.position.toString());
            bvt.invalidate();
        }
    }

    void Tick(){
        simulate();
    }


    private FrameLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        physicsScene.setWorldSize(dm.widthPixels,dm.heightPixels);

        frame = (FrameLayout) findViewById(R.id.mylayout);
        Start(frame);



        final MyHandler handler = new MyHandler(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean[] isPost = {true};
                while(true) {
                    //使用 handler 发送空消息
                    if(isPost[0]) {
                        isPost[0] =false;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Tick();
                                isPost[0] =true;
                            }
                        }, timer);
                    }
                }
            }
        }).start();

    }

    private static class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity activity) {
            this.weakReference = new WeakReference(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = weakReference.get();
            super.handleMessage(msg);
            if (null != activity) {
                //执行业务逻辑
                Toast.makeText(activity,"handleMessage",Toast.LENGTH_SHORT).show();
            }
        }
    }
}