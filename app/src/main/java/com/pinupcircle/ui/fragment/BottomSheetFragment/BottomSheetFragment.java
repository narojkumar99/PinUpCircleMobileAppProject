package com.pinupcircle.ui.fragment.BottomSheetFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pinupcircle.R;
import com.pinupcircle.utils.Utility;

public class BottomSheetFragment  extends BottomSheetDialogFragment {
    public static BottomSheetFragment newInstance() {
        BottomSheetFragment fragment = new BottomSheetFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog( savedInstanceState );
        Utility.setMargins( dialog, 0, 0, 0, 1275 );
        return dialog;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_buttom_sheet, null);
        dialog.setContentView(contentView);

       ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
}
