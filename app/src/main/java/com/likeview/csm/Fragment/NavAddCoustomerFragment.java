package com.likeview.csm.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.ApiResponse.ApiResponseWithoutResData;
import com.likeview.csm.ApiResponse.Model.ProfileDetailModel;
import com.likeview.csm.MainActivity;
import com.likeview.csm.R;
import com.likeview.csm.Reciver.RemainderBroadCast;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;
import com.likeview.csm.storage.SharedPrefManager;
import com.likeview.csm.utils.FileUtil;
import com.likeview.csm.utils.FileUtilsNew;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

//import com.aswdc.autocadetutorial.adapter.VideoAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavAddCoustomerFragment extends Fragment implements View.OnClickListener,View.OnTouchListener{
    TextView textUserName ,textPhoneNumber,textEmail,textCity,textState,textCountry,textPincode;
    EditText editTextFirmName,editTextPersionName,editTextAddressLine1,editTextAddressLine2,editTextCity,editTextState,editTextCountry;
    //    TextInputEditText ;
    EditText editTextEmail,editTextMobile,editTextWhatsap,editTextWebsite  ,editTextTilesSize,editTextQuantity,editTextPaymentType,editTextCreditTime,editTextDealingWith,editTextDealingFirm,editTextDealingSince,editTextCommunication,datepick;
    String NotifactionDate = "";
    CheckBox checkboxwp;
    ImageView textEdit;
    private ViewFlipper mViewFlipper;
    private GestureDetector mGestureDetector;
    CircleImageView imgUserProfilePhoto;
    Button btnSubmit;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    Context context;
    Bitmap bitmap;
    Uri moveimagefront,moveimageback,moveimageprofile ;
    ImageView chooseLogoimage,imgprofilePic = null;
    MultipartBody.Part imagepartfront,imagepartback,imagepartprofile;
    FloatingActionButton chooseLogo;
    ArrayList<Bitmap> myList = new ArrayList<Bitmap>();
    protected static final int CAMERA_REQUEST = 0;
    private static final int PICK_IMAGE = 1;
    public static final int BITMAP_SAMPLE_SIZE = 8;
    Spinner spStateAddUser;
    TextInputLayout datelayout;
    CountryCodePicker codeMobile,codewp;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog;
    String spid;
    Bitmap imageBitmap;
    final String NAME_REGEX = "^([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";

    CardView viewVisitingCard;
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
        codeMobile = view.findViewById(R.id.codeMobile);
        codewp = view.findViewById(R.id.codewp);
        imgprofilePic= view.findViewById(R.id.imgprofilePic);
        editTextFirmName = view.findViewById(R.id.editTextFirmName);
        editTextPersionName = view.findViewById(R.id.editTextPersionName);
        editTextAddressLine1 = view.findViewById(R.id.editTextAddressLine1);
        editTextAddressLine2 = view.findViewById(R.id.editTextAddressLine2);
        editTextCity = view.findViewById(R.id.editTextCity);
        editTextState = view.findViewById(R.id.editTextState);
        editTextCountry = view.findViewById(R.id.editTextCountry);

        editTextEmail = view.findViewById( R.id.editTextEmail );
        editTextMobile = view.findViewById( R.id.editTextMobile );
        editTextWhatsap = view.findViewById( R.id.editTextWhatsap );
        editTextWebsite = view.findViewById( R.id.editTextWebsite );
        editTextTilesSize = view.findViewById( R.id.editTextTilesSize );
        editTextQuantity= view.findViewById( R.id.editTextQuantity );
        editTextPaymentType = view.findViewById( R.id.editTextPaymentType );
        editTextCreditTime = view.findViewById( R.id.editTextCreditTime );
        editTextDealingWith = view.findViewById( R.id.editTextDealingWith );
        editTextDealingFirm = view.findViewById( R.id.editTextDealingFirm );
        editTextDealingSince = view.findViewById( R.id.editTextDealingSince );
        editTextCommunication = view.findViewById( R.id.editTextCommunication );
        chooseLogoimage = view.findViewById( R.id.chooseLogoimage );
        chooseLogo = view.findViewById( R.id.chooseLogo );
        chooseLogo.setOnClickListener( this );
        checkboxwp = view.findViewById( R.id.checkboxwp);
        btnSubmit = view.findViewById( R.id.btnSubmit );
        btnSubmit.setOnClickListener( this );
        datepick = view.findViewById(R.id.datepicker);
        datepick.setInputType(InputType.TYPE_NULL);
        datelayout = view.findViewById(R.id.datlayout);
        imgprofilePic.setTag(R.drawable.ic_menu_camera);

        Log.d( "Dk::3", "Hello" );
        mViewFlipper =  view.findViewById(R.id.viewFlipper);
        flipper();
        checkboxwp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CheckBox checkBox = (CheckBox)v;
                if(checkboxwp.isChecked() == true){
                    Log.d( "checkbox::",""+"Hello" );
                    editTextWhatsap.setText( editTextMobile.getText().toString().trim() );
                    Log.d( "abc",""+editTextMobile.getText().toString().trim() );
                }
                if(checkboxwp.isChecked() == false){
                    Log.d( "checkbox::",""+"Hello 1" );
                    editTextWhatsap.setText( "" );
                    Log.d( "abc",""+"" );
                }
            }
        } );
