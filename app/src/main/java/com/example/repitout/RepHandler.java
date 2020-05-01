package com.example.repitout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.util.Config.LOGD;

public class RepHandler extends AppCompatActivity {
    public TextView excTV, totalRep, receivedReps, repsRecNum;
    public EditText setsET, repsET;
    Button repTotal, sendReps , saveReps;
    protected Handler myHandler;
    String r, q, fromWatch, day,data , newDay , sendback;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Workout");
    DatabaseReference db, dbhist;
    Map<String, String> reps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_handler);

        excTV = findViewById(R.id.excTV);
        setsET = findViewById(R.id.etSetsNumber);
        repsET = findViewById(R.id.etRepsNumber);
        totalRep = findViewById(R.id.totalReps);
        receivedReps = findViewById(R.id.RepsRecievedTV);
        repTotal = findViewById(R.id.countReps_Btn);
        sendReps = findViewById(R.id.send_Btn);
        repsRecNum = findViewById(R.id.RepsRecievedNum);
        saveReps = findViewById(R.id.saveBtn);


        //get exercises name
        Intent intent = getIntent();
        q = intent.getStringExtra("Exercise");
        excTV.setText(q);




        //retrieve day from sharedPreferences in recordworkout class
        SharedPreferences sharedPreferences = this.getSharedPreferences("DayForDB",Context.MODE_PRIVATE);
        day = sharedPreferences.getString("DayW","");



        repTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setsval = setsET.getText().toString();
                int setv = Integer.parseInt(setsval);
                String repsval = repsET.getText().toString();
                int repsv = Integer.parseInt(repsval);
                int total = (setv * repsv);
                r = String.valueOf(total);
                totalRep.setText(r);

            }
        });



        //Create a message handler//

        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                messageText(stuff.getString("messageText"));
                return true;
            }
        });

        //Register to receive local broadcasts, which we'll be creating in the next step//

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

        saveReps.setOnClickListener(v -> {
            if (receivedReps.getText().equals("0")){
                saveReps.setClickable(false);
                Toast.makeText(this,"You have not recorded any reps",Toast.LENGTH_SHORT).show();
            }else {
                saveReps();
                String repss = receivedReps.getText().toString();
                Intent intent1 = new Intent(RepHandler.this, RecordWorkout.class);
                intent1.putExtra("repetitions", repss);
                intent1.putExtra("DayName", day);
                startActivity(intent1);
            }
        });






    }


    private void messageText(String newmess) {
        repsRecNum.setText(newmess);
        fromWatch = newmess;

    }


    public class Receiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
           Bundle bundle = intent.getExtras();
            data = bundle.getString("reps");
            newDay = intent.getStringExtra("Day");
            receivedReps.setText(data);
        }
    }

    public void talkClick(View v) {

//Sending a message can block the main UI thread, so use a new thread//
        new NewThread("/my_path", ("Exercise : " + q + "\n" + " Reps : " + r)).start();
        Toast.makeText(this,"Goal reps sent to watch for " + q,Toast.LENGTH_SHORT).show();
    }

    //use a bundle to encapsulate the message
    public void sendMessage(String message) {
        Bundle bundle = new Bundle();
        bundle.putString("Reps", message);
        Message msg = myHandler.obtainMessage();
        msg.setData(bundle);

    }

    class NewThread extends Thread {
        String path, message, exc;

        //create constructor to send information to data layer

        NewThread(String mpath, String myMessage) {
            path = mpath;
            message = myMessage;

        }

        @Override
        public void run() {
            //need to get connected devices/nodes
            Task<List<Node>> wearableList = Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();

            try {
                List<Node> nodes = Tasks.await(wearableList);
                for (Node node : nodes) {
                    //sending the message
                    Task<Integer> sendMessageTask =
                            Wearable.getMessageClient(RepHandler.this).sendMessage(node.getId(), path, message.getBytes());
                    /*Task<Integer> sendExcTask =
                            Wearable.getMessageClient(RepHandler.this).sendMessage(node.getId(), path, exc.getBytes());*/
                    try {
                        //block on a task and get the result synchronously
                        Integer result = Tasks.await(sendMessageTask);
                       /* Integer result1 = Tasks.await(sendExcTask);*/
                        sendMessage("Reps sent to watch");
                        //catch failure if task fails
                    } catch (ExecutionException exception) {

                    } catch (InterruptedException exception) {

                    }
                }
            } catch (InterruptedException exception) {

            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveReps() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        dbhist = databaseReference.child(userID).child("Routine_History").child("Exercises");

        Exercises_helper exercises_helper = new Exercises_helper(q, data);
        db = databaseReference.child(userID).child("Routines").child(day).child("Exercises");
        DatabaseReference dbr = db.child(q);
        dbr.setValue(exercises_helper);
        dbhist.push().setValue(exercises_helper);
        Toast.makeText(this, "Your Reps have been recorded", Toast.LENGTH_LONG).show();
    }
}