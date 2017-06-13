package com.example.olgacoll.sifu;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olgacoll.sifu.remote.APIService;
import com.example.olgacoll.sifu.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestFragment extends Fragment {
    
    public static final String TAG ="RequestFragment";
    private APIService apiService;
    String nombre, apellidos, email, telefono, provincia, mensaje;
    TextView textViewInfoRequest;
    EditText editTextNombre, editTextApellidos, editTextEmail, editTextTelefono, editTextComentarios;
    Spinner spinner;
    String dadesSpinner[];
    CheckBox checkbox;
    Button buttonSendRequest;
    View.OnClickListener listener;
    AdapterView.OnItemSelectedListener listenerSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_request, container, false);
        initComponents(view);
        initFont();
        onPrepareListener();
        controlSpinner(view);
        buttonSendRequest.setOnClickListener(listener);
        return view;
    }

    public void initComponents(View view) {
        apiService = ApiUtils.getAPIService();
        textViewInfoRequest = (TextView) view.findViewById(R.id.textViewInfoRequest);
        editTextNombre = (EditText) view.findViewById(R.id.input_nombre);
        editTextApellidos = (EditText) view.findViewById(R.id.input_apellidos);
        editTextEmail = (EditText) view.findViewById(R.id.input_email);
        editTextTelefono = (EditText) view.findViewById(R.id.input_telefono);
        editTextComentarios = (EditText) view.findViewById(R.id.input_comentarios);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        checkbox = (CheckBox)view.findViewById(R.id.checkBox);
        buttonSendRequest = (Button) view.findViewById(R.id.buttonSendRequest);
    }

    private void initFont(){
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Regular.ttf");
        textViewInfoRequest.setTypeface(face);
        editTextNombre.setTypeface(face);
        editTextApellidos.setTypeface(face);
        editTextEmail.setTypeface(face);
        editTextTelefono.setTypeface(face);
        editTextComentarios.setTypeface(face);
        checkbox.setTypeface(face);
        buttonSendRequest.setTypeface(face);
    }

    public void onPrepareListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonSendRequest:
                        initSend();
                        break;
                }
            }
        };
    }

    public void controlSpinner(View view) {
        dadesSpinner = new String[]{"Provincia", "Álava", "Albacete", "Alicante", "Almería", "Asturias", "Ávila", "Badajoz", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Cantabria", "Castellón", "Ciudad Real", "Córdoba",
                "La Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara", "Guipúzcoa", "Huelva", "Huesca", "Islas Baleares", "Jaén", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia",
                "Navarra", "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja", "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona", "Santa Cruz de Tenerife", "Teruel", "Toledo",
                "Valencia", "Vizcaya", "Zamora", "Zaragona"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, dadesSpinner);
        spinner.setAdapter(adaptador);
        spinner.setOnItemSelectedListener(listenerSpinner);
    }

    public boolean validate() {
        boolean valid = true;
        nombre = editTextNombre.getText().toString();
        apellidos = editTextApellidos.getText().toString();
        email = editTextEmail.getText().toString();
        telefono = editTextTelefono.getText().toString();
        provincia = spinner.getSelectedItem().toString();
        mensaje = editTextComentarios.getText().toString();

        if (nombre.isEmpty() || nombre.length() < 1) {
            editTextNombre.setError("Nombre demasiado corto");
            valid = false;
        } else {
            editTextNombre.setError(null);
        }

        if (apellidos.isEmpty() || apellidos.length() < 3) {
            editTextApellidos.setError("Apellidos incorrectos");
            valid = false;
        } else {
            editTextApellidos.setError(null);
        }

        if (email.isEmpty() || email.length() < 5 || !email.contains("@")) {
            editTextEmail.setError("Mail incorrecto");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (telefono.isEmpty() || telefono.length() < 9) {
            editTextTelefono.setError("Teléfono incorrecto");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (provincia.equals("Provincia")){
            valid = false;
        }

        if (mensaje.isEmpty()){
            editTextComentarios.setError("Escriba un comentario para completar su solicitud");
            valid = false;
        } else {
            editTextComentarios.setError(null);
        }
        return valid;
    }

    public void initSend() {
        if (!validate()) {
            sendMailFailed();
        } else {
            sendMailSuccess();
        }
    }

    public void sendMailFailed(){
        //showMessage("Error enviando el email");
    }

    public void sendMailSuccess(){
        if(!checkbox.isChecked()){
            showMessage("Acepta los términos y condiciones para poder completar la solicitud");
        }else{
            apiService.sendMail(nombre, apellidos, email, telefono, provincia, mensaje).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    showMessage("Solicitud enviada");
                    Log.i(TAG, "post submitted to API.");
                    createMail();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                }
            });

        }
    }

    public void createMail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ocoll@deideasmarketing.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, nombre + "" + apellidos);
        i.putExtra(Intent.EXTRA_TEXT, mensaje);
        i.putExtra(Intent.EXTRA_TEXT, "Nombre: " + nombre + "\nApellidos: " + apellidos + "\nEmail: " + email + "\nProvincia: " + provincia + "\nTelefono: " + telefono + "\nComentarios: " + mensaje);
        try {
            startActivity(Intent.createChooser(i, "Enviando mail..."));
            showMessage("Solicitud enviada");
            cleanFields();
        } catch (android.content.ActivityNotFoundException ex) {
            showMessage("No hay ninguna aplicación de correo instalada");
        }
    }

    public void cleanFields(){
        editTextNombre.setText("");
        editTextApellidos.setText("");
        editTextEmail.setText("");
        editTextTelefono.setText("");
        spinner.setSelection(0);
        editTextComentarios.setText("");
        if(checkbox.isChecked()){
            checkbox.setChecked(false);
            checkbox.setSelected(false);
        }
    }

    private void showMessage(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    public void onResume(){
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarCenterTitle("Solicitud de información");
        ((MainActivity) getActivity()).getNavigationVisible(true);
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }
}
