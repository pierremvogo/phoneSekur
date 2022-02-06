package com.example.phonesekur.ui.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.phonesekur.Infos;
import com.example.phonesekur.MainActivity;
import com.example.phonesekur.R;
import com.example.phonesekur.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationsFragment extends Fragment {
    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    ListView simpleList;

    ArrayList<String> listItems = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Infos.active = 1;
        listItems.add(Infos.phoneNumberBlock);
        listItems.add(Infos.smsToBlock);
        View rootView = inflater.inflate(R.layout.fragment_notifications,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        simpleList = (ListView) getView().findViewById(R.id.list_notification);
        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(getContext(), R.layout.activity_listview, R.id.textview1,listItems);
        simpleList.setAdapter((arrayAdapter));
        arrayAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}