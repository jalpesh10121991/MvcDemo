package in.mvcdemo.Net;

import android.util.Base64;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import in.mvcdemo.Utills.Constant;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class ServiceGenerator {
    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    /**
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClass) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constant.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient));

        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }

    /**
     * @param serviceClass
     * @param username
     * @param password
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        // set endpoint url and use OkHTTP as HTTP client
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constant.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient));

        if (username != null && password != null) {
            // concatenate username and password with colon for authentication
            final String credentials = username + ":" + password;

            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    // create Base64 encodet string
                    String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", string);
                }
            });
        }

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}
