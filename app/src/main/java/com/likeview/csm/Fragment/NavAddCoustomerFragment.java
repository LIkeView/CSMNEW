package com.likeview.csm.Fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.ApiResponse.ApiResponseWithoutResData;
import com.likeview.csm.MainActivity;
import com.likeview.csm.R;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

//import com.aswdc.autocadetutorial.adapter.VideoAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavAddCoustomerFragment extends Fragment implements View.OnClickListener{
    TextView textUserName ,textPhoneNumber,textEmail,textCity,textState,textCountry,textPincode;
    ImageView textEdit;
    CircleImageView imgUserProfilePhoto;
    Button btnSubmit;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    Context context;
    Bitmap bitmap;
    Uri uri;
    ImageView chooseLogo;
    protected static final int CAMERA_REQUEST = 0;
    private static final int PICK_IMAGE = 1;
    Spinner spStateAddUser;
    TextInputEditText datepick;
    TextInputLayout datelayout;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog;
//    SharedPrefManager sfm = SharedPrefManager.getInstance(getActivity());
//    ProfileDetail pd = sfm.getUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_nav_add_coustomer, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        // Toolbar

        TextView toolbartext;
        toolbar=  view.findViewById(R.id.toolbar);
        toolbartext=  view.findViewById(R.id.toolbartext);
        toolbartext.setText( "Add Coustomer" );
        btnSubmit = view.findViewById( R.id.btnSubmit );
        btnSubmit.setOnClickListener( this );
//        datepick = view.findViewById(R.id.datepickeredit);
//        datepick.setInputType(InputType.TYPE_NULL);
        datepick = view.findViewById(R.id.datepicker);
        datepick.setInputType(InputType.TYPE_NULL);
        datelayout = view.findViewById(R.id.datlayout);
        Log.d( "Dk::3", "Hello" );
        chooseLogo = view.findViewById( R.id.chooseLogo );
        chooseLogo.setOnClickListener( this );

        spStateAddUser = view.findViewById(R.id.spStateAddUser);

        String[] items = new String[]{
                "Select an item","HIGH PRICE", "HAVE DEALERS", "DESIGN ISSUE", "QUALITY ISSUE","AVALIBLITY","PENDING"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStateAddUser.setAdapter(adapter);
        spStateAddUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Hold = spStateAddUser.getSelectedItemPosition();
                Log.v("item", String.valueOf(spStateAddUser.getSelectedItemPosition()));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                
            }
        });

        datepick.setText(getCurrentDate());
        Calendar newCalendar = Calendar.getInstance();

        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        datepick.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },  newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        return sdf.format(cal.getTime());
    }

    private void userLogin() {

//        String email = editTextEmail.getText().toString().trim();
        String Name = "Darshan Kasundra";

//        if (email.isEmpty()) {
//            editTextEmail.setError( "Email is required" );
//            editTextEmail.requestFocus();
//            return;
//        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Enter a valid email");
//            editTextEmail.requestFocus();
//            return;
//        }

//        if (password.isEmpty()) {
//            editTextPassword.setError( "Password required" );
//            editTextPassword.requestFocus();
//            return;
//        }
//
//        if (password.length() < 8) {
//            editTextPassword.setError( "Password should be atleast 8 character long" );
//            editTextPassword.requestFocus();
//            return;
//        }
        Log.d( "Dk::2", "Hello" );

        Api api = RetrofitClient.getApi().create( Api.class );
        Call<ApiResponseWithoutResData> call = api.getAddCoustomer( 1,"Darshan Kasundra" );

        Log.d( "Dk::2", "Hello" );

        call.enqueue( new Callback<ApiResponseWithoutResData>() {
            @Override
            public void onResponse(Call<ApiResponseWithoutResData> call, Response<ApiResponseWithoutResData> response) {
                ApiResponseWithoutResData apiResponseWithoutResData = response.body();
                Log.d( "Dk::1", "" + apiResponseWithoutResData.getResMessage() );

                if (response.body().getResCode() == 1) {
                    Log.d( "Dk", "" + apiResponseWithoutResData.getResMessage() );
                    Toast.makeText( getActivity(), apiResponseWithoutResData.getResMessage(), Toast.LENGTH_LONG ).show();
//                    Intent intent = new Intent( LoginActivity.this, MainNavActivity.class );
//                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                    startActivity( intent );
                } else {
                    Toast.makeText( getActivity(), apiResponseWithoutResData.getResMessage(), Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseWithoutResData> call, Throwable t) {
                Log.d( "Login", "" + t.getLocalizedMessage() );
                Toast.makeText( getActivity() , "Hello", Toast.LENGTH_LONG ).show();
            }
        } );

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    chooseLogo.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//                Log.d("path of image from gallery......******************.........", picturePath+"");
                chooseLogo.setImageBitmap(thumbnail);
            }
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult( requestCode, resultCode, data );
//        switch (requestCode) {
//            case PICK_IMAGE:
//                if (resultCode == RESULT_OK) {
//                    uri = data.getData();
//                    try {
////                        bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), uri );
//                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
//                        chooseLogo.setImageBitmap( bitmap );
//                        Log.d("bitmap",""+bitmap);
////                        String imagepath = data.getData().getPath();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//        }
//
//    }


//    private void selectImage() {
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE );
//        } else {
//            Intent galleryIntent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
//            startActivityForResult( galleryIntent, PICK_IMAGE );
//        }
//    }

    private void selectImage() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PICK_IMAGE );
        } else {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//                        Uri f = FileProvider.getUriForFile(context, android.os.Environment.getExternalStorageDirectory() + ".provider", new File("temp.jpg"));
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, f);
                        startActivityForResult(intent, 1);
                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                userLogin();
                break;
            case R.id.chooseLogo:
                selectImage();
                break;
        }
    }
}
