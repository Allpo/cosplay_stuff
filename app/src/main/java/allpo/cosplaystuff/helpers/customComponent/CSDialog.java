package allpo.cosplaystuff.helpers.customComponent;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Allpo on 10/08/2014.
 */
public class CSDialog extends Dialog {
    public CSDialog(Context context) {
        super(context);
    }

    public CSDialog(Context context, int theme) {
        super(context, theme);
    }

    protected CSDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
