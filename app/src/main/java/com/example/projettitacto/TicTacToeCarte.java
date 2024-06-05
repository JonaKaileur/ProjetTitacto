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

public class TicTacToeCarte extends View {
    private final int CouleurCarte;
    private final int CouleurX;
    private final int CouleurO;

    private boolean winningLine = false;

    private final int WinningLineColor;

    private final Paint paint = new Paint();

    private int cellSize = getWidth() / 3;

    private final GameLogic game;

    // Constructeur de la vue TicTacToeCarte
    public TicTacToeCarte(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new GameLogic();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeCarte, 0, 0);

        try {
            CouleurCarte = a.getInteger(R.styleable.TicTacToeCarte_CouleurCarte, 0);
            CouleurX = a.getInteger(R.styleable.TicTacToeCarte_CouleurX, 0);
            CouleurO = a.getInteger(R.styleable.TicTacToeCarte_CouleurO, 0);
            WinningLineColor = a.getInteger(R.styleable.TicTacToeCarte_WinningLineColor, 0);
        } finally {
            //Libère les ressources de TypedArray après utilisation
            a.recycle();
        }
    }

    // Mesure les dimensions de la vue et définit la taille de chaque cellule
    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        int dimensions = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimensions / 3;

        setMeasuredDimension(dimensions, dimensions);
    }

    // Dessine le plateau de jeu et les marqueurs
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        // Dessiner le plateau de jeu
        drawGameboard(canvas);
        // Dessiner les marqueurs (X et O)
        drawMarkers(canvas);

        // Si une ligne gagnante est détectée, la dessiner
        if (winningLine) {
            paint.setColor(WinningLineColor);
            drawWinningLine(canvas);
        }
    }

    // Gère les événements tactiles pour mettre à jour le plateau de jeu
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            int row = (int) Math.ceil(y / cellSize);
            int col = (int) Math.ceil(x / cellSize);
            if (!winningLine) {
                if (game.updateGameBoard(row, col)) {
                    invalidate();
                    if (game.winnerCheck()) {
                        winningLine = true;
                        invalidate();
                    }

                    // Met à jour le joueur qui joue
                    if (game.getPlayer() % 2 == 0) {
                        game.setPlayer(game.getPlayer() - 1);
                    } else {
                        game.setPlayer(game.getPlayer() + 1);
                    }
                }
            }

            invalidate();
            return true;
        }

        return false;
    }

    // Dessine le plateau de jeu
    public void drawGameboard(Canvas canvas) {
        paint.setColor(CouleurCarte);
        paint.setStrokeWidth(16);
        //Dessin des lignes horizontales
        for (int c = 1; c < 3; c++) {
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getWidth(), paint);
        }
        //Dessin des lignes horizontales
        for (int r = 1; r < 3; r++) {
            canvas.drawLine(0, cellSize * r, canvas.getWidth(), cellSize * r, paint);
        }
    }

    // Dessine les marqueurs (X et O) sur le plateau
    private void drawMarkers(Canvas canvas) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (game.getGameBoard()[r][c] != 0) {
                    if (game.getGameBoard()[r][c] == 1) {
                        drawX(canvas, r, c);
                    } else {
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    // Dessine un X dans la cellule spécifiée
    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(CouleurX);
        canvas.drawLine((float) ((col + 1) * cellSize - cellSize * 0.2),
                (float) (row * cellSize + cellSize * 0.2),
                (float) (col * cellSize + cellSize * 0.2),
                (float) ((row + 1) * cellSize - cellSize * 0.2),
                paint);

        canvas.drawLine((float) (col * cellSize + cellSize * 0.2),
                (float) (row * cellSize + cellSize * 0.2),
                (float) ((col + 1) * cellSize - cellSize * 0.2),
                (float) ((row + 1) * cellSize - cellSize * 0.2),
                paint);
    }

    // Dessine un O dans la cellule spécifiée
    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(CouleurO);

        canvas.drawOval((float) (col * cellSize + cellSize * 0.2),
                (float) (row * cellSize + cellSize * 0.2),
                (float) ((col * cellSize + cellSize) - cellSize * 0.2),
                (float) ((row * cellSize + cellSize) - cellSize * 0.2),
                paint);
    }

    // Dessine une ligne horizontale pour une ligne gagnante
    private void dessineLigneHorizontal(Canvas canvas, int row, int col) {
        canvas.drawLine(col, row * cellSize + cellSize / 2,
                cellSize * 3, row * cellSize + cellSize / 2,
                paint);
    }

    // Dessine une ligne verticale pour une ligne gagnante
    private void dessineLigneVertical(Canvas canvas, int row, int col) {
        canvas.drawLine(col * cellSize + cellSize / 2, row,
                col * cellSize + cellSize / 2, cellSize * 3,
                paint);
    }

    // Dessine une ligne diagonale de haut en bas à droite pour une ligne gagnante
    private void dessineLigneDiagonalNeg(Canvas canvas) {
        canvas.drawLine(0, 0,
                cellSize * 3, cellSize * 3,
                paint);
    }

    // Dessine une ligne diagonale de bas en haut à droite pour une ligne gagnante
    private void dessineLigneDiagonalPos(Canvas canvas) {
        canvas.drawLine(0, cellSize * 3,
                cellSize * 3, 0,
                paint);
    }

    // Dessine la ligne gagnante
    private void drawWinningLine(Canvas canvas) {
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];
        switch (game.getWinType()[2]) {
            case 1:
                dessineLigneHorizontal(canvas, row, col);
                break;
            case 2:
                dessineLigneVertical(canvas, row, col);
                break;
            case 3:
                dessineLigneDiagonalNeg(canvas);
                break;
            case 4:
                dessineLigneDiagonalPos(canvas);
                break;
        }
    }

    // Configure le jeu avec les boutons et les noms des joueurs
    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] names) {
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }

    // Réinitialise le jeu
    public void reinitJeux() {
        game.reinitJeux();
        winningLine = false;
    }
}
