package com.example.phonesekur.ui.notifications;

import androidx.fragment.app.Fragment;

import com.example.phonesekur.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}