//        checkboxwp.isChecked();
//        if (checkboxwp.isChecked() == true){
//            Log.d( "checkbox::",""+"Hello" );
//        }
//        if (checkboxwp.isChecked() == false){
//            Log.d( "checkbox::",""+"Hello 1" );
//        }




//        int[] resources = { R.drawable.user_profile, R.drawable.visiting_card,  R.drawable.whatsapp_16 };


//        chooseLogo = view.findViewById( R.id.chooseLogo );
//        chooseLogo.setOnClickListener( this );

        imgprofilePic.setOnClickListener( this );
        spStateAddUser = view.findViewById(R.id.spStateAddUser);

        String[] items = new String[]{
                "Select an item","HIGH PRICE", "HAVE DEALERS", "DESIGN ISSUE", "QUALITY ISSUE","AVALIBLITY","PENDING"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStateAddUser.setAdapter(adapter);
        spStateAddUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id ) {
//                Hold = spStateAddUser.getSelectedItemPosition();
                spid = String.valueOf(spStateAddUser.getSelectedItemPosition());

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());
    }

    private void AddCoustomer() {

        String FirmName = editTextFirmName.getText().toString().trim();
        String PersionName = editTextPersionName.getText().toString().trim();
        String AddressLine1 = editTextAddressLine1.getText().toString().trim();
        String AddressLine2 = editTextAddressLine2.getText().toString().trim();
        String City = editTextCity.getText().toString().trim();
        String State = editTextState.getText().toString().trim();
        String Country = editTextCountry.getText().toString().trim();
        String Email = editTextEmail.getText().toString().trim();
        String Mobile = editTextMobile.getText().toString().trim();
        String Whatsap =codewp.getSelectedCountryCode()+ editTextWhatsap.getText().toString().trim();
        String Website = editTextWebsite.getText().toString().trim();
        String TilesSize = editTextTilesSize.getText().toString().trim();
        String Quantity = editTextQuantity.getText().toString().trim();
        String PaymentType = editTextPaymentType.getText().toString().trim();
        String CreditTime = editTextCreditTime.getText().toString().trim();
        String DealingWith = editTextDealingWith.getText().toString().trim();
        String DealingFirm = editTextDealingFirm.getText().toString().trim();
        String DealingSince = editTextDealingSince.getText().toString().trim();
        String Communication = editTextCommunication.getText().toString().trim();
        NotifactionDate = datepick.getText().toString().trim();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("value", FirmName);
        editor.apply();

        if (FirmName.isEmpty()) {
            editTextFirmName.setError( "Firm Name is required" );
            editTextFirmName.requestFocus();
            return;
        }

        if (PersionName.isEmpty()) {
            editTextPersionName.setError( "Persion Name is required" );
            editTextPersionName.requestFocus();
            return;
        }
        if (Mobile.isEmpty()) {
            editTextMobile.setError( "Mobile Number is required" );
            editTextMobile.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        SharedPrefManager sfm = SharedPrefManager.getInstance( context );
        ProfileDetailModel pd = sfm.getUser();
        Api api = RetrofitClient.getApi().create( Api.class );
        RequestBody requestBodyspid = RequestBody.create( MediaType.parse( "multipart/form-data" ), spid);
        RequestBody requestBodyUserID = RequestBody.create( MediaType.parse( "multipart/form-data" ), String.valueOf( pd.getUserId() ));
        RequestBody requestBodyFirmName = RequestBody.create( MediaType.parse( "multipart/form-data" ), FirmName);
        RequestBody requestBodyPersionName = RequestBody.create( MediaType.parse( "multipart/form-data" ), PersionName);
        RequestBody requestBodyAddressLine1 = RequestBody.create( MediaType.parse( "multipart/form-data" ), AddressLine1);
        RequestBody requestBodyCity = RequestBody.create( MediaType.parse( "multipart/form-data" ), City);
        RequestBody requestBodyState = RequestBody.create( MediaType.parse( "multipart/form-data" ), State);
        RequestBody requestBodyCountry = RequestBody.create( MediaType.parse( "multipart/form-data" ), Country);
        RequestBody requestBodyEmail = RequestBody.create( MediaType.parse( "multipart/form-data" ), Email);
        RequestBody requestBodyWebsite = RequestBody.create( MediaType.parse( "multipart/form-data" ), Website);
        RequestBody requestBodyMobile = RequestBody.create( MediaType.parse( "multipart/form-data" ), Mobile);
        RequestBody requestBodyWhatsap = RequestBody.create( MediaType.parse( "multipart/form-data" ), Whatsap);
        RequestBody requestBodyTilesSize = RequestBody.create( MediaType.parse( "multipart/form-data" ), TilesSize);
        RequestBody requestBodyQuantity = RequestBody.create( MediaType.parse( "multipart/form-data" ), Quantity);
        RequestBody requestBodyPaymentType = RequestBody.create( MediaType.parse( "multipart/form-data" ), PaymentType);
        RequestBody requestBodyCreditTime = RequestBody.create( MediaType.parse( "multipart/form-data" ), CreditTime);
        RequestBody requestBodyDealingWith = RequestBody.create( MediaType.parse( "multipart/form-data" ), DealingWith);
        RequestBody requestBodyDealingFirm = RequestBody.create( MediaType.parse( "multipart/form-data" ), DealingFirm);
        RequestBody requestBodyDealingSince = RequestBody.create( MediaType.parse( "multipart/form-data" ), DealingSince);
        RequestBody requestBodyNotifactionDate = RequestBody.create( MediaType.parse( "multipart/form-data" ), NotifactionDate);
        RequestBody requestBodyCommunication = RequestBody.create( MediaType.parse( "multipart/form-data" ), Communication);





        Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.cmslogo);
        Log.d( "check::100::",""+ myList.size());
        if(myList.size()==0){
            Log.d( "check::11::",""+ myList.size());
            moveimagefront=getImageUri(getContext(),icon);
            moveimageback=getImageUri(getContext(),icon);

        }
        if(myList.size()==1){
            Log.d( "check::12::",""+ myList.size());
            moveimagefront=getImageUri(getContext(),myList.get(0));
            moveimageback=getImageUri(getContext(),icon);

        }
        else if(myList.size()==2){
            Log.d( "check::13::",""+ myList.size());
            moveimagefront=getImageUri( getContext(),myList.get(0) );
            moveimageback=getImageUri(getContext(),myList.get(1) );
        }


//        else {
//            moveimagefront=getImageUri( getContext(),myList.get(0) );
//            moveimageback=getImageUri( getContext(),myList.get(1) );
//        }

        Log.d("hiral", String.valueOf(imgprofilePic));
//        BitmapDrawable drawable = (BitmapDrawable) imgprofilePic.getDrawable();
        if((int)imgprofilePic.getTag() == R.drawable.ic_menu_camera){
            moveimageprofile=getImageUri(getContext(),icon);
        }
        else {
//            Log.d("budhivagarni::",""+imgprofilePic);
            BitmapDrawable drawable = (BitmapDrawable) imgprofilePic.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
            Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888 );
            Canvas canvas = new Canvas( bitmap );
            drawable.setBounds( 0, 0, canvas.getWidth(), canvas.getHeight() );
            drawable.draw( canvas );
            moveimageprofile = getImageUri( getContext(), bitmap );
        }
