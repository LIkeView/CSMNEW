package com.likeview.csm.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.likeview.csm.ApiResponse.Model.ListClientModel;
import com.likeview.csm.R;

import java.util.ArrayList;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import net.simplifiedcoding.retrofitandroidtutorial.R;
//import net.simplifiedcoding.retrofitandroidtutorial.models.User;
//import com.aswdc.archdaily.models.UserDetail;

public class NavSaveAdapter extends RecyclerView.Adapter<NavSaveAdapter.UsersViewHolder> {
    private static final int REQUEST_PHONE_CALL = 1;

    private Activity context;
    private ArrayList<ListClientModel> listEvents;

    public NavSaveAdapter(Activity context, ArrayList<ListClientModel> listEvents) {
        this.context = context;
        this.listEvents = listEvents;
    }
    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.recyclerview_save, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
//        EventDetail eventDetail = new EventDetail();
//        UserDetail userDetail = new UserDetail();



//        holder.textMobile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ActivityCompat.checkSelfPermission( context, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions( context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL );
//                } else {
//                    Intent i;
//                    i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + listEvents.get( position ).()));
//                    context.startActivity(i);
//                }
//            }
//        });
//        holder.textwp.setOnClickListener ( new View.OnClickListener () {
//            @Override
//            public void onClick(View view) {
//                try {
//                    String headerReceiver = "";// Replace with your message.
//                    String bodyMessageFormal = "";// Replace with your message.
//                    String whatsappContain = headerReceiver + bodyMessageFormal;
//                    String trimToNumner = "+919909973304"; //10 digit number
//                    Intent intent = new Intent ( Intent.ACTION_VIEW );
//                    intent.setData ( Uri.parse ( "https://wa.me/" + trimToNumner + "/?text=" + "Hello" ) );
//                    context.startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace ();
//                }
//            }
//        } );


//        holder.itemView.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                SharedPrefManager sfm = SharedPrefManager.getInstance(context);
//                ProfileDetail pd = sfm.getUser();
//
//                Intent intent = new Intent( context, EventDetailActivity.class );
//                intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra( eventDetail.getEventId(), listEvents.get(position).getEventId());
////                intent.putExtra( userDetail.getUserId(), listEvents.get(position).getEventId());
//                intent.putExtra( String.valueOf( pd.getUserId() ), listEvents.get(position).getEventId());
//                context.startActivity(intent);
//
//
//            }
//        } );
//        holder.First_letter.setText( listEvents.get( position ).getEventName());

        holder.textViewProjectName.setText( listEvents.get( position ).getFirmName() );
//        holder.textMobile.setText( listEvents.get( position ).getEventName() );
//        holder.textwp.setText( listEvents.get( position ).getEventName() );

        //        holder.textAmount.setText(listEvents.get( position ).getFees());
//        holder.textStatus.setText(listEvents.get(position).getStatus());
//        holder.textMaxuser.setText(listEvents.get(position).getMaxContestant());

//        Picasso.with( context ).
//                load( listEvents.get( position ).
//                        getMainBannerPath() ).fit().centerCrop().into( holder.imgProjHome );
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView textViewProjectName ,First_letter ,textMobile,textwp;
        public UsersViewHolder(View itemView) {
            super(itemView);
            textViewProjectName = itemView.findViewById(R.id.textViewProjectName);
            textMobile = itemView.findViewById(R.id.textMobile);
            textwp = itemView.findViewById(R.id.textwp);


            First_letter = itemView.findViewById(R.id.First_letter);

        }
    }
}
