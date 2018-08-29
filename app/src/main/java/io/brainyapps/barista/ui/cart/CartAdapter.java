package io.brainyapps.barista.ui.cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Order;
import io.brainyapps.barista.util.DisplayAlertDialog;


public class CartAdapter extends
        RecyclerView.Adapter<CartAdapter.ViewHolder>
        implements CartContract.Adapter {

    private String mTAG = MainActivity.TAG;
    private CartContract.View mView;
    private CartContract.Presenter mPresenter;

    private List<CartDrink> mDrinks;

    private int qty;
    private double price, total;

    public CartAdapter(CartContract.View view,
                       CartContract.Presenter presenter,
                       List<CartDrink> cartDrinks) {
        mView = view;
        mPresenter = presenter;
        mDrinks = cartDrinks;

        mView.setAdapter(this);
        mPresenter.setAdapter(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {

        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cart_list_row,
                        parent,
                        false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        holder.nameTextView.setText(mDrinks.get(position).getName());
        holder.mlTextView.setText(String.valueOf(mDrinks.get(position).getMl()));
        holder.qtyTextView.setText(String.valueOf(mDrinks.get(position).getCount()));
        holder.priceTextView.setText(String.valueOf(mDrinks.get(position).getPrice()) + " ₴");

        //price = Integer.parseInt(holder.priceTextView.getText().toString().replaceAll("[\\D]", ""));
       // String priceStr = holder.priceTextView.getText().toString();
       // price = Double.parseDouble(priceStr.substring(0, priceStr.length() - 2));
       // qty = Integer.parseInt(holder.qtyTextView.getText().toString());
        //total = price * (double) qty;
        price = mDrinks.get(position).getPrice();
        qty = mDrinks.get(position).getCount();
        total =mDrinks.get(position).takeTotal();
        //Log.i(mTAG, "mAdapter.price " + price + " mAdapter.qty " + qty + " mAdapter.total " + total);

        holder.totalTextView.setText(String.valueOf(total) + " ₴");

        mView.takeTotalAmount(total);
        //mView.setInitialTotalAmount(total);




        holder.minusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qtyStr = holder.qtyTextView.getText().toString();
                Integer numb = Integer.parseInt(qtyStr);

                if (mDrinks == null || mDrinks.size() == 0) {
                    return;
                } else if ( numb < 2) {
                   // Log.i(mTAG, "numb < 2");
                   // Log.i(mTAG,  "holder" + holder + "i: " + mDrinks.indexOf(holder) + " holder.getAdapterPosition() " + holder.getAdapterPosition());
                   // Log.i(mTAG,  " mAdapter.minusTextView.setOnClickListener numb " + numb  + "position " + position + " mDrinks.size(): " + mDrinks.size());

                    mPresenter.deleteDrink(mDrinks.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                    // mView.takeTotalAmount(0);
                    mView.takeTotalAmount(-price);
                } else {
                  // Log.i(mTAG, "numb > 1");
                  //  Log.i(mTAG, "holder" + holder + "i: " + mDrinks.indexOf(holder) + " holder.getAdapterPosition() " + holder.getAdapterPosition());
                   // Log.i(mTAG, " mAdapter.minusTextView.setOnClickListener numb " + numb + "position " + position + " mDrinks.size(): " + mDrinks.size());

                    mPresenter.getCount(mDrinks.get(holder.getAdapterPosition()), holder, -1);
                    mView.takeTotalAmount(-price);
                    }
                }

        });

        holder.plusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mDrinks != null  || mDrinks.size() != 0) {

                    String qtyStr = holder.qtyTextView.getText().toString();
                    Integer numb = Integer.parseInt(qtyStr);

                   // Log.i(mTAG,  " mAdapter.plusTextView.setOnClickListener numb " + numb + "holder.getAdapterPosition() " + holder.getAdapterPosition());

                    mPresenter.getCount(mDrinks.get(holder.getAdapterPosition()), holder, 1);
                    mView.takeTotalAmount(price);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (mDrinks != null) {
            return mDrinks.size();
        }
        return 0;
    }

    @Override
    public void setCount(CartDrink cartDink, ViewHolder holder) {
        if (mDrinks == null || mDrinks.size() == 0) {
            return;
        }
       // Log.i(mTAG, "mAdapter.setCount " + cartDink.getCount());
        holder.qtyTextView.setText(String.valueOf(cartDink.getCount()));
        holder.totalTextView.setText(String.valueOf(cartDink.takeTotal()) + " ₴");

        //notifyItemChanged(holder.getAdapterPosition());
       // for (int i = 0; i < mDrinks.size(); i++) {
            //Log.i(mTAG, "mFragment mDrinks i" + i + ": " + mDrinks.get(i).getName());
       // }
        //Log.i(mTAG, "mFragment mDrinks.size() " + mDrinks.size() + ": " + mDrinks);
    }

    @Override
    public void deleteItem(CartDrink cartDink, int position) {
        if (mDrinks == null || mDrinks.size() == 0 ) {
            return;
        } else if (mDrinks.size() == 1) {
            mDrinks = null;
            notifyDataSetChanged();
        } else  {
            //Log.i(mTAG, "mDrinks " + mDrinks);
            //for (int i = 0; i < mDrinks.size(); i++) {
           //     Log.i(mTAG, "mFragment mDrinks position: " + position + ", " + "mFragment mDrinks i" + i + ": " + mDrinks.get(i).getName());
           // }
           // Log.i(mTAG, "mFragment mDrinks.size() " + mDrinks.size() + ": " + mDrinks);
           // Log.i(mTAG, "mDrinks.remove " + mDrinks.get(position).getName() + "position: " + position + ", " + "i: " + mDrinks.indexOf(mDrinks.get(position)));

            mDrinks.remove(mDrinks.get(position));
            //notifyDataSetChanged();
            notifyItemRemoved(position);
        }
    }

    @Override
    public void makeOrder() {

        String uid = UUID.randomUUID().toString();

        Order order = new Order("#" + uid.substring(uid.length() - 7, uid.length()), new Date());
        Log.i(mTAG, "CartAdapter makeOrder " + order.toString());
        if (mDrinks != null) {
            mPresenter.saveOrderAndReturnId(order, mDrinks);
            // mPresenter.saveOrder(order);
            // mPresenter.saveOrderItems(order.getOrderName(), mDrinks);
        }
    }

    @Override
    public void refresh() {
        mDrinks = null;
        notifyDataSetChanged();

    }


    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mainCardView;
        //TextView idTextView;
        TextView nameTextView;
        TextView mlTextView;
        TextView priceTextView;
        TextView qtyTextView;
        TextView minusTextView;
        TextView plusTextView;
        TextView totalTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mainCardView = itemView.findViewById(R.id.mainCardView);
            //idTextView = itemView.findViewById(R.id.idTextView);
            qtyTextView = itemView.findViewById(R.id.qtyTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            mlTextView = itemView.findViewById(R.id.mlTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            minusTextView = itemView.findViewById(R.id.minusTextView);
            plusTextView = itemView.findViewById(R.id.plusTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
        }


        @Override
        public void onClick(View v) {
            //
        }
    }
}

