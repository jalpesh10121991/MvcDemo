package in.mvcdemo.Utills;


import android.app.Activity;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import in.mvcdemo.R;

public class MessageUtil {

    Activity context;
    MaterialDialog dialog;

    public MessageUtil(Activity context) {

        this.context = context;
    }

    public void showMessageDialog(String message) {

        new MaterialDialog.Builder(context)
                .title(R.string.title)
                .content(message)
                .positiveText("OK")
                .show();
    }

    public void showMessageDialog(String title, String message) {

        new MaterialDialog.Builder(context)
                .title(R.string.title)
                .content(message)
                .positiveText("OK")
                .show();
    }

    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public void showProgressDialog() {

        context.runOnUiThread(
                new Runnable() {
                    public void run() {
                        if (dialog == null) {
                            dialog = new MaterialDialog.Builder(context)
                                    .title(R.string.title)
                                    .content("Loading... Please wait...")
                                    .cancelable(false)
                                    .progress(true, 500)
                                    .show();
                        } else
                            dialog.show();
                    }
                });
    }

    public void hideProgressDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    public void handle(final Exception e) {

        context.runOnUiThread(
                new Runnable() {
                    public void run() {
                        e.printStackTrace();
                        showMessageDialog(e.getMessage());
                    }
                });
    }

    public void showMessage(final String message) {

        context.runOnUiThread(
                new Runnable() {
                    public void run() {
                        showMessageDialog(message);
                    }
                });
    }

    public void showMessage(final String title, final String message) {

        context.runOnUiThread(
                new Runnable() {
                    public void run() {
                        showMessageDialog(title, message);
                    }
                });
    }
}
