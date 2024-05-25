package com.example.projettitacto;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.projettitacto.R;

import java.security.PrivateKey;

public class TicTacToeCarte extends View {
    private final int CouleurCarte;
    private final int CouleurX;
    private final int CouleurO;

    private final int WinnigLineColor;

    private final Paint paint = new Paint();

    private int cellSize = getWidth()/3;

    public TicTacToeCarte(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a =context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeCarte,0,0);

        try {
            CouleurCarte=a.getInteger(R.styleable.TicTacToeCarte_CouleurCarte,0);
            CouleurX=a.getInteger(R.styleable.TicTacToeCarte_CouleurX,0);
            CouleurO=a.getInteger(R.styleable.TicTacToeCarte_CouleurO,0);
            WinnigLineColor=a.getInteger(R.styleable.TicTacToeCarte_WinningLineColor,0);
        }finally {
            a.recycle();
        }
    }
    @Override
    protected void onMeasure(int width,int height){
        super.onMeasure(width,height);
        int dimensions = Math.min(getMeasuredWidth(),getMeasuredHeight());
        cellSize = dimensions/3;

        setMeasuredDimension(dimensions,dimensions);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameboard(canvas);
        drawX(canvas,1,1);

    }

    public void drawGameboard(Canvas canvas){
        paint.setColor(CouleurCarte);
        paint.setStrokeWidth(16);
        
        for (int c=1; c<3;c++){
            canvas.drawLine(cellSize*c,0,cellSize*c,canvas.getWidth(),paint);
        }

        for (int r=1; r<3;r++){
            canvas.drawLine(0,cellSize*r,canvas.getWidth(),cellSize*r,paint);
        }
    }




    private void drawX(Canvas canvas,int row,int col){
        paint.setColor(CouleurX);

        canvas.drawLine((col+1)*cellSize,row*cellSize,col*cellSize,(row+1)*cellSize,paint);
        canvas.drawLine(col*cellSize,row*cellSize,(row+1)*cellSize,(row+1)*cellSize,paint);
    }
    private void drawO(Canvas canvas,int row,int col){
        paint.setColor(CouleurO);
    }

}
