package io.brainyapps.barista.ui.history;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.data.OrderWithOrderItems;
import io.brainyapps.barista.data.entity.OrderItem;
import io.brainyapps.barista.util.DateToString;


public class HistoryAdapter extends
        RecyclerView.Adapter<HistoryAdapter.ViewHolder>
        implements HistoryContract.Adapter {

    private String mTAG = MainActivity.TAG;

    private HistoryContract.View mView;
    private HistoryContract.Presenter mPresenter;
    //private List<Order> mOrders;
    private List<OrderWithOrderItems> mOrders;
    private List<OrderItem> mOrderItems;

    private AnimatedVectorDrawable mArrowUpDrawable, mArrowDownDrawable;
    private Animation mAnimationIn, mAnimationOut;

    public HistoryAdapter(HistoryContract.View view,
                          HistoryContract.Presenter presenter,
                          //List<Order> orders
                          List<OrderWithOrderItems> ordersWithOrderItems
    ) {
        mView = view;
        mPresenter = presenter;
        mOrders = ordersWithOrderItems;

        mView.setAdapter(this);
        mPresenter.setAdapter(this);
    }

    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.history_list_row,
                        parent,
                        false);

        return new  ViewHolder(itemLayoutView);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {


        holder.nameTextView.setText(mOrders.get(position).getOrderName());

        String ft = DateToString.dateToString(mOrders.get(position).getCreationDate());

        holder.dateTextView.setText(ft);

        if (mOrders.get(position).getStatus() != null) {
            holder.statusTextView.setText(mOrders.get(position).getStatus());
        }

        holder.totalTextView.setText(String.valueOf( mOrders.get(position).takeTotalAmount()));

        mOrderItems = mOrders.get(position).getOrderItems();

        HistoryDetailsAdapter historyDetailsAdapter =
                new HistoryDetailsAdapter(mOrderItems);

        GridLayoutManager gridVertical =
                new GridLayoutManager(holder.historyDetailsListRecyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false);
        holder.historyDetailsListRecyclerView.setLayoutManager(gridVertical);

        holder.historyDetailsListRecyclerView.setAdapter(historyDetailsAdapter);



        mArrowDownDrawable = (AnimatedVectorDrawable) holder.mainCardView.getResources().getDrawable(R.drawable.ic_arrow_down_animatable);
        mArrowUpDrawable = (AnimatedVectorDrawable) holder.mainCardView.getResources().getDrawable(R.drawable.ic_arrow_up_animatable);

        mAnimationIn = AnimationUtils.loadAnimation(holder.mainCardView.getContext(), R.anim.fall_down);
        mAnimationOut = AnimationUtils.loadAnimation(holder.mainCardView.getContext(), R.anim.hide_up);

        holder.historyDetailsListRecyclerView.setVisibility(View.GONE);

        holder.arrowImageView.setImageDrawable(mArrowDownDrawable);

       /* if (!holder.isDetailsShown) {

            holder.historyDetailsListRecyclerView.setVisibility(View.GONE);

            holder.arrowImageView.setImageDrawable(mArrowDownDrawable);

        } else  {

            holder.historyDetailsListRecyclerView.setVisibility(View.VISIBLE);

            holder.arrowImageView.setImageDrawable(mArrowUpDrawable);

        }
*/
        holder.arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.isDetailsShown = !holder.isDetailsShown;
                setDetailsVisibility(holder.isDetailsShown, holder);
            }
        });

    }

    private void setDetailsVisibility(Boolean isDetailsShown,  ViewHolder holder) {


        if (!isDetailsShown) {
           /* mAnimationOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.i(mTAG, "onAnimationStart holder " + holder.getAdapterPosition());
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    holder.historyDetailsListRecyclerView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    //
                }
            });*/

            holder.historyDetailsListRecyclerView.startAnimation(mAnimationOut);
            holder.historyDetailsListRecyclerView.setVisibility(View.GONE);

            holder.arrowImageView.setImageDrawable(mArrowDownDrawable);
            mArrowDownDrawable.start();


        } else  {
           // mAnimationOut.setAnimationListener(null);

            holder.historyDetailsListRecyclerView.setVisibility(View.VISIBLE);
            holder.historyDetailsListRecyclerView.startAnimation(mAnimationIn);

            holder.arrowImageView.setImageDrawable(mArrowUpDrawable);
            mArrowUpDrawable.start();
        }
    }


    @Override
    public int getItemCount() {
        if (mOrders != null) {
            return mOrders.size();
        }
        return 0;

    }

    @Override
    public void deleteAllOrders() {
        mOrders = null;
        notifyDataSetChanged();
    }


    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mainCardView;
        TextView nameTextView;
        TextView dateTextView;
        TextView statusTextView;
        TextView totalTextView;
        AppCompatImageView arrowImageView;
        RecyclerView historyDetailsListRecyclerView;

        Boolean isDetailsShown = false;


        public ViewHolder(View itemView) {
            super(itemView);
            mainCardView = itemView.findViewById(R.id.mainCardView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            arrowImageView = itemView.findViewById(R.id.arrowImageView);
            historyDetailsListRecyclerView = itemView.findViewById(R.id.historyDetailsListRecyclerView);
        }


        @Override
        public void onClick(View v) {
            //
        }
    }
}

