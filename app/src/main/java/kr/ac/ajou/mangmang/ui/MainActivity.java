package kr.ac.ajou.mangmang.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import kr.ac.ajou.mangmang.R;


public class MainActivity extends AppCompatActivity {
    private CircleWaveProgressView mWaterWaveView;
    private Message message;
    private int progress=0;



    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int p=msg.what;
            mWaterWaveView.setmWaterLevel((float)p/100);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        mWaterWaveView = (CircleWaveProgressView)findViewById(R.id.circlewaveprogressView);
        mWaterWaveView.setmWaterLevel(0.0F);
        mWaterWaveView.startWave();

        new Thread(runnable).start();
    }

    Runnable runnable=new Runnable() {

        @Override
        public void run() {
            message=handler.obtainMessage();
            // TODO Auto-generated method stub
            try {
                for (int i = 1; i <= 100; i++) {
                    int x=progress++;
                    message.what=x;
                    handler.sendEmptyMessage(message.what);
                    mWaterWaveView.setWaveProgess(String.valueOf(progress)+"%");
                    if(progress==100){
                        mWaterWaveView.setWaveUpdate("Completed");

                    }else{
                        mWaterWaveView.setWaveUpdate("Loading...");
                    }
                    Thread.sleep(75);
                }
                //mWaterWaveView.stopWave();

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }



    };

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        mWaterWaveView.stopWave();
        mWaterWaveView=null;
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void ListListener(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(),GroupActivity.class);
        startActivity(intent);//list 받아오기
    }


}
