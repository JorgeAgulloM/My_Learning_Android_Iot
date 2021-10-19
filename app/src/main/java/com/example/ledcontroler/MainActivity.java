package com.example.ledcontroler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //Variables
    MaterialTextView txtGpio14;
    MaterialTextView txtGpio15;
    SwitchMaterial idSwitch;
    MaterialButton btnLedOn;
    MaterialButton btnLedOff;
    PetitionsAPI api;

    String gpioSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de variables
        txtGpio14 = findViewById(R.id.txtGpio14);
        txtGpio15 = findViewById(R.id.txtGpio15);
        idSwitch = findViewById(R.id.idSwitch);
        btnLedOn = findViewById(R.id.btnLedOn);
        btnLedOff = findViewById(R.id.btnLedOff);

        gpioSelect = "14";

        //Varaible Retrofit para llamar al servicio web
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.37/raspberry/src/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Se carga la variable api con retrofit
        api = retrofit.create(PetitionsAPI.class);

        //Suitch para seleccionar uno u otro Gpio
        idSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (idSwitch.isChecked()) {
                    gpioSelect = "15";
                    btnLedOn.setText("Led On");
                    btnLedOff.setText("Led Off");
                } else {
                    gpioSelect = "14";
                    btnLedOn.setText("Lámpara On");
                    btnLedOff.setText("Lámpara Off");
                }
            }
        });

        //Botón de encendido
        btnLedOn.setOnClickListener(v -> {
            callApi(gpioSelect, "on");
        });

        //Botón de apagado
        btnLedOff.setOnClickListener(v -> {
            callApi(gpioSelect, "off");
        });
    }
    

    //Fución para conectar con la Api y enviar la info correspondiente.
    private void callApi(String gpio, String status){

        //Se llama a la api y se le pasan los valores selecionados
        Call<DataResponseAPI> enviarDato = api.enviarDatos(gpio, status);

        //Se recoge la respuesta del api
        enviarDato.enqueue(new Callback<DataResponseAPI>() {

            //Si es correcta. Además, devuelve los valores del script, por lo que asegura que la conexión y ejecución han sido correctos.
            @Override
            public void onResponse(Call<DataResponseAPI> call, Response<DataResponseAPI> response) {

                //Se visualiza un snackbar que confirma el OK.
                View view = findViewById(R.id.activityLayout);
                Snackbar.make(view,"Dato enviado: " + status + " al Gpio Nº:" + gpio + " " + response.message(), BaseTransientBottomBar.LENGTH_LONG).show();
            }

            //Si no es correcta.
            @Override
            public void onFailure(Call<DataResponseAPI> call, Throwable t) {

                //Se muestra un toast avisando del error.
                Toast.makeText(MainActivity.this, "Fallo al enviar el dato", Toast.LENGTH_SHORT).show();
            }
        });

    }

}