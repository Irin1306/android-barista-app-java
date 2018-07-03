package io.brainyapps.barista.ui.hits;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;


public class HitsAdapter extends
        RecyclerView.Adapter<io.brainyapps.barista.ui.hits.HitsAdapter.ViewHolder>
        implements HitsContract.Adapter {

    private HitsContract.View mView;
    private HitsContract.Presenter mPresenter;

    private List<Drink> mDrinks;


    public HitsAdapter(HitsContract.View view,
                       HitsContract.Presenter presenter,
                             List<Drink> drinks) {
        mView = view;
        mPresenter = presenter;
        mDrinks = drinks;

        mView.setAdapter(this);
        mPresenter.setAdapter(this);
    }

    @NonNull
    @Override
    public io.brainyapps.barista.ui.hits.HitsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                                           int viewType) {

        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.hits_list_row,
                        parent,
                        false);

        return new io.brainyapps.barista.ui.hits.HitsAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull io.brainyapps.barista.ui.hits.HitsAdapter.ViewHolder holder,
                                 int position) {
        holder.idTextView.setText(
                String.valueOf(mDrinks.get(position).getId())
        );
        holder.nameTextView.setText(mDrinks.get(position).getName());

        holder.mainCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //
            }
        });


        holder.imageViewBig.setOnClickListener(new View.OnClickListener() {

            //change size
            @Override
            public void onClick(View v) {

               //
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDrinks.size();
    }



    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mainCardView;
        TextView idTextView;
        TextView nameTextView;
        TextView priceTextView;
        ImageView imageViewBig;


        public ViewHolder(View itemView) {
            super(itemView);
            mainCardView = itemView.findViewById(R.id.mainCardView);
            idTextView = itemView.findViewById(R.id.idTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            imageViewBig = itemView.findViewById(R.id.appCompatImageViewBig);
        }


        @Override
        public void onClick(View v) {
            //
        }
    }
}
