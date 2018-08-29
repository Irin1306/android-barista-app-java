package io.brainyapps.barista.ui.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.OrderItem;


public class HistoryDetailsAdapter extends
        RecyclerView.Adapter<HistoryDetailsAdapter.ViewHolder>{

    private String mTAG = MainActivity.TAG;

    private List<OrderItem> mItems;


    public HistoryDetailsAdapter(List<OrderItem> orderItems) {

        mItems = orderItems;

    }

    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {


        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.history_details_list_row,
                        parent,
                        false);

        return new  ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameTextView.setText(mItems.get(position).getName());
        holder.mlTextView.setText(String.valueOf(mItems.get(position).getMl()));
        holder.priceTextView.setText(String.valueOf(mItems.get(position).getPrice()));
        holder.qtyTextView.setText(String.valueOf(mItems.get(position).getCount()));
        holder.totalTextView.setText(String.valueOf(mItems.get(position).takeTotal()));
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CardView mainCardView;
        TextView nameTextView, mlTextView, priceTextView, qtyTextView, totalTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mainCardView = itemView.findViewById(R.id.mainCardView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            mlTextView = itemView.findViewById(R.id.mlTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            qtyTextView = itemView.findViewById(R.id.qtyTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
        }
    }
}
