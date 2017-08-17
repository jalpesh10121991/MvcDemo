package in.mvcdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import in.mvcdemo.Custom.CircleImageView;
import in.mvcdemo.Custom.CustomEditText;
import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.Model.GetPlayerProfile;
import in.mvcdemo.Net.RetrofitClient;
import in.mvcdemo.R;
import in.mvcdemo.Utills.Constant;
import in.mvcdemo.Utills.ImageUtils;
import in.mvcdemo.Utills.VPreferences;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EActivity(R.layout.activity_player_prof)
public class ActivityPlayerProfile extends BaseActivity {

    @ViewById(R.id.imgPlayer)
    CircleImageView imgPlayer;
    @ViewById(R.id.edtPlayerName)
    CustomEditText edtPlayerName;
    @ViewById(R.id.edtTeamName)
    CustomEditText edtTeamName;
    @ViewById(R.id.edtCoachName)
    CustomEditText edtCoachName;
    @ViewById(R.id.edtShirtNo)
    CustomEditText edtShirtNo;
    @ViewById(R.id.edtOtherArrt)
    CustomEditText edtOtherArrt;
    @ViewById(R.id.edtPosition)
    CustomEditText edtPosition;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.imgEditProfile)
    ImageView imgEditProfile;
    @ViewById(R.id.txtMaleFemale)
    CustomTextView txtMaleFemale;
    @ViewById(R.id.etlOtherAttr)
    TextInputLayout etlOtherAttr;


    @ViewById(R.id.txt_title)
    CustomTextView txt_title;
    private String Pla_name,Pla_JNo,Pla_Event,Pla_Id;

    @AfterViews
    public void init(){
        setupToolbar();

        Bundle extras = getIntent().getExtras();
        Pla_name = extras.getString("PLA_NAME");
        Pla_JNo = extras.getString("PLA_JNO");
        Pla_Event = extras.getString("PLA_EVENT");
        Pla_Id = extras.getString("PLA_ID");
        getPlayerProfile();
    }

    @Click
    public void imgEditProfile(){
        Intent intent  = new Intent(ActivityPlayerProfile.this,EditProfile_.class);
        intent.putExtra("PLA_NAME",Pla_name);
        intent.putExtra("PLA_JNO",Pla_JNo);
        intent.putExtra("PLA_ID",Pla_Id);
        startActivity(intent);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        txt_title.setText("Player Profile");
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

    private final retrofit.Callback playerProfileCallback = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            GetPlayerProfile object = (GetPlayerProfile) o;

            if(object.getStatus().equalsIgnoreCase("100")){
                ImageUtils.getImageLoader(ActivityPlayerProfile.this).
                        displayImage(Constant.PENDING_IMG_URL + object.getGuest().get(0).getImage(),
                                imgPlayer);
                if(object.getGuest().get(0).getGender().equalsIgnoreCase("male")){
                    txtMaleFemale.setText("Male");
                }else{
                    txtMaleFemale.setText("Female");
                }
                edtPlayerName.setText(object.getGuest().get(0).getName());
                edtTeamName.setText(object.getGuest().get(0).getTeamname());
                edtCoachName.setText(object.getGuest().get(0).getCoachname());
                edtShirtNo.setText(object.getGuest().get(0).getShirtno());
                if(object.getGuest().get(0).getOther_attributes().equalsIgnoreCase("")){
                    etlOtherAttr.setVisibility(View.GONE);
                }
                else{
                    etlOtherAttr.setVisibility(View.VISIBLE);
                    edtOtherArrt.setText(object.getGuest().get(0).getOther_attributes());
                }
                edtPosition.setText(object.getGuest().get(0).getPosition());
            }


        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }
    };

    @Background
    public void getPlayerProfile() {
        messageUtil.showProgressDialog();
        RetrofitClient.getInstance().getRestOkClient().
                getPlayerProfile(Pla_name, Pla_JNo, VPreferences.getPreferanceEventID(ActivityPlayerProfile.this),Pla_Id, playerProfileCallback);
    }
}
