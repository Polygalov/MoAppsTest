package ua.com.adr.android.moapps.adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.com.adr.android.moapps.model.Datum;
import ua.com.adr.android.moapps.activity.PreviewActivity;
import ua.com.adr.android.moapps.R;
import ua.com.adr.android.moapps.RecyclerItemClickListener;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private ArrayList<Datum> dataList;
    private RecyclerItemClickListener recyclerItemClickListener;
    private String appUrl;

    public DataAdapter(ArrayList<Datum> dataList, RecyclerItemClickListener recyclerItemClickListener) {
        this.dataList = dataList;
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_app, parent, false);

        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, final int position) {

        appUrl = dataList.get(position).getApplicationUrl();
        String appName = dataList.get(position).getApplicationName();
        if (appName == null || appName.isEmpty()) {
            appName = holder.viewAppName.getContext().getString(R.string.label_no_name);
        }
        String appPayment;
        if (dataList.get(position).getIsPayment()) {
            appPayment = String.format("%s %s", new Object[]{
                    holder.viewAppPayment.getContext().getString(R.string.label_paid),
                    dataList.get(position).getEndOfPayment()});
        } else {
            appPayment = holder.viewAppPayment.getContext().getString(R.string.label_unpaid);
        }
        String appStatus;
        int iconRes;
        if (dataList.get(position).isApplicationStatus()) {
            appStatus = holder.viewAppStatus.getContext().getString(R.string.label_complete);
            iconRes = R.drawable.ic_finished;
        } else {
            appStatus = holder.viewAppStatus.getContext().getString(R.string.label_incomplete);
            iconRes = R.drawable.ic_unfinished;
        }

        holder.viewAppName.setText(appName);
        holder.viewAppPayment.setText(appPayment);
        holder.viewAppStatus.setText(appStatus);
        holder.viewAppStatus.setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0);
        Picasso.with(holder.ivIcon.getContext()).load(dataList.get(position).getApplicationIcoUrl()).placeholder(R.drawable.app_icon).error(R.drawable.app_icon).into(holder.ivIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView viewAppName, viewAppPayment, viewAppStatus;
        ImageView ivIcon;

        DataViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.app_icon);
            viewAppName = (TextView) itemView.findViewById(R.id.app_name);
            viewAppPayment = (TextView) itemView.findViewById(R.id.app_payment);
            viewAppStatus = (TextView) itemView.findViewById(R.id.app_status);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PreviewActivity.class);
            intent.putExtra("app_url", appUrl);
            view.getContext().startActivity(intent);
        }
    }
}