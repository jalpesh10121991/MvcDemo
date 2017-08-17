package in.mvcdemo.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.mvcdemo.R;
import in.mvcdemo.Utills.MessageUtil;
import in.mvcdemo.Utills.VPreferences;


public class BaseActivity extends AppCompatActivity {

    public VPreferences preferences;
    public MessageUtil messageUtil;
    public ProgressDialog mDialog;
    public int onStartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new VPreferences(this);
        messageUtil = new MessageUtil(this);

        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);

        } else if (onStartCount == 1) {
            onStartCount++;
        }

    }


}

