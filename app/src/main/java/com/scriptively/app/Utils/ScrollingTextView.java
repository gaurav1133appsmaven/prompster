package com.scriptively.app.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class ScrollingTextView extends AppCompatTextView {
    @SuppressWarnings("unused")
    private static boolean mirror;

    public ScrollingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        if (mirror) {
            canvas.translate((float) getWidth(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        super.onDraw(canvas);
    }
}