//        moveimagefront=getImageUri( getContext(),myList.get(0) );
//        moveimageback=getImageUri( getContext(),myList.get(1) );
//        BitmapDrawable drawable = (BitmapDrawable) imgprofilePic.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        moveimageprofile=getImageUri( getContext(),bitmap );
        File filefront = new File( FileUtil.getPath( moveimagefront, getContext() ) );
        File fileback = new File( FileUtil.getPath( moveimageback, getContext() ) );
        File fileprofile = new File( FileUtil.getPath( moveimageprofile, getContext() ) );


        RequestBody requestBodyFront = RequestBody.create( MediaType.parse( "image/*" ), filefront );
        imagepartfront = MultipartBody.Part.createFormData( "visiting_card_front", filefront.getName(), requestBodyFront );

        RequestBody requestBodyBack = RequestBody.create( MediaType.parse( "image/*" ), fileback );
        imagepartback = MultipartBody.Part.createFormData( "visiting_card_back", fileback.getName(),requestBodyBack  );
//
        String abc = null ;
        RequestBody requestBodyProfile = RequestBody.create( MediaType.parse( "image/*" ), fileprofile );

        abc = fileprofile.getName();

        imagepartprofile = MultipartBody.Part.createFormData( "profile_pic", abc,requestBodyProfile);

