package in.mvcdemo.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
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
import in.mvcdemo.Custom.cropimage.CropImage;
import in.mvcdemo.Model.AddPlayer;
import in.mvcdemo.Model.GetTeamName;
import in.mvcdemo.Net.RetrofitClient;
import in.mvcdemo.R;
import in.mvcdemo.Utills.Utility;
import in.mvcdemo.Utills.VLogger;
import in.mvcdemo.Utills.VPreferences;
import in.mvcdemo.Utills.Validator;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


@EActivity(R.layout.activity_add_player_info)
public class ActivityAddPlayerProfile extends BaseActivity implements AdapterView.OnItemSelectedListener {

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


    private static final String TAG = ActivityAddPlayerProfile.class.getSimpleName();
    private static final int SELECT_FILE_1 = 102;
    private static final int REQUEST_CAMERA_1 = 101;
    private static final int REQUEST_CROP_1 = 103;

    public final String TEMP_PHOTO1_FILE_NAME = new Date().getTime() + "profile_1" + ".jpg";

    private TypedFile file1;
    private File mTempFile1;
    private Bitmap mBitmap1;
    private String mAuthTokenType;
    private File mFileTemp1;
    private String teamID, teamOne, teamTwo, finalSelectedTeam;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @AfterViews
    public void init() {
        setupToolbar();


        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp1 = new File(Environment.getExternalStorageDirectory(),
                    TEMP_PHOTO1_FILE_NAME);

        } else {
            mFileTemp1 = new File(getFilesDir(), TEMP_PHOTO1_FILE_NAME);
        }

