package feedback.mpnsc;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by goutams on 26/05/17.
 */

public class CustomTextViewThin extends TextView {

    public CustomTextViewThin(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomTextViewThin(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomTextViewThin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context){
        Typeface customFont = FontCache.getTypeface(context, "SystemSanFranciscoDisplayThin.ttf");

        setTypeface(customFont);
    }
}
