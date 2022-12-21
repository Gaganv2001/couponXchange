package com.example.coupxchange;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class recycleradapter extends FirebaseRecyclerAdapter<Token,recycleradapter.myviewholder> {

    public String coups;
    public recycleradapter(@NonNull FirebaseRecyclerOptions<Token> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Token model) {
        holder.webname.setText(model.getUseremail());
        holder.wcoupon.setText(model.getToken());

        holder.cpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = null;
                clipboard = (ClipboardManager) holder.cpy.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Text", holder.wcoupon.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(holder.cpy.getContext(),"Copied",Toast.LENGTH_SHORT).show();
            }
        });
    }




    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_list,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        TextView webname,wcoupon;
        ImageView cpy;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            webname=itemView.findViewById(R.id.websitename);
            wcoupon=itemView.findViewById(R.id.wcoupon);
            cpy=itemView.findViewById(R.id.cpy_icon);


        }
    }

}