        getTeamName();
        edtTeamName.setOnItemSelectedListener(ActivityAddPlayerProfile.this);
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbMale) {
                    messageUtil.showToast("Selected Male");
                } else {
                    messageUtil.showToast("Selected Female");
                }
            }
        });
    }

    @Click
    public void txtSubmit() {
        addPlayer();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        txt_title.setText("Add Player Profile");
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
    public void imgPlayer() {
        if (isStoragePermissionGranted() && locationpermission()) {
            selectImage1();
        }
    }

    /**
     * @return
     */
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d("-->", "Permission is granted");
                return true;
            } else {

                Log.d("-->", "Permission is revoked");
                ActivityCompat.requestPermissions(ActivityAddPlayerProfile.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.d("-->", "Permission is granted");
            return true;
        }
    }

    private boolean locationpermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(ActivityAddPlayerProfile.this
                ,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityAddPlayerProfile.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(ActivityAddPlayerProfile.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)     {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Log.d("-->", "Permission: " + permissions[0] + "was " + grantResults[0]);
//            selectImage1();
//
//            //resume tasks needing this permission
//        }
//    }

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
            Log.d("==>", ex.getMessage(), ex);
        }
    }

    private void cropCapturedImage1() {
        Intent intent = new Intent(ActivityAddPlayerProfile.this, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp1.getPath());
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.ASPECT_X, 3);
        intent.putExtra(CropImage.ASPECT_Y, 2);

        startActivityForResult(intent, REQUEST_CROP_1);
    }

    /**
     * @param bitmap
     * @return
     */
    private Bitmap getMutableBitmap(final Bitmap bitmap) {
        try {
            final Bitmap mutBmp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            final Canvas can = new Canvas(mutBmp);
            can.drawBitmap(bitmap, 0, 0, new Paint());
            return mutBmp;
        } catch (final Exception e) {
            Log.d("-->", e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     */
    @SuppressLint({"InlinedApi", "NewApi"})
    private void saveInSdcard1() {
        FileOutputStream fo = null;
        try {
            final File dir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/photopass");
            if (!dir.exists()) {
                dir.mkdir();
            }
            mTempFile1 = new File(dir, System.currentTimeMillis() + ".jpeg");
            fo = new FileOutputStream(mTempFile1);

            mBitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fo);
            fo.flush();
            fo.close();
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    mTempFile1.getAbsolutePath(), mTempFile1.getName(),
                    mTempFile1.getName());
        } catch (final Exception e) {
            Log.e("-->", e.getMessage(), e);
        } finally {
            try {
                fo.close();
            } catch (final Throwable e) {
            }
        }
    }

    public void encode1() {
        String encodedImage1 = null;
        if (mBitmap1 != null) {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            mBitmap1
                    .compress(Bitmap.CompressFormat.JPEG, 35, out);
            encodedImage1 = Base64.encodeToString(out.toByteArray(),
                    Base64.DEFAULT);
            encodedImage1 = "data:image/jpeg;base64," + encodedImage1;
        }

//        mDialog = new ProgressDialog(ActivityAddPlayerProfile.this);
//        mDialog.setMessage("Loading.....");
//        mDialog.show();
    }

    public void shareFile1() {
        try {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(final Void... params) {
                    saveInSdcard1();
                    return null;
                }

                @Override
                protected void onPostExecute(final Void result) {
                    super.onPostExecute(result);
                }

            }.execute();
        } catch (final Exception e) {
            Log.e("==>", e.getMessage(), e);
        }
    }

    public String getPath(final Uri uri) {
        final String[] projection = {MediaStore.Images.Media.DATA};
        final Cursor cursor = getContentResolver().query(uri, projection, null,
                null, null);
        final int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void selectImage1() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddPlayerProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int item) {
                if (items[item].equals("Take Photo")) {
                    final Intent intent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    final File f = new File(Environment
                            .getExternalStorageDirectory(),
                            TEMP_PHOTO1_FILE_NAME);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA_1);
                } else if (items[item].equals("Choose from Gallery")) {
                    final Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE_1);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }




    private final Callback addPlayerCallBack = new Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            AddPlayer object = (AddPlayer) o;
            if (object.getData().get(0).getStatus().equalsIgnoreCase("102")) {
                messageUtil.showMessageDialog("The TeamMate", "User Already Exists");
            } else if (object.getData().get(0).getStatus().equalsIgnoreCase("100")) {
                messageUtil.showMessageDialog("Successfully Added Player");
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

    public void addPlayer() {
        VLogger.infoLog("-->Add Player<--");
        messageUtil.showProgressDialog();
        if (validateInput()) {
            if (Utility.checkInternetConnection(ActivityAddPlayerProfile.this)) {

                int selectedId = rgGender.getCheckedRadioButtonId();
                String selectedString = null;
                if (selectedId == rbMale.getId()) {
                    selectedString = "male";
                } else {
                    selectedString = "female";
                }
                encode1();
                TypedFile typedFile1 = null;
                typedFile1 = new TypedFile("multipart/form-data", new File(mFileTemp1.getPath()));
                Log.d("Final Team", finalSelectedTeam);
                VLogger.infoLog("ADD PLAYER");
                RetrofitClient.getInstance().getRestOkClient().addPlayer(edtPlayerName.getText().toString()
                        , selectedString, typedFile1, VPreferences.getPreferanceEventID(ActivityAddPlayerProfile.this), edtCoachName.getText().toString()
                        , finalSelectedTeam, edtShirtNo.getText().toString(), edtOtherArrt.getText().toString()
                        , edtPosition.getText().toString(), addPlayerCallBack);
            } else {
                messageUtil.showToast("No Internet!!");
            }

        } else {
            messageUtil.showMessage("Server Error Occured");
        }
    }

    public void getTeamName() {
        messageUtil.showProgressDialog();
        if (Utility.checkInternetConnection(ActivityAddPlayerProfile.this)) {
            VLogger.infoLog("ADD PLAYER");

            RetrofitClient.getInstance().getRestOkClient().getTeamName(VPreferences.getPreferanceEventID(ActivityAddPlayerProfile.this), getTeamCallback);
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
                        ActivityAddPlayerProfile.this, R.layout.spinner_item, categories) {
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
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ActivityAddPlayerProfile Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        if (resultCode == RESULT_OK && (requestCode == REQUEST_CAMERA_1 || requestCode == SELECT_FILE_1 || requestCode == REQUEST_CROP_1)) {
            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            if (requestCode == REQUEST_CAMERA_1) {
                for (final File temp : f.listFiles()) {
                    if (temp.getName().equals(TEMP_PHOTO1_FILE_NAME)) {
                        f = temp;
                        break;
                    }
                }
                cropCapturedImage1();
            } else if (requestCode == SELECT_FILE_1) {
                try {
                    InputStream inputStream = getContentResolver()
                            .openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            mFileTemp1);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    cropCapturedImage1();
                } catch (Exception e) {
                    Log.e("-->", e.getMessage(), e);
                }

            } else if (requestCode == REQUEST_CROP_1) {
                String path = data.getStringExtra(CropImage.IMAGE_PATH);
                if (path == null) {
                    Log.e("-->", "Path nullll");
                    return;
                }

                mBitmap1 = BitmapFactory.decodeFile(mFileTemp1.getPath());
                Log.d("-->", mFileTemp1.getPath());
                imgPlayer.setImageBitmap(mBitmap1);
                f.delete();
            }
        }

    }
}
