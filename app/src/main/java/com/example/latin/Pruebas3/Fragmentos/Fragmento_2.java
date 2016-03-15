package com.example.latin.Pruebas3.Fragmentos;

import android.app.Fragment;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.latin.Pruebas3.R;
import com.example.latin.Pruebas3.Utils.UtilsMessage;
import com.example.latin.Pruebas3.logic.Message;
import com.example.latin.Pruebas3.service.MessageListAdapter;
import com.example.latin.Pruebas3.service.Upstream;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.ArrayList;
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
                sendMessageToServer(inputMsg.getText()
                        .toString());
                // Clearing the input filed once message was sent
                inputMsg.setText("");
            }
        });

        listMessages = new ArrayList<Message>();

        adapter = new MessageListAdapter(getActivity().getApplicationContext(), listMessages);
        listViewMessages.setAdapter(adapter);



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

        upstream.doGcmSendUpstreamMessage(getActivity(),"59156844246",message,"key",message);

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
}
