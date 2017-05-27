package com.example.administrator.dbmsproject.util;

import android.content.Context;
import android.support.annotation.IntDef;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-3-28.
 */

public class ToastUtil {

    private static Toast toastInstance;

    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    @interface TIME_LENGTH {
    }

    public static void showToast(Context context, CharSequence chars, @TIME_LENGTH int time) {
        if (toastInstance == null) {
            toastInstance = Toast.makeText(context, chars, time);
        } else {
            toastInstance.setText(chars);
            toastInstance.setDuration(time);
        }
        toastInstance.show();
    }
}
