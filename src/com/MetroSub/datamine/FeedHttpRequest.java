package com.MetroSub.datamine;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Kush Patel
 * Date: 7/2/13
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedHttpRequest {

    private String TAG = "FeedHttpRequest";

    private final int DEFAULT_CONNECTION_TIMOUT = 30 * 1000;
    private final int DEFAULT_SOCKET_TIMEOUT = 90 * 1000;

    private final String url = "http://datamine.mta.info/mta_esi.php?key=07065023a9a71c6809525c31f8cb2a1e";

    public String getRequestData() {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpParams httpParams = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, DEFAULT_CONNECTION_TIMOUT);
        HttpConnectionParams.setSoTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);

        HttpRequestBase request = new HttpGet(url);

        String responseString = null;

        try {
            HttpResponse response = httpClient.execute(request);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                responseString = EntityUtils.toString(response.getEntity());

                if (statusCode < 200 || statusCode >= 300) {
                    Log.e(TAG,"Server error code " + statusCode);
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"IOException: " + e.getMessage());
        }

        return responseString;
    }

}
