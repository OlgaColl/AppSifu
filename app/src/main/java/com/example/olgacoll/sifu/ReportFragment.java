package com.example.olgacoll.sifu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class ReportFragment extends Fragment {

    public static final String TAG ="ReportFragment";
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    private APIService apiService;
    EditText editTextNombre, editTextApellidos, editTextEmail, editTextTelefono, editTextComentarios;
    String nombre, apellidos, email, telefono, cliente, site, comentarios, uuid;
    File image_01, image_02, image_03, image_04;
    Spinner spinner;
    String dadesSpinner[];
    String provincia;
    List<Boolean> checkButtons;
    int indexButton;
    CheckBox checkbox;
    Button buttonSubirImagen, buttonEnviar;
    Button buttonEscogeImagen, buttonEscogeImagen2, buttonEscogeImagen3, buttonEscogeImagen4;
    Button buttonBorrarImagen2, buttonBorrarImagen3, buttonBorrarImagen4;
    View.OnClickListener listener;
    AdapterView.OnItemSelectedListener listenerSpinner;
    private int PICK_IMAGE_REQUEST = 1;
    Context applicationContext = MainActivity.getContextOfApplication();
    int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_report, container, false);
        initComponents(view);
        onPrepareListener();
        controlSpinner(view);
        initListeners();
        return view;
    }

    public void initComponents(View view) {
        apiService = ApiUtils.getAPIService();
        cliente = "gruposifu";
        uuid = Secure.getString(this.getActivity().getContentResolver(), Secure.ANDROID_ID);
        editTextNombre = (EditText) view.findViewById(R.id.input_nombre);
        editTextApellidos = (EditText) view.findViewById(R.id.input_apellidos);
        editTextEmail = (EditText) view.findViewById(R.id.input_email);
        editTextTelefono = (EditText) view.findViewById(R.id.input_telefono);
        editTextComentarios = (EditText) view.findViewById(R.id.input_comentarios);
        checkbox = (CheckBox)view.findViewById(R.id.checkBox);
        buttonSubirImagen = (Button) view.findViewById(R.id.buttonSubirImagen);
        buttonEscogeImagen = (Button) view.findViewById(R.id.buttonEscogeImagen);
        buttonEscogeImagen2 = (Button) view.findViewById(R.id.buttonEscogeImagen2);
        buttonEscogeImagen3 = (Button) view.findViewById(R.id.buttonEscogeImagen3);
        buttonEscogeImagen4 = (Button) view.findViewById(R.id.buttonEscogeImagen4);
        buttonBorrarImagen2 = (Button) view.findViewById(R.id.buttonBorrarImagen2);
        buttonBorrarImagen3 = (Button) view.findViewById(R.id.buttonBorrarImagen3);
        buttonBorrarImagen4 = (Button) view.findViewById(R.id.buttonBorrarImagen4);
        buttonEnviar = (Button) view.findViewById(R.id.buttonEnviar);
        checkButtons = new ArrayList<>();
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
                        initSendReport();
                        break;
                    case R.id.buttonSubirImagen:
                        initSubirImagen();
                        break;
                    case R.id.buttonBorrarImagen2:
                          if(buttonEscogeImagen2.getVisibility() == View.VISIBLE){
                              buttonEscogeImagen2.setVisibility(View.GONE);
                              buttonBorrarImagen2.setVisibility(View.GONE);
                              image_02 = new File("image02");
                              checkButtons.set(0, false);
                          }
                          break;
                    case R.id.buttonBorrarImagen3:
                        if(buttonEscogeImagen3.getVisibility() == View.VISIBLE){
                            buttonEscogeImagen3.setVisibility(View.GONE);
                            buttonBorrarImagen3.setVisibility(View.GONE);
                            image_03 = new File("image03");
                            checkButtons.set(1, false);
                        }
                        break;
                    case R.id.buttonBorrarImagen4:
                        if(buttonEscogeImagen4.getVisibility() == View.VISIBLE){
                            buttonEscogeImagen4.setVisibility(View.GONE);
                            buttonBorrarImagen4.setVisibility(View.GONE);
                            image_04 = new File("image04");
                            checkButtons.set(2, false);
                        }
                        break;
                    case R.id.buttonEscogeImagen:
                        escogerImagen();
                        break;
                    case R.id.buttonEscogeImagen2:
                        escogerImagen();
                        break;
                    case R.id.buttonEscogeImagen3:
                        escogerImagen();
                        break;
                    case R.id.buttonEscogeImagen4:
                        escogerImagen();
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
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptador);
        spinner.setOnItemSelectedListener(listenerSpinner);
    }

    public void initListeners(){
        buttonSubirImagen.setOnClickListener(listener);
        buttonEscogeImagen.setOnClickListener(listener);
        buttonEscogeImagen2.setOnClickListener(listener);
        buttonEscogeImagen3.setOnClickListener(listener);
        buttonEscogeImagen4.setOnClickListener(listener);
        buttonBorrarImagen2.setOnClickListener(listener);
        buttonBorrarImagen3.setOnClickListener(listener);
        buttonBorrarImagen4.setOnClickListener(listener);
        buttonEnviar.setOnClickListener(listener);
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
            sendReportFailed();
        } else {
            sendMailSuccess();
        }
    }

    public void sendReportFailed(){
        //showMessage("Error creando la incidencia.");
    }

    public void sendMailSuccess(){
        if(!checkbox.isChecked()) {
            showMessage("Acepta los términos y condiciones para poder completar la incidencia");
        }else {

            File filesDir = getActivity().getApplicationContext().getFilesDir();
            File imageFile = new File(filesDir, "image1" + ".jpg");

            RequestBody image1 = RequestBody.create(MediaType.parse("image/*"), imageFile);
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
                    System.out.println(response.body().toString());
                }

                @Override
                public void onFailure(Call<Incidencia> call, Throwable t) {
                    System.out.println(t.getCause() + t.getMessage());
                }
            });
        }
    }

    private void showCallAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setIcon(R.drawable.ic_stat_name);
        alert.setTitle("Aviso");
        alert.setMessage("Si la incidencia es muy urgente, llame al siguiente numero de teléfono: ");
        alert.setPositiveButton("OK",null);
        alert.show();
    }

    private void initSubirImagen() {

        int i = 0;
        boolean flag = false;

        while(!flag && i < checkButtons.size()){

            if(checkButtons.get(i).equals(false)){
                switch(i){
                    case 0:
                        buttonEscogeImagen2.setVisibility(View.VISIBLE);
                        buttonBorrarImagen2.setVisibility(View.VISIBLE);
                        checkButtons.set(0, true);
                        break;
                    case 1:
                        buttonEscogeImagen3.setVisibility(View.VISIBLE);
                        buttonBorrarImagen3.setVisibility(View.VISIBLE);
                        checkButtons.set(1, true);
                        break;
                    case 2:
                        buttonEscogeImagen4.setVisibility(View.VISIBLE);
                        buttonBorrarImagen4.setVisibility(View.VISIBLE);
                        checkButtons.set(2, true);
                        break;
                }

                flag = true;
            }

            i++;
        }
    }
    private static final int PHOTO_REQUEST_CAMERA = 0;//camera
    private static final int PHOTO_REQUEST_GALLERY = 1;//gallery
    private static final int PHOTO_REQUEST_CUT = 2;//image crop

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
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photo));
                        imageUri = Uri.fromFile(photo);
                        startActivityForResult(intent, TAKE_PICTURE);
                        break;
                    case 1:// Choose Existing Photo
                        // Do Pick Photo task here
                        Intent intent2 = new Intent();
                        // Show only images, no videos or anything else
                        intent2.setType("image/*");
                        intent2.setAction(Intent.ACTION_GET_CONTENT);
                        // Always show the chooser (if there are multiple options available)
                        startActivityForResult(Intent.createChooser(intent2, "Select Picture"), PICK_IMAGE_REQUEST);
                        //Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        //startActivityForResult(galleryIntent , 0);//one can be replaced with any action code
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

    private void showMessage(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);

                persistImage(bitmap, "image_01");
                //String myBase64Image = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
                //System.out.println(myBase64Image);
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                System.out.println("Message: " + e.getMessage());
                //e.printStackTrace();
            }
        }
    }

    private void persistImage(Bitmap bitmap, String name) {
        File filesDir = getActivity().getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            System.out.println(os.toString() + os);
            os.flush();
            os.close();

            /*apiService.sendFiles(imageFile, imageFile, imageFile, imageFile).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //System.out.println("SEND FILES: " + response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println("Cause " + t.getCause() + " Message: " + t.getMessage());
                }
            });*/
        } catch (Exception e) {
            System.out.println("Error, exception: " + e.getMessage());
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
    }
}