//        RequestBody requestBodymainFile = RequestBody.create( MediaType.parse( "*/*" ), filemain_file );
//        filePart = MultipartBody.Part.createFormData( "main_file", filemain_file.getName(), requestBodymainFile );



        Call<ApiResponseWithoutResData> call = api.getAddCoustomer( requestBodyspid, requestBodyUserID,requestBodyFirmName,
                requestBodyPersionName,requestBodyAddressLine1,requestBodyCity,requestBodyState,
                requestBodyCountry,requestBodyEmail,requestBodyWebsite,requestBodyMobile,requestBodyWhatsap,
                imagepartfront,imagepartback,imagepartprofile,
                requestBodyTilesSize, requestBodyQuantity,requestBodyPaymentType,requestBodyCreditTime,
                requestBodyDealingWith,requestBodyDealingFirm,requestBodyDealingSince,requestBodyNotifactionDate,
                requestBodyCommunication);


        call.enqueue( new Callback<ApiResponseWithoutResData>() {
            @Override
            public void onResponse(Call<ApiResponseWithoutResData> call, Response<ApiResponseWithoutResData> response) {
                ApiResponseWithoutResData apiResponseWithoutResData = response.body();
                if (response.body().getResCode() == 1) {
                    Log.d( "Dk", "" + apiResponseWithoutResData.getResMessage() );
                    Toast.makeText( getActivity(), FirmName + " Added", Toast.LENGTH_LONG ).show();
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
                Toast.makeText( getActivity() , "Error!!!!", Toast.LENGTH_LONG ).show();
            }
        } );

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
//                    File pathnew = Environment.getExternalStoragePublicDirectory(
//                            Environment.DIRECTORY_PICTURES);
//                    File filenew = new File(pathnew, "DemoPicture.jpg");
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//                    chooseLogo.setImageBitmap(imageBitmap);
                    myList.add(imageBitmap);
                    flipper();
                    chooseLogoimage.setImageBitmap( imageBitmap );
                    ocr();
//                    chooseLogoimage.setAdjustViewBounds(true);
//                    chooseLogoimage.setScaleType(ImageView.ScaleType.FIT_XY);
//                    chooseLogoimage.setBackgroundColor(0x00000000);
//                    chooseLogoimage.setPadding(0, 0, 0, 0);
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
            }
            else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//                Log.d("path of image from gallery......******************.........", picturePath+"");
