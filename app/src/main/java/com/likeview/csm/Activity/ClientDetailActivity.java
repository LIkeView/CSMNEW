package com.likeview.csm.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.ApiResponse.Model.ClientDetailsModel;
import com.likeview.csm.R;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.aswdc.archdaily.models.UserDetail;

public class ClientDetailActivity extends AppCompatActivity {

//    globley declare
private static final int REQUEST_PHONE_CALL = 1;

    RecyclerView rcvEventDetail , rcvUserEventDetail ;
    Context context;
//    Button btnJoin;
    TextView textFirmName,textPersionName,textaddress,textwp,textMobile,textEmail ,textWebsite,textSize,textQty,textPaymentType;
    LinearLayout linearlayoutvotebutton,linearlayoutDescrotion;
    ImageView imgProjHome ,backButton ;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_event_detail );

//      find all field using findViewById
        imgProjHome = findViewById( R.id.imgProjHome );
        textFirmName = findViewById( R.id.textFirmName);
        textPersionName = findViewById( R.id.textPersionName);
        textaddress = findViewById( R.id.textaddress);
        textwp = findViewById( R.id.textwp);
        textMobile = findViewById( R.id.textMobile);
        textEmail = findViewById( R.id.textEmail);
        textWebsite = findViewById( R.id.textWebsite);
        textSize = findViewById( R.id.textSize);
        textQty = findViewById( R.id.textQty);
        textPaymentType = findViewById( R.id.textPaymentType);
        initReference();
    }
    void initReference( ){

//        ProgressDialog progress = new ProgressDialog( EventDetailActivity.this );
//        progress.setTitle("Loading");
//        //                getSupportActionBar().setHomeAsUpIndicator( R.drawable.ic_home );
//        progress.setIcon( R.drawable.ic_home );
//        progress.setMessage("Wait while loading...");
//        progress.setCancelable(false);
//        progress.show();

//        SharedPrefManager sfm = SharedPrefManager.getInstance(context);
//        ProfileDetail pd = sfm.getUser();
        ClientDetailsModel clientDetailsModel = new ClientDetailsModel();
        int clientID=getIntent().getIntExtra( clientDetailsModel.getClientId(),0);
        String FirmName = getIntent().getStringExtra( "firm_name");
        String Visiting_card_front = getIntent().getStringExtra( "visiting_card_front");
        String Visiting_card_back = getIntent().getStringExtra( "visiting_card_back");
        String Profile_pic = getIntent().getStringExtra( "profile_pic");


        String PersionName = getIntent().getStringExtra( "personal_name");
        String Address1 = getIntent().getStringExtra( "address");
        String Whatsapp = getIntent().getStringExtra( "wp_no");
        String Mobile = getIntent().getStringExtra( "mobile_no");
        String Email = getIntent().getStringExtra( "email");
        String Website = getIntent().getStringExtra( "website");
        String Size = getIntent().getStringExtra( "req_size");
        String Qnty = getIntent().getStringExtra( "qty");
        String PaymentType = getIntent().getStringExtra( "payment_type");


        Toolbar toolbar;
        TextView toolbartext;
        toolbar=  findViewById( R.id.toolbar);
        toolbartext=  findViewById( R.id.toolbartext);
        toolbartext.setText( FirmName );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( FirmName );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );


        textFirmName.setText( FirmName );
        Picasso.with( getApplicationContext() ).load("http://cms.likeview.in/assets/photos/"+Visiting_card_front).fit().centerCrop().into( imgProjHome );
        Log.d( "aaaaaa" ,""+Visiting_card_front);
        textPersionName.setText( PersionName );
        textaddress.setText( Address1 );
        textwp.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                try {
                    String headerReceiver = "";// Replace with your message.
                    String bodyMessageFormal = "";// Replace with your message.
                    String whatsappContain = headerReceiver + bodyMessageFormal;
                    String trimToNumner = Whatsapp; //10 digit number
                    Intent intent = new Intent ( Intent.ACTION_VIEW );
                    intent.setData ( Uri.parse ( "https://wa.me/" + trimToNumner + "/?text=" + "Hello" ) );
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        } );

        textMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission( context, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( (Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL );
                } else {
                    Intent i;
                    i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Mobile));
                    context.startActivity(i);
                }
            }
        });
        textwp.setText( Whatsapp );
        textMobile.setText( Mobile );
        textEmail.setText( Email );
        textWebsite.setText( Website );
        textSize.setText( Size );
        textQty.setText( Qnty );
        textPaymentType.setText( PaymentType );

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return super.onOptionsItemSelected( item );
            default:
                return super.onOptionsItemSelected( item );
        }
    }

}
