package com.astix.allanasosfa.customwidgets;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class ViewGenerator {

    public static LinearLayout createVerticalLayout(Context context, String tagVal) {
        LinearLayout llayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        llayout.setLayoutParams(layoutParams1);
        //layoutParams1.setMargins(4,4,4,4);
        llayout.setTag(tagVal);
        llayout.setOrientation(LinearLayout.VERTICAL);

        return llayout;
    }

    public static View createMarginView(Context context){
        View view=new View(context);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 1);
        layoutParams1.setMargins(0,10,0,0);
        view.setLayoutParams(layoutParams1);
        view.setBackgroundColor(Color.parseColor("#B3B3B3"));
        return view;
    }
}