//                chooseLogo.setImageBitmap(thumbnail);
                myList.add(thumbnail);
                flipper();
//                chooseLogoimage.setImageBitmap( thumbnail );
                chooseLogoimage.setImageBitmap( thumbnail );
                ocr();
//                chooseLogoimage.setAdjustViewBounds(true);
//                chooseLogoimage.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                chooseLogoimage.setBackgroundColor(0x00000000);
//                chooseLogoimage.setPadding(0, 0, 0, 0);
            }
            if (requestCode == 3) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
//                    File pathnew = Environment.getExternalStoragePublicDirectory(
//                            Environment.DIRECTORY_PICTURES);
//                    File filenew = new File(pathnew, "DemoPicture.jpg");
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//                    chooseLogo.setImageBitmap(imageBitmap);
//                    myList.add(imageBitmap);
//                    flipper();
                    imgprofilePic.setImageBitmap( imageBitmap );
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
            }
            else if (requestCode == 4) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//                Log.d("path of image from gallery......******************.........", picturePath+"");
//                chooseLogo.setImageBitmap(thumbnail);
//                myList.add(thumbnail);
//                flipper();
                imgprofilePic.setImageBitmap( thumbnail );
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
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
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
                        startActivityForResult(intent, PICK_IMAGE);
                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                        startActivityForResult( intent, 2 );
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    }
    private void selectProfileImage() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 3 );
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
                        startActivityForResult(intent, 3);
                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                        startActivityForResult( intent, 4 );
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                AddCoustomer();
//                imageupload();
//                new Handler().postDelayed( new Runnable() {
//                    @Override
//                    public void run() {
//                        imageupload();
//                    }
//                },2000);
                setNotification();
                break;
            case R.id.chooseLogo:
                selectImage();
                break;
            case R.id.imgprofilePic:
                selectProfileImage();
                break;

        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);

//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void imageupload() {
        RequestBody requestBodyClientID = RequestBody.create( MediaType.parse( "multipart/form-data" ), String.valueOf(5));
//        RequestBody requestBodyeventid = RequestBody.create( MediaType.parse( "multipart/form-data" ), String.valueOf( 10 ) );
        Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.cmslogo);
        Log.d( "check::100::",""+ myList.size());
        if(myList.size()==0){
            Log.d( "check::11::",""+ myList.size());
            moveimagefront=getImageUri(getContext(),icon);
            moveimageback=getImageUri(getContext(),icon);

        }
        if(myList.size()==1){
            Log.d( "check::12::",""+ myList.size());
            moveimagefront=getImageUri(getContext(),myList.get(0));
            moveimageback=getImageUri(getContext(),icon);

        }
        else if(myList.size()==2){
            Log.d( "check::13::",""+ myList.size());
            moveimagefront=getImageUri( getContext(),myList.get(0) );
            moveimageback=getImageUri(getContext(),myList.get(1) );
        }


//        else {
//            moveimagefront=getImageUri( getContext(),myList.get(0) );
//            moveimageback=getImageUri( getContext(),myList.get(1) );
//        }

        Log.d("hiral", String.valueOf(imgprofilePic));
//        BitmapDrawable drawable = (BitmapDrawable) imgprofilePic.getDrawable();
        if((int)imgprofilePic.getTag() == R.drawable.ic_menu_camera){
            moveimageprofile=getImageUri(getContext(),icon);
        }
        else {
//            Log.d("budhivagarni::",""+imgprofilePic);
            BitmapDrawable drawable = (BitmapDrawable) imgprofilePic.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
            Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888 );
            Canvas canvas = new Canvas( bitmap );
            drawable.setBounds( 0, 0, canvas.getWidth(), canvas.getHeight() );
            drawable.draw( canvas );
            moveimageprofile = getImageUri( getContext(), bitmap );
        }
