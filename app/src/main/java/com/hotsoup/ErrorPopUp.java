package com.hotsoup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ErrorPopUp extends AppCompatDialogFragment {
    String title;
    String message;
    public ErrorPopUp(String title, String message){
        this.title = title;
        this.message = message;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Creates Dialog which will popup and show given title and message
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
        .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
