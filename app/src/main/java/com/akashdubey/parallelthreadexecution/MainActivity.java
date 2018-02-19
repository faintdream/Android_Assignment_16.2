package com.akashdubey.parallelthreadexecution;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button start;
    TextView result1,result2;
    ProgressBar pb1, pb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.startBtn);
        result1 = (TextView) findViewById(R.id.result1TV);
        result2 = (TextView) findViewById(R.id.result2TV);
        pb1 = (ProgressBar) findViewById(R.id.progress1);
        pb2 = (ProgressBar) findViewById(R.id.progress2);
        final String[] urls = {"https://github.com/faintdream/Android_Assignment_16.1/blob/master/gradle.properties",
                "https://github.com/faintdream/Android_Assignment_15.2/blob/master/.gitignore",
                "https://www.google.com"};
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadWebContent1 downloadWebContent = new DownloadWebContent1();

                downloadWebContent.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urls[0]);


                DownloadWebContent2 downloadWebContent2 = new DownloadWebContent2();
                downloadWebContent2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urls[1]);
            }


        });
    }

    public class DownloadWebContent1 extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            result1.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb1.setProgress(values[0]);

        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;
            request = new Request.Builder()
                    .url(strings[0])
                    .build();

            try {
                for (int k=0;k<10;k++){
                    Thread.sleep(100);
                    publishProgress(k * 10);
                }
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
//                        return response.body().string();
                    return "Success File: " + strings[0];
                }else{
                    return "Download Failed";
                }
            } catch (IOException e) {
                Log.i("sometag", "IO Exception occured");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("sometag", "File :" + strings[0]);
            return "Success File: " + strings[0];
        }
    }

    public class DownloadWebContent2 extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            result2.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb2.setProgress(values[0]);

        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;
            request = new Request.Builder()
                    .url(strings[0])
                    .build();

            try {


               for (int k=0;k<10;k++){
                   Thread.sleep(100);
                   publishProgress(k * 10);
               }

                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
//                        return response.body().string();
                    return "Success File: " + strings[0];
                }
                else{
                    return "Download Failed";
                }
            } catch (IOException e) {
                Log.i("sometag", "IO Exception occured");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("sometag", "File :" + strings[0]);
            return "Success File : " + strings[0];
        }
    }
}
