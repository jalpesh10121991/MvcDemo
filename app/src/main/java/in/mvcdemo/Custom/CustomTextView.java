package in.mvcdemo.Custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(FontCache.getFont(getContext(), "Lato-Regular.ttf"));
    }
}

class FontCache {

    private static Map<String, Typeface> fontMap = new HashMap<String, Typeface>();

    public static Typeface getFont(Context context, String fontname) {
        if (fontMap.containsKey(fontname)) {
            return fontMap.get(fontname);
        } else {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontname);
            fontMap.put(fontname, tf);
            return tf;
        }
    }
}