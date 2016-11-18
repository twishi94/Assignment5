package com.example.cozoo.dory;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    Button mButton;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButton = (Button) findViewById(R.id.button_ok);
        textView =(TextView)findViewById(R.id.textView1);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ParseURL().execute("https://www.iiitd.ac.in/about");
            }
        });
    }

    private class ParseURL extends AsyncTask<String, Void, String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {

            pd=new ProgressDialog(MainActivity.this);
            pd.setTitle("Fetch WebPage");
            pd.setMessage("Fetching...Please wait");
            pd.show();
        }

        protected String doInBackground(String... urls)
        {
            int len = 50;
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.connect();

                InputStream inputStream= conn.getInputStream();

                // Convert the InputStream into a string
                result = readIt(inputStream, len);



            }  catch (IOException e) {
            }
            return result;
        }

        protected void onPostExecute(String result)  {
            pd.dismiss();
           textView.setText(result);
            
        }
        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }

    }


}
