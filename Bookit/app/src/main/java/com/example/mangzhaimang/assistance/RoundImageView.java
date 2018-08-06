package com.example.mangzhaimang.assistance;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class RoundImageView extends AppCompatImageView {

    private Paint paint=null;
    private PaintFlagsDrawFilter pfdf = null;
    private Path path = null;

    private int mBorderThickness = 0;
    private int defaultColor = 0xFFFFFFFF;

    public RoundImageView(Context context){
        super(context);
        init(context,null);
    }

    public RoundImageView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context,attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init(context,attrs);
    }

    public void init(Context context,AttributeSet attrs){
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        int clearBits = 0;
        int setBits = Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG;
        pfdf = new PaintFlagsDrawFilter(clearBits,setBits);
    }

    @Override
    protected void onDraw(Canvas canvas){
        int width = getWidth();
        int height = getHeight();
        if(path == null){
            path = new Path();
            path.addCircle(width/2f,height/2f,Math.min(width/2f,height/2f),Path.Direction.CCW);
            path.close();
        }
        int saveCount = canvas.save();
        canvas.setDrawFilter(pfdf);
        canvas.clipPath(path, Region.Op.INTERSECT);
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }
}
