package com.example.sunny.numbersapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button questFacts;
    private Button randomFacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intialization
        randomFacts=(Button) findViewById(R.id.RandomFacts);
        questFacts=(Button) findViewById(R.id.QuestFacts);

        //Setting RandomFacts onClickListeners
        randomFacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RandomFacts.class));
            }
        });

        //setting QuestFacts onClicklisteners
        questFacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,QuestFacts.class));
            }
        });
    }

}
