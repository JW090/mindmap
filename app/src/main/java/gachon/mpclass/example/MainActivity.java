package gachon.mpclass.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton start_mindmap;
    EditText starting_text;

    Mindmap mp;

    NodeFragment first;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_mindmap = findViewById(R.id.btn_start);
        starting_text = findViewById(R.id.start_text);



        start_mindmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = starting_text.getText().toString();

                Intent intent = new Intent(getApplicationContext(),Mindmap.class);
                intent.putExtra("starting",str);
                startActivity(intent);


            }
        });


    }
}