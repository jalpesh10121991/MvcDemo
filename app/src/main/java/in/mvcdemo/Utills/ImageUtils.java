package in.mvcdemo.Utills;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;

/**
 * Created by riontech1 on 25/11/15.
 */
public class ImageUtils {
    private static final String TAG = ImageUtils.class.getSimpleName();
    public static DisplayImageOptions displayOptions;
    private static ImageLoader imageLoader;
    private int halfWidth;

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
        Bitmap bm = null;

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        options.inSampleSize = 2;
        bm = BitmapFactory.decodeResource(res, resId, options);

        return bm;
    }

    public static ImageLoader getImageLoader(Context context) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(getDisplayOption());
        if (imageLoader == null) {
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(builder.build());
        }
        return imageLoader;
    }

    @SuppressWarnings("deprecation")
    public static DisplayImageOptions getDisplayOption() {

        if (displayOptions == null)
            displayOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true)
                    .cacheInMemory(true)
                    .resetViewBeforeLoading()
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        return displayOptions;
    }


    public static String getRealPathFromURI(Uri uri, Context context) {
        Cursor cursor = context.getContentResolver().query(uri, null, null,
                null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static Bitmap getMutableBitmap(Bitmap bitmap) {
        try {

            Bitmap mutBmp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas can = new Canvas(mutBmp);
            can.drawBitmap(bitmap, 0, 0, new Paint());
            return mutBmp;
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDeviceWidth(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        try {
            ((Activity) context).getWindowManager().getDefaultDisplay()
                    .getMetrics(displaymetrics);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return displaymetrics.widthPixels;
    }

    private static int greatestCommonFactor(int width, int height) {
        return (height == 0) ? width : greatestCommonFactor(height, width % height);
    }

    public static int[] getResolutionRation(int width, int height) {
        int factor = greatestCommonFactor(width, height);
        int widthRatio = width / factor;
        int heightRatio = height / factor;
        return new int[]{widthRatio, heightRatio};
    }

    public static byte[] getImageBytes(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImageFromBytes(byte[] bytes) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

//    public static Bitmap mergeBitmap(Context context, byte[] bCaptured, byte[] bOverlay, RelativeLayout frame) {
//        Bitmap captured = cropBitmap(bCaptured, frame, context);
//        //captured = rotateBitmap(captured, 90);
//        Bitmap overlay = getImageFromBytes(bOverlay);
//
//
//        int width = captured.getWidth();
//        int height = captured.getHeight();
//        AppLog.d(TAG, "captured");
//        AppLog.d(TAG, "width::" + width + " height::" + height);
//        AppLog.d(TAG, "Overlay");
//        AppLog.d(TAG, "width::" + overlay.getWidth() + " height::" + overlay.getHeight());
//        Bitmap.Config config = captured.getConfig();
//        Bitmap newOverlay = Bitmap.createScaledBitmap(overlay, width, height, false);
//
//        //Bitmap mergedBmp = Bitmap.createBitmap(width, height, config);
//
//        Canvas canvas = new Canvas(captured);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//
//        canvas.drawBitmap(captured, 0, 0, paint);
//        paint.setAlpha(240);
//        canvas.drawBitmap(newOverlay, 0, 0, paint);
//
//        //captured.recycle();
//        overlay.recycle();
//        newOverlay.recycle();
//        Runtime.getRuntime().gc();
//
//        return captured;
//    }

//    public static Bitmap cropBitmap(byte[] bCaptured, RelativeLayout frame, Context context) {
//        Bitmap captured = getImageFromBytes(bCaptured);
//        captured = rotateBitmap(captured, 90);
//
//        int targetWidth = frame.getWidth();
//        int targetHeight = frame.getHeight();
//        int x = (int) frame.getX();
//        int y = (int) frame.getY();
//        AppLog.d(TAG, "Size");
//        AppLog.d(TAG, targetWidth + "w X " + targetHeight + "h");
//
//        AppLog.d(TAG, "X and Y Cordinator");
//        AppLog.d(TAG, "x::" + x + " y::" + y);
//
//        Bitmap resizedBitmap = Bitmap.createBitmap(captured, x, y,
//                targetWidth, targetHeight, new Matrix(), true);
//
//        AppLog.d(TAG, "Croped Bitmap size");
//        AppLog.d(TAG, resizedBitmap.getWidth() + "w" + resizedBitmap.getHeight() + "h");
//
//        captured.recycle();
//        Runtime.getRuntime().gc();
//
//        return resizedBitmap;
//    }

//    public static File getAppImageStoregeFile() {
//        File sdDir = Environment.getExternalStorageDirectory();
//        File pictureFileDir = new File(sdDir, "/" + Constants.FOLDER_NAME_SAVE_PHOTO);
//        pictureFileDir.mkdirs();
//        return new File(pictureFileDir, Long.toString(System.currentTimeMillis()) + ".jpg");
//    }

//    public static Bitmap rotateBitmap(Bitmap source, float angle) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(angle);
//
////        ByteArrayOutputStream stream = new ByteArrayOutputStream();
////        source.compress(Bitmap.CompressFormat.JPEG, 100, stream);
////        byte[] byteArray = stream.toByteArray();
////
////        BitmapFactory.Options options = new BitmapFactory.Options();
////        options.inJustDecodeBounds = false;
////        options.inSampleSize = 2;
//
////        Bitmap newSource = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
//        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
//    }

//    /**
//     * @param context
//     * @param bitmap
//     * @param callBack
//     */
//    public static void saveImage(final Context context, final Bitmap bitmap, final ImageSaveCallBack callBack, final boolean isDialog) {
//        new AsyncTask<Void, Integer, String>() {
//            boolean running;
//            ProgressDialog progressDialog;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                running = true;
//                if(isDialog) {
//                    progressDialog = ProgressDialog.show(context,
//                            "Saving image",
//                            "Please wait...");
//
//                    progressDialog.setCanceledOnTouchOutside(false);
//                }
//            }
//
//            @Override
//            protected void onProgressUpdate(Integer... values) {
//                super.onProgressUpdate(values);
//                if(isDialog)
//                    progressDialog.setMessage(String.valueOf(values[0]));
//            }
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                File storegeFile = ImageUtils.getAppImageStoregeFile();
//
//                try {
//                    FileOutputStream out = new FileOutputStream(storegeFile);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                    long totalLen = bitmap.getByteCount();
//                    long writted = storegeFile.length();
//                    int percentage = (int) ((writted * 100) / totalLen);
//                    AppLog.d(TAG, "total::" + totalLen);
//                    AppLog.d(TAG, "written::" + writted);
//                    AppLog.d(TAG, "Per::" + percentage);
////                    while (writted != totalLen){
////                        publishProgress(percentage);
////                    }
//
//                    out.flush();
//                    out.close();
//
//                    MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                            storegeFile.getAbsolutePath(), storegeFile.getName(),
//                            storegeFile.getName());
//                } catch (FileNotFoundException e) {
//                    Log.d("In Saving File", e + "");
//                } catch (IOException e) {
//                    Log.d("In Saving File", e + "");
//                }
//                return storegeFile.getAbsolutePath();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                if(isDialog)
//                    progressDialog.dismiss();
//                callBack.imageSaveDone(s);
//            }
//        }.execute();
//
//
//    }

//    public static byte[] compress(byte[] data) throws IOException {
//        Deflater deflater = new Deflater();
//        deflater.setInput(data);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        deflater.finish();
//        byte[] buffer = new byte[1024];
//        while (!deflater.finished()) {
//            int count = deflater.deflate(buffer); // returns the generated code... index
//            outputStream.write(buffer, 0, count);
//        }
//        outputStream.close();
//        byte[] output = outputStream.toByteArray();
//        AppLog.d(TAG, "Original: " + data.length / 1024 + " Kb");
//        AppLog.d(TAG, "Compressed: " + output.length / 1024 + " Kb");
//        return output;
//    }

//    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
//        Inflater inflater = new Inflater();
//        inflater.setInput(data);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        byte[] buffer = new byte[1024];
//        while (!inflater.finished()) {
//            int count = inflater.inflate(buffer);
//            outputStream.write(buffer, 0, count);
//        }
//        outputStream.close();
//        byte[] output = outputStream.toByteArray();
//        AppLog.d(TAG, "Original: " + data.length);
//        AppLog.d(TAG, "Compressed: " + output.length);
//        return output;
//    }

//    public static int[] getDeviceSize(Activity activity) {
//        Display display = activity.getWindowManager().getDefaultDisplay();
//        int realWidth;
//        int realHeight;
//
//        if (Build.VERSION.SDK_INT >= 17) {
//            //new pleasant way to get real metrics
//            DisplayMetrics realMetrics = new DisplayMetrics();
//            display.getRealMetrics(realMetrics);
//            realWidth = realMetrics.widthPixels;
//            realHeight = realMetrics.heightPixels;
//
//        } else if (Build.VERSION.SDK_INT >= 14) {
//            //reflection for this weird in-between time
//            try {
//                Method mGetRawH = Display.class.getMethod("getRawHeight");
//                Method mGetRawW = Display.class.getMethod("getRawWidth");
//                realWidth = (Integer) mGetRawW.invoke(display);
//                realHeight = (Integer) mGetRawH.invoke(display);
//            } catch (Exception e) {
//                //this may not be 100% accurate, but it's all we've got
//                realWidth = display.getWidth();
//                realHeight = display.getHeight();
//            }
//
//        } else if (Build.VERSION.SDK_INT >= 8) {
//            //This should be close, as lower API devices should not have window navigation bars
//            Point size = new Point();
//            display.getSize(size);
//            realWidth = size.x;
//            realHeight = size.y;
//        } else {
//            //This should be close, as lower API devices should not have window navigation bars
//            realWidth = display.getWidth();
//            realHeight = display.getHeight();
//        }
//
//
//        return new int[]{realWidth, realHeight};
//    }

//    public static int getNavigationBarHeight(Context context){
//        Resources resources = context.getResources();
//        try {
//            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//            if (resourceId > 0) {
//                return resources.getDimensionPixelSize(resourceId);
//            }
//            return 0;
//        } catch (Exception e){
//            return 0;
//        }
//    }
}
