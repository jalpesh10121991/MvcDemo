package in.mvcdemo.Activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.webapptech.crutattoo.BuildConfig;
import com.webapptech.crutattoo.Custom.CustomEditText;
import com.webapptech.crutattoo.Custom.CustomTextView;
import com.webapptech.crutattoo.Custom.signature.views.SignaturePad;
import com.webapptech.crutattoo.Model.ModelTatooConsent;
import com.webapptech.crutattoo.Net.RetrofitClient;
import com.webapptech.crutattoo.R;
import com.webapptech.crutattoo.Utils.Utility;
import com.webapptech.crutattoo.Utils.VPreferences;
import com.webapptech.crutattoo.Utils.Validator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class TatooConsentActivity extends BaseActivity {

    private CustomTextView txt_title, txtdate, txtID;
    private CustomEditText edtPrintName, edtArtistName, edtName, edtAddress, edtMob, edtAge, edtdob, edtdisc;
    private CharSequence artistText;
    private int mYear, mMonth, mDay;
    //    private ImageView imgTemp;
    private SignaturePad signature_pad;
    private CustomTextView txtSave;
    private File cacheDir;
    private Button btnsign, btnSubmit;
    private Switch switch1, switch2, switch3, switch4, switch5, switch6, switch7, switch8, switch9, switch10, switch11;
    private ImageView imgCam;
    private int count = 0;
    private Toolbar toolbar;
    private static final int SELECT_FILE_1 = 102;
    private static final int REQUEST_CAMERA_1 = 101;
    private static final int REQUEST_CROP_1 = 103;
    private static int PICK_IMAGE = 100;
    private int sw1 = 0, sw2 = 0, sw3 = 0, sw4 = 0, sw5 = 0, sw6 = 0, sw7 = 0, sw8 = 0, sw9 = 0, sw10 = 0, sw11 = 0;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String FRAGMENT_DIALOG = "dialog";
    private static final int ACTION_TAKE_PHOTO_B = 1;
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView imgID, btnCamera;

    public final String TEMP_PHOTO1_FILE_NAME = d.getTime() + "profile" + ".jpg";
    public final String TEMP_PHOTO_SIGN_FILE_NAME = d.getTime() + "signature" + ".jpg";
    private TypedFile file1, file2;
    private File mTempFile1, mTempFile2;
    private Bitmap mBitmap1, mBitmap2;
    private String mAuthTokenType;
    private File mFileTemp1, mFileTemp2;
    private File mTempFile;
    private Bitmap mBitmapProfile;
    private File mFileTemp;
    private static Date d = new Date();
    private static final String EXTRA_FILENAME =
            "${applicationId}.EXTRA_FILENAME";
    private static final String FILENAME = d.getTime() + "Camera_Consent" + ".jpg";
    private static final int CONTENT_REQUEST = 1337;
    private static final String AUTHORITY =
            BuildConfig.APPLICATION_ID + ".provider";
    private static final String PHOTOS = "photos";
    private File output = null;


//    CustomTextView.OnClickListener mTakePicOnClickListener =
//            new Button.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tatoo_consent);

        isCameraermission();

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp1 = new File(Environment.getExternalStorageDirectory(),
                    TEMP_PHOTO1_FILE_NAME);
            mFileTemp2 = new File(Environment.getExternalStorageDirectory(),
                    TEMP_PHOTO_SIGN_FILE_NAME);
        } else {
            mFileTemp1 = new File(this.getFilesDir(), TEMP_PHOTO1_FILE_NAME);
            mFileTemp2 = new File(this.getFilesDir(), TEMP_PHOTO_SIGN_FILE_NAME);
        }
        messageUtil.showMessageDialog("Tattoo Consent");

        edtPrintName = (CustomEditText) findViewById(R.id.edtPrintName);
        btnCamera = (ImageView) findViewById(R.id.btnCamera);
        txtSave = (CustomTextView) findViewById(R.id.txtSave);
        signature_pad = (SignaturePad) findViewById(R.id.signature_pad);
        edtArtistName = (CustomEditText) findViewById(R.id.edtArtistName);
        edtName = (CustomEditText) findViewById(R.id.edtName);
        edtAddress = (CustomEditText) findViewById(R.id.edtAddress);
        edtMob = (CustomEditText) findViewById(R.id.edtMob);
        edtAge = (CustomEditText) findViewById(R.id.edtAge);
        edtdob = (CustomEditText) findViewById(R.id.edtdob);
        edtdisc = (CustomEditText) findViewById(R.id.edtdisc);

