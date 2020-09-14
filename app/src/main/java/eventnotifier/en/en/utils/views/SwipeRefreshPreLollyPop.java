package eventnotifier.en.en.utils.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import eventnotifier.en.en.R;

public class SwipeRefreshPreLollyPop extends SwipeRefreshLayout {

    public SwipeRefreshPreLollyPop(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshPreLollyPop(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setProgressViewOffset(false,
                (int) getResources().getDimension(R.dimen.refresh_layout_start_offset),
                (int) getResources().getDimension(R.dimen.refresh_layout_end_offset));
    }
}
