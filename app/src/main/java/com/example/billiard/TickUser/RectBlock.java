package com.example.billiard.TickUser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.billiard.BasicType.Vector2;

public class RectBlock extends BaseViewTick {

    @Override
    public void simulate(float dt, Vector2 gravity) {
        this.position.x += 20;
        this.position.x += 20;

    }

    public RectBlock(Context context) {
        super(context);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(position.x, position.y, position.x + 20, position.y + 20, p);
    }
}
