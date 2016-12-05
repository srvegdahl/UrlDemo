package edu.up.cs301.urldemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class URLActivity extends Activity {

    private final static String urlString = "http://faculty.up.edu/vegdahl/index.htm";
	private String message = "No message.";
	
	TextView theTextView;
	
	Handler mainHandler;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        
        mainHandler = new Handler();
        
       Thread t = new Thread(new NetworkRunner());
       t.start();
        
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.url, menu);
//        return true;
//    }
    
    private class NetworkRunner implements Runnable {
    	public void run() {
            try {
            	URL url = new URL(urlString);
            	InputStream inStream = url.openStream();
            	BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
            	message = reader.readLine();
            	reader.close();
            } catch (Exception ex) {
            	message = "error occurred "+ex;
            }
            
            Runnable myRunner = new Runnable(){
            	public void run(){
                	theTextView = (TextView)findViewById(R.id.theTextView);
                	theTextView.setText(message);
            	}
            };
            mainHandler.post(myRunner);
    	}
    }
}

