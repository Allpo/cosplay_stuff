package allpo.cosplay_stuffv2.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Static methods for dealing with soft input.
 */
public final class SoftInputHelper {


    /**
     * Hide the soft input that was previously shown for an {@link android.widget.EditText}.
     *
     * @param editText the {@link android.widget.EditText}.
     */
    public static void hideSoftInput(EditText editText) {
        if (editText == null) {
            throw new IllegalArgumentException("EditText can't be null");
        }

        final Context context = editText.getContext();
        final InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * Show the soft input for an {@link android.widget.EditText}.
     *
     * @param editText the {@link android.widget.EditText} for which the soft input will be shown.
     */
    public static void showSoftInput(EditText editText) {
        if (editText == null) {
            throw new IllegalArgumentException("EditText can't be null");
        }

        final Context context = editText.getContext();
        final InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    // Non instantiability.
    private SoftInputHelper() {
    }
}
