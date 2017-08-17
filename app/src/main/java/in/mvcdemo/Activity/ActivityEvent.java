package in.mvcdemo.Activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import in.mvcdemo.Adapter.EventAdapter;
import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.Custom.DividerItemDecoration;
import in.mvcdemo.Model.GetAllEvent;
import in.mvcdemo.Model.GetAllEventGuest;
import in.mvcdemo.Net.RetrofitClient;
import in.mvcdemo.R;
import in.mvcdemo.Utills.Utility;
import in.mvcdemo.Utills.VLogger;
import retrofit.RetrofitError;
import retrofit.client.Response;


@EActivity(R.layout.activity_event)
public class ActivityEvent extends BaseActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.txt_title)
    CustomTextView txt_title;
    @ViewById(R.id.rvEvent)
    RecyclerView rvEvent;
    @ViewById(R.id.txtFuture)
    CustomTextView txtFuture;
    @ViewById(R.id.txtLive)
    CustomTextView txtLive;
    @ViewById(R.id.txtPast)
    CustomTextView txtPast;
    @ViewById(R.id.txtNoItem)
    CustomTextView txtNoItem;
    @ViewById(R.id.imgRefresh)
    ImageView imgRefresh;
    private RecyclerView.LayoutManager layoutManager;
    private EventAdapter eventAdapter;
    private List<GetAllEventGuest> mAllGuest = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private String city;
    private String flag_1 = "0", flag_0 = "0", flag_2 = "0";

    @AfterViews
    public void init() {
        setupToolbar();
        Bundle extras = getIntent().getExtras();
        city = extras.getString("CITY");
        txtLive.setBackgroundColor(getResources().getColor(R.color.white));
        txtLive.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtPast.setTextColor(getResources().getColor(R.color.white));
        txtFuture.setTextColor(getResources().getColor(R.color.white));
        getLiveEvent();

        txtLive.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                eventAdapter.clearData();
                getLiveEvent();
                txtLive.setBackgroundColor(getResources().getColor(R.color.white));
                txtLive.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtPast.setBackground(getResources().getDrawable(R.drawable.txtborder));
                txtPast.setTextColor(getResources().getColor(R.color.white));
                txtFuture.setBackground(getResources().getDrawable(R.drawable.txtborder));
                txtFuture.setTextColor(getResources().getColor(R.color.white));
            }
        });

        txtPast.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
//                if (eventAdapter != null) {
                eventAdapter.clearData();
//                }
                getPastEvent();
                txtPast.setBackgroundColor(getResources().getColor(R.color.white));
                txtPast.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtLive.setBackground(getResources().getDrawable(R.drawable.txtborder));
                txtLive.setTextColor(getResources().getColor(R.color.white));
                txtFuture.setBackground(getResources().getDrawable(R.drawable.txtborder));
                txtFuture.setTextColor(getResources().getColor(R.color.white));
            }
        });
        txtFuture.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
//                if(eventAdapter != null){
                eventAdapter.clearData();
