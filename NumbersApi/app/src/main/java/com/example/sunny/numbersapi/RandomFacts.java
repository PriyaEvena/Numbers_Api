package com.example.sunny.numbersapi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class RandomFacts extends AppCompatActivity {
    private Button triviaRandomButton;
    private Button mathRandomButton;
    private Button dateRandomButton;
    private Button yearRandomButton;
    private int reqNumInfo;
    private String NUMAPI_REQUEST_URL = "http://numbersapi.com/random/";
    private static final String LOG_TAG = RandomFacts.class.getSimpleName();
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_facts);

        //Intialization
        triviaRandomButton = (Button) findViewById(R.id.Trivia_random);
        mathRandomButton = (Button) findViewById(R.id.Math_random);
        dateRandomButton = (Button) findViewById(R.id.Date_random);
        yearRandomButton = (Button) findViewById(R.id.Year_random);

        //Setting onCLicklisteners
        triviaRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NUMAPI_REQUEST_URL = "http://numbersapi.com/random/" + "trivia?json";
                reqNumInfo = 1;
                numapiAsyncTask task = new numapiAsyncTask();
                task.execute();
            }
        });
        mathRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NUMAPI_REQUEST_URL = "http://numbersapi.com/random/" + "math?json";
                reqNumInfo = 2;
                numapiAsyncTask task = new numapiAsyncTask();
                task.execute();
            }
        });
        dateRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NUMAPI_REQUEST_URL = "http://numbersapi.com/random/" + "date?json";
                reqNumInfo = 3;
                numapiAsyncTask task = new numapiAsyncTask();
                task.execute();
            }
        });
        yearRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NUMAPI_REQUEST_URL = "http://numbersapi.com/random/" + "year?json";
                reqNumInfo = 4;
                numapiAsyncTask task = new numapiAsyncTask();
                task.execute();
            }
        });

    }

    //Updatting UI with number info
    /*
    1.trivia
    2.math
    3.date
    4.year
     */
    private void updateUI(String numinfo) {
        TextView triviaDisplayInfo = (TextView) findViewById(R.id.triviaRandomContect);
        TextView mathDisplayInfo = (TextView) findViewById(R.id.mathRandomContect);
        TextView dateDisplayInfo = (TextView) findViewById(R.id.dateRandomContect);
        TextView yearDisplayInfo = (TextView) findViewById(R.id.yearRandomContect);
        if (reqNumInfo != 0)
            switch (reqNumInfo) {
                case 1:
                    mathDisplayInfo.setVisibility(View.GONE);
                    dateDisplayInfo.setVisibility(View.GONE);
                    yearDisplayInfo.setVisibility(View.GONE);
                    triviaDisplayInfo.setText(numinfo);
                    triviaDisplayInfo.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    triviaDisplayInfo.setVisibility(View.GONE);
                    dateDisplayInfo.setVisibility(View.GONE);
                    yearDisplayInfo.setVisibility(View.GONE);
                    mathDisplayInfo.setText(numinfo);
                    mathDisplayInfo.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    mathDisplayInfo.setVisibility(View.GONE);
                    triviaDisplayInfo.setVisibility(View.GONE);
                    yearDisplayInfo.setVisibility(View.GONE);
                    dateDisplayInfo.setText(numinfo);
                    dateDisplayInfo.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    mathDisplayInfo.setVisibility(View.GONE);
                    dateDisplayInfo.setVisibility(View.GONE);
                    triviaDisplayInfo.setVisibility(View.GONE);
                    yearDisplayInfo.setText(numinfo);
                    yearDisplayInfo.setVisibility(View.VISIBLE);
                    break;
            }
    }

    //Async task for background request of data from numberApi
    private class numapiAsyncTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(RandomFacts.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(URL... params) {
            // Create URL object
            URL url = createUrl(NUMAPI_REQUEST_URL);
            Log.e("url created is--", url + "");
            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
                Log.e("exception in ---","do in background");
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            Log.e("printed received data",jsonResponse);
            String infoNum = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return infoNum;

        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (s != null && !s.equals("")) {
                updateUI(s);
            } else
                Log.e("in postexecute", "string is null");
        }

        //To Create URL
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        //To Make HttpRequest
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                Log.e("url in make is",url+"");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                Log.e("success ","Connected");
                inputStream = urlConnection.getInputStream();
                Log.e("success ","ipstream");
                jsonResponse = readFromStream(inputStream);
                Log.e("lets see jsonResponse", jsonResponse);
            } catch (IOException e) {
                // TODO: Handle the exception
                Log.e("in makeHttpRequest",e+"");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
                return jsonResponse;
            }

        }

        //To read from Stream
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                Log.e("trail", line);
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        //To Extract JSON from String
        private String extractFeatureFromJson(String numApiJSON) {
            try {
                Log.e("extract input string", numApiJSON);

                JSONObject baseJsonResponse = new JSONObject(numApiJSON);
                if (baseJsonResponse != null) {
                    String info = baseJsonResponse.getString("text");
                    return info;
                } else
                    Log.e("in extract ", "Object is null");

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }
            return null;
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
