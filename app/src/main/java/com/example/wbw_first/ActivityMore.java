package com.example.wbw_first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ActivityMore extends AppCompatActivity {

    private Toolbar tbDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        // Initialize UI
        initUI();

        // Listener onclick
        onClickListener();
    }

    private void initUI(){
        // Tobar
        tbDialog = findViewById(R.id.tbReturnDialogMore);
        final Drawable iconReturn = getResources().getDrawable(R.drawable.ic_return);
        iconReturn.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        tbDialog.setNavigationIcon(iconReturn);
    }

    private void onClickListener(){
        // Usando lamda para capturar el evento click
        tbDialog.setNavigationOnClickListener(view -> {
            //overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
            this.finish();
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}