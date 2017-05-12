package com.example.olgacoll.sifu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olgacoll.sifu.model.Solicitud;

/**
 * Created by olgacoll on 11/5/17.
 */

public class RequestFragment extends Fragment {

    private EditText editTextNombre, editTextApellidos, editTextEmail, editTextTelefono, editTextComentarios;
    Spinner spinner;
    String dadesSpinner[];
    String provinciaSeleccionada;
    boolean isCheck; //controla si la checkbox ha sido marcada
    Bundle bundle;
    Button buttonSendRequest;
    View.OnClickListener listener;
    AdapterView.OnItemSelectedListener listenerSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_request, container, false);

        initComponents(view);
        onPrepareListener();
        controlSpinner(view);

        buttonSendRequest.setOnClickListener(listener);

        return view;
    }
    public void initComponents(View view){
        editTextNombre = (EditText) view.findViewById(R.id.input_nombre);
        editTextApellidos = (EditText)view.findViewById(R.id.input_apellidos);
        editTextEmail = (EditText)view.findViewById(R.id.input_email);
        editTextTelefono = (EditText)view.findViewById(R.id.input_telefono);
        editTextComentarios = (EditText)view.findViewById(R.id.input_comentarios);
        buttonSendRequest = (Button)view.findViewById(R.id.buttonSendRequest);
        isCheck = false;
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

    public void controlSpinner(View view){
        spinner = (Spinner)view.findViewById(R.id.spinner);
        dadesSpinner =  new String[]{"Barcelona", "Madrid", "Valencia"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, dadesSpinner);
        spinner.setAdapter(adaptador);
        prepareItemListener();
        spinner.setOnItemSelectedListener(listenerSpinner);
    }

    public void prepareItemListener() {
        listenerSpinner =
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {
                        switch (dadesSpinner[position]) {
                            case "Barcelona":
                                provinciaSeleccionada = "Barcelona";
                                //showMessage(provinciaSeleccionada);
                                break;
                            case "Madrid":
                                provinciaSeleccionada = "Madrid";
                                //showMessage(provinciaSeleccionada);
                                break;
                            case "Valencia":
                                provinciaSeleccionada = "Valencia";
                                //showMessage(provinciaSeleccionada);
                                break;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                };
    }

    private void showMessage(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    //Acción que comprueba si
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox:
                if (checked){
                    isCheck = true;
                }else{
                    isCheck = false;
                }
                break;
        }
    }

    /*private void setBundle(){
        bundle = this.getIntent().getExtras();
        bundle.remove("nombreRequest");
        bundle.putString("nombreRequest", String.valueOf(editTextNombre.getText()));
        bundle.remove("apellidosRequest");
        bundle.putString("apellidosRequest", String.valueOf(editTextApellidos.getText()));
        bundle.remove("emailRequest");
        bundle.putString("emailRequest", String.valueOf(editTextEmail.getText()));
        bundle.remove("telefonoRequest");
        bundle.putString("telefonoRequest", String.valueOf(editTextTelefono.getText()));
        bundle.remove("provinciaRequest");
        bundle.putString("provinciaRequest", String.valueOf(provinciaSeleccionada));
        bundle.remove("comentariosRequest");
        bundle.putString("comentariosRequest", String.valueOf(editTextComentarios.getText()));
    }*/

    //Creación del objeto Solicitud, comprobando campos vacios
    private Solicitud setRequest(){
        Solicitud s = null;
        //si está bien validado, crearemos el objeto SOlicitud, si no, devolverá nulo
        //if(validate()){
        String nombre = editTextNombre.getText().toString();
        String apellidos = editTextApellidos.getText().toString();
        String email = editTextEmail.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String provincia = provinciaSeleccionada;
        String comentarios = editTextComentarios.getText().toString();
        s = new Solicitud(nombre, apellidos, email, telefono, provincia, comentarios);
        //}
        return s;
    }

    //validamos todos los campos comprobando que estén rellenados
    public boolean validate() {
        boolean valid = true;
        String nombre = editTextNombre.getText().toString();
        String apellidos = editTextApellidos.getText().toString();
        String email = editTextEmail.getText().toString();
        String telefono = editTextTelefono.getText().toString();

        //Provincia y comentarios no necesitaran validación alguna.
        //String provincia = provinciaSeleccionada;
        //String comentarios = editTextComentarios.getText().toString();

        if (nombre.isEmpty() || nombre.length() < 3) {
            editTextNombre.setError("at least 3 characters");
            valid = false;
        } else {
            editTextNombre.setError(null);
        }

        if (apellidos.isEmpty() || apellidos.length() < 3) {
            editTextApellidos.setError("at least 3 characters");
            valid = false;
        } else {
            editTextApellidos.setError(null);
        }

        if (email.isEmpty() || email.length() < 3 || !email.contains("@")) {
            editTextEmail.setError("El email no es correcto.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (telefono.isEmpty() || telefono.length() < 9) {
            editTextTelefono.setError("Teléfono incorrecto.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }
        return valid;
    }

    public void initSend(){
        //Comprobamos que el checkbox haya sido seleccionado, si no es así avisaremos al usuario
        if(!isCheck){
            showMessage("Acepta los términos para poder completar la solicitud.");
        }else{
            //Creamos el objeto Solicitud, en el caso de que haya habido algun problema, devuelve null y no se realizará la solicitud.
            if(!validate()){
                showMessage(Boolean.toString(validate()));
            }else{
                Solicitud solicitud = setRequest();
                if(setRequest() != null){
                    showMessage(solicitud.toString());
                }else{
                    showMessage("Ha habido un error al enviar la solicitud.");
                }
            }
        }

        //setBundle();
        //Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtras(bundle);
        //startActivity(intent);
    }
}
