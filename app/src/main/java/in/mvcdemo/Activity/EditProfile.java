package in.mvcdemo.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.mvcdemo.Custom.CircleImageView;
import in.mvcdemo.Custom.CustomEditText;
import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.Model.GetPlayerProfile;
import in.mvcdemo.Model.GetTeamName;
import in.mvcdemo.Model.UpdatePlayer;
import in.mvcdemo.Net.RetrofitClient;
import in.mvcdemo.R;
import in.mvcdemo.Utills.Constant;
import in.mvcdemo.Utills.ImageUtils;
import in.mvcdemo.Utills.Utility;
import in.mvcdemo.Utills.VLogger;
import in.mvcdemo.Utills.VPreferences;
import in.mvcdemo.Utills.Validator;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Payal on 10/1/2016.
 */

@EActivity(R.layout.activity_edit_profile)
public class EditProfile extends BaseActivity implements AdapterView.OnItemSelectedListener  {

    @ViewById(R.id.txtSubmit)
    CustomTextView txtSubmit;
    @ViewById(R.id.imgPlayer)
    CircleImageView imgPlayer;
    @ViewById(R.id.edtPlayerName)
    CustomEditText edtPlayerName;
    @ViewById(R.id.edtTeamName)
    Spinner edtTeamName;
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
    @ViewById(R.id.rbMale)
    RadioButton rbMale;
    @ViewById(R.id.rbFemale)
    RadioButton rbFemale;
    @ViewById(R.id.rgGender)
    RadioGroup rgGender;
    @ViewById(R.id.txt_title)
    CustomTextView txt_title;
    private String teamID, teamOne, teamTwo, finalSelectedTeam;
    private String flag = "0";
    private String imgUrl;
    private String noSelect = "None";

    private static final String TAG = EditProfile.class.getSimpleName();
//    private static final int SELECT_FILE_1 = 102;
//    private static final int REQUEST_CAMERA_1 = 101;
//    private static final int REQUEST_CROP_1 = 103;
//    private static int PICK_IMAGE = 100;
//    Date d = new Date();
//    public final String TEMP_PHOTO1_FILE_NAME = d.getTime() + "player_pro_1" + ".jpg";
//    private static int RESULT_LOAD_IMG_1 = 1001;
//    private Bitmap mBitmap1;
//    private File mFileTemp1;
    Date d = new Date();
    public final String TEMP_PHOTO_FILE_NAME1 = Math.random() + "sample1.jpg";
    private static final int REQUEST_CAMERA = 101;
    private static final int SELECT_FILE = 102;
    private static int RESULT_LOAD_IMG_1 = 100;
    private String eventName, teamName;
    private String PLA_NAME,PLA_JNO,PLA_ID;

    private File file1;
    private TypedFile typedFile1;
    private String imgDecodableString1;



