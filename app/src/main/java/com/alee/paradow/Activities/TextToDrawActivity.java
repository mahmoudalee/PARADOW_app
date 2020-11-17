package com.alee.paradow.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alee.paradow.R;

public class TextToDrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_draw);
    }

    public void choose(View view) {
        Toast.makeText(this, "Confirm", Toast.LENGTH_SHORT).show();
    }
}
