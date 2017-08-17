package in.mvcdemo.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.Model.Instruction;
import in.mvcdemo.Net.RetrofitClient;
import in.mvcdemo.R;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EActivity(R.layout.activity_instruction)
public class InstructionActivity extends BaseActivity {

    @ViewById(R.id.txtInsturction)
    CustomTextView txtInsturction;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.txt_title)
    CustomTextView txt_title;

    @AfterViews
    public void init() {
        setupToolbar();
        getInstruction();

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        txt_title.setText("Instruction");
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

    private final retrofit.Callback instCallback = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();
            Instruction object = (Instruction) o;
            txtInsturction.setText(object.getGuest().get(0).getI_description());
        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }
    };

    public void getInstruction() {
        RetrofitClient.getInstance().getRestOkClient().
                getInstruction(instCallback);
    }
}
