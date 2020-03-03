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

public class DistanceActivity extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {
    // Les déclaration suivantes doivent être accessible par toutes les méthodes de la classe.

    // Ces variables contuennent les unités de distance de départ et de conversion.
    String UnitDepart = "cm";
    String UnitConvertie = "m";

    // Ces variables contiennent les distances de depart et de conversion.
    Float DistanceDepart = null;
    Float DistanceConvertie = null;

    //L'objet DecimalFormat permets de formatter les nombres decimaux de différentes façons.
    DecimalFormat numberFormat = new DecimalFormat("0.00");

    // Surclassement de la méthode OnCreate.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Affichage de l'interface de l'activité.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        // Rechercher les instances de RadioGroup. Nous en aurons besoin pour poser les listeners.
        RadioGroup rgFrom = findViewById(R.id.rgDepartDistance);
        RadioGroup rgTo = findViewById(R.id.rgFinDistance);

        // Recercher l'instance du EditText. Nous en aurons besoin pour poser le listener.
        EditText distanceFrom = findViewById(R.id.textDepartDistance);

        // Assignation du listeners sur les RadioGroups
        rgFrom.setOnCheckedChangeListener(this);
        rgTo.setOnCheckedChangeListener(this);

        // Assignation du listeners sur le EditText.
        distanceFrom.addTextChangedListener(this);
    }

    // Méthodes requises par l'interface TextWatcher. Elles doivent etre déclarées même si ele ne sont pas utilisées.

    // Cette méthode est lancée juste après que le texte a été modifié.
    public void afterTextChanged (Editable s) {
        // Rechercher l'instance du EditText
        EditText distanceFrom = findViewById(R.id.textDepartDistance);

        // Gestion des cas spéciaux.
        // 1 - Si le EditText est vide.
        if (distanceFrom.getText().toString().isEmpty()) {
            // On recherche l'instance de TextView pour le résultat.
            TextView distanceTo = findViewById(R.id.textResultatDistance);
            // On lui assigne une chaine vide.
            distanceTo.setText("");
            // La distance de départ est nulle
            DistanceDepart = null;
            // Sinon, si le EditText contient seulement les signes + ou -.
        } else if ((distanceFrom.getText().toString().equals("-")) || (distanceFrom.getText().toString().equals("+"))) {
            // On ne fait rien puisqu'il n'y a pas de valeur numérique dans le EditText.
            DistanceDepart = null;
            // Sinon, c'est qu'il y a des nombres dans le EditText.
        } else {
            // On converti le contenu du EditText en float et on assigne la valeur à la variable DistanceDepart.
            DistanceDepart = Float.valueOf(distanceFrom.getText().toString());
            // On appele la méthode quelCalcul qui permet de savoir quel calcul efectuer.
            quelCalcul(UnitDepart, UnitConvertie, DistanceDepart);
        }
    }

    // Cette méthode est lancée just avant de modifier le text.
    public  void beforeTextChanged(CharSequence s, int start, int count, int after) {
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
            case R.id.rbDepartCm:
                // RadioButton de cm
                UnitDepart = "cm";
                break;
            case R.id.rbDepartM:
                // RadioButton de m
                UnitDepart = "m";
                break;
            case R.id.rbDepartKm:
                // RadioButton de km
                UnitDepart = "km";
                break;
            case R.id.rbDepartPouce:
                // RadioButton de pouce
                UnitDepart = "pouce";
                break;
            case R.id.rbDepartPied:
                // RadioButton de pied
                UnitDepart = "pied";
                break;
            case R.id.rbDepartMile:
                // RadioButton de mile
                UnitDepart = "mile";
                break;
            case R.id.rbResultatCm:
                // RadioButton à cm
                UnitConvertie = "cm";
                break;
            case R.id.rbResultatM:
                // RadioButton à m
                UnitConvertie = "m";
                break;
            case R.id.rbResultatKm:
                // RadioButton à km
                UnitConvertie = "km";
                break;
            case R.id.rbResultatPouce:
                // RadioButton à pouce
                UnitConvertie = "pouce";
                break;
            case R.id.rbResultatPied:
                // RadioButton à pied
                UnitConvertie = "pied";
                break;
            default:
                // RadioButton de mile
                UnitConvertie = "mile";
                break;
        }

        // Si la distance de depart n'est pas nulle
        if (DistanceDepart != null) {
            // On appelle lka methode quelCalcul qui permet de savoir quel calcul effectuer
            quelCalcul(UnitDepart, UnitConvertie, DistanceDepart);
        }
    }

    /*  Méthode qui permet de determiner quel calcul effectuer. Elle reçoit en parametre l'unité
        de depart, l'unité de conversion et la distance de depart. */
    public void quelCalcul(String UnitDepart, String UnitConvertie, Float DistanceDepart) {
        // On assigne ces valeurs aux variables
        this.UnitDepart = UnitDepart;
        this.UnitConvertie = UnitConvertie;
        this.DistanceDepart = DistanceDepart;

        // Rechercher l'instance du TextView
        TextView distanceTo = findViewById(R.id.textResultatDistance);

        /*  Choix multiple basé sur l'unité de depart. Dans chaque cas, on teste l'unité de conversion
            et on appelle la méthode correspondante. Les autres cas ne requiers pas de conversion */
        switch (UnitDepart) {
            case ("cm"):
                if (UnitConvertie.equals("m")) {
                    DistanceConvertie = ConversionCmEnMetre(DistanceDepart);
                } else if (UnitConvertie.equals("km")) {
                    DistanceConvertie = ConversionCmEnKm(DistanceDepart);
                } else if (UnitConvertie.equals("pouce")) {
                    DistanceConvertie = ConversionCmEnPouce(DistanceDepart);
                } else if (UnitConvertie.equals("pied")) {
                    DistanceConvertie = ConversionCmEnPied(DistanceDepart);
                } else if (UnitConvertie.equals("mile")) {
                    DistanceConvertie = ConversionCmEnMile(DistanceDepart);
                } else {
                    DistanceConvertie = DistanceDepart;
                }
                break;
            case ("m"):
                if (UnitConvertie.equals("cm")) {
                    DistanceConvertie = ConversionMetreEnCm(DistanceDepart);
                } else if (UnitConvertie.equals("km")) {
                    DistanceConvertie = ConversionMetreEnKm(DistanceDepart);
                } else if (UnitConvertie.equals("pouce")) {
                    DistanceConvertie = ConversionMetreEnPouce(DistanceDepart);
                } else if (UnitConvertie.equals("pied")) {
                    DistanceConvertie = ConversionMetreEnPied(DistanceDepart);
                } else if (UnitConvertie.equals("mile")) {
                    DistanceConvertie = ConversionMetreEnMile(DistanceDepart);
                } else {
                    DistanceConvertie = DistanceDepart;
                }
                break;
            case ("km"):
                if (UnitConvertie.equals("cm")) {
                    DistanceConvertie = ConversionKmEnCm(DistanceDepart);
                } else if (UnitConvertie.equals("m")) {
                    DistanceConvertie = ConversionKmEnMetre(DistanceDepart);
                } else if (UnitConvertie.equals("pouce")) {
                    DistanceConvertie = ConversionKmEnPouce(DistanceDepart);
                } else if (UnitConvertie.equals("pied")) {
                    DistanceConvertie = ConversionKmEnPied(DistanceDepart);
                } else if (UnitConvertie.equals("mile")) {
                    DistanceConvertie = ConversionKmEnMile(DistanceDepart);
                } else {
                    DistanceConvertie = DistanceDepart;
                }
                break;
            case ("pouce"):
                if (UnitConvertie.equals("cm")) {
                    DistanceConvertie = ConversionPouceEnCm(DistanceDepart);
                } else if (UnitConvertie.equals("m")) {
                    DistanceConvertie = ConversionPouceEnMetre(DistanceDepart);
                } else if (UnitConvertie.equals("km")) {
                    DistanceConvertie = ConversionPouceEnKm(DistanceDepart);
                } else if (UnitConvertie.equals("pied")) {
                    DistanceConvertie = ConversionPouceEnPied(DistanceDepart);
                } else if (UnitConvertie.equals("mile")) {
                    DistanceConvertie = ConversionPouceEnMile(DistanceDepart);
                } else {
                    DistanceConvertie = DistanceDepart;
                }
                break;
            case ("pied"):
                if (UnitConvertie.equals("cm")) {
                    DistanceConvertie = ConversionPiedEnCm(DistanceDepart);
                } else if (UnitConvertie.equals("m")) {
                    DistanceConvertie = ConversionPiedEnMetre(DistanceDepart);
                } else if (UnitConvertie.equals("km")) {
                    DistanceConvertie = ConversionPiedEnKm(DistanceDepart);
                } else if (UnitConvertie.equals("pouce")) {
                    DistanceConvertie = ConversionPiedEnPouce(DistanceDepart);
                } else if (UnitConvertie.equals("mile")) {
                    DistanceConvertie = ConversionPiedEnMile(DistanceDepart);
                } else {
                    DistanceConvertie = DistanceDepart;
                }
                break;
            case ("mile"):
                if (UnitConvertie.equals("cm")) {
                    DistanceConvertie = ConversionMileEnCm(DistanceDepart);
                } else if (UnitConvertie.equals("m")) {
                    DistanceConvertie = ConversionMileEnMetre(DistanceDepart);
                } else if (UnitConvertie.equals("km")) {
                    DistanceConvertie = ConversionMileEnKm(DistanceDepart);
                } else if (UnitConvertie.equals("pouce")) {
                    DistanceConvertie = ConversionMileEnPouce(DistanceDepart);
                } else if (UnitConvertie.equals("pied")) {
                    DistanceConvertie = ConversionMileEnPied(DistanceDepart);
                } else {
                    DistanceConvertie = DistanceDepart;
                }
        }

        // On affiche le resultat dans le TextView
        distanceTo.setText(numberFormat.format(DistanceConvertie));
    }

    // Cm en metre
    public float ConversionCmEnMetre(Float distanceCm) {
        return (distanceCm / 100);
    }

    // Cm en km
    public float ConversionCmEnKm(Float distanceCm) {
        return (distanceCm / 100000);
    }

    // Cm en pouce
    public float ConversionCmEnPouce(Float distanceCm) {
        return (distanceCm / 2.54f);
    }

    // Cm en pied
    public float ConversionCmEnPied(Float distanceCm) {
        return (distanceCm / 30.48f);
    }

    // Cm en mile
    public float ConversionCmEnMile(Float distanceCm) {
        return (distanceCm / 160934);
    }

    // Metre en cm
    public float ConversionMetreEnCm(Float distanceMetre) {
        return (distanceMetre * 100);
    }

    // Metre en km
    public float ConversionMetreEnKm(Float distanceMetre) {
        return (distanceMetre / 1000);
    }

    // Metre en pouce
    public float ConversionMetreEnPouce(Float distanceMetre) {
        return (distanceMetre * 39.37f);
    }

    // Metre en pied
    public float ConversionMetreEnPied(Float distanceMetre) {
        return (distanceMetre * 3.281f);
    }

    // Metre en mile
    public float ConversionMetreEnMile(Float distanceMetre) {
        return (distanceMetre / 1609);
    }

    // Km en cm
    public float ConversionKmEnCm(Float distanceKm) {
        return (distanceKm * 100000);
    }

    // Km en metre
    public float ConversionKmEnMetre(Float distanceKm) {
        return (distanceKm * 1000);
    }

    // Km en pouce
    public float ConversionKmEnPouce(Float distanceKm) {
        return (distanceKm * 39370);
    }

    // Km en pied
    public float ConversionKmEnPied(Float distanceKm) {
        return (distanceKm * 3281);
    }

    // Km en mile
    public float ConversionKmEnMile(Float distanceKm) {
        return (distanceKm / 1.609f);
    }

    // Pouce en cm
    public float ConversionPouceEnCm(Float distancePouce) {
        return (distancePouce * 2.54f);
    }

    // Pouce en metre
    public float ConversionPouceEnMetre(Float distancePouce) {
        return (distancePouce / 39.37f);
    }

    // Pouce en km
    public float ConversionPouceEnKm(Float distancePouce) {
        return (distancePouce / 39370);
    }

    // Pouce en Pied
    public float ConversionPouceEnPied(Float distancePouce) {
        return (distancePouce / 12);
    }

    // Pouce en mile
    public float ConversionPouceEnMile(Float distancePouce) {
        return (distancePouce / 63360);
    }

    // Pied en cm
    public float ConversionPiedEnCm(Float distancePied) {
        return (distancePied * 30.48f);
    }

    // Pied en metre
    public float ConversionPiedEnMetre(Float distancePied) {
        return (distancePied / 3.281f);
    }

    // Pied en km
    public float ConversionPiedEnKm(Float distancePied) {
        return (distancePied / 3281);
    }

    // Pied en pouce
    public float ConversionPiedEnPouce(Float distancePied) {
        return (distancePied * 12);
    }

    // Pied en mile
    public float ConversionPiedEnMile(Float distancePied) {
        return (distancePied / 5280);
    }

    // Mile en cm
    public float ConversionMileEnCm(Float distanceMile) {
        return (distanceMile * 1.609f);
    }

    // Mile en Metre
    public float ConversionMileEnMetre(Float distanceMile) {
        return (distanceMile * 1609);
    }

    // Mile en km
    public float ConversionMileEnKm(Float distanceMile) {
        return (distanceMile * 1.609f);
    }

    // Mile en pouce
    public float ConversionMileEnPouce(Float distanceMile) {
        return (distanceMile * 63360);
    }

    // Mile en pied
    public float ConversionMileEnPied(Float distanceMile) {
        return (distanceMile * 5280);
    }
}