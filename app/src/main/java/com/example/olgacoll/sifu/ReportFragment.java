package com.example.olgacoll.sifu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import android.widget.Toast;

import com.example.olgacoll.sifu.model.Incidencia;
import com.example.olgacoll.sifu.remote.APIService;
import com.example.olgacoll.sifu.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by olgacoll on 11/5/17.
 */

public class ReportFragment extends Fragment {

    private static final String TAG = ReportFragment.class.getSimpleName();
    private APIService apiService;
    EditText editTextNombre, editTextApellidos, editTextEmail, editTextTelefono, editTextCliente, editTextComentarios;
    Spinner spinner;
    String dadesSpinner[];
    String provincia;
    Bundle bundle;
    int indexButton;
    Button buttonSubirImagen, buttonEnviar;
    Button buttonEscogeImagen, buttonEscogeImagen2, buttonEscogeImagen3, buttonEscogeImagen4;
    Button buttonBorrarImagen2, buttonBorrarImagen3, buttonBorrarImagen4;
    View.OnClickListener listener;
    AdapterView.OnItemSelectedListener listenerSpinner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_report, container, false);
        apiService = ApiUtils.getAPIService();
        initComponents(view);
        onPrepareListener();
        //setupToolbar();
        controlSpinner(view);

        buttonSubirImagen.setOnClickListener(listener);
        buttonEscogeImagen.setOnClickListener(listener);
        buttonEscogeImagen2.setOnClickListener(listener);
        buttonEscogeImagen3.setOnClickListener(listener);
        buttonEscogeImagen4.setOnClickListener(listener);
        buttonBorrarImagen2.setOnClickListener(listener);
        buttonBorrarImagen3.setOnClickListener(listener);
        buttonBorrarImagen4.setOnClickListener(listener);
        buttonEnviar.setOnClickListener(listener);

        return view;
    }

    public void initComponents(View view) {
        editTextNombre = (EditText) view.findViewById(R.id.input_nombre);
        editTextApellidos = (EditText) view.findViewById(R.id.input_apellidos);
        editTextEmail = (EditText) view.findViewById(R.id.input_email);
        editTextTelefono = (EditText) view.findViewById(R.id.input_telefono);
        editTextCliente = (EditText) view.findViewById(R.id.input_cliente);
        editTextComentarios = (EditText) view.findViewById(R.id.input_comentarios);
        buttonSubirImagen = (Button) view.findViewById(R.id.buttonSubirImagen);
        buttonEscogeImagen = (Button) view.findViewById(R.id.buttonEscogeImagen);
        buttonEscogeImagen2 = (Button) view.findViewById(R.id.buttonEscogeImagen2);
        buttonEscogeImagen3 = (Button) view.findViewById(R.id.buttonEscogeImagen3);
        buttonEscogeImagen4 = (Button) view.findViewById(R.id.buttonEscogeImagen4);
        buttonBorrarImagen2 = (Button) view.findViewById(R.id.buttonBorrarImagen2);
        buttonBorrarImagen3 = (Button) view.findViewById(R.id.buttonBorrarImagen3);
        buttonBorrarImagen4 = (Button) view.findViewById(R.id.buttonBorrarImagen4);
        buttonEnviar = (Button) view.findViewById(R.id.buttonEnviar);
        indexButton = 1; //Con este índice, controlaremos las veces que hayan dado clic en Subir Imagen.
    }

    public void onPrepareListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonEnviar:
                        initSend();
                        break;
                    case R.id.buttonSubirImagen:
                        if (indexButton <= 4) {
                            indexButton++; //control para que nunca pase de 4.
                        }
                        initSubirImagen(indexButton);
                        break;
                }
            }
        };
    }

    public void controlSpinner(View view) {
        spinner = (Spinner) view.findViewById(R.id.spinner);
        dadesSpinner = new String[]{"Álava", "Albacete", "Alicante", "Almería", "Asturias", "Ávila", "Badajoz", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Cantabria", "Castellón", "Ciudad Real", "Córdoba",
                                    "La Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara", "Guipúzcoa", "Huelva", "Huesca", "Islas Baleares", "Jaén", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia",
                                    "Navarra", "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja", "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona", "Santa Cruz de Tenerife", "Teruel", "Toledo",
                                    "Valencia", "Vizcaya", "Zamora", "Zaragona"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, dadesSpinner);
        spinner.setAdapter(adaptador);
        prepareItemListener();
        spinner.setOnItemSelectedListener(listenerSpinner);
    }

    public void prepareItemListener() {
        listenerSpinner = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provincia = dadesSpinner[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void showMessage(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    public void initSend() {
        /*String name = editTextNombre.getText().toString();
        String last_name = editTextApellidos.getText().toString();
        String company = "company";
        String description = editTextComentarios.getText().toString();
        String email = editTextEmail.getText().toString();
        String phone = editTextTelefono.getText().toString();
        String site = "gruposifu";
        String client = editTextCliente.getText().toString();*/
        System.out.println("Entra");
        String name = "Olga";
        String last_name = "Coll Pérez";
        String company = "company";
        String description = "description";
        String email = "olga@gmail.com";
        String phone = "685472156";
        String site = "gruposifu";
        String client = "yo";

        apiService.sendIncidencia(name, last_name, company, description, email, phone, site, client).enqueue(new Callback<Incidencia>() {
            @Override
            public void onResponse(Call<Incidencia> call, Response<Incidencia> response) {
                if(response.isSuccessful()){
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    System.out.println(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<Incidencia> call, Throwable t) {
                showErrorMessage();
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void showResponse(String response) {
        if(!response.equals("")) Log.e("Show response", response);
    }

    public void showErrorMessage() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    private void initSubirImagen(int indexButton) {
        switch (indexButton) {
            case 2:
                buttonEscogeImagen2.setVisibility(View.VISIBLE);
                buttonBorrarImagen2.setVisibility(View.VISIBLE);
                break;
            case 3:
                buttonEscogeImagen3.setVisibility(View.VISIBLE);
                buttonBorrarImagen3.setVisibility(View.VISIBLE);
            case 4:
                buttonEscogeImagen4.setVisibility(View.VISIBLE);
                buttonBorrarImagen4.setVisibility(View.VISIBLE);
                break;
        }
    }


}