    @AfterViews
    public void init(){
        setupToolbar();

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            file1 = new File(Environment.getExternalStorageDirectory(),
                    TEMP_PHOTO_FILE_NAME1);

            Log.d("---->",TEMP_PHOTO_FILE_NAME1);

        } else {
            file1 = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME1);
        }
        getTeamName();
        edtTeamName.setOnItemSelectedListener(EditProfile.this);
        Bundle extras = getIntent().getExtras();
        PLA_NAME = extras.getString("PLA_NAME");
        PLA_JNO = extras.getString("PLA_JNO");
        PLA_ID = extras.getString("PLA_ID");

        getPlayerProfile();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        txt_title.setText("Edit Player Profile");
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
    public void txtSubmit(){
        addPlayer();
    }



    public void loadImagefromGallery1(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        flag="1";

        Intent galleryIntent1 = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent1, RESULT_LOAD_IMG_1);
    }

    public void copyStream(final InputStream is, final OutputStream os) {
        final int buffer_size = 1024;
        try {
            final byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                // Read byte from input stream
                final int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                // Write byte from output stream
                os.write(bytes, 0, count);
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG_1 && resultCode == RESULT_OK
                    && null != data) {
                try {
                    Uri selectedImage = data.getData();
                    InputStream inputStream = getContentResolver()
                            .openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            file1);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    imgPlayer.setImageURI(selectedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private final retrofit.Callback updatePlayerCallBack = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            UpdatePlayer object = (UpdatePlayer) o;
            if (object.getData().get(0).getStatus().equalsIgnoreCase("102")) {
                messageUtil.showMessageDialog("The TeamMate", "User Already Exists");
            } else if (object.getData().get(0).getStatus().equalsIgnoreCase("100")) {
                messageUtil.showMessageDialog("Successfully Update Player");
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

    @Background
    public void getPlayerProfile() {
        messageUtil.showProgressDialog();
        RetrofitClient.getInstance().getRestOkClient().
                getPlayerProfile(PLA_NAME, PLA_JNO, VPreferences.getPreferanceEventID(EditProfile.this),PLA_ID, playerProfileCallback);
    }

    public void addPlayer() {
        VLogger.infoLog("-->Add Player<--");
        messageUtil.showProgressDialog();
        if (validateInput()) {
            if (Utility.checkInternetConnection(EditProfile.this) && (finalSelectedTeam.equalsIgnoreCase("None"))) {
                messageUtil.showMessage("Choose a Teamname");
            }else if(Utility.checkInternetConnection(EditProfile.this)){

                int selectedId = rgGender.getCheckedRadioButtonId();
                String selectedString = null;
                if (selectedId == rbMale.getId()) {
                    selectedString = "male";
                } else {
                    selectedString = "female";
                }

                TypedFile typedFile1 = null;
                Log.d("----->DATA",file1.getPath());
                typedFile1 = new TypedFile("multipart/form-data", new File(file1.getPath()));

                if(flag.equals("0")){
                    RetrofitClient.getInstance().getRestOkClient().editPlayer(edtPlayerName.getText().toString()
                            , selectedString, null, VPreferences.getPreferanceEventID(EditProfile.this), edtCoachName.getText().toString()
                            , finalSelectedTeam, edtShirtNo.getText().toString(), edtOtherArrt.getText().toString()
                            , edtPosition.getText().toString(),PLA_ID ,updatePlayerCallBack);
                }else{
                    RetrofitClient.getInstance().getRestOkClient().editPlayer(edtPlayerName.getText().toString()
                            , selectedString, typedFile1, VPreferences.getPreferanceEventID(EditProfile.this), edtCoachName.getText().toString()
                            , finalSelectedTeam, edtShirtNo.getText().toString(), edtOtherArrt.getText().toString()
                            , edtPosition.getText().toString(),PLA_ID ,updatePlayerCallBack);
                }

                VLogger.infoLog("ADD PLAYER");
                Log.d("--->PLAID",PLA_ID);

            } else {
                messageUtil.showToast("No Internet!!");
            }

        } else {
            messageUtil.showMessage("Server Error Occured");
        }

    }

    private final retrofit.Callback playerProfileCallback = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            GetPlayerProfile object = (GetPlayerProfile) o;
            if(object.getStatus().equals("100")){
                edtPlayerName.setText(object.getGuest().get(0).getName());
                if(object.getGuest().get(0).getGender().equals("male")){
                    rbMale.setChecked(true);
                    rbFemale.setChecked(false);
                }else if(object.getGuest().get(0).getGender().equals("female")){
                    rbFemale.setChecked(true);
                    rbMale.setChecked(false);
                }
//                edtTeamName.setText(object.getGuest().get(0).getTeamname());
                edtCoachName.setText(object.getGuest().get(0).getCoachname());
                edtShirtNo.setText(object.getGuest().get(0).getShirtno());
                edtOtherArrt.setText(object.getGuest().get(0).getOther_attributes());
                edtPosition.setText(object.getGuest().get(0).getPosition());

                ImageUtils.getImageLoader(EditProfile.this).
                        displayImage(Constant.PENDING_IMG_URL + object.getGuest().get(0).getImage(),
                                imgPlayer);

                imgUrl = object.getGuest().get(0).getImage();

            } else {
                messageUtil.showMessage("No Event Found");
            }

        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error Occurred");
            messageUtil.hideProgressDialog();
        }
    };

    private boolean validateInput() {
        if (Validator.validateString(edtPlayerName.getText().toString())) {
                if (Validator.validateString(edtCoachName.getText().toString())) {
                    if (Validator.validateString(edtShirtNo.getText().toString())) {
                            return true;
                    } else
                        messageUtil.showMessageDialog("Shirt Number required");
                } else
                    messageUtil.showMessageDialog("Coach Name required");
        } else
            messageUtil.showMessageDialog("Player Name required");
        return false;
    }

    public void getTeamName() {
        messageUtil.showProgressDialog();
        if (Utility.checkInternetConnection(EditProfile.this)) {
            VLogger.infoLog("ADD PLAYER");

            RetrofitClient.getInstance().getRestOkClient().getTeamName(VPreferences.getPreferanceEventID(EditProfile.this), getTeamCallback);
        } else {
            messageUtil.showToast("No Internet!!");
        }
    }

    private final Callback getTeamCallback = new Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            GetTeamName object = (GetTeamName) o;
            if (object.getStatus().equalsIgnoreCase("100")) {

                List<String> categories = new ArrayList<String>();
                categories.add("Choose Team");
                categories.add(object.getGuest().get(0).getName());
                categories.add(object.getGuest().get(1).getName());
                final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        EditProfile.this, R.layout.spinner_item, categories) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            // Disable the first item from Spinner
                            // First item will be use for hint
                            return false;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        CustomTextView tv = (CustomTextView) view;
                        if (position == 0) {
                            // Set the hint text color gray
                            tv.setTextColor(Color.GRAY);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                teamOne = object.getGuest().get(0).getTid();
                teamTwo = object.getGuest().get(1).getTid();

                Log.d("--->T1", teamOne);
                Log.d("--->T2", teamTwo);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

                // attaching data adapter to spinner
                edtTeamName.setAdapter(spinnerArrayAdapter);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        Log.d("-->i", String.valueOf(i));

        if (i == 1) {
            teamID = "0";
            finalSelectedTeam = teamOne;
            Log.d("Final SelectedTeam", finalSelectedTeam);
        } else if (i == 2) {
            teamID = "1";
            finalSelectedTeam = teamTwo;
            Log.d("Final SelectedTeam", finalSelectedTeam);
        } else if(i == 0){
            finalSelectedTeam = noSelect;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
