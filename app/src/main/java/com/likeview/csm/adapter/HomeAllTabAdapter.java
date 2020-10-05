package com.likeview.csm.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.likeview.csm.Activity.ClientDetailActivity;
import com.likeview.csm.ApiResponse.ApiResponseWithoutResData;
import com.likeview.csm.ApiResponse.Model.ClientDetailsModel;
import com.likeview.csm.ApiResponse.Model.ListClientModel;
import com.likeview.csm.R;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.likeview.csm.R.drawable.ic_baseline_favorite_24;
import static com.likeview.csm.R.drawable.ic_baseline_favorite_border_24;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import net.simplifiedcoding.retrofitandroidtutorial.R;
//import net.simplifiedcoding.retrofitandroidtutorial.models.User;
//import com.aswdc.archdaily.models.UserDetail;

public class HomeAllTabAdapter extends RecyclerView.Adapter<HomeAllTabAdapter.UsersViewHolder> implements Filterable {
    private static final int REQUEST_PHONE_CALL = 1;

    private Activity context;
    ValueFilter valueFilter;
    private ArrayList<ListClientModel> listEvents;
    ArrayList<ListClientModel> listEventsFiltered;


    public HomeAllTabAdapter(Activity context, ArrayList<ListClientModel> listEvents) {
        this.context = context;
        this.listEvents = listEvents;
        this.listEventsFiltered = listEvents;
    }
    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.recyclerview_home_all_tab, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
//        EventDetail eventDetail = new EventDetail();
//        UserDetail userDetail = new UserDetail();
        final ListClientModel list = listEventsFiltered.get(position);
        holder.textMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission( context, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL );
                } else {
                    Intent i;
                    i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + list.getMobile_no()));
                    context.startActivity(i);
                }
            }
        });
        holder.textwp.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                try {
                    String headerReceiver = "";// Replace with your message.
                    String bodyMessageFormal = "";// Replace with your message.
                    String whatsappContain = headerReceiver + bodyMessageFormal;
                    String trimToNumner = list.getWp_no(); //10 digit number
                    Intent intent = new Intent ( Intent.ACTION_VIEW );
                    intent.setData ( Uri.parse ( "https://wa.me/" + trimToNumner + "/?text=" + "Hello" ) );
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        } );


        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientDetailsModel clientDetailsModel = new ClientDetailsModel();
                Intent intent = new Intent( context, ClientDetailActivity.class );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra( clientDetailsModel.getClientId(), listEvents.get(position).getClientId());
                intent.putExtra( "firm_name", listEvents.get(position).getFirmName());
                intent.putExtra( "personal_name", listEvents.get(position).getPersionname());
                intent.putExtra( "address", listEvents.get(position).getAddress());
                intent.putExtra( "wp_no", listEvents.get(position).getWp_no());
                intent.putExtra( "mobile_no", listEvents.get(position).getMobile_no());
                intent.putExtra( "email", listEvents.get(position).getEmail());
                intent.putExtra( "website", listEvents.get(position).getWebsite());
                intent.putExtra( "req_size", listEvents.get(position).getReq_size());
                intent.putExtra( "qty", listEvents.get(position).getQty());
                intent.putExtra( "payment_type", listEvents.get(position).getPayment_type());
                //                intent.putExtra( String.valueOf( pd.getUserId() ), listEvents.get(position).getEventId());
                context.startActivity(intent);
            }
        } );
        Log.d( "save",""+list.getIs_saved() );
        if (list.getIs_saved() != "no"){
            holder.savebutton.setBackgroundResource( ic_baseline_favorite_24 );
            Log.d( "save::1",""+list.getIs_saved() );

        }
        if (list.getIs_saved() != "yes"){
            holder.savebutton.setBackgroundResource(ic_baseline_favorite_border_24  );
            Log.d( "save::2",""+list.getIs_saved() );

        }
        else {
            Log.d( "save::3",""+list.getIs_saved() );

        }
        holder.savebutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Api api = RetrofitClient.getApi().create(Api.class);
                Call<ApiResponseWithoutResData> call = api.saveButtonclientlists(list.getClientId());
                call.enqueue( new Callback<ApiResponseWithoutResData>() {
                    @Override
                    public void onResponse(Call<ApiResponseWithoutResData> call, Response<ApiResponseWithoutResData> response) {
                        if (response.body().getResCode() == 1) {
                            holder.savebutton.setBackgroundResource( ic_baseline_favorite_24 );
                            Toast.makeText(context, list.getFirmName()+response.body().getResMessage(), Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                        }
//                progress.dismiss();

                    }
                    @Override
                    public void onFailure(Call<ApiResponseWithoutResData> call, Throwable t) {
                        Log.d("Z",""+t.getLocalizedMessage());
                        Toast.makeText( context,"Error", Toast.LENGTH_LONG ).show();
                    }
                } );




            }
        } );
//        holder.First_letter.setText( listEvents.get( position ).getEventName());


        holder.textViewProjectName.setText( list.getFirmName() );
        holder.textMobile.setText( list.getMobile_no() );
        holder.textwp.setText( list.getWp_no() );

//        holder.First_letter.setText(listEvents.get( position ).getFirmName().charAt( 0 ));
//        holder.textStatus.setText(listEvents.get(position).getStatus());
//        holder.textMaxuser.setText(listEvents.get(position).getMaxContestant());

//        Picasso.with( context ).
//                load( listEvents.get( position ).
//                        getMainBannerPath() ).fit().centerCrop().into( holder.imgProjHome );
    }

    @Override
    public int getItemCount() {
        return listEventsFiltered.size();
    }

    @Override
    public Filter getFilter() {
            valueFilter = new ValueFilter();
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String charString = constraint.toString();
            if (charString.isEmpty()) {
                listEventsFiltered = listEvents;
            } else {
                ArrayList<ListClientModel> filteredList = new ArrayList<>();
                for (ListClientModel row : listEvents) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getFirmName().toLowerCase().contains(charString.toLowerCase())) {
                        filteredList.add(row);
                    }
                }

                listEventsFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = listEventsFiltered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            listEventsFiltered = (ArrayList<ListClientModel>) results.values;
            notifyDataSetChanged();
        }

    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView textViewProjectName ,First_letter ,textMobile,textwp;
        ShimmerFrameLayout shimmerFrameLayout;
        ImageView savebutton;
        public UsersViewHolder(View itemView) {
            super(itemView);
//            shimmerFrameLayout = itemView.findViewById(R.id.shimmerFrameLayout);
            textViewProjectName = itemView.findViewById(R.id.textViewProjectName);
            textMobile = itemView.findViewById(R.id.textMobile);
            textwp = itemView.findViewById(R.id.textwp);


            First_letter = itemView.findViewById(R.id.First_letter);
            savebutton = itemView.findViewById( R.id.savebutton );
        }
    }
}
