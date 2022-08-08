package com.example.wbw_first.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class Drawer {

    // Abrir contenido del menu
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    };

    // Cerrar contenido del menu
    public static void closeDrawer(DrawerLayout drawerLayout) {
        // Close drawer layout
        // Check codition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            // When drawer is open
            // Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    // Redireccionar a otro activity
    public static void redirectActivity(Activity activity, Class aClass) {
        // Initialize intent
        Intent intent = new Intent(activity, aClass);

        // Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start activity
        activity.startActivity(intent);
    }

    // Cerrar aplicación
    public static void logout(Activity activity) {
        // Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // Set title
        builder.setTitle("Logout");

        // Set message
        builder.setMessage("Está seguro que desea salir de la APP?");

        // Positivy yes button
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Finish activity
                activity.finishAffinity();

                // Exit app
                System.exit(0);
            }
        });

        // negative no button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss dialog
                dialogInterface.dismiss();

            }
        });

        // Show dialog
        builder.show();
    }

}
