package com.likeview.csm.Fragment;


import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.likeview.csm.ApiResponse.ApiResponseWithoutResData;
import com.likeview.csm.R;
import com.likeview.csm.Reciver.RemainderBroadCast;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
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
    TextInputEditText editTextFirmName,editTextPersionName,editTextAddressLine1,editTextAddressLine2,editTextCity,editTextState,editTextCountry;
    TextInputEditText editTextEmail,editTextMobile,editTextWhatsap,editTextWebsite,editTextTilesSize,editTextQuantity,editTextPaymentType,editTextCreditTime,editTextDealingWith,editTextDealingFirm,editTextDealingSince,editTextCommunication,datepick;
    String NotifactionDate = "";
    ImageView textEdit;
    private ViewFlipper mViewFlipper;
    private GestureDetector mGestureDetector;
//    int[] resources = { R.drawable.xtj, R.drawable.xtj1,  R.drawable.xtj2, R.drawable.xtj3 };
    ArrayList<Bitmap> myList = new ArrayList<Bitmap>();
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
    public static final int BITMAP_SAMPLE_SIZE = 8;
    Spinner spStateAddUser;
    TextInputLayout datelayout;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog;
    String spid;
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

        btnSubmit = view.findViewById( R.id.btnSubmit );
        btnSubmit.setOnClickListener( this );
        datepick = view.findViewById(R.id.datepicker);
        datepick.setInputType(InputType.TYPE_NULL);
        datelayout = view.findViewById(R.id.datlayout);
        Log.d( "Dk::3", "Hello" );
        mViewFlipper =  view.findViewById(R.id.viewFlipper);
        flipper();


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

    public void flipper(){
        for (int i = 0; i < myList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
//            imageView.setImageResource(Integer.parseInt(String.valueOf(myList.get(i))));
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

    private void userLogin() {

//        String email = editTextEmail.getText().toString().trim();
//        String Name = "Darshan Kasundra";
        String FirmName = editTextFirmName.getText().toString().trim();
        String PersionName = editTextPersionName.getText().toString().trim();
        String AddressLine1 = editTextAddressLine1.getText().toString().trim();
        String AddressLine2 = editTextAddressLine2.getText().toString().trim();
        String City = editTextCity.getText().toString().trim();
        String State = editTextState.getText().toString().trim();
        String Country = editTextCountry.getText().toString().trim();
        String Email = editTextEmail.getText().toString().trim();
        String Mobile = editTextMobile.getText().toString().trim();
        String Whatsap = editTextWhatsap.getText().toString().trim();
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

        Api api = RetrofitClient.getApi().create( Api.class );
        Call<ApiResponseWithoutResData> call = api.getAddCoustomer( spid,FirmName,PersionName,AddressLine1,City,State,Country,Email,Website,Mobile,Whatsap,TilesSize,
                Quantity,PaymentType,CreditTime,DealingWith,DealingFirm,DealingSince,NotifactionDate,Communication);


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
                Toast.makeText( getActivity() , "Hello", Toast.LENGTH_LONG ).show();
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
                    myList.add(imageBitmap);
                    flipper();
//                    chooseLogo.setImageBitmap(imageBitmap);
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
                    myList.add(thumbnail);
                    flipper();
//                chooseLogo.setImageBitmap(thumbnail);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                userLogin();
                setNotification();
                break;
            case R.id.chooseLogo:
                selectImage();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setNotification() {
        Log.d("check::1::","abcd");
//        Toast.makeText(getContext(), "Reminder Set!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), RemainderBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(datepick.getText().toString());
            Log.d("abcde",""+date);
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
}
