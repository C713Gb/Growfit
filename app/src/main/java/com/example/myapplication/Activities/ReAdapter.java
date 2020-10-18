package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ViewHolder> {
    private List<ListItem> listItems;
    private Context context;
    public ReAdapter(Context context,List<ListItem> listItems) {
        this.listItems = listItems;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ListItem listItem=listItems.get(position);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        holder.name.setText(listItem.getV_name());
        Picasso.with(context).load(listItem.getV_image()).into(holder.videoimage);
//        holder.description.setText(listItem.getV_desc());
//        holder.user.setText(listItem.getV_user());
       // Picasso.with(context).load(listItem.getV_image()).resize(80,80).transform(new CropCircleTransformation()).into(holder.videoimage);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("videourl",listItem.getV_video_url());
                editor.putString("videoname",listItem.getV_name());
                editor.putString("videodesc",listItem.getV_desc());
                editor.putString("videouser",listItem.getV_user());
                editor.commit();
//                intent.putExtra("videourl",listItem.getV_video_url());
//                intent.putExtra("videoname",listItem.getV_name());
//                intent.putExtra("videodesc",listItem.getV_desc());
//                intent.putExtra("videouser",listItem.getV_user());
                Intent intent=new Intent(context,YoutubePlayerApiDemo.class);
                context.startActivity(intent);
              //  Toast.makeText(context,"YouViewed"+listItem.getUser(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView description;
        public  TextView user;
       // public TextView fee;
        public ImageView videoimage;
       // public Button books;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.vidname);
//            description= itemView.findViewById(R.id.viddescription);
//            user= itemView.findViewById(R.id.viduser);
            videoimage= itemView.findViewById(R.id.vidimage);
            linearLayout=itemView.findViewById(R.id.linearclick);
        }
    }
}
