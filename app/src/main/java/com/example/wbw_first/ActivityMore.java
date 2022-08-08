package com.example.wbw_first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wbw_first.Utilities.Drawer;

public class ActivityMore extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        // Initialize UI
        initUI();

        // Listener onclick
        ivBackMore.setOnClickListener(this);
    }

    private void initUI(){
        ivBackMore = findViewById(R.id.ivBackMore);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBackMore:
                finish();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}