//                }
                getFutureEvent();
                txtFuture.setBackgroundColor(getResources().getColor(R.color.white));
                txtFuture.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtLive.setBackground(getResources().getDrawable(R.drawable.txtborder));
                txtLive.setTextColor(getResources().getColor(R.color.white));
                txtPast.setBackground(getResources().getDrawable(R.drawable.txtborder));
                txtPast.setTextColor(getResources().getColor(R.color.white));

            }
        });
        rvEvent.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(ActivityEvent.this);
        rvEvent.addItemDecoration(new DividerItemDecoration(ActivityEvent.this, LinearLayoutManager.VERTICAL));
        rvEvent.setLayoutManager(mLayoutManager);
    }

    @Click
    public void imgRefresh() {
        finish();
        startActivity(getIntent());
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        txt_title.setText("Events");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final ActionBar actionBar =
                getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private final retrofit.Callback LiveEventCallBack = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            GetAllEvent object = (GetAllEvent) o;

            if (object.getStatus().equalsIgnoreCase("100")) {


                mAllGuest.addAll(object.getGuest());
                eventAdapter = new EventAdapter(ActivityEvent.this, mAllGuest);
                rvEvent.setAdapter(eventAdapter);

                for (int i = 0; i < object.getGuest().size(); i++) {
                    if (object.getGuest().get(i).getLive().equalsIgnoreCase("1")) {
                        flag_1 = "1";
                    } else if (object.getGuest().get(i).getLive().equalsIgnoreCase("0")) {
                        mAllGuest.remove(object.getGuest().get(i));
                    } else if (object.getGuest().get(i).getLive().equalsIgnoreCase("2")) {
                        mAllGuest.remove(object.getGuest().get(i));
                    }
                }
                eventAdapter = new EventAdapter(ActivityEvent.this, mAllGuest);
                rvEvent.setAdapter(eventAdapter);

                if (flag_1.equalsIgnoreCase("0")) {
                    txtNoItem.setVisibility(View.VISIBLE);
                    txtNoItem.setText("Please sign up with NameTheTeammate.com to add your event, then load up your player. An even will need to be added to create your team roster. ");
                } else {
                    txtNoItem.setVisibility(View.INVISIBLE);
                }

            } else {
                messageUtil.showToast("No events matching your city");
                onBackPressed();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }
    };

    public void getLiveEvent() {
        VLogger.infoLog("-->Live Event<--");
        messageUtil.showProgressDialog();
        txtNoItem.setVisibility(View.GONE);
        if (Utility.checkInternetConnection(ActivityEvent.this)) {
            VLogger.infoLog("Event");
            RetrofitClient.getInstance().getRestOkClient().getLiveEvent(city, LiveEventCallBack);
        } else {
            messageUtil.showToast("No Internet!!");
        }

    }

    private final retrofit.Callback pastEventCallBack = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            GetAllEvent object = (GetAllEvent) o;

            if (object.getStatus().equalsIgnoreCase("100")) {

                mAllGuest.addAll(object.getGuest());
                eventAdapter = new EventAdapter(ActivityEvent.this, mAllGuest);
                rvEvent.setAdapter(eventAdapter);

                for (int i = 0; i < object.getGuest().size(); i++) {
                    if (object.getGuest().get(i).getLive().equalsIgnoreCase("0")) {
                        flag_0 = "1";
                    } else if (object.getGuest().get(i).getLive().equalsIgnoreCase("1")) {
                        mAllGuest.remove(object.getGuest().get(i));
                    } else if(object.getGuest().get(i).getLive().equalsIgnoreCase("2")){
                        mAllGuest.remove(object.getGuest().get(i));
                    }
                }

                if (flag_0.equalsIgnoreCase("0")) {
                    txtNoItem.setVisibility(View.VISIBLE);
                    txtNoItem.setText("No Past Event going on currently.\n Please check back later");
                } else {
                    txtNoItem.setVisibility(View.INVISIBLE);
                }
                eventAdapter = new EventAdapter(ActivityEvent.this, mAllGuest);
                rvEvent.setAdapter(eventAdapter);


            } else {
                messageUtil.showToast("No events matching your city");
                onBackPressed();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }
    };

    public void getPastEvent() {
        VLogger.infoLog("-->Past Event<--");
        messageUtil.showProgressDialog();
        txtNoItem.setVisibility(View.GONE);
        if (Utility.checkInternetConnection(ActivityEvent.this)) {
            VLogger.infoLog("Past Event");
            RetrofitClient.getInstance().getRestOkClient().getPastEvent(city, pastEventCallBack);
        } else {
            messageUtil.showToast("No Internet!!");
        }

    }

    public void getFutureEvent() {
        VLogger.infoLog("-->Future Event<--");
        messageUtil.showProgressDialog();
        txtNoItem.setVisibility(View.GONE);
        if (Utility.checkInternetConnection(ActivityEvent.this)) {
            VLogger.infoLog("Past Event");
            RetrofitClient.getInstance().getRestOkClient().getFutureEvent(city, futureEventCallBack);
        } else {
            messageUtil.showToast("No Internet!!");
        }
    }

    private final retrofit.Callback futureEventCallBack = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            GetAllEvent object = (GetAllEvent) o;

            if (object.getStatus().equalsIgnoreCase("100")) {

                mAllGuest.addAll(object.getGuest());
                eventAdapter = new EventAdapter(ActivityEvent.this, mAllGuest);
                rvEvent.setAdapter(eventAdapter);

                for (int i = 0; i < object.getGuest().size(); i++) {
                    if (object.getGuest().get(i).getLive().equalsIgnoreCase("2")) {
                        flag_2 = "1";
                    } else if (object.getGuest().get(i).getLive().equalsIgnoreCase("1")) {
                        mAllGuest.remove(object.getGuest().get(i));
                    } else if (object.getGuest().get(i).getLive().equalsIgnoreCase("0")){
                        mAllGuest.remove(object.getGuest().get(i));
                    }
                }

                if (flag_2.equalsIgnoreCase("0")) {
                    txtNoItem.setVisibility(View.VISIBLE);
                    txtNoItem.setText("No Future Event going on currently.\n Please check back later");
                } else {
                    txtNoItem.setVisibility(View.INVISIBLE);
                }
                eventAdapter = new EventAdapter(ActivityEvent.this, mAllGuest);
                rvEvent.setAdapter(eventAdapter);


            } else {
                messageUtil.showToast("No events matching your city");
                onBackPressed();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }
    };
}
