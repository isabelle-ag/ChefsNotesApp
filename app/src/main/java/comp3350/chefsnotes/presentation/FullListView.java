package comp3350.chefsnotes.presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
//a version of listview that will resize to fit the entire list when set to wrap_content
//could be helpful for recipe browser
class FullListView extends ListView {

    public FullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullListView(Context context) {
        super(context);
    }

    public FullListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
