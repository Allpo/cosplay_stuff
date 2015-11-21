package allpo.cosplaystuff.helpers.customComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import allpo.cosplaystuff.helpers.CSApplication;

/**
 * Created by Allpo on 10/08/2014.
 */
public class CSTextView extends TextView {

    public CSTextView(Context context) {
        super(context);
        if (!this.isInEditMode()) {
            setTypeface(((CSApplication) getContext().getApplicationContext()).getDefaultTypeFace());
        }
    }

    public CSTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()) {
            setTypeface(((CSApplication) getContext().getApplicationContext()).getDefaultTypeFace());
        }
    }

    public CSTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!this.isInEditMode()) {
            setTypeface(((CSApplication) getContext().getApplicationContext()).getDefaultTypeFace());
        }
    }
}
