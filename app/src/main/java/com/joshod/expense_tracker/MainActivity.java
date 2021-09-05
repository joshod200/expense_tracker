package com.joshod.expense_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joshod.expense_tracker.models.Account;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joshod.expense_tracker.models.Move;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovesFormDialogFragment.Listener {
    List<Move> moves;
    MovesAdapter movesAdapter;
    Account account;
    TextView balanceText;


    List<Move> fetchAllMoves(){
        return Move.listAll(Move.class);
    }

    void setBalanceText(){
        balanceText.setText(String.format("GHC %s", account.balance));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account = Account.findById(Account.class, 1);
        if(account == null){
            account = new Account("0");
            account.save();
        }
        balanceText = findViewById(R.id.balance);
        setBalanceText();
        moves = fetchAllMoves();
        RecyclerView movesRV = findViewById(R.id.movesRV);
        FloatingActionButton newMovesFAB = findViewById(R.id.newMovesFAB);

        movesAdapter = new MovesAdapter(moves);
        movesRV.setAdapter(movesAdapter);
        movesRV.setLayoutManager(new LinearLayoutManager(this));
        movesRV.addItemDecoration(new DividerItemDecoration(getApplication(), DividerItemDecoration.VERTICAL));

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback() {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getLayoutPosition();
                Move move = moves.get(pos);
                account = move.account; // move account is a different instance so account won't be updated without this. It has to come before destroy
                Move.undoMove(move);
                moves.remove(pos);
                movesAdapter.notifyItemRemoved(pos);
                setBalanceText();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(movesRV);

        newMovesFAB.setOnClickListener(v -> showBottomSheetDialog());

    }

    private void showBottomSheetDialog() {
        MovesFormDialogFragment movesFormDialogFragment = new MovesFormDialogFragment();
        movesFormDialogFragment.show(getSupportFragmentManager(), movesFormDialogFragment.getTag());
    }

    @Override
    public void onCreateMove(Move move) {
        setBalanceText();
        moves.clear();
        moves.addAll(fetchAllMoves());
        movesAdapter.notifyDataSetChanged();
    }
}

