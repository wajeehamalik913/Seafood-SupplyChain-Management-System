package com.example.app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class OrderAdapter extends FirestoreRecyclerAdapter<SeafoodData, OrderAdapter.mViewHolder> {

    private OnItemClickListener listener;
    public OrderAdapter(@NonNull FirestoreRecyclerOptions<SeafoodData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderAdapter.mViewHolder holder, int position, @NonNull SeafoodData model) {
        holder.mName.setText(model.getName());
        holder.mType.setText(model.getType());
        holder.mDate.setText(model.getDate()+"");
        holder.mQuantity.setText(model.getQuantity());
        holder.mCost.setText(model.getCost());
    }

    @NonNull
    @Override
    public OrderAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view,parent,false);
        return new mViewHolder(view);
    }

    class mViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mType;
        private TextView mDate;
        private TextView mQuantity;
        private TextView mCost;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.list_Name);

            mType=itemView.findViewById(R.id.list_Type);
            mDate=itemView.findViewById(R.id.list_Date);
            mQuantity=itemView.findViewById(R.id.list_Quantity);
            mCost=itemView.findViewById(R.id.list_Cost);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });

        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

}
