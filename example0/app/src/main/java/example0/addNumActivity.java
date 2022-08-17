package moji.physics.example0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class addNumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_num);
        //to launch another activity
        if (getIntent().hasExtra("org.moji.physics.example0.data0")) {
            TextView tv = (TextView) findViewById(R.id.additionResult);
            String text = getIntent().getExtras().getString("org.moji.physics.example0.data0");
            tv.setText(text);
        }

        Button addButton =(Button) findViewById(R.id.addBtn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText firstNumber = (EditText) findViewById(R.id.firstNumberTxt);
                EditText secondNumber = (EditText) findViewById(R.id.secNumberTxt);
                TextView myResult = (TextView) findViewById(R.id.additionResult);
                int num1 = Integer.parseInt(firstNumber.getText().toString());
                int num2 = Integer.parseInt(secondNumber.getText().toString());
                myResult.setText((num1+num2)+"");
            }
        });
    }
}