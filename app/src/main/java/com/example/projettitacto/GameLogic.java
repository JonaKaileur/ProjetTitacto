package com.example.projettitacto;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {
    private int[][] gameBoard;
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerTurn;
    private int player=1;
    private String[] playerNames={"Player1","Player2"};

    GameLogic(){
        gameBoard =new int[3][3];
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                gameBoard[r][c] = 0;
            }
        }
    }

    public boolean updateGameBoard(int row,int col){
        if (gameBoard[row-1][col-1]==0){
            gameBoard[row - 1][col - 1] = player;
            if (player ==1){
                playerTurn.setText(("au tour du :"+playerNames[1]));
            }else {
                playerTurn.setText(("au tour du :"+playerNames[0]));
            }

            return true;
        }else{
            return false;
        }
    }
    public void reinitJeux(){
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                gameBoard[r][c] = 0;
            }
        }

        player = 1;

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        playerTurn.setText(("au tour"+ playerNames[0]));
    }

    public boolean winnerCheck(){
        boolean iswinner = false;
        for(int r = 0;r<3;r++){
            if(gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] &&
                    gameBoard[r][0] != 0){
                iswinner = true;

            }
        }
        for(int c = 0;c<3;c++){
            if(gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] &&
                    gameBoard[0][c] != 0){
                iswinner = true;

            }
        }

        if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] &&
                gameBoard[0][0] != 0){
            iswinner = true;

        }

        if(gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] &&
                gameBoard[2][0] != 0){
            iswinner = true;

        }

        int boerdfilled = 0;
        for (int r = 0; r<3; r++){
            for (int c = 0; c<3;c++){
                if(gameBoard[r][c] != 0){
                    boerdfilled += 1;
                }
            }
        }
        if (iswinner){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText((playerNames[player-1] + "Gagné !!!!"));
            return true;
        }
        else if (boerdfilled == 9){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText("Egalité");
            return true;
        }
        else {
            return false;
        }
    }

    public void setPlayAgainBTN(Button playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    public void setHomeBTN(Button homeBTN) {
        this.homeBTN = homeBTN;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }
}
