package in.mvcdemo.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import in.mvcdemo.Adapter.PlayerAdapter;
import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.Custom.DividerItemDecoration;
import in.mvcdemo.Model.GetPlayerEvent;
import in.mvcdemo.Model.GetPlayerEventGuest;
import in.mvcdemo.Net.RetrofitClient;
import in.mvcdemo.R;
import in.mvcdemo.Utills.VPreferences;
import retrofit.RetrofitError;
import retrofit.client.Response;


@EActivity(R.layout.activity_player)
public class ActivityPlayer extends BaseActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.txt_title)
    TextView txt_title;
    @ViewById(R.id.imgFilter)
    ImageView imgFilter;
    @ViewById(R.id.search_view)
    EditText search_view;
    @ViewById(R.id.rvPlayer)
    RecyclerView rvPlayer;
    @ViewById(R.id.txtNoItem)
    TextView txtNoItem;
    private RecyclerView.LayoutManager layoutManager;
    private PlayerAdapter playerAdapter;
    private List<GetPlayerEventGuest> mPlayerEvent = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private String sNO;
    private String eventName, teamName;
    private Menu menu;
    @ViewById(R.id.contextMenu)
    LinearLayout contextMenu;
    @ViewById(R.id.txtAtoz)
    CustomTextView txtAtoz;
    @ViewById(R.id.txtTeamWise)
    CustomTextView txtTeamWise;
    @ViewById(R.id.txtCoachWise)
    CustomTextView txtCoachWise;
    int count = 1;


    private final retrofit.Callback playerCallback = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            GetPlayerEvent object = (GetPlayerEvent) o;
            if (object != null) {
                if (object.getStatus().equalsIgnoreCase("100")) {
                    txtNoItem.setVisibility(View.GONE);
                    if (mPlayerEvent == null) {
                        mPlayerEvent.addAll(object.getGuest());
                        playerAdapter = new PlayerAdapter(ActivityPlayer.this, mPlayerEvent);
                        rvPlayer.setAdapter(playerAdapter);
                    } else {
                        mPlayerEvent.clear();
                        mPlayerEvent.addAll(object.getGuest());
                        playerAdapter = new PlayerAdapter(ActivityPlayer.this, mPlayerEvent);
                        rvPlayer.setAdapter(playerAdapter);
                    }

                } else {
                    mPlayerEvent.clear();
                    txtNoItem.setVisibility(View.VISIBLE);
                }

            } else {
                messageUtil.showMessage("No Event Found");
            }

        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }
    };

    private final retrofit.Callback playerTeamWiseCallback = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            GetPlayerEvent object = (GetPlayerEvent) o;
            if (object != null) {
                if (object.getStatus().equalsIgnoreCase("100")) {
                    txtNoItem.setVisibility(View.GONE);
                    if (mPlayerEvent == null) {
                        mPlayerEvent.addAll(object.getGuest());
                        playerAdapter = new PlayerAdapter(ActivityPlayer.this, mPlayerEvent);
                        rvPlayer.setAdapter(playerAdapter);
                    } else {
                        mPlayerEvent.clear();
                        mPlayerEvent.addAll(object.getGuest());
                        playerAdapter = new PlayerAdapter(ActivityPlayer.this, mPlayerEvent);
                        rvPlayer.setAdapter(playerAdapter);
                    }
                } else {
                    mPlayerEvent.clear();
                    txtNoItem.setVisibility(View.VISIBLE);
                }

            } else {
                messageUtil.showMessage("No Event Found");
            }

        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }
    };

    private final retrofit.Callback playerCoachWiseCallback = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            GetPlayerEvent object = (GetPlayerEvent) o;
            if (object != null) {
                if (object.getStatus().equalsIgnoreCase("100")) {
                    txtNoItem.setVisibility(View.GONE);
                    if (mPlayerEvent == null) {
                        mPlayerEvent.addAll(object.getGuest());
                        playerAdapter = new PlayerAdapter(ActivityPlayer.this, mPlayerEvent);
                        rvPlayer.setAdapter(playerAdapter);
                    } else {
                        mPlayerEvent.clear();
                        mPlayerEvent.addAll(object.getGuest());
                        playerAdapter = new PlayerAdapter(ActivityPlayer.this, mPlayerEvent);
                        rvPlayer.setAdapter(playerAdapter);
                    }
                } else {
                    mPlayerEvent.clear();
                    txtNoItem.setVisibility(View.VISIBLE);
                }

            } else {
                messageUtil.showMessage("No Event Found");
            }

        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }
    };

    @AfterViews
    public void init() {
        setupToolbar();
        getPlayer();

        rvPlayer.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(ActivityPlayer.this);
        rvPlayer.addItemDecoration(new DividerItemDecoration(ActivityPlayer.this, LinearLayoutManager.VERTICAL));
        rvPlayer.setLayoutManager(mLayoutManager);


    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        txt_title.setText("Player");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("");
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    public void showTeamDialog() {
        new MaterialDialog.Builder(this)
                .title("The TeamMate")
                .content("Enter team name")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRange(2, 16)
                .positiveText("Submit")
                .positiveColor(R.color.colorPrimary)
                .input("", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        getPlayerTeamName(input.toString());
                    }
                }).show();
    }

    public void showCoachDialog() {
        new MaterialDialog.Builder(this)
                .title("The TeamMate")
                .content("Enter coach name")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRange(2, 16)
                .positiveText("Submit")
                .positiveColor(R.color.colorPrimary)
                .input("", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        getPlayerCoachName(input.toString());
                    }
                }).show();
    }

    @Background
    public void getPlayer() {
        messageUtil.showProgressDialog();
        RetrofitClient.getInstance().getRestOkClient().
                getPlayerByEvent(VPreferences.getPreferanceEventID(ActivityPlayer.this), playerCallback);
    }


    @Background
    public void getPlayerTeamName(String teamname) {
        messageUtil.showProgressDialog();
        RetrofitClient.getInstance().getRestOkClient().
                getPlayerByTeamName(teamname, VPreferences.getPreferanceEventID(ActivityPlayer.this), playerTeamWiseCallback);
    }

    @Background
    public void getPlayerCoachName(String coachname) {
        messageUtil.showProgressDialog();
        RetrofitClient.getInstance().getRestOkClient().
                getPlayerByCoachName(coachname, VPreferences.getPreferanceEventID(ActivityPlayer.this), playerCoachWiseCallback);
    }

    @Click
    public void imgFilter() {
        if (count == 0) {
            contextMenu.setVisibility(View.VISIBLE);
            count = 1;
            txtAtoz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextMenu.setVisibility(View.GONE);
                    getPlayer();
                }
            });

            txtTeamWise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextMenu.setVisibility(View.GONE);
                    showTeamDialog();
                }
            });

            txtCoachWise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextMenu.setVisibility(View.GONE);
                    showCoachDialog();
                }
            });
        } else {
            contextMenu.setVisibility(View.GONE);
            count = 0;
        }

    }
}

