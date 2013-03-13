
package com.fridge.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class ExpandedListView extends ExpandableListView
{

    private android.view.ViewGroup.LayoutParams params;

    private int old_count = 0;

    public ExpandedListView(Context context,
            AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if(getCount() != old_count)
        {
            old_count = getCount();
            params = getLayoutParams();
            params.height = getCount() * (old_count > 0 ? getChildAt(0).getHeight() : 0);
            setLayoutParams(params);
        }

        super.onDraw(canvas);
    }

}