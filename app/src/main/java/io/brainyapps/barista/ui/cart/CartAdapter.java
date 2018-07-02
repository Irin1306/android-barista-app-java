package io.brainyapps.barista.ui.cart;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;




public class CartAdapter extends
        RecyclerView.Adapter<io.brainyapps.barista.ui.cart.CartAdapter.ViewHolder>
        implements CartContract.Adapter {

    private String mTAG = MainActivity.TAG;
    private CartContract.View mView;
    private CartContract.Presenter mPresenter;

    private List<Drink> mDrinks;

    private int total;

    public CartAdapter(CartContract.View view,
                       CartContract.Presenter presenter,
                          List<Drink> drinks) {
        mView = view;
        mPresenter = presenter;
        mDrinks = drinks;

        mView.setAdapter(this);
        mPresenter.setAdapter(this);
    }

    @NonNull
    @Override
    public io.brainyapps.barista.ui.cart.CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                                         int viewType) {

        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cart_list_row,
                        parent,
                        false);

        return new io.brainyapps.barista.ui.cart.CartAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull io.brainyapps.barista.ui.cart.CartAdapter.ViewHolder holder,
                                 int position) {
        holder.idTextView.setText(
                String.valueOf(mDrinks.get(position).getId())
        );
        holder.nameTextView.setText(mDrinks.get(position).getName());

       /* Log.i(mTAG, "" +  holder.priceTextView.getText().toString());
        Log.i(mTAG, "" +  holder.priceTextView.getText().toString().replaceAll("[\\D]", ""));
        Log.i(mTAG, "" +  Integer.parseInt(holder.priceTextView.getText().toString().replaceAll("[\\D]", "")));

        Log.i(mTAG, "" + holder.qtyTextView.getText().toString());
        Log.i(mTAG, "" + Integer.parseInt(holder.qtyTextView.getText().toString()));*/

        int price = Integer.parseInt(holder.priceTextView.getText().toString().replaceAll("[\\D]", ""));
        int qty = Integer.parseInt(holder.qtyTextView.getText().toString());
        total = price * qty;
        holder.totalTextView.setText(String.valueOf(total) + " ₴");
        mView.takeTotalAmount(total);


        holder.mainCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        holder.minusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(mTAG, "" + holder.qtyTextView.getText());
                String string = holder.qtyTextView.getText().toString();
                Log.i(mTAG, string);
                Integer numb = Integer.parseInt(string) - 1;
                Log.i(mTAG, "" +  numb);
                int mId = mDrinks.get(position).getId();
                Log.i(mTAG, "position " + mDrinks.get(position).toString() + " id " + mId);
                total = price * numb;

                Log.i(mTAG, "-price " + -price);
                if (numb > 0){
                    holder.qtyTextView.setText(String.valueOf(numb));
                    holder.totalTextView.setText(String.valueOf(total) + " ₴");
                    mView.takeTotalAmount(-price);
                }
                else {
                   /* for (Iterator<Drink> iterator = mDrinks.iterator(); iterator.hasNext();){
                        Object obj = iterator.next();
                        if (obj.getId().equals(mId)) {
                            iterator.remove();
                        }
                    }*/
                    mDrinks.remove(mDrinks.get(position));
                    notifyDataSetChanged();
                    //notifyItemRemoved(mDrinks.indexOf(mDrinks.get(position)));
                    mView.takeTotalAmount(0);
                }
            }
        });
        holder.plusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(mTAG, "" + holder.qtyTextView.getText());
                String string = holder.qtyTextView.getText().toString();
                Log.i(mTAG, string);
                Integer numb = Integer.parseInt(string) + 1;
                Log.i(mTAG, "" +  numb);
                holder.qtyTextView.setText(String.valueOf(numb));
                total = price * numb;
                holder.totalTextView.setText(String.valueOf(total) + " ₴");
                mView.takeTotalAmount(price);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(mDrinks != null) {
            return mDrinks.size();
        }
        return 0;
    }


    @Override
    public void addItem(Drink drink) {
//
    }

    @Override
    public void deleteItem(Drink drink) {
//
    }


    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mainCardView;
        TextView idTextView;
        TextView nameTextView;
        TextView priceTextView;
        TextView qtyTextView;
        TextView minusTextView;
        TextView plusTextView;
        TextView totalTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mainCardView = itemView.findViewById(R.id.mainCardView);
            idTextView = itemView.findViewById(R.id.idTextView);
            qtyTextView = itemView.findViewById(R.id.qtyTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            minusTextView = itemView.findViewById(R.id.minusTextView);
            plusTextView = itemView.findViewById(R.id. plusTextView);
            totalTextView = itemView.findViewById(R.id. totalTextView);
        }


        @Override
        public void onClick(View v) {
            //
        }
    }
}

