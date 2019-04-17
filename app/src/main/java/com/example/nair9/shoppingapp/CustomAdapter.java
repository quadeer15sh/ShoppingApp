package com.example.nair9.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nair9 on 13-04-2019.
 */
public class CustomAdapter extends ArrayAdapter<Product> {

    Context ctx;
    int resource;
    List<Product> itemsList;
    public CustomAdapter(Context ctx, int resource, List<Product> itemsList) {
        super(ctx, resource,itemsList);

        this.ctx = ctx;
        this.resource = resource;
        this.itemsList = itemsList;
    }





    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.cart_item,null);


        TextView tvProName = (TextView) view.findViewById(R.id.tvProName);
        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        TextView tvWeight = (TextView) view.findViewById(R.id.tvWeight);

        final Product item = itemsList.get(position);

        tvProName.setText(item.getName());
        tvPrice.setText(item.getPrice());
        tvWeight.setText(item.getWeight());





        Button btnPlus = (Button) view.findViewById(R.id.btnPlus);
        Button btnMinus = (Button) view.findViewById(R.id.btnMinus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
                int num = Integer.parseInt(String.valueOf(item.getQuant()));
                if (num <1){
                    num ++;
                    tvQuantity.setText(String.valueOf(num));

                }
                //tvQuantity.setText(item.getQuant());
            }
        });

                return view;
    }


}
