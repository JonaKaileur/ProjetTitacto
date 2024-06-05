package com.example.projettitacto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameDisplay extends AppCompatActivity {
    private TicTacToeCarte ticTacToeCarte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_display);

        Button playAgainBTN=findViewById(R.id.play_again);
        Button homeBTN=findViewById(R.id.home_button);
        TextView playerturn =findViewById(R.id.player_display);

        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        if (playerNames != null){
            playerturn.setText("au tour de "+playerNames[0]);
        }

        ticTacToeCarte =findViewById(R.id.ticTacToeCarte);
        ticTacToeCarte.setUpGame(playAgainBTN,homeBTN,playerturn,playerNames);
    }
    public void ButtonPlayAgainClik(View view){
        ticTacToeCarte.reinitJeux();
        ticTacToeCarte.invalidate();
    }

    public void ButtonGoHome(View view){
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}