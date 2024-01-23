package com.ariyanjakaria.spacetour;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class packageAdapter extends RecyclerView.Adapter<packageAdapter.viewHolder> {
    private final boolean valuee;
    private List<packageModel>packageModelList;

    public packageAdapter(List<packageModel> packageModelList,boolean valuee) {
        this.packageModelList = packageModelList;
        this.valuee=valuee;
        
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.popular,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
     String Name=packageModelList.get(position).getName();
     String Image=packageModelList.get(position).getImage();
     String uid=packageModelList.get(position).getUid();
     String dView=packageModelList.get(position).getDview();
     String Gif=packageModelList.get(position).getGif();

           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(valuee) {
                       Intent intent = new Intent(holder.itemView.getContext(), semiviewActivity.class);
                       intent.putExtra("UID", uid);
                       intent.putExtra("DVIEW", dView);
                       intent.putExtra("GVIEW", Gif);


                       holder.itemView.getContext().startActivity(intent);
                   }else {
                       Intent intent = new Intent(holder.itemView.getContext(), view.class);
                       intent.putExtra("UID", uid);


                       holder.itemView.getContext().startActivity(intent);
                   }

               }
           });


     holder.setData(Image,Name);
    }

    @Override
    public int getItemCount() {
        return packageModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView packImage;
        private TextView packName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            packImage=itemView.findViewById(R.id.packageImage);
            packName=itemView.findViewById(R.id.textView2);

        }
        public void setData(String Im,String Nam)
        {
            Glide.with(itemView.getContext()).load(Im).into(packImage);
            packName.setText(Nam);
        }
    }
}
