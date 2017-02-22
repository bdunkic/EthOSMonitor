package com.bdunkic.ethosmonitor;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Bojan on 1.2.2017..
 */
public class RigListActivity extends AppCompatActivity {

  private TextView condition;
    private TextView miner;
    private TextView ip;
    private TextView gpu_temps;
    private TextView mem_info;
    private TextView miner_hashes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rig_info);

        initWidgets();

        onHandleIntent();






    }

    private void onHandleIntent() {

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String rig_condition = bundle.getString(RecieveingActivity.CONDITION_TAG);
            String rig_miner = bundle.getString(RecieveingActivity.MINER_TAG);
            String rig_gpu_temps = bundle.getString(RecieveingActivity.GPUTEMP_TAG);
            String rig_miner_hashes = bundle.getString(RecieveingActivity.MINERHASH_TAG);
            String rig_ip = bundle.getString(RecieveingActivity.IP_TAG);
            String rig_mem_info=bundle.getString(RecieveingActivity.MEMINFO_TAG);





            condition.setText(String.format("Condition: %s", rig_condition));

            assert rig_condition != null;
            if (rig_condition.equals("mining")){

                condition.setTextColor(Color.GREEN);
            }else{

                condition.setTextColor(Color.RED);
            }
            miner.setText(String.format("Miner: %s", rig_miner));


            gpu_temps.setText(String.format("GPU Temperatures: %s", rig_gpu_temps));
            ip.setText(String.format("IP address: %s", rig_ip));
            miner_hashes.setText(String.format("Miner hashes: %s", rig_miner_hashes));
            mem_info.setText(String.format("%s%s", getString(R.string.rig_info), rig_mem_info));



        }





    }

    private void initWidgets() {

        condition= (TextView)findViewById(R.id.condition);
        miner= (TextView)findViewById(R.id.miner);
        ip= (TextView)findViewById(R.id.ip);
        miner_hashes= (TextView)findViewById(R.id.miner_hashes);
        mem_info= (TextView)findViewById(R.id.mem_info);
        gpu_temps= (TextView)findViewById(R.id.gputemp);


    }
}
