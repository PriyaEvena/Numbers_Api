package com.example.sunny.numbersapi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class QuestFacts extends AppCompatActivity {
    private Button triviaQuestButton;
    private Button mathQuestButton;
    private Button dateQuestButton;
    private Button yearQuestButton;
    private Button submitButton;
    private EditText triviaInput;
    private EditText mathInput;
    private EditText dateInput;
    private EditText yearInput;
    private TextView questHeading;
    private TextView questContentnew;
    private int reqNumInfo = 0;
    private int flag = 0;
    private String NUMAPI_REQUEST_URL = "http://numbersapi.com/";
    private static final String LOG_TAG = RandomFacts.class.getSimpleName();
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_facts);

        //Intialization
        triviaQuestButton = (Button) findViewById(R.id.Trivia_quest);
        mathQuestButton = (Button) findViewById(R.id.Math_quest);
        dateQuestButton = (Button) findViewById(R.id.Date_quest);
        yearQuestButton = (Button) findViewById(R.id.Year_quest);
        submitButton = (Button) findViewById(R.id.submitQuest);

        triviaInput = (EditText) findViewById(R.id.triviaQuestContent);
        mathInput = (EditText) findViewById(R.id.mathQuestContent);
        dateInput = (EditText) findViewById(R.id.dateQuestContent);
        yearInput = (EditText) findViewById(R.id.yearQuestContent);

        questContentnew = (TextView) findViewById(R.id.questContent);
        questHeading = (TextView) findViewById(R.id.questHeading);

        //Setting onClickListeners
        triviaQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(triviaInput.getVisibility()==View.VISIBLE){
                    triviaInput.setVisibility(View.GONE);
                    reqNumInfo=0;
                }
                else{
                    mathInput.setVisibility(View.GONE);
                    dateInput.setVisibility(View.GONE);
                    yearInput.setVisibility(View.GONE);
                    triviaInput.setVisibility(View.VISIBLE);
                    reqNumInfo = 1;
                }
            }
        });
        mathQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mathInput.getVisibility()==View.VISIBLE){
                    mathInput.setVisibility(View.GONE);
                    reqNumInfo=0;
                }
                else{
                    dateInput.setVisibility(View.GONE);
                    yearInput.setVisibility(View.GONE);
                    triviaInput.setVisibility(View.GONE);
                    mathInput.setVisibility(View.VISIBLE);
                    reqNumInfo = 2;
                }

            }
        });
        dateQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateInput.getVisibility()==View.VISIBLE){
                    dateInput.setVisibility(View.GONE);
                    reqNumInfo=0;
                }
                else{
                    yearInput.setVisibility(View.GONE);
                    triviaInput.setVisibility(View.GONE);
                    mathInput.setVisibility(View.GONE);
                    dateInput.setVisibility(View.VISIBLE);
                    reqNumInfo = 3;
                }

            }
        });
        yearQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yearInput.getVisibility()==View.VISIBLE){
                    yearInput.setVisibility(View.GONE);
                    reqNumInfo=0;
                }
                else{
                    triviaInput.setVisibility(View.GONE);
                    mathInput.setVisibility(View.GONE);
                    dateInput.setVisibility(View.GONE);
                    yearInput.setVisibility(View.VISIBLE);
                    reqNumInfo = 4;
                }

            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reqNumInfo == 0) {
                    Toast.makeText(QuestFacts.this, "Please enter value in any category", Toast.LENGTH_SHORT).show();
                } else {
                    String categoryValue;
                    switch (reqNumInfo) {
                        case 1:
                            categoryValue = triviaInput.getText().toString();
                            if (categoryValue.trim().equals("") || categoryValue == null) {
                                flag = 1;
                                Toast.makeText(QuestFacts.this, "Please enter value in trivia", Toast.LENGTH_SHORT).show();
                            } else {
                                flag = 0;
                                NUMAPI_REQUEST_URL = "http://numbersapi.com/" + categoryValue + "/trivia?json";
                            }

                            break;
                        case 2:
                            categoryValue = mathInput.getText().toString();
                            if (categoryValue.trim().equals("") || categoryValue == null) {
                                flag = 1;
                                Toast.makeText(QuestFacts.this, "Please enter value in math", Toast.LENGTH_SHORT).show();
                            } else {
                                flag = 0;
                                NUMAPI_REQUEST_URL = "http://numbersapi.com/" + categoryValue + "/math?json";
                            }
                            break;
                        case 3:
                            categoryValue = dateInput.getText().toString();
                            if (categoryValue.trim().equals("") || categoryValue == null) {
                                flag = 1;
                                Toast.makeText(QuestFacts.this, "Please enter value in date", Toast.LENGTH_SHORT).show();
                            } else {
                                if(categoryValue.length()>5){
                                    Toast.makeText(QuestFacts.this, "Please enter date in MM/DD format", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String a[]=categoryValue.split("/");
                                    if(a.length!=2)
                                        Toast.makeText(QuestFacts.this, "Please enter date in MM/DD format", Toast.LENGTH_SHORT).show();
                                    else{
                                        try{
                                            int m=Integer.parseInt(a[0]);
                                            int d=Integer.parseInt(a[1]);
                                            flag = 0;
                                            NUMAPI_REQUEST_URL = "http://numbersapi.com/" + categoryValue + "/date?json";
                                        }
                                        catch (Exception e){
                                            Toast.makeText(QuestFacts.this, "Please enter date in MM/DD format", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }

                            }
                            break;
                        case 4:
                            categoryValue = yearInput.getText().toString();
                            if (categoryValue.trim().equals("") || categoryValue == null) {
                                flag = 1;
                                Toast.makeText(QuestFacts.this, "Please enter value in year", Toast.LENGTH_SHORT).show();
                            } else {
                                if (categoryValue.length() > 4) {
                                    Toast.makeText(QuestFacts.this, "Please enter valid year", Toast.LENGTH_SHORT).show();
                                } else {
                                    flag = 0;
                                    NUMAPI_REQUEST_URL = "http://numbersapi.com/" + categoryValue + "/year?json";
                                }
                            }
                            break;
                    }
                    if (flag == 0) {
                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        numapiAsyncTask task = new numapiAsyncTask();
                        task.execute();
                    }
                }
            }
        });
    }

    private void updateUI(String str) {
        switch (reqNumInfo) {
            case 1:
                questHeading.setText("Trivia");
                questHeading.setVisibility(View.VISIBLE);
                break;
            case 2:
                questHeading.setText("Math");
                questHeading.setVisibility(View.VISIBLE);
                break;
            case 3:
                questHeading.setText("Date");
                questHeading.setVisibility(View.VISIBLE);
                break;
            case 4:
                questHeading.setText("Year");
                questHeading.setVisibility(View.VISIBLE);
                break;
        }
        questContentnew.setText(str);
        questContentnew.setVisibility(View.VISIBLE);
    }

    //Async task for background request of data from numberApi
    private class numapiAsyncTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(QuestFacts.this);
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
                Log.e("exception in ---", "do in background");
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            Log.e("printed received data", jsonResponse);
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
                Log.e("url in make is", url + "");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                Log.e("success ", "Connected");
                inputStream = urlConnection.getInputStream();
                Log.e("success ", "ipstream");
                jsonResponse = readFromStream(inputStream);
                Log.e("lets see jsonResponse", jsonResponse);
            } catch (IOException e) {
                // TODO: Handle the exception
                Log.e("in makeHttpRequest", e + "");
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