//        moveimagefront=getImageUri( getContext(),myList.get(0) );
//        moveimageback=getImageUri( getContext(),myList.get(1) );
//        BitmapDrawable drawable = (BitmapDrawable) imgprofilePic.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        moveimageprofile=getImageUri( getContext(),bitmap );
        File filefront = new File( FileUtil.getPath( moveimagefront, getContext() ) );
        File fileback = new File( FileUtil.getPath( moveimageback, getContext() ) );
        File fileprofile = new File( FileUtil.getPath( moveimageprofile, getContext() ) );


        RequestBody requestBodyFront = RequestBody.create( MediaType.parse( "image/*" ), filefront );
        imagepartfront = MultipartBody.Part.createFormData( "visiting_card_front", filefront.getName(), requestBodyFront );

        RequestBody requestBodyBack = RequestBody.create( MediaType.parse( "image/*" ), fileback );
        imagepartback = MultipartBody.Part.createFormData( "visiting_card_back", fileback.getName(),requestBodyBack  );
//
        String abc = null ;
        RequestBody requestBodyProfile = RequestBody.create( MediaType.parse( "image/*" ), fileprofile );

        abc = fileprofile.getName();

        imagepartprofile = MultipartBody.Part.createFormData( "profile_pic", abc,requestBodyProfile);

