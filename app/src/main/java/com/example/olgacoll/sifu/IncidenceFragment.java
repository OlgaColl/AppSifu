package com.example.olgacoll.sifu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompatBase;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;
import com.example.olgacoll.sifu.model.Incidencia;
import com.example.olgacoll.sifu.remote.APIService;
import com.example.olgacoll.sifu.remote.ApiUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

import static android.app.Activity.RESULT_OK;

public class IncidenceFragment extends Fragment {

    public static final String TAG = "IncidenceFragment";
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    private APIService apiService;
    TextView textSwitch;
    TextView textViewSubirImagen, textViewSubirImagen2, textViewSubirImagen3, textViewSubirImagen4;
    EditText editTextNombre, editTextApellidos, editTextEmail, editTextTelefono, editTextComentarios;
    String nombre, apellidos, email, telefono, cliente, comentarios, uuid;
    File image_01, image_02, image_03, image_04;
    ImageView imageView, imageView2, imageView3, imageView4;
    ImageView imageDelete, imageDelete2, imageDelete3, imageDelete4;
    Bitmap bmap;
    Spinner spinner;
    String dadesSpinner[];
    String provincia;
    List<Boolean> checkButtons;
    int indexButton;
    Switch onOffSwitch;
    boolean isCheckedSwitch;
    Button buttonEnviar;
    View.OnClickListener listener;
    //View.OnTouchListener listenerDrawable;
    AdapterView.OnItemSelectedListener listenerSpinner;
    Context applicationContext = MainActivity.getContextOfApplication();
    int i = 0;
    private static final int PHOTO_REQUEST_CODE1 = 1;
    private static final int PHOTO_REQUEST_CODE2 = 2;
    private static final int PHOTO_REQUEST_CODE3 = 3;
    private static final int PHOTO_REQUEST_CODE4 = 4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_incidence, container, false);
        initComponents(view);
        initFont();
        onPrepareListener();
        controlSpinner(view);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isCheckedSwitch = true;
                    onOffSwitch.getThumbDrawable().setColorFilter(Color.rgb(241, 139, 35), PorterDuff.Mode.MULTIPLY);
                    buttonEnviar.setBackgroundResource(R.drawable.shape_orange_buttons);
                }else{
                    isCheckedSwitch = false;
                    onOffSwitch.getThumbDrawable().setColorFilter(Color.rgb(229, 229, 229), PorterDuff.Mode.MULTIPLY);
                    buttonEnviar.setBackgroundResource(R.drawable.shape_grey_buttons);
                }
            }
        });
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
        textSwitch = (TextView) view.findViewById(R.id.textSwitch);
        editTextNombre = (EditText) view.findViewById(R.id.input_nombre);
        editTextApellidos = (EditText) view.findViewById(R.id.input_apellidos);
        editTextEmail = (EditText) view.findViewById(R.id.input_email);
        editTextTelefono = (EditText) view.findViewById(R.id.input_telefono);
        editTextComentarios = (EditText) view.findViewById(R.id.input_comentarios);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        imageView3 = (ImageView) view.findViewById(R.id.imageView3);
        imageView4 = (ImageView) view.findViewById(R.id.imageView4);
        imageDelete = (ImageView) view.findViewById(R.id.imageDelete);
        imageDelete2 = (ImageView) view.findViewById(R.id.imageDelete2);
        imageDelete3 = (ImageView) view.findViewById(R.id.imageDelete3);
        imageDelete4 = (ImageView) view.findViewById(R.id.imageDelete4);
        buttonEnviar = (Button) view.findViewById(R.id.buttonEnviar);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        checkButtons = new ArrayList<>();
        checkButtons.add(false);
        checkButtons.add(false);
        checkButtons.add(false);
        checkButtons.add(false);
        indexButton = 1; //Con este índice, controlaremos las veces que hayan dado clic en Subir Imagen.
        isCheckedSwitch = true;
        onOffSwitch = (Switch) view.findViewById(R.id.switch1);
        onOffSwitch.getThumbDrawable().setColorFilter(Color.rgb(241, 139, 35), PorterDuff.Mode.MULTIPLY);
    }

    private void initFont(){
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Regular.ttf");
        textViewSubirImagen.setTypeface(face);
        textViewSubirImagen2.setTypeface(face);
        textViewSubirImagen3.setTypeface(face);
        textViewSubirImagen4.setTypeface(face);
        editTextNombre.setTypeface(face);
        editTextApellidos.setTypeface(face);
        editTextEmail.setTypeface(face);
        editTextTelefono.setTypeface(face);
        editTextComentarios.setTypeface(face);
        textSwitch.setTypeface(face);
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
                    case R.id.textViewSubirImagen:
                        escogerImagen(1);
                        break;
                    case R.id.textViewSubirImagen2:
                        escogerImagen(2);
                        break;
                    case R.id.textViewSubirImagen3:
                        escogerImagen(3);
                        break;
                    case R.id.textViewSubirImagen4:
                        escogerImagen(4);
                        break;
                    case R.id.imageDelete:
                        imageView.setVisibility(View.GONE);
                        imageDelete.setVisibility(View.GONE);
                        textViewSubirImagen.setVisibility(View.VISIBLE);
                        image_01 = new File("image01");
                        if(imageView2.getVisibility() == View.GONE) textViewSubirImagen2.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageDelete2:
                        imageView2.setVisibility(View.GONE);
                        imageDelete2.setVisibility(View.GONE);
                        textViewSubirImagen2.setVisibility(View.VISIBLE);
                        image_02 = new File("image02");
                        if(imageView3.getVisibility() == View.GONE) textViewSubirImagen3.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageDelete3:
                        imageView3.setVisibility(View.GONE);
                        imageDelete3.setVisibility(View.GONE);
                        textViewSubirImagen3.setVisibility(View.VISIBLE);
                        image_03 = new File("image03");
                        if(imageView4.getVisibility() == View.GONE) textViewSubirImagen4.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageDelete4:
                        imageView4.setVisibility(View.GONE);
                        imageDelete4.setVisibility(View.GONE);
                        textViewSubirImagen4.setVisibility(View.VISIBLE);
                        image_04 = new File("image04");
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
        imageDelete.setOnClickListener(listener);
        imageDelete2.setOnClickListener(listener);
        imageDelete3.setOnClickListener(listener);
        imageDelete4.setOnClickListener(listener);
        buttonEnviar.setOnClickListener(listener);
        textViewSubirImagen.setOnClickListener(listener);
        textViewSubirImagen2.setOnClickListener(listener);
        textViewSubirImagen3.setOnClickListener(listener);
        textViewSubirImagen4.setOnClickListener(listener);
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
        if(!isCheckedSwitch) {
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

            initUrgentIncidence();
            /*if(checkHours()){
                initUrgentIncidence();
            }else{
                initIncidenceSent();
            }*/

            apiService.sendIncidence(image1, name, last_name, company, description, mail, phone, client, device_id).enqueue(new Callback<Incidencia>() {
                @Override
                public void onResponse(Call<Incidencia> call, Response<Incidencia> response) {
                    if(response.code() == 200){

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
                        checkButtons.set(0, true);
                        break;
                    case 1:
                        textViewSubirImagen3.setVisibility(View.VISIBLE);
                        checkButtons.set(1, true);
                        break;
                    case 2:
                        textViewSubirImagen4.setVisibility(View.VISIBLE);
                        checkButtons.set(2, true);
                        break;
                }
                flag = true;
            }
            i++;
        }
    }

    private void escogerImagen(int i){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        switch(i){
            case 1:
                startActivityForResult(pickPhoto, PHOTO_REQUEST_CODE1);
                imageView.setVisibility(View.VISIBLE);
                imageDelete.setVisibility(View.VISIBLE);
                textViewSubirImagen.setVisibility(View.GONE);
                if(imageView2.getVisibility() == View.GONE) textViewSubirImagen2.setVisibility(View.VISIBLE);
                break;
            case 2:
                startActivityForResult(pickPhoto, PHOTO_REQUEST_CODE2);
                imageView2.setVisibility(View.VISIBLE);
                imageDelete2.setVisibility(View.VISIBLE);
                textViewSubirImagen2.setVisibility(View.GONE);
                if(imageView3.getVisibility() == View.GONE) textViewSubirImagen3.setVisibility(View.VISIBLE);
                break;
            case 3:
                startActivityForResult(pickPhoto, PHOTO_REQUEST_CODE3);
                imageView3.setVisibility(View.VISIBLE);
                imageDelete3.setVisibility(View.VISIBLE);
                textViewSubirImagen3.setVisibility(View.GONE);
                if(imageView4.getVisibility() == View.GONE) textViewSubirImagen4.setVisibility(View.VISIBLE);
                break;
            case 4:
                startActivityForResult(pickPhoto, PHOTO_REQUEST_CODE4);
                imageView4.setVisibility(View.VISIBLE);
                imageDelete4.setVisibility(View.VISIBLE);
                textViewSubirImagen4.setVisibility(View.GONE);
                break;
        }
    }

    private void showMessage(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {

            case PHOTO_REQUEST_CODE1:
                if(resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                }
                break;

            case PHOTO_REQUEST_CODE2:
                if(resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView2.setImageURI(selectedImage);
                }
                break;

            case PHOTO_REQUEST_CODE3:
                if(resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView3.setImageURI(selectedImage);
                }
                break;

            case PHOTO_REQUEST_CODE4:
                if(resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView4.setImageURI(selectedImage);
                }
                break;
        }
    }

    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarCenterTitle("Reportar incidencia");
        ((MainActivity) getActivity()).getNavigationVisible(true);
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }
}