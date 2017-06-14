package com.example.olgacoll.sifu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;
import com.example.olgacoll.sifu.model.Incidencia;
import com.example.olgacoll.sifu.remote.APIService;
import com.example.olgacoll.sifu.remote.ApiUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class IncidenceFragment extends Fragment {

    public static final String TAG = "IncidenceFragment";
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    private APIService apiService;
    TextView textViewSubirImagen, textViewSubirImagen2, textViewSubirImagen3, textViewSubirImagen4, textViewSubirOtraImagen;
    TextView textViewBorrarImagen2, textViewBorrarImagen3, textViewBorrarImagen4;
    EditText editTextNombre, editTextApellidos, editTextEmail, editTextTelefono, editTextComentarios;
    String nombre, apellidos, email, telefono, cliente, comentarios, uuid;
    File image_01, image_02, image_03, image_04;
    ImageView imageView;
    Bitmap bmap;
    Spinner spinner;
    String dadesSpinner[];
    String provincia;
    List<Boolean> checkButtons;
    int indexButton;
    CheckBox checkbox;
    Button buttonEnviar;
    View.OnClickListener listener;
    //View.OnTouchListener listenerDrawable;
    AdapterView.OnItemSelectedListener listenerSpinner;
    Context applicationContext = MainActivity.getContextOfApplication();
    int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_report, container, false);
        initComponents(view);
        initFont();
        onPrepareListener();
        controlSpinner(view);
        initListeners();
        return view;
    }

    public void initComponents(View view) {
        apiService = ApiUtils.getAPIService();
        cliente = "gruposifu";
        uuid = Secure.getString(this.getActivity().getContentResolver(), Secure.ANDROID_ID);
        textViewSubirImagen = (TextView) view.findViewById(R.id.textViewSubirImagen);
        textViewSubirImagen2 = (TextView) view.findViewById(R.id.textViewSubirImagen2);
        textViewSubirImagen3 = (TextView) view.findViewById(R.id.textViewSubirImagen3);
        textViewSubirImagen4 = (TextView) view.findViewById(R.id.textViewSubirImagen4);
        textViewBorrarImagen2 = (TextView) view.findViewById(R.id.textViewBorrarImagen2);
        textViewBorrarImagen3 = (TextView) view.findViewById(R.id.textViewBorrarImagen3);
        textViewBorrarImagen4 = (TextView) view.findViewById(R.id.textViewBorrarImagen4);
        textViewSubirOtraImagen = (TextView) view.findViewById(R.id.textViewSubirOtraImagen);
        editTextNombre = (EditText) view.findViewById(R.id.input_nombre);
        editTextApellidos = (EditText) view.findViewById(R.id.input_apellidos);
        editTextEmail = (EditText) view.findViewById(R.id.input_email);
        editTextTelefono = (EditText) view.findViewById(R.id.input_telefono);
        editTextComentarios = (EditText) view.findViewById(R.id.input_comentarios);
        checkbox = (CheckBox) view.findViewById(R.id.checkBox);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        buttonEnviar = (Button) view.findViewById(R.id.buttonEnviar);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        checkButtons = new ArrayList<>();
        checkButtons.add(false);
        checkButtons.add(false);
        checkButtons.add(false);
        indexButton = 1; //Con este índice, controlaremos las veces que hayan dado clic en Subir Imagen.
    }

    private void initFont(){
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Regular.ttf");
        textViewSubirImagen.setTypeface(face);
        textViewSubirImagen2.setTypeface(face);
        textViewSubirImagen3.setTypeface(face);
        textViewSubirImagen4.setTypeface(face);
        textViewSubirOtraImagen.setTypeface(face);
        textViewBorrarImagen2.setTypeface(face);
        textViewBorrarImagen3.setTypeface(face);
        textViewBorrarImagen4.setTypeface(face);
        editTextNombre.setTypeface(face);
        editTextApellidos.setTypeface(face);
        editTextEmail.setTypeface(face);
        editTextTelefono.setTypeface(face);
        editTextComentarios.setTypeface(face);
        checkbox.setTypeface(face);
        buttonEnviar.setTypeface(face);
    }

    public void onPrepareListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonEnviar:
                        initSendReport();
                        break;
                    case R.id.textViewBorrarImagen2:
                        if(textViewSubirImagen2.getVisibility() == View.VISIBLE){
                            textViewSubirImagen2.setVisibility(View.GONE);
                            textViewBorrarImagen2.setVisibility(View.GONE);
                            image_02 = new File("image02");
                            checkButtons.set(0, false);
                        }
                        break;
                    case R.id.textViewBorrarImagen3:
                        if(textViewSubirImagen3.getVisibility() == View.VISIBLE){
                            textViewSubirImagen3.setVisibility(View.GONE);
                            textViewBorrarImagen3.setVisibility(View.GONE);
                            image_03 = new File("image03");
                            checkButtons.set(1, false);
                        }
                        break;
                    case R.id.textViewBorrarImagen4:
                        if(textViewSubirImagen4.getVisibility() == View.VISIBLE){
                            textViewSubirImagen4.setVisibility(View.GONE);
                            textViewBorrarImagen4.setVisibility(View.GONE);
                            image_04 = new File("image04");
                            checkButtons.set(2, false);
                        }
                        break;
                    case R.id.textViewSubirImagen:
                        escogerImagen();
                        break;
                    case R.id.textViewSubirImagen2:
                        escogerImagen();
                        break;
                    case R.id.textViewSubirImagen3:
                        escogerImagen();
                        break;
                    case R.id.textViewSubirImagen4:
                        escogerImagen();
                        break;
                    case R.id.textViewSubirOtraImagen:
                        initSubirImagen();
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

    public void initListeners(){
        textViewBorrarImagen2.setOnClickListener(listener);
        textViewBorrarImagen3.setOnClickListener(listener);
        textViewBorrarImagen4.setOnClickListener(listener);
        buttonEnviar.setOnClickListener(listener);
        textViewSubirImagen.setOnClickListener(listener);
        textViewSubirImagen2.setOnClickListener(listener);
        textViewSubirImagen3.setOnClickListener(listener);
        textViewSubirImagen4.setOnClickListener(listener);
        textViewSubirOtraImagen.setOnClickListener(listener);
    }

    public boolean validate() {
        boolean valid = true;
        nombre = editTextNombre.getText().toString();
        apellidos = editTextApellidos.getText().toString();
        email = editTextEmail.getText().toString();
        telefono = editTextTelefono.getText().toString();
        provincia = spinner.getSelectedItem().toString();
        comentarios = editTextComentarios.getText().toString();

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

        if (comentarios.isEmpty()){
            editTextComentarios.setError("Escriba un comentario para completar su solicitud");
            valid = false;
        } else {
            editTextComentarios.setError(null);
        }

        return valid;
    }

    public void initSendReport() {
        if (!validate()) {
            //callPhone();
            sendIncidenceFailed();
        } else {
            sendIncidenceSuccess();
        }
    }

    /*private void callPhone(){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "680296461"));
        startActivity(intent);
    }*/

    private boolean checkHours(){
        boolean flag = false;
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date s
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        if(h >= 00 && h < 8){
            flag = true;
        }
        return flag;
    }

    public void sendIncidenceFailed(){
        //showMessage("Error creando la incidencia.");
    }

    public void sendIncidenceSuccess(){
        if(!checkbox.isChecked()) {
            showMessage("Acepta los términos y condiciones para poder completar la incidencia");
        }else {
            File filesDir = getActivity().getApplicationContext().getFilesDir();
            File imageFile = new File(filesDir, "image01" + ".jpg");

            imageView.buildDrawingCache();
            bmap = imageView.getDrawingCache();

            OutputStream os;
            try {
                os = new FileOutputStream(imageFile);
                bmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            RequestBody image1 = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), nombre);
            RequestBody last_name = RequestBody.create(MediaType.parse("text/plain"), apellidos);
            RequestBody company = RequestBody.create(MediaType.parse("text/plain"), provincia);
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), comentarios);
            RequestBody mail = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), telefono);
            RequestBody client = RequestBody.create(MediaType.parse("text/plain"), cliente);
            RequestBody device_id = RequestBody.create(MediaType.parse("text/plain"), uuid);

            apiService.sendIncidence(image1, name, last_name, company, description, mail, phone, client, device_id).enqueue(new Callback<Incidencia>() {
                @Override
                public void onResponse(Call<Incidencia> call, Response<Incidencia> response) {
                    if(response.code() == 200){
                        if(checkHours()){
                            initUrgentIncidence();
                        }else{
                            initIncidenceSent();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Incidencia> call, Throwable t) {
                    System.out.println(t.getCause() + t.getMessage());
                }
            });
        }
    }

    private void initUrgentIncidence(){
        Fragment fragment = new UrgentIncidenceFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void initIncidenceSent(){
        Fragment fragment = new IncidenceSentFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void initSubirImagen() {
        int i = 0;
        boolean flag = false;

        while(!flag && i < checkButtons.size()){

            if(checkButtons.get(i).equals(false)){
                switch(i){
                    case 0:
                        textViewSubirImagen2.setVisibility(View.VISIBLE);
                        textViewBorrarImagen2.setVisibility(View.VISIBLE);
                        checkButtons.set(0, true);
                        break;
                    case 1:
                        textViewSubirImagen3.setVisibility(View.VISIBLE);
                        textViewBorrarImagen3.setVisibility(View.VISIBLE);
                        checkButtons.set(1, true);
                        break;
                    case 2:
                        textViewSubirImagen4.setVisibility(View.VISIBLE);
                        textViewBorrarImagen4.setVisibility(View.VISIBLE);
                        checkButtons.set(2, true);
                        break;
                }
                flag = true;
            }
            i++;
        }
    }

    private void escogerImagen(){
        String title = "Escoger una imagen";
        CharSequence[] itemlist ={"Cámara", "Elegir foto de galería"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_home_black_24dp);
        builder.setTitle(title);
        builder.setItems(itemlist, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:// Take Photo
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);
                        break;
                    case 1:// Choose Existing Photo
                        //Intent intent2 = new Intent();
                        //intent2.setType("image/*");
                        //intent2.setAction(Intent.ACTION_GET_CONTENT);
                        //startActivityForResult(Intent.createChooser(intent2, "Select Picture"), PICK_IMAGE_REQUEST);
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);
                        break;
                    default:
                        break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.setCancelable(true);
        alert.show();
    }

    private void showMessage(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    //imageView.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    //imageView.setImageURI(selectedImage);
                }
                break;
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input){
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void onResume(){
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarCenterTitle("Reportar incidencia");
        ((MainActivity) getActivity()).getNavigationVisible(true);
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }
}