//        RequestBody requestBodymainFile = RequestBody.create( MediaType.parse( "*/*" ), filemain_file );
//        filePart = MultipartBody.Part.createFormData( "main_file", filemain_file.getName(), requestBodymainFile );

        Api api = RetrofitClient.getApi().create( Api.class );
        Call<ApiResponseWithoutResData> call = api.uplodeFile( requestBodyClientID, imagepartfront, imagepartback, imagepartprofile);
        call.enqueue( new Callback<ApiResponseWithoutResData>() {
            @Override
            public void onResponse(Call<ApiResponseWithoutResData> call, Response<ApiResponseWithoutResData> response) {
                if (response.body().getResCode() == 1) {
//                    progressDialog.dismiss();
                    Toast.makeText( getContext(), response.body().getResMessage(), Toast.LENGTH_LONG ).show();
                } else {
                    Toast.makeText(getContext(), response.body().getResMessage(), Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseWithoutResData> call, Throwable t) {
                Log.d( "fail::2", "" + t.getLocalizedMessage() );
                Toast.makeText( getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG ).show();
            }
        } );
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setNotification() {
        Intent intent = new Intent(getContext(), RemainderBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(datepick.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String NotifyDate = datepick.getText().toString().trim();
//        String insertDate = EventListAdapter.KEY_DATE;
        String[] items1 = NotifyDate.split("/");
        String d1=items1[0];
        String m1=items1[1];
        String y1=items1[2];
        int d = Integer.parseInt(d1);
        int m = Integer.parseInt(m1);
        int y = Integer.parseInt(y1);
        Log.d( "notification::d",""+d );
        Log.d( "notification::m",""+m );
        Log.d( "notification::y",""+y );
        Log.d( "notification",""+datepick.getText().toString() );

//        String dayOfTheWeek = (String) DateFormat.format("EEEE", date);
//        Log.d("abcd",dayOfTheWeek);
        cal.set(y,m,d,15,56);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        long timeAtButtonClick = System.currentTimeMillis();
        long tenSecond = 1000*10;
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeAtButtonClick+tenSecond,AlarmManager.INTERVAL_DAY,pendingIntent);
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return false;
    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                mViewFlipper.showNext();
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                mViewFlipper.showPrevious();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void flipper(){
        if(myList.size() ==2) {
            for (int i = 0; i < myList.size(); i++) {
                chooseLogoimage.setVisibility(View.INVISIBLE);
                mViewFlipper.setVisibility(View.VISIBLE);
                chooseLogo.setEnabled( false );
                chooseLogo.setBackgroundColor( R.color.background );
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageBitmap(myList.get(i));
                Log.d("bitmap", String.valueOf(myList.get(i)));
                mViewFlipper.addView(imageView);
                mViewFlipper.setInAnimation(getActivity(), android.R.anim.slide_in_left);
                mViewFlipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
                mViewFlipper.setAutoStart(true);
                mViewFlipper.setFlipInterval(2000);// flip every 2 seconds (2000ms)
                mViewFlipper.startFlipping();
                CustomGestureDetector customGestureDetector = new CustomGestureDetector();
                mGestureDetector = new GestureDetector(getActivity(), customGestureDetector);
            }
        }

    }
    private void ocr() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getActivity().getApplicationContext()).build();

        if(!textRecognizer.isOperational()){
            Toast.makeText(getContext(), "Could not get the Text", Toast.LENGTH_SHORT).show();
        }

        else{
            Frame frame = new Frame.Builder().setBitmap(myList.get(0)).build();

            final SparseArray<TextBlock> items = textRecognizer.detect(frame);

            StringBuilder sb = new StringBuilder();

            for(int i=items.size()-1;i>=0;i--){
                final TextBlock myitems = items.valueAt(i);
                sb.append(myitems.getValue());
                sb.append("\n");

                Pattern name = Pattern.compile(String.valueOf(NAME_REGEX), Pattern.MULTILINE);
                Matcher mname =  name.matcher(myitems.getValue());

                Pattern phonenum = Pattern.compile(String.valueOf(Patterns.PHONE),Pattern.MULTILINE);
                Matcher mphonenum = phonenum.matcher(myitems.getValue().trim());

                Pattern email = Pattern.compile(String.valueOf(Patterns.EMAIL_ADDRESS), Pattern.MULTILINE);
                Matcher memail = email.matcher(myitems.getValue());

                Pattern website = Pattern.compile(String.valueOf(Patterns.WEB_URL), Pattern.MULTILINE);
                Matcher mwebsite = website.matcher(myitems.getValue());


                if(i==0 || i>0) {
                    if (mname.find()) {
//                        Toast.makeText(getContext(), "Match", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
//                adb.setView(alertDialogView);
                        adb.setTitle("Is "+ mname.group() + " name?" );
                        adb.setIcon(android.R.drawable.ic_dialog_alert);
                        final int finalI = i;
                        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editTextPersionName.setText(mname.group());
//                        EditText et = (EditText)alertDialogView.findViewById(R.id.EditText1);
//                                        Toast.makeText(getContext(),""+myitems.getValue(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        adb.show();

                    }
                    if (mphonenum.find()) {
//                        Toast.makeText(getContext(), "Match", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
//                adb.setView(alertDialogView);
                        Log.d("ab",mphonenum.group());
                        adb.setTitle("Is " + mphonenum.group() + " phoneNumber?");
                        adb.setIcon(android.R.drawable.ic_dialog_alert);
                        final int finalI = i;
                        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editTextMobile.setText(mphonenum.group());
//                        EditText et = (EditText)alertDialogView.findViewById(R.id.EditText1);
//                                        Toast.makeText(MainActivity.this,""+finalI, Toast.LENGTH_SHORT).show();
                            }
                        });
                        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        adb.show();

                    }
                    if (memail.find()) {
                        Toast.makeText(getContext(), "Match", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
//                adb.setView(alertDialogView);
                        Log.d("ab",memail.group());
                        adb.setTitle("Is " + memail.group() + " email?");
                        adb.setIcon(android.R.drawable.ic_dialog_alert);
                        final int finalI = i;
                        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editTextEmail.setText(memail.group());
//                        EditText et = (EditText)alertDialogView.findViewById(R.id.EditText1);
//                                        Toast.makeText(MainActivity.this,""+finalI, Toast.LENGTH_SHORT).show();
                            }
                        });
                        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        adb.show();

                    }
                    if (mwebsite.find()) {
                        Toast.makeText(getContext(), "Match", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
//                adb.setView(alertDialogView);
                        Log.d("ab",mwebsite.group());
                        adb.setTitle("Is " + mwebsite.group() + " website?");
                        adb.setIcon(android.R.drawable.ic_dialog_alert);
                        final int finalI = i;
                        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editTextWebsite.setText(mwebsite.group());
//                        EditText et = (EditText)alertDialogView.findViewById(R.id.EditText1);
//                                        Toast.makeText(MainActivity.this,""+finalI, Toast.LENGTH_SHORT).show();
                            }
                        });
                        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        adb.show();

                    }

                }
            }

        }
    }


}
