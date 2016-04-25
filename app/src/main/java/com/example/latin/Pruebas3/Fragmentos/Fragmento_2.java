package com.example.latin.Pruebas3.Fragmentos;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.latin.Pruebas3.R;
import com.example.latin.Pruebas3.Utils.GcmAction;
import com.example.latin.Pruebas3.Utils.UtilsMessage;
import com.example.latin.Pruebas3.logic.Message;
import com.example.latin.Pruebas3.service.GcmService;
import com.example.latin.Pruebas3.service.LoggingService;
import com.example.latin.Pruebas3.service.MessageListAdapter;
import com.example.latin.Pruebas3.service.Upstream;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by Latin on 23/02/2016.
 */
public class Fragmento_2 extends Fragment {

    private Button btnSend;
    private EditText inputMsg;

    private MessageListAdapter adapter;
    private List<Message> listMessages;
    private ListView listViewMessages;
    private View myFragmentView;
    private Upstream upstream = new Upstream();


    private UtilsMessage utils;

    // Client name
    private String name = null;

    // JSON flags to identify the kind of JSON response
    private static final String TAG_SELF = "self", TAG_NEW = "new",
            TAG_MESSAGE = "message", TAG_EXIT = "exit";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragmento_2, container, false);

        btnSend = (Button) myFragmentView.findViewById(R.id.btnSend);
        inputMsg = (EditText)  myFragmentView.findViewById(R.id.inputMsg);
        listViewMessages = (ListView)  myFragmentView.findViewById(R.id.list_view_messages);

        utils = new UtilsMessage(getActivity().getApplicationContext());

        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Sending message to web socket server
//                sendMessageToServer(utils.getSendMessageJSON(inputMsg.getText()
//                        .toString()));
                sendMessageToServer(utils.getSendMessageJSON(inputMsg.getText()
                        .toString()));
                // Clearing the input filed once message was sent
                parseMessage(inputMsg.getText().toString(),"Diego",Boolean.TRUE);
                inputMsg.setText("");

            }
        });

        listMessages = new ArrayList<Message>();

        adapter = new MessageListAdapter(getActivity().getApplicationContext(), listMessages);
        listViewMessages.setAdapter(adapter);



//        String test = getArguments().getString("mensaje");
//        String user = getArguments().getString("user");
//        Boolean response = new Boolean(getArguments().getString("response"));
//        parseMessage(test,user,false);
        return myFragmentView;

    }



    //Metodo para enviar mensajes
    private void sendMessageToServer(String message) {
//        if (client != null && client.isConnected()) {
//            client.send(message);
//        }
        Context context = getActivity().getApplicationContext();
        Bundle extras = new Bundle();
        System.out.println("entro");
        try {
            System.out.println("entro1");
            String token = InstanceID.getInstance(context.getApplicationContext())
                    .getToken("59156844246", "GCM", extras);
            System.out.println("token:"+token);
        }catch(IOException e){

        }

        upstream.doGcmSendUpstreamMessage(getActivity(), "59156844246", "Android", "mensaje", message);

    }

    private void parseMessage(String message,String to,Boolean response){

        if(message!=null){
            Message m = new Message(to,message,response);
            appendMessage(m);
        }


    }

    private void appendMessage(final Message m) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                listMessages.add(m);
                adapter.notifyDataSetChanged();

                // Playing device's notification
                playBeep();
            }
        });
    }

    public void playBeep() {

        try {
            Uri notification = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(),
                    notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        // Unregister since the activity is paused.
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                mMessageReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mMessageReceiver, new IntentFilter("custom-event-name"));
        super.onResume();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent

            //        String test = getArguments().getString("mensaje");
//        String user = getArguments().getString("user");

            parseMessage(intent.getStringExtra("mensaje"),intent.getStringExtra("user"),false);

            System.out.println("entro al broadcast fragmento");
            String message = intent.getStringExtra("message");
            System.out.println("message: "+message);
        }
    };


}
