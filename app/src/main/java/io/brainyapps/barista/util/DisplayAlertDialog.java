package io.brainyapps.barista.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;


public class DisplayAlertDialog {


    public static void displayAlertDialog(Context context, int message, int positiveButton){

        String mTAG = MainActivity.TAG;

        Button b;

        AlertDialog.Builder ad = new AlertDialog.Builder(context, R.style.MyDialogTheme );
        //ad.setTitle(title);
        ad.setMessage(message);
        ad.setPositiveButton(
                positiveButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Intent intent = new Intent(context, MainActivity.class);

                        if (message == R.string.dialog_add_to_cart) {

                                  intent.putExtra("item_ind", 3);

                        } else if (message == R.string.dialog_make_order) {

                            intent.putExtra("item_ind", 2);
                        }

                        context.startActivity(intent);

                        dialog.dismiss();
                    }
                }
        );
        ad.setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {


                        dialog.dismiss();
                    }
                }
        );
        ad.show();


    }

}
    /*public static void setDefaultColorTextForDialogButton(AlertDialog alertDialog, Resources r) {
    Button b;
    b= alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
    if(b != null) {
        b.setTextColor(r.getColor(R.color.default_text));
        b.setBackgroundColor(Color.WHITE);
    }
    b = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
    if(b != null) {
        b.setTextColor(r.getColor(R.color.default_text));
        b.setBackgroundColor(Color.WHITE);
    }
    b = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
    if(b != null) {
        b.setTextColor(r.getColor(R.color.default_text));
        b.setBackgroundColor(Color.WHITE);
    }
    }*/