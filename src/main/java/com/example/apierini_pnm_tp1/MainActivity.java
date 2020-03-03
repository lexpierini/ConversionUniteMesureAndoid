package com.example.apierini_pnm_tp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mainVersMenu (View view) {
        Intent intent = new Intent(this,menuActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        // Creation d'une boîte de dialogue pour donner le choix de quitter à l'utilisateur
        AlertDialog.Builder DialogueQuitter = new AlertDialog.Builder(MainActivity.this);
        // Réglage de propriétés de la boîte de dialogue (Titre et îcone)
        DialogueQuitter.setTitle(R.string.app_name);
        DialogueQuitter.setIcon(R.mipmap.ic_launcher);
        /*  En ne mettant pas de point virgule a la fin de la ligne, on peut définir plusieurs
            attributs sans avoir a rementionner l'objet.
         */

        // Message à afficher
        DialogueQuitter.setMessage("Voulez vous vraiment quitter?")
                // Est ce qu'on peut annuler?
                .setCancelable(false)
                /*  bouton positif. On défini un listener pour exécuter une action lorsque le
                    bouton est appuyé
                 */
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                /*  bouton négatif. On defini un listener pour exécuter une action lorsque le
                    bouton est appuyé.
                 */
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // créeation et affichage de la boîte de dialogue
        AlertDialog alert = DialogueQuitter.create();
        alert.show();
    }
}
