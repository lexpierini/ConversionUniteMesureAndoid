package com.example.apierini_pnm_tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MasseActivity extends AppCompatActivity implements TextWatcher {
    // Les déclaration suivantes doivent être accessible par toutes les méthodes de la classe.

    EditText MasseFrom;

    // Ces variables contuennent les unités de masse de départ et de conversion.
    String UnitDepart = "kg";
    String UnitConvertie = "livre";

    // Ces variables contiennent les masse de depart et de conversion.
    Float MasseDepart = null;
    Float MasseConvertie = null;

    //L'objet DecimalFormat permets de formatter les nombres decimaux de différentes façons.
    DecimalFormat numberFormat = new DecimalFormat("0.00");

    // Surclassement de la méthode OnCreate.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Affichage de l'interface de l'activité.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masse);

        MasseFrom = findViewById(R.id.textDepartMasse);
        MasseFrom.addTextChangedListener(this);

        Switch sw = findViewById (R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    if (!MasseFrom.getText().toString().isEmpty())
                        quelCalcul("livre", "kg", Float.parseFloat(MasseFrom.getText().toString()));
                }else{
                    if (!MasseFrom.getText().toString().isEmpty())
                        System.out.println("entrou aqui");
                        quelCalcul("kg", "livre", Float.parseFloat(MasseFrom.getText().toString()));
                }
            }
        });
    }

    // Méthodes requises par l'interface TextWatcher. Elles doivent etre déclarées même si ele ne sont pas utilisées.

    // Cette méthode est lancée juste après que le texte a été modifié.
    @Override
    public void afterTextChanged(Editable s) {
        // Rechercher l'instance du EditText
        EditText masseFrom = findViewById(R.id.textDepartMasse);

        // Gestion des cas spéciaux.
        // 1 - Si le EditText est vide.
        if (masseFrom.getText().toString().isEmpty()) {
            // On recherche l'instance de TextView pour le résultat.
            TextView masseTo = findViewById(R.id.textResultatMasse);
            // On lui assigne une chaine vide.
            masseTo.setText("");
            // La masse de départ est nulle
            MasseDepart = null;
            // Sinon, si le EditText contient seulement les signes + ou -.
        } else if ((masseFrom.getText().toString().equals("-")) || (masseFrom.getText().toString().equals("+"))) {
            // On ne fait rien puisqu'il n'y a pas de valeur numérique dans le EditText.
            MasseDepart = null;
            // Sinon, c'est qu'il y a des nombres dans le EditText.
        } else {
            // On converti le contenu du EditText en float et on assigne la valeur à la variable MasseDepart.
            MasseDepart = Float.valueOf(masseFrom.getText().toString());
            // On appele la méthode quelCalcul qui permet de savoir quel calcul efectuer.
            quelCalcul(UnitDepart, UnitConvertie, MasseDepart);
        }
    }

    // Cette méthode est lancée just avant de modifier le text.
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Rien à faire.
    }

    // Cette méthode est lancée au moment ou le texte est modifié.
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Rien à faire.
    }

    /*  Méthode qui permet de determiner quel calcul effectuer. Elle reçoit en parametre l'unité
        de depart, l'unité de conversion et la distance de depart. */
    public void quelCalcul(String UnitDepart, String UnitConvertie, Float MasseDepart) {
        // On assigne ces valeurs aux variables
        this.UnitDepart = UnitDepart;
        this.UnitConvertie = UnitConvertie;
        this.MasseDepart = MasseDepart;

        // Rechercher l'instance du TextView
        TextView masseTo = findViewById(R.id.textResultatMasse);

        /*  Choix multiple basé sur l'unité de depart. Dans chaque cas, on teste l'unité de conversion
            et on appelle la méthode correspondante. Les autres cas ne requiers pas de conversion */

        // UnitDepart == "kg" ? MasseConvertie = kgEnLivre(MasseDepart) : MasseConvertie = livreEnKg(MasseDepart);

        if (UnitDepart == "kg") {
            MasseConvertie = kgEnLivre(MasseDepart);
            masseTo.setText(numberFormat.format(MasseConvertie) + " lb");
        } else {
            MasseConvertie = livreEnKg(MasseDepart);
            masseTo.setText(numberFormat.format(MasseConvertie) + " kg");
        }

        // On affiche le resultat dans le TextView

    }

    // Kg en livre
    public float kgEnLivre(Float masseKg) {
        return (masseKg * 2.205f);
    }

    // Livre en kg
    public float livreEnKg(Float masseLivre) {
        return (masseLivre / 2.205f);
    }
}
