package com.horsegaming.sensorchecker.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Horse on 05.09.2016.
 */
public class Diagram extends View
{

    double[] values;
    final double max = 100;

    Paint paint = new Paint();
    public Diagram(Context context) {
        super(context);
    }

    public Diagram(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Diagram(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void set(double[] value){
        this.values = value;
        paint.setColor(Color.BLACK);
        this.invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int stepX = this.getWidth()/values.length;
        if(stepX < 1){
            stepX = 1;
        }
        canvas.drawColor(Color.WHITE);
        int length = values.length -1;
        int stepY = (int)(this.getHeight() / max);

        for (int i = 0; i < length; i++) {
            int tmp = i + 1;
            canvas.drawLine(i*stepX, (int)(stepY*values[i]),tmp*stepX, (int)(stepY*values[tmp]), paint);
        }
    }
}
