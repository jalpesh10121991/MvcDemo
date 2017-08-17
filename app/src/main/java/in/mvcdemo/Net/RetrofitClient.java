package in.mvcdemo.Net;


/**
 * Created by NAVAB.
 */
public class RetrofitClient {
    private final static String TAG = RetrofitClient.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static FinalWrapper<RetrofitClient> helperWrapper;
    private final RestClient mRestOkClient;


    private RetrofitClient() {
        // Rest client without basic authorization
        mRestOkClient = ServiceGenerator.createService(RestClient.class);
    }

    /**
     * @return
     */
    public static RetrofitClient getInstance() {
        FinalWrapper<RetrofitClient> wrapper = helperWrapper;

        if (wrapper == null) {
            synchronized (LOCK) {
                if (helperWrapper == null) {
                    helperWrapper = new FinalWrapper<>(new RetrofitClient());
                }
                wrapper = helperWrapper;
            }
        }
        return wrapper.value;
    }

    public RestClient getRestOkClient() {
        return mRestOkClient;
    }

}