package moji.physics.example0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button rollButton;
    TextView resultsTextView;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollButton = (Button) findViewById(R.id.rollButton);
        resultsTextView = (TextView) findViewById(R.id.resultsTextView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        rollButton.setOnClickListener(this);

        Button numActivity = (Button) findViewById(R.id.callAddNum);
        numActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //two activities are linked
                Intent startIntent = new Intent(getApplicationContext(),addNumActivity.class);
                //Passing info to another activity
                startIntent.putExtra("org.moji.physics.example0.data0","Resolution");
                startActivity(startIntent);
            }
        });

        Button shopActivity = (Button) findViewById(R.id.ShopListBtn);
        shopActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),ShopListAct.class);
                startActivity(startIntent);
            }
        });
        //to launch an activity outside the app
        /*
        Button browserBtn = (Button) findViewById(R.id.callAddNum);
        browserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String duck = "https://www.duckduckgo.com";
                Uri webDuck= Uri.parse(duck);//converts string to web address
                Intent gotoDuck = new Intent(Intent.ACTION_VIEW,webDuck);
                if(gotoDuck.resolveActivity(getPackageManager())!=null){
                    startActivity(gotoDuck);
                }
            }
        });
        */
    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.rollButton){
           int rand= new Random().nextInt(seekBar.getProgress());
            resultsTextView.setText(String.valueOf(rand));
        }
    }
}