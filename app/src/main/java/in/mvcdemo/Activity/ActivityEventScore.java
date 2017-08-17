package in.mvcdemo.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.Model.GetScore;
import in.mvcdemo.Net.RetrofitClient;
import in.mvcdemo.R;
import in.mvcdemo.Utills.VPreferences;
import retrofit.RetrofitError;
import retrofit.client.Response;



@EActivity(R.layout.activity_event_score)
public class ActivityEventScore extends BaseActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.txt_title)
    CustomTextView txt_title;
    @ViewById(R.id.txt_score)
    CustomTextView txt_score;
    @ViewById(R.id.txt_event)
    CustomTextView txt_event;

    @AfterViews
    public void init(){
        setupToolbar();
        getScore();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        txt_title.setText("Event Score");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }


    @Background
    public void getScore() {
        messageUtil.showProgressDialog();
        RetrofitClient.getInstance().getRestOkClient().
                viewScore(VPreferences.getPreferanceEventID(ActivityEventScore.this), eventScoreCallback);
    }

    private final retrofit.Callback eventScoreCallback = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            GetScore object = (GetScore) o;

            if(object.getStatus().equals("100")){
                txt_score.setText(object.getGuest().get(0).getScore());
                txt_event.setText(object.getGuest().get(0).getEventname());
            }else if(object.getStatus().equals("101")){
                txt_event.setText("No Score Updated By Admin");
            }
        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }
    };

}
