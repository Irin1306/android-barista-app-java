package io.brainyapps.barista.ui.drinks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.util.DisplayAlertDialog;


public class DrinksListAdapter extends
        RecyclerView.Adapter<DrinksListAdapter.ViewHolder>
        implements DrinksListContract.Adapter {


    private DrinksListContract.View mView;

    private DrinksListContract.Presenter mPresenter;

    private List<Drink> mDrinks;


    public DrinksListAdapter(DrinksListContract.View view,
                             DrinksListContract.Presenter presenter,
                             List<Drink> drinks) {
        mView = view;
        mPresenter = presenter;
        mDrinks = drinks;

        mView.setAdapter(this);
        mPresenter.setAdapter(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.drinks_list_row,
                        parent,
                        false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {


        holder.nameTextView.setText(mDrinks.get(position).getName());

        holder.priceTextView.setText(String.valueOf(mDrinks.get(position).getPrice()) + " ₴");

        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mView.startDrinkDetails(
                        mDrinks.get(holder.getAdapterPosition()).getId()
                );
            }
        });

        holder.imageViewBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mView.startDrinkDetails(
                        mDrinks.get(holder.getAdapterPosition()).getId()
                );
            }
        });

        holder.cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.addToCart(mDrinks.get(
                        holder.getAdapterPosition()
                ));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDrinks.size();
    }

    @Override
    public void deleteLastElement() {

        if (mDrinks.size() == 0) {
            return;
        }

        mDrinks.remove(mDrinks.size() - 1);
        //notifyDataSetChanged();
        notifyItemRemoved(mDrinks.size());
    }

    @Override
    public void addFirstElement(Drink drink) {
        mDrinks.add(0, drink);
        notifyItemInserted(0);
    }


    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mainCardView;
       // TextView idTextView;
        TextView nameTextView;
        TextView priceTextView;
        ImageView imageViewBig;
        AppCompatImageView cartImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            mainCardView = itemView.findViewById(R.id.mainCardView);
           // idTextView = itemView.findViewById(R.id.idTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            imageViewBig = itemView.findViewById(R.id.appCompatImageViewBig);
            cartImageView = itemView.findViewById(R.id.cartImageView);
        }



        @Override
        public void onClick(View v) {
            //
        }
    }
}
