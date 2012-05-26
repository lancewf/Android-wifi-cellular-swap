package com.finfrock.phoneswap;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PhoneSwapActivity extends Activity {
    private Tools tools = new Tools(this);
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tools = new Tools(this);
        
        Settings.System.putString(
                getApplicationContext().getContentResolver(),
                Settings.System.AIRPLANE_MODE_RADIOS,
                Settings.System.RADIO_CELL);
        status();
        
        tools.testMode(this.getApplicationContext());

        Button statusButton = (Button)findViewById(R.id.status);
        statusButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                status();
            }
        });
        
        Button atHomeButton = (Button)findViewById(R.id.atHome);
        atHomeButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                atHome();
                status();
            }
        });
        
        Button awayButton = (Button)findViewById(R.id.away);
        awayButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                away();
                status();
            }
        });
        
        Button testButton = (Button)findViewById(R.id.test);
        testButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                tools.testMode(PhoneSwapActivity.this.getApplicationContext());
                status();
            }
        });
        
        Button addCurrentWifiButton = (Button)findViewById(R.id.addCurrentWifi);
        addCurrentWifiButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                tools.addCurrentWifiConnection(PhoneSwapActivity.this.getApplicationContext());
                status();
            }
        });
        status();
    }
    
    private void away(){
        tools.away(getApplicationContext());
    }
    
    private void atHome(){
        tools.atHome(getApplicationContext());
    }
    
    private void status(){
        String status = tools.getStatus(getApplicationContext());
        
        TextView textView = (TextView)findViewById(R.id.output);
        
        textView.setText(status);
    }
}