//        imgTemp = (ImageView) findViewById(R.id.imgTemp);
        imgID = (ImageView) findViewById(R.id.imgID);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_title = (CustomTextView) findViewById(R.id.txt_title);
        setupToolbar();

        txtID = (CustomTextView) findViewById(R.id.txtID);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    output = new File(new File(getFilesDir(), PHOTOS), FILENAME);

                    if (output.exists()) {
                        output.delete();
                    } else {
                        output.getParentFile().mkdirs();
                    }

                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri outputUri = FileProvider.getUriForFile(TatooConsentActivity.this, AUTHORITY, output);

                    i.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ClipData clip =
                                ClipData.newUri(getContentResolver(), "A photo", outputUri);

                        i.setClipData(clip);
                        i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    } else {
                        List<ResolveInfo> resInfoList =
                                getPackageManager()
                                        .queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY);

                        for (ResolveInfo resolveInfo : resInfoList) {
                            String packageName = resolveInfo.activityInfo.packageName;
                            grantUriPermission(packageName, outputUri,
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }
                    }

                    try {
                        startActivityForResult(i, CONTENT_REQUEST);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(TatooConsentActivity.this, R.string.msg_no_camera, Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    output = (File) savedInstanceState.getSerializable(EXTRA_FILENAME);
                }

            }
        });


// Signature
        btnsign = (Button) findViewById(R.id.btnsign);
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
                    @Override
                    public void onStartSigning() {
                    }

                    @Override
                    public void onSigned() {
                        txtSave.setEnabled(true);
//                        mClearButton.setEnabled(true);
//                        mCompressButton.setEnabled(true);
                    }

                    @Override
                    public void onClear() {
                        txtSave.setEnabled(false);
//                        mClearButton.setEnabled(false);
//                        mCompressButton.setEnabled(false);
                    }
                });

                if (count == 0) {
                    signature_pad.setVisibility(View.VISIBLE);
                    txtSave.setVisibility(View.VISIBLE);
                    count = 1;
                } else if (count == 1) {
                    signature_pad.setVisibility(View.GONE);
                    txtSave.setVisibility(View.GONE);
                    count = 0;
                }
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = signature_pad.getSignatureBitmap();
                if (addJpgSignatureToGallery(signatureBitmap)) {
                    Toast.makeText(TatooConsentActivity.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TatooConsentActivity.this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
                }
            }
        });
//


//        sign = (ImageView)rootView.findViewById(R.id.imgsign);
//        sign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new SignatureDialogBuilder()
//                        .show(this, new SignatureDialogBuilder.SignatureEventListener() {
//                            @Override
//                            public void onSignatureEntered(File savedFile) {
//                                new ImageRequest(getActivity(),rootView.findViewById(R.id.imgsign))
//                                        .setTargetFile(savedFile)
//                                        .setFadeTransition()
//                                        .execute(); // Just showing the image
//                            }
//
//                            @Override
//                            public void onSignatureInputCanceled() {
//                                Toast.makeText(getActivity(), "Signature input canceled", Toast.LENGTH_SHORT)
//                                        .show();
//                            }
//
//                            @Override
//                            public void onSignatureInputError(Throwable e) {
//                                if(e instanceof NoSignatureException) // They clicked confirm without entering anything
//                                    doSomethingOnNoSignatureEntered();
//                                else Toast.makeText(getActivity(), "Signature error", Toast.LENGTH_SHORT)
//                                        .show();
//                            }
//                        });
//            }
//        });


// * * * Date

        txtdate = (CustomTextView) findViewById(R.id.txtdate);
//        String dt;
//        Date cal = (Date) Calendar.getInstance().getTime();
//        dt = cal.toLocaleString();

        Date startDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(startDate);
        Log.d("-->", formattedDate);
        txtdate.setText(formattedDate.toString());

