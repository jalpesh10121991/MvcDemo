package in.mvcdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.R;
import in.mvcdemo.Utills.VPreferences;

/**
 * Created by Payal on 9/6/2016.
 */

@EActivity(R.layout.activity_landing)
public class ActivityLanding extends BaseActivity {


    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.txt_title)
    CustomTextView txt_title;
    @ViewById(R.id.txtPlayerInfo)
    CustomTextView txtPlayerInfo;
    @ViewById(R.id.txtViewScore)
    CustomTextView txtViewScore;
    @ViewById(R.id.txtEnterPlayer)
    CustomTextView txtEnterPlayer;
    @ViewById(R.id.txtLocateEvent)
    CustomTextView txtLocateEvent;
    public String eventName,teamName;

    @AfterViews
    public void init(){
        setupToolbar();
        Bundle extras = getIntent().getExtras();
        String type = extras.getString("EVENT_TYPE");
        if(type.equals("0")){
            txtEnterPlayer.setVisibility(View.GONE);
            txtLocateEvent.setVisibility(View.GONE);
        }else{
            txtEnterPlayer.setVisibility(View.VISIBLE);
            txtLocateEvent.setVisibility(View.VISIBLE);
        }

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        txt_title.setText(VPreferences.getPreferanceEventName(ActivityLanding.this));
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

    @Click
    public void txtPlayerInfo(){
        Intent intent = new Intent(ActivityLanding.this,ActivityPlayer_.class);
        startActivity(intent);
    }

    @Click
    public void txtViewScore(){
        Intent intent = new Intent(ActivityLanding.this,ActivityEventScore_.class);
        startActivity(intent);
    }

    @Click
    public void txtEnterPlayer(){
        Intent intent = new Intent(ActivityLanding.this,ActivityAddPlayerProfile_.class);
        startActivity(intent);
    }

    @Click
    public void txtLocateEvent(){
        Intent intent = new Intent(ActivityLanding.this,LocateActivity.class);
        startActivity(intent);
    }
}
