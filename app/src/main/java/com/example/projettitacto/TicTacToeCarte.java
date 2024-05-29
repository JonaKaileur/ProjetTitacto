package com.example.projettitacto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.projettitacto.R;

import java.security.PrivateKey;

public class TicTacToeCarte extends View {
    private final int CouleurCarte;
    private final int CouleurX;
    private final int CouleurO;

    private boolean winningLine = false;

    private final int WinnigLineColor;

    private final Paint paint = new Paint();

    private int cellSize = getWidth()/3;

    private final GameLogic game;

    public TicTacToeCarte(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new GameLogic();
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

        // drawX(canvas,2,2);
        //drawO(canvas,1,1);

        drawGameboard(canvas);
        drawMarkers(canvas);

        if (winningLine){
            paint.setColor(WinnigLineColor);
            drawWinningLine(canvas);
        }

    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();



        if(action == MotionEvent.ACTION_DOWN){
            int row = (int)Math.ceil(y/cellSize);
            int col = (int)Math.ceil(x/cellSize);
            if (!winningLine){
                if (game.updateGameBoard(row,col)){
                    invalidate();
                    if(game.winnerCheck()){
                        winningLine = true;
                        invalidate();
                    }

                    // sa met a jour les joueur pour dire quelle joueur joue
                    if (game.getPlayer() % 2 == 0){
                        game.setPlayer(game.getPlayer()-1);
                    }else {
                        game.setPlayer(game.getPlayer()+1);
                    }
                }

            }

            invalidate();
            return true;
        }

        return false;
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




    private void drawMarkers (Canvas canvas){
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                if(game.getGameBoard()[r][c] != 0){
                    if(game.getGameBoard() [r][c] == 1){
                        drawX(canvas,r,c);
                    }
                    else {
                        drawO(canvas,r,c);
                    }
                }
            }
        }
    }





    private void drawX(Canvas canvas,int row,int col){
        paint.setColor(CouleurX);
        //X se trace de la deuxieme colone a la première et y de la preemière à la deuxième
        //Pour améliore le design de la video aller sur la partie 4 a la minute 6.00
        canvas.drawLine((float) ((col+1)*cellSize - cellSize*0.2),
                    (float) (row*cellSize + cellSize*0.2),
                    (float) (col*cellSize + cellSize*0.2),
                    (float) ((row+1)*cellSize - cellSize*0.2),
                               paint);

        canvas.drawLine((float) (col*cellSize + cellSize*0.2),
                (float) (row*cellSize + cellSize*0.2),
                (float) ((col+1)*cellSize - cellSize*0.2),
                (float) ((row+1)*cellSize - cellSize*0.2),
                paint);
    }
    private void drawO(Canvas canvas,int row,int col){
        paint.setColor(CouleurO);

        canvas.drawOval((float) (col*cellSize + cellSize*0.2),
                (float) (row*cellSize + cellSize*0.2),
                (float) ((col*cellSize+cellSize) - cellSize*0.2),
                (float) ((row*cellSize+cellSize) - cellSize*0.2),
                paint);
    }

    private void drawHorizontalLine(Canvas canvas,int row,int col){
        canvas.drawLine(col,row *cellSize+cellSize/2,
                cellSize*3,row*cellSize+cellSize/2,
                paint);
    }

    private  void drawVerticalLine(Canvas canvas,int row,int col){
        canvas.drawLine(col*cellSize+cellSize/2,row,
                col*cellSize+cellSize/2,cellSize*3,
                paint);
    }

    private void drawDiagonalLineNeg(Canvas canvas){
        canvas.drawLine(0,0,
                cellSize*3,cellSize*3,
                paint);
    }
    private void drawDiagonalLinePos(Canvas canvas){
        canvas.drawLine(0,cellSize*3 ,
                cellSize*3,0,
                paint);
    }

    private void drawWinningLine(Canvas canvas){
        int row =game.getWinType()[0];
        int col =game.getWinType()[1];
        switch (game.getWinType()[2]){
            case 1:
                drawHorizontalLine(canvas,row,col);
                break;
            case 2:
                drawVerticalLine(canvas,row,col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
    }

    public void setUpGame(Button playAgain, Button home, TextView playerDisplay,String[] names){
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }

    public  void  reinitJeux() {
        game.reinitJeux();
        winningLine = false;
    }

}
