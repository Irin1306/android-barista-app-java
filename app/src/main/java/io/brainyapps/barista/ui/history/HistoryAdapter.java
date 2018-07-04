package io.brainyapps.barista.ui.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;



public class HistoryAdapter extends
        RecyclerView.Adapter<io.brainyapps.barista.ui.history.HistoryAdapter.ViewHolder>
        implements HistoryContract.Adapter {

    private HistoryContract.View mView;
    private HistoryContract.Presenter mPresenter;
    private List<Drink> mDrinks;


    public HistoryAdapter(HistoryContract.View view,
                          HistoryContract.Presenter presenter,
                       List<Drink> drinks) {
        mView = view;
        mPresenter = presenter;
        mDrinks = drinks;

        mView.setAdapter(this);
        mPresenter.setAdapter(this);
    }

    @NonNull
    @Override
    public io.brainyapps.barista.ui.history.HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                                   int viewType) {

        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.history_list_row,
                        parent,
                        false);

        return new io.brainyapps.barista.ui.history.HistoryAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull io.brainyapps.barista.ui.history.HistoryAdapter.ViewHolder holder,
                                 int position) {
        holder.idTextView.setText(
                String.valueOf(mDrinks.get(position).getId())
        );
        holder.nameTextView.setText(mDrinks.get(position).getName());



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
        TextView orderTextView;
        TextView statusTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mainCardView = itemView.findViewById(R.id.mainCardView);
            idTextView = itemView.findViewById(R.id.idTextView);
            orderTextView = itemView.findViewById(R.id.ordTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }


        @Override
        public void onClick(View v) {
            //
        }
    }
}

