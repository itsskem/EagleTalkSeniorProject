package com.example.eagletalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{

    private Context mContext;
    private List<Upload> mUploads;

  //  private FirebaseUser firebaseUser;

    //private DatabaseReference theReference;

    public ImageAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }

  /*  public ImageAdapter() {

    } */


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.the_image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

    Upload uploadCurrent = mUploads.get(position);
    //    holder.textViewUser.setText(uploadCurrent.getmUsername());
    //    holder.textViewDescription.setText(uploadCurrent.getmDescription());
        holder.display(mUploads.get(position));
    //    Glide.with(mContext).load(holder.getmImageUrl())
      //          .into(holder.imageView);




     //   Picasso.get().setLoggingEnabled(true);

    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{


        private TextView textViewUser;
        private ImageView imageView;

        private TextView textViewDescription;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view_upload);
            textViewUser = itemView.findViewById(R.id.text_view_user);
            textViewDescription=itemView.findViewById(R.id.text_view_description);
        }

        public void display(Upload uploadHawk) {
            this.textViewDescription.setText(uploadHawk.getmDescription());
            this.textViewUser.setText(uploadHawk.getmUsername());
            Picasso.get()
                    .load(uploadHawk.getImageUrl())
                    .into(imageView);


        }
    }
}
