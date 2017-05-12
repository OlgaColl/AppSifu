package com.example.olgacoll.sifu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

/**
 * Created by olgacoll on 11/5/17.
 */

public class ReportFragment extends Fragment {

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
      if (indexButton <= 4) indexButton++; //control para que nunca pase de 4.
      initSubirImagen(indexButton);
      break;
    }
   }
  };
 }

 /*private void initHome(){
  Intent intent = new Intent(this, MainActivity.class);
  startActivity(intent);
 }

 private void initReport(){
  Intent intent = new Intent(this, ReportActivity.class);
  startActivity(intent);
 }

 private void initRequest(){
  Intent intent = new Intent(this, RequestActivity.class);
  startActivity(intent);
 }

 private void initInfo(){
  Intent intent = new Intent(this, InfoActivity.class);
  startActivity(intent);
 }*/

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
              provincia = "Barcelona";
              showMessage();
              break;
             case "Madrid":
              provincia = "Madrid";
              showMessage();
              break;
             case "Valencia":
              provincia = "Valencia";
              showMessage();
              break;
            }
           }
           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
          };
 }

 private void showMessage() {
  Toast.makeText(getActivity(), "Provincia seleccionada: " + provincia, Toast.LENGTH_SHORT).show();
 }

 /*private void setBundle(){
 bundle = this.getIntent().getExtras();
 bundle.remove("nombreReport");
 bundle.putString("nombreReport", String.valueOf(editTextNombre.getText()));
 bundle.remove("apellidosReport");
 bundle.putString("apellidosReport", String.valueOf(editTextApellidos.getText()));
 bundle.remove("emailReport");
 bundle.putString("emailReport", String.valueOf(editTextEmail.getText()));
 bundle.remove("telefonoReport");
 bundle.putString("telefonoReport", String.valueOf(editTextTelefono.getText()));
 bundle.remove("provinciaReport");
 bundle.putString("provinciaReport", String.valueOf(provincia));
 bundle.remove("clienteReport");
 bundle.putString("clienteReport", String.valueOf(editTextCliente.getText()));
 bundle.remove("comentariosReport");
 bundle.putString("comentariosReport", String.valueOf(editTextComentarios.getText()));
 }*/

 public void initSend(){
    //setBundle();
    Toast.makeText(getActivity(), "OK incidencia", Toast.LENGTH_SHORT).show();
    //Intent intent = new Intent(this, MainActivity.class);
    //intent.putExtras(bundle);
    //startActivity(intent);
 }

 private void initSubirImagen(int indexButton){
      switch(indexButton){
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

 /*private void initConfig(){
  Intent intent = new Intent(this, ConfigActivity.class);
  startActivity(intent);
 }
}*/
}
