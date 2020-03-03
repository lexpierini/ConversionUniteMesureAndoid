package com.example.apierini_pnm_tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

/*
    Déclaration de la classe de l'activité. On implémente un listener pour le EditText et  un autre pour les
    RadioButton. L'implémentation de l'interface TextWatcher requiers le surclassement des méthodes
    BeforeTextChanged, OnTextChanged et AfterTextChanged. L'implementation de l'interface RadioGroup.
    OnCheckChangeListener requiert le surclassement de la méthode OnCheckedChanged.
 */

public class TemperatureActivity extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {
    // Les déclaration suivantes doivent être accessible par toutes les méthodes de la classe.

    // Ces variables contuennent les unités de température de départ et de conversion.
    String UnitDepart = "C";
    String UnitConvertie = "F";

    // Ces variables contiennent les températures de depart et de conversion.
    Float TempDepart = null;
    Float TempConvertie = null;

    //L'objet DecimalFormat permets de formatter les nombres decimaux de différentes façons.
    DecimalFormat numberFormat = new DecimalFormat("0.00");

    // Surclassement de la méthode OnCreate.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Affichage de l'interface de l'activité.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        // Rechercher les instances de RadioGroup. Nous en aurons besoin pour poser les listeners.
        RadioGroup rgFrom = findViewById(R.id.rgDepartTemperature);
        RadioGroup rgTo = findViewById(R.id.rgFinTemperature);

        // Recercher l'instance du EditText. Nous en aurons besoin pour poser le listener.
        EditText tempFrom = findViewById(R.id.textDepartTemperature);

        // Assignation du listeners sur les RadioGroups
        rgFrom.setOnCheckedChangeListener(this);
        rgTo.setOnCheckedChangeListener(this);

        // Assignation du listeners sur le EditText.
        tempFrom.addTextChangedListener(this);
    }

    // Méthodes requises par l'interface TextWatcher. Elles doivent etre déclarées même si ele ne sont pas utilisées.

    // Cette méthode est lancée juste après que le texte a été modifié.
    public void afterTextChanged (Editable s) {
        // Rechercher l'instance du EditText
        EditText tempFrom = findViewById(R.id.textDepartTemperature);

        // Gestion des cas spéciaux.
        // 1 - Si le EditText est vide.
        if (tempFrom.getText().toString().isEmpty()) {
            // On recherche l'instance de TextView pour le résultat.
            TextView tempTo = findViewById(R.id.textResultatTemperature);
            // On lui assigne une chaine vide.
            tempTo.setText("");
            // La température de départ est nulle
            TempDepart = null;
            // Sinon, si le EditText contient seulement les signes + ou -.
        } else if ((tempFrom.getText().toString().equals("-")) || (tempFrom.getText().toString().equals("+"))) {
            // On ne fait rien puisqu'il n'y a pas de valeur numérique dans le EditText.
            TempDepart = null;
            // Sinon, c'est qu'il y a des nombres dans le EditText.
        } else {
            // On converti le contenu du EditText en float et on assigne la valeur à la variable TempDepart.
            TempDepart = Float.valueOf(tempFrom.getText().toString());
            // On appele la méthode quelCalcul qui permet de savoir quel calcul efectuer
            quelCalcul(UnitDepart, UnitConvertie, TempDepart);
        }
    }

    // Cette méthode est lancée just avant de modifier le text.
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Rien à faire.
    }

    // Cette méthode est lancée au moment ou le texte est modifié.
    public void onTextChanged(CharSequence arg0, int start, int before, int count) {
        // Rien à faire.
    }

    // Méthode apelée lorsqu'un RadioButton est cliqué.
    // La méthode reçoit en parametre le groupe de bouton et le bouton sélectionné.
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //rEnCours contient le boutton actif.
        RadioButton rEnCours = findViewById(checkedId);

        // Choix multiple en fonction du RadioBouton Actif
        switch (rEnCours.getId()) {
            case R.id.rbDepartCelsius:
                // RadioButton de celcius
                UnitDepart = "C";
                break;
            case R.id.rbDepartFarhenheit:
                // RadioButton de farhenheit
                UnitDepart = "F";
                break;
            case R.id.rbDepartKelvin:
                // RadioButton de kelvin
                UnitDepart = "K";
                break;
            case R.id.rbResultatCelsius:
                // RadioButton à celsius
                UnitConvertie = "C";
                break;
            case R.id.rbResultatFarhenheit:
                // RadioButton à farhenheit
                UnitConvertie = "F";
                break;
            default:
                // RadioButton à kelvin
                UnitConvertie = "K";
                break;
        }

        // Si la temperature de depart n'est pas nulle
        if (TempDepart != null) {
            // On appelle lka methode quelCalcul qui permet de savoir quel calcul effectuer
            quelCalcul(UnitDepart, UnitConvertie, TempDepart);
        }
    }

    /*  Méthode qui permet de determiner quel calcul effectuer. Elle reçoit en parametre l'unité
        de depart, l'unité de conversion et la temperature de depart. */
    public void quelCalcul(String UnitDepart, String UnitConvertie, Float TempDepart) {
        // On assigne ces valeurs aux variables
        this.UnitDepart = UnitDepart;
        this.UnitConvertie = UnitConvertie;
        this.TempDepart = TempDepart;

        // Rechercher l'instance du TextView
        TextView tempTo = findViewById(R.id.textResultatTemperature);

        /*  Choix multiple basé sur l'unité de depart. Dans chaque cas, on teste l'unité de conversion
            et on appelle la méthode correspondante. Les choix possibles sont
            C en F, C en K, F en C, F en K, K en C, K en F.
            Les autres cas ne requiers pas de conversion */
        switch (UnitDepart) {
            case ("C"):
                if (UnitConvertie.equals("F")) {
                    TempConvertie = ConversionCaF(TempDepart);
                } else if (UnitConvertie.equals("K")) {
                    TempConvertie = ConversionCaK(TempDepart);
                } else {
                    TempConvertie = TempDepart;
                }
                break;
            case ("F"):
                if (UnitConvertie.equals("C")) {
                    TempConvertie = ConversionFaC(TempDepart);
                } else if (UnitConvertie.equals("K")) {
                    TempConvertie = ConversionFaK(TempDepart);
                } else {
                    TempConvertie = TempDepart;
                }
                break;
            default:
                if (UnitConvertie.equals("C")) {
                    TempConvertie = ConversionKaC(TempDepart);
                } else if (UnitConvertie.equals("F")) {
                    TempConvertie = ConversionKaF(TempDepart);
                } else {
                    TempConvertie = TempDepart;
                }
                break;
        }

        // On affiche le resultat dans le TextView
        tempTo.setText(numberFormat.format(TempConvertie));
    }

    // Celcius en fahrenheit
    public float ConversionCaF(Float temperatureC) {
        return ((temperatureC * 9 / 5) + 32);
    }

    // Celsius en kelvin
    public float ConversionCaK(Float temperatureC) {
        return (temperatureC + 273.15f);
    }

    // Fahreinheit en celcius
    public float ConversionFaC(Float temperatureF) {
        return ((temperatureF - 32) * 5 / 9);
    }

    // Fahreinheit en kelvin
    public float ConversionFaK(Float temperatureF) {
        return (((temperatureF - 32) * 5 / 9) + 273.15f);
    }

    // Kelvin en celcius
    public float ConversionKaC(Float temperatureK) {
        return (temperatureK - 273.15f);
    }

    // Kelvin en fahrenheit
    public  float ConversionKaF(Float temperatureK) {
        return  (((temperatureK - 273.15f) * 9 / 5) + 32);
    }
}
