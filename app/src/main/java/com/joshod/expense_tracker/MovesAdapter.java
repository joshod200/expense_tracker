package com.joshod.expense_tracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joshod.expense_tracker.models.Move;

import java.util.List;

import static com.joshod.expense_tracker.models.Move.EXPENSE;

class MovesAdapter extends RecyclerView.Adapter<MovesAdapter.TransactionsViewHolder>{
    List<Move> moves;

    public MovesAdapter(List<Move> moves){
        this.moves = moves;
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View taskItemView = inflater.inflate(R.layout.move_item, parent, false);
        return new TransactionsViewHolder(taskItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        Move move = moves.get(position);
        TextView textView = holder.moveItemName;
        TextView textView2 = holder.moveItemAmount;
        TextView moveItemCreatedAt = holder.moveItemCreatedAt;
        textView.setText(move.name);
        String ma = String.format("GHC %s", move.amount);
        if(move.type == EXPENSE) {
            ma =  "- " + ma;
            textView2.setTextColor(Color.RED);
        }
        textView2.setText(ma);
        moveItemCreatedAt.setText(move.created_at);
    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

     class TransactionsViewHolder extends RecyclerView.ViewHolder{
        TextView moveItemName;
        TextView moveItemAmount;
        TextView moveItemCreatedAt;


         public TransactionsViewHolder(@NonNull View itemView) {
             super(itemView);
             moveItemName = itemView.findViewById(R.id.moveItemName);
             moveItemAmount = itemView.findViewById(R.id.textView2);
             moveItemCreatedAt = itemView.findViewById(R.id.moveCreatedAt);
         }
     }

}