//        txtdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(TatooConsentActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//
//                                txtdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                            }
//                        }, mYear, mMonth, mDay);
//
//                datePickerDialog.show();
//
//            }
//        });

//Submit

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();

            }
        });

//Switches
        switch1 = (Switch) findViewById(R.id.switch1);


        switch2 = (Switch) findViewById(R.id.switch2);


        switch3 = (Switch) findViewById(R.id.switch3);


        switch4 = (Switch) findViewById(R.id.switch4);


        switch5 = (Switch) findViewById(R.id.switch5);

        switch6 = (Switch) findViewById(R.id.switch6);
        switch7 = (Switch) findViewById(R.id.switch7);
        switch8 = (Switch) findViewById(R.id.switch8);

        switch9 = (Switch) findViewById(R.id.switch9);

        switch10 = (Switch) findViewById(R.id.switch10);

        switch11 = (Switch) findViewById(R.id.switch11);
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);

            Log.d("---->", String.valueOf(photo));
            VPreferences.setSignImagePath(TatooConsentActivity.this, String.valueOf(photo));
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        TatooConsentActivity.this.sendBroadcast(mediaScanIntent);
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    /**
     * @return
     */
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean isCameraermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        } else {
            return true;
        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            selectImage1();
//
//
//        }
//    }

//    public class ConfirmationDialog extends DialogFragment {
//
//        @NonNull
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//            return new AlertDialog.Builder(getActivity())
//                    .setMessage(R.string.request_permission)
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(TatooConsentActivity.this,
//                                    new String[]{Manifest.permission.CAMERA},
//                                    REQUEST_CAMERA_PERMISSION);
//                        }
//                    })
//                    .setNegativeButton(android.R.string.cancel,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Toast.makeText(TatooConsentActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                    .create();
//        }
//
//        public void show(TatooConsentActivity tatooConsentActivity, String fragmentDialog) {
//        }
//    }


    private void selectImage1() {

        final Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        final File f = new File(Environment
                .getExternalStorageDirectory(),
                TEMP_PHOTO1_FILE_NAME);
//        Uri apkURI = FileProvider.getUriForFile(
//                TatooConsentActivity.this,
//                TatooConsentActivity.this.getApplicationContext()
//                        .getPackageName() + ".provider", f);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, REQUEST_CAMERA_1);
//        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
//                "Cancel"};
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(final DialogInterface dialog, final int item) {
//                if (items[item].equals("Take Photo")) {
//                    final Intent intent = new Intent(
//                            MediaStore.ACTION_IMAGE_CAPTURE);
//                    final File f = new File(Environment
//                            .getExternalStorageDirectory(),
//                            TEMP_PHOTO1_FILE_NAME);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                    startActivityForResult(intent, REQUEST_CAMERA_1);
//                } else if (items[item].equals("Choose from Gallery")) {
//                    final Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType("image/*");
//                    startActivityForResult(
//                            Intent.createChooser(intent, "Select File"),
//                            SELECT_FILE_1);
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
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

    }

    public void encode2() {
        String encodedImage2 = null;
        if (mBitmap2 != null) {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            mBitmap2
                    .compress(Bitmap.CompressFormat.JPEG, 35, out);
            encodedImage2 = Base64.encodeToString(out.toByteArray(),
                    Base64.DEFAULT);
            encodedImage2 = "data:image/jpeg;base64," + encodedImage2;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(EXTRA_FILENAME, output);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == CONTENT_REQUEST) {
            if (resultCode == RESULT_OK) {
//                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri outputUri = FileProvider.getUriForFile(this, AUTHORITY, output);

                imgID.setImageURI(outputUri);
//                File f = new File(Environment.getExternalStorageDirectory()
//                        .toString());
//                Log.d("----->URL", String.valueOf(outputUri));
//                Log.d("----->URL", String.valueOf(output));
//                for (final File temp : f.listFiles()) {
//                    if (temp.getName().equals(TEMP_PHOTO1_FILE_NAME)) {
//                        f = temp;
//                        VPreferences.setImagePath(TatooConsentActivity.this, f.getPath());
//                        mBitmap1 = BitmapFactory.decodeFile(f.getPath());
//                        Log.d("====>",f.getPath());
//                        imgID.setImageBitmap(mBitmap1);
//                        break;
//                    }

//                    i.setDataAndType(outputUri, "image/jpeg");
//                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                    try {
//                        startActivity(i);
//                    } catch (ActivityNotFoundException e) {
//                        Toast.makeText(this, R.string.msg_no_viewer, Toast.LENGTH_LONG).show();
//                    }


                }
            }
    }

//    @Override
//    public void onActivityResult(final int requestCode, final int resultCode,
//                                 final Intent data) {

//        switch (requestCode) {
//            case ACTION_TAKE_PHOTO_B: {
//                if (resultCode == RESULT_OK) {
//                    handleBigCameraPhoto();
//                }
//                break;
//            } // ACTION_TAKE_PHOTO_B
//        }
//    }
//        if (resultCode == RESULT_OK && (requestCode == REQUEST_CAMERA_1 )) {
//            File f = new File(Environment.getExternalStorageDirectory()
//                    .toString());
//            if (requestCode == REQUEST_CAMERA_1) {
//                for (final File temp : f.listFiles()) {
//                    if (temp.getName().equals(TEMP_PHOTO1_FILE_NAME)) {
//                        f = temp;
//                        VPreferences.setImagePath(TatooConsentActivity.this, f.getPath());
//                        mBitmap1 = BitmapFactory.decodeFile(f.getPath());
//                        Log.d("-=-===>", f.getPath());
//                        imgID.setImageBitmap(mBitmap1);
//                        break;
//                    }
//                }
////                cropCapturedImage1();
//            } else if (requestCode == SELECT_FILE_1) {
//                try {
//                    InputStream inputStream = this.getContentResolver()
//                            .openInputStream(data.getData());
//                    FileOutputStream fileOutputStream = new FileOutputStream(
//                            mFileTemp1);
//                    copyStream(inputStream, fileOutputStream);
//                    fileOutputStream.close();
//                    inputStream.close();
//                    VPreferences.setImagePath(TatooConsentActivity.this, mFileTemp1.getPath());
//                    mBitmap1 = BitmapFactory.decodeFile(mFileTemp1.getPath());
//                    Log.d("-=-===>", mFileTemp1.getPath());
//                    imgID.setImageBitmap(mBitmap1);
////                    cropCapturedImage1();
//                } catch (Exception e) {
//                }
//
//            } else if (requestCode == REQUEST_CROP_1) {
//                String path = data.getStringExtra(CropImage.IMAGE_PATH);
//                if (path == null) {
//                    return;
//                }
//
//                mBitmap1 = BitmapFactory.decodeFile(mFileTemp1.getPath());
//                imgID.setImageBitmap(mBitmap1);
//                f.delete();
//            }
//        }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        txt_title.setText("Tattoo Consent");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
        }
    }


    private boolean validate() {
        if (Validator.validateString(edtPrintName.getText().toString())) {
            if (Validator.validateString(edtArtistName.getText().toString()))
                if (Validator.validateString(edtName.getText().toString()))
                    if (Validator.validateString(edtAddress.getText().toString()))
                        if (Validator.validateString(edtMob.getText().toString()))
                            if (Validator.validateString(edtAge.getText().toString()))
                                if (Validator.validateString(edtdob.getText().toString()))
                                    if (Validator.validateString(txtdate.getText().toString()))
                                        if (Validator.validateString(edtdisc.getText().toString()))
                                            return true;
                                        else
                                            messageUtil.showMessage("Discription Name Required");
                                    else
                                        messageUtil.showMessage("Date Required");
                                else
                                    messageUtil.showMessage("Date of Birth Required");
                            else
                                messageUtil.showMessage("Age Required");
                        else
                            messageUtil.showMessage("Mobile Number Required");
                    else
                        messageUtil.showMessage("Address Required");
                else
                    messageUtil.showMessage("Name Required");
            else
                messageUtil.showMessage("Artist Name Required");
        } else
            messageUtil.showMessage("Print Your Required");
        return false;

    }

    public void submit() {

        if (validate()) {
            messageUtil.showProgressDialog();
            if (Utility.checkInternetConnection(TatooConsentActivity.this)) {
                messageUtil.showProgressDialog();
                encode1();
                TypedFile typedFile1 = null;
                typedFile1 = new TypedFile("multipart/form-data", new File(String.valueOf(output)));
                Log.d("--------DATAFINAL", String.valueOf(typedFile1));

                encode2();
                TypedFile typedFile2 = null;
                typedFile2 = new TypedFile("multipart/form-data", new File(VPreferences.getSignImagePath(TatooConsentActivity.this)));
                Log.d("--------DATAFINAL", String.valueOf(typedFile2));
                if (switch1.isChecked()) {
                    sw1 = 1;
                } else {
                    sw1 = 0;
                }

                if (switch2.isChecked()) {
                    sw2 = 1;
                } else {
                    sw2 = 0;
                }

                if (switch3.isChecked()) {
                    sw3 = 1;
                } else {
                    sw3 = 0;
                }

                if (switch4.isChecked()) {
                    sw4 = 1;
                } else {
                    sw4 = 0;
                }

                if (switch5.isChecked()) {
                    sw5 = 1;
                } else {
                    sw5 = 0;
                }

                if (switch6.isChecked()) {
                    sw6 = 1;
                } else {
                    sw6 = 0;
                }

                if (switch7.isChecked()) {
                    sw7 = 1;
                } else {
                    sw7 = 0;
                }

                if (switch8.isChecked()) {
                    sw8 = 1;
                } else {
                    sw8 = 0;
                }

                if (switch9.isChecked()) {
                    sw9 = 1;
                } else {
                    sw9 = 0;
                }

                if (switch10.isChecked()) {
                    sw10 = 1;
                } else {
                    sw10 = 0;
                }

                if (switch11.isChecked()) {
                    sw11 = 1;
                } else {
                    sw11 = 0;
                }

                //                encode2();
//                TypedFile typedFile2 = null;
//                typedFile2 = new TypedFile("multipart/form-data", new File(VPreferences.getSignImagePath(TatooConsentActivity.this)));
//                Log.d("--------DATAFINAL", String.valueOf(typedFile2));

                RetrofitClient.getInstance().getRestOkClient().tattooConsent(edtPrintName.getText().toString(),
                        edtArtistName.getText().toString(),
                        String.valueOf(sw1), String.valueOf(sw2), String.valueOf(sw3), String.valueOf(sw4), String.valueOf(sw5), String.valueOf(sw6),
                        String.valueOf(sw7), String.valueOf(sw8), String.valueOf(sw9), String.valueOf(sw10), String.valueOf(sw11), edtName.getText().toString(),
                        edtAddress.getText().toString(), edtMob.getText().toString(),
                        edtAge.getText().toString(), edtdob.getText().toString(),
                        txtdate.getText().toString(), edtdisc.getText().toString(),
                        typedFile1, typedFile2, tattooConsentCallBack);

            } else {
                messageUtil.showMessageDialog("CRU Tattoo", "No Internet");
            }

        } else {
            messageUtil.showMessageDialog("CRU Tattoo", "Enter Correct Details");
        }

    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void setBtnListenerOrDisable(
            Button btn,
            Button.OnClickListener onClickListener,
            String intentName
    ) {
        if (isIntentAvailable(this, intentName)) {
            btn.setOnClickListener(onClickListener);
        } else {
            btn.setText(
                    getText(R.string.cannot).toString() + " " + btn.getText());
            btn.setClickable(false);
        }
    }


    private final retrofit.Callback tattooConsentCallBack = new retrofit.Callback() {

        @Override
        public void success(Object o, Response response) {
            messageUtil.hideProgressDialog();

            ModelTatooConsent object = (ModelTatooConsent) o;
            if (object.getResult().equalsIgnoreCase("inserted")) {
                messageUtil.showMessageDialog("CRU Tattoo", "Successfully Added");
                finish();
            } else {
                messageUtil.showMessage("CRU Tattoo", "Something Went Wrong");
            }
        }

        @Override
        public void failure(RetrofitError error) {
            messageUtil.showMessageDialog("Server Error !!");
            messageUtil.hideProgressDialog();
        }

    };
}
