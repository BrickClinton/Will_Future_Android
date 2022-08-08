package com.example.wbw_first.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wbw_first.R;


public class DialogFragmentDemo extends DialogFragment {
    Activity activity;
    ImageButton ibClose;
    LinearLayout lyNavbarTop;

    public DialogFragmentDemo() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return createDialog();
    }

    private Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // initialize references
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_demo, null);
        builder.setView(view);

        lyNavbarTop = view.findViewById(R.id.lyNavbarTop);
        ibClose = view.findViewById(R.id.ibDialogClose);

        listenerOnClicks();

        return builder.create();
    }

    private void listenerOnClicks() {
        lyNavbarTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Top", Toast.LENGTH_SHORT).show();
            }
        });

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  Activity){
            this.activity = (Activity) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

}