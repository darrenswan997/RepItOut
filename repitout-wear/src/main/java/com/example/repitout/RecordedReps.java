package com.example.repitout;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class RecordedReps extends WearableActivity {

    private TextView mTextView, setRepsT;
    Button saveSRBtn;
    counter counterclass = new counter();
    Map<String,String> setsReps;
    String list , reps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_reps);

        mTextView = (TextView) findViewById(R.id.text);
        setRepsT = findViewById(R.id.setandrepsTV);

        Intent i = getIntent();
        list = i.getStringExtra("reps");
        /*StringBuilder sb = new StringBuilder();
        for (String s : list){
            Integer setNum = 1;
            sb.append("set " + setNum++ + ":" + s + "\n");*/
        setRepsT.setText(list);
        //*setsReps = new HashMap<>();
        // setsReps.put("Set",s);*//
        //}
         reps = list;
        // Enables Always-on
        setAmbientEnabled();

        saveSRBtn = findViewById(R.id.saveSetsReps);
        saveSRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataPath = "/my_path";
                new SendMessage(dataPath,reps).start();
                Intent intent = new Intent(RecordedReps.this,MainActivity.class);
                startActivity(intent);
            }
        });




    }

    class SendMessage extends Thread {
        String path;
        String message;

        //Constructor for sending information to the Data Layer
        SendMessage(String p, String m) {
            path = p;
            message = m;
        }

        public void run() {

            //Retrieve the connected devices//
            Task<List<Node>> nodeListTask =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try {

                //Block on a task and get the result synchronously

                List<Node> nodes = Tasks.await(nodeListTask);
                for (Node node : nodes) {

                    //Send the message
                    Task<Integer> sendMessageTask =
                            Wearable.getMessageClient(RecordedReps.this).sendMessage(node.getId(), path, message.getBytes());

                    try {

                        Integer result = Tasks.await(sendMessageTask);

                    } catch (ExecutionException exception) {

                    } catch (InterruptedException exception) {

                    }
                }

            } catch (ExecutionException exception) {

            } catch (InterruptedException exception) {

            }
        }
    }


}
