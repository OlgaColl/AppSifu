package com.example.olgacoll.sifu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
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
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    Incidencia[] listIncidencias;
    String provincia;
    Bundle bundle;
    List<Boolean> checkButtons = new ArrayList();
    int indexButton;
    Button buttonSubirImagen, buttonEnviar;
    Button buttonEscogeImagen, buttonEscogeImagen2, buttonEscogeImagen3, buttonEscogeImagen4;
    Button buttonBorrarImagen2, buttonBorrarImagen3, buttonBorrarImagen4;
    View.OnClickListener listener;
    AdapterView.OnItemSelectedListener listenerSpinner;
    private static final int SELECT_FILE = 1;

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
        checkButtons.add(false);
        checkButtons.add(false);
        checkButtons.add(false);
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
                        //if (indexButton <= 4) indexButton++;
                        //initSubirImagen(indexButton);

                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        break;
                    case R.id.buttonBorrarImagen2:
                      if(buttonEscogeImagen2.getVisibility() == View.VISIBLE){
                          buttonEscogeImagen2.setVisibility(View.GONE);
                          buttonBorrarImagen2.setVisibility(View.GONE);
                      }
                      break;
                    case R.id.buttonBorrarImagen3:
                        if(buttonEscogeImagen3.getVisibility() == View.VISIBLE){
                            buttonEscogeImagen3.setVisibility(View.GONE);
                            buttonBorrarImagen3.setVisibility(View.GONE);
                        }
                        break;
                    case R.id.buttonBorrarImagen4:
                        if(buttonEscogeImagen4.getVisibility() == View.VISIBLE){
                            buttonEscogeImagen4.setVisibility(View.GONE);
                            buttonBorrarImagen4.setVisibility(View.GONE);
                        }
                        break;
                }

                /*if (myView.getVisibility() == View.VISIBLE) {
                    // Its visible
                } else {
                    // Either gone or invisible
                }*/
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
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        String name = "Nombre";
        String last_name = "Apellidos";
        String phone = "685472156";
        String site = "gruposifu";
        String description = "description";
        String client = "yo";
        String email = "olga@gmail.com";

        apiService.sendIncidencia(name, last_name, phone, site, description, client, email).enqueue(new Callback<Incidencia>() {
            @Override
            public void onResponse(Call<Incidencia> call, Response<Incidencia> response) {

                if(response.isSuccessful()){
                    System.out.println("Status code " + response.code());
                    Log.i(TAG, "post submitted to API.");
                }
            }

            @Override
            public void onFailure(Call<Incidencia> call, Throwable t) {
                showErrorMessage();
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
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
    private static final int PHOTO_REQUEST_CAMERA = 0;//camera
    private static final int PHOTO_REQUEST_GALLERY = 1;//gallery
    private static final int PHOTO_REQUEST_CUT = 2;//image crop

    public void onResume(){
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarCenterTitle("Reportar incidencia");
    }

}