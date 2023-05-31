package com.example.demo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.demo.R;
import com.example.demo.activity.SecondActivity;
import com.example.demo.model.MyModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyviewHolder> {

    private List<MyModel> models;
    private Context context;

    public MyAdapter(List<MyModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        MyModel data = models.get(position);
//        holder.userid.setText(data);
        holder.title.setText(data.getTitle());
        holder.desc.setText(data.getBody());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context,data.getTitle(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, SecondActivity.class);
                context.startActivity(intent);
//                Animatoo.animateSlideUp(this);

//                Intent intent = new Intent(context, E_comm_product_details.class);
//                intent.putExtra("send_image",mylist.getProduct_list_image());
//                intent.putExtra("send_book_name",mylist.getProduct_list_title());
//                intent.putExtra("book_desc",mylist.getProduct_list_desc());
//                intent.putExtra("book_price",mylist.getProduct_list_price());
//
//
//                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class  MyviewHolder extends RecyclerView.ViewHolder {

        public TextView userid,it,title,desc;
        public LinearLayout layout;

       public MyviewHolder(@NonNull View itemView) {
           super(itemView);
           userid = itemView.findViewById(R.id.idno);
           title = itemView.findViewById(R.id.title);
           desc = itemView.findViewById(R.id.desc);
           layout = itemView.findViewById(R.id.linear);
       }
   }
}
