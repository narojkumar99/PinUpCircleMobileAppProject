package com.pinupcircle.utility.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class EditTextFontsRegular extends AppCompatEditText {
    public EditTextFontsRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextFontsRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextFontsRegular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");
            setTypeface(tf);
        }
    }
}
