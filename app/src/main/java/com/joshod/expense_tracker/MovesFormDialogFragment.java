package com.joshod.expense_tracker;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.joshod.expense_tracker.models.Move;


public class MovesFormDialogFragment extends BottomSheetDialogFragment {
    private Listener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_moves_form_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView moveNameText = view.findViewById(R.id.moveName);
        TextView moveAmountText = view.findViewById(R.id.moveAmount);
        Button createMoveButton = view.findViewById(R.id.createMoveButton);
        Spinner spin = (Spinner) view.findViewById(R.id.moveType);


        createMoveButton.setOnClickListener(view1 -> {
            String moveName = moveNameText.getText().toString();
            String moveAmount = moveAmountText.getText().toString();
            int pos = spin.getSelectedItemPosition();
            MainActivity mainActivity = (MainActivity) mListener;


            if(moveName.isEmpty()) Toast.makeText(getContext(), "Move name can;t be blank", Toast.LENGTH_SHORT).show();
            else if(pos == 0) Toast.makeText(getContext(), "Select a valid transaction type", Toast.LENGTH_SHORT).show();
            else if(moveAmount.isEmpty()) Toast.makeText(getContext(), "Move amount can;t be blank", Toast.LENGTH_SHORT).show();
            else if(Double.parseDouble(moveAmount) <= 0) Toast.makeText(getContext(), "Move amount can't be below 1", Toast.LENGTH_SHORT).show();
            else {
                Move move = new Move(moveName, pos, moveAmount, mainActivity.account);
                Move.doMove(move);
                mListener.onCreateMove(move);
            }
        });

    }

    @Override
     public void onAttach(Context context) {
        super.onAttach(context);
        this.mListener = (Listener) context;
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    interface Listener {
        void onCreateMove(Move move);
    }

}
