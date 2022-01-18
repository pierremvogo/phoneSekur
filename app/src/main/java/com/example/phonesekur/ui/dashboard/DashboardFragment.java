package com.example.phonesekur.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.phonesekur.Infos;
import com.example.phonesekur.R;
import com.example.phonesekur.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    public Switch switch_blockCall;
    public Switch switch_blockSms;
    public Switch switch_allowAlert;
    public  TextView textView_cong;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        switch_blockCall = (Switch) root.findViewById(R.id.switch_block_call);
        switch_blockSms = (Switch) root.findViewById(R.id.switch_block_sms);
        switch_allowAlert = (Switch) root.findViewById(R.id.switch_activer_alerts);
        textView_cong = root.findViewById(R.id.textView_Configuration);





        try {

            if(Infos.blockCall == 1){
                switch_blockCall.setChecked(true);
            }
            else{
                switch_blockCall.setChecked(false);
            }

            if(Infos.BlockSms == 1){
                switch_blockSms.setChecked(true);
            }
            else{
                switch_blockSms.setChecked(false);
            }

            if(Infos.allowAlert == 1){
                switch_allowAlert.setChecked(true);
            }
            else{
                switch_allowAlert.setChecked(false);
            }


        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }





        try{
            switch_blockSms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(Infos.BlockSms == 0){
                        Infos.BlockSms = 1;
                        Toast.makeText(getContext(), "Filtrage SMS activé sur votre mobile", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Infos.BlockSms = 0;
                        Toast.makeText(getContext(), "Attention!!! Filtrage SMS désactivé!!", Toast.LENGTH_LONG).show();
                    }
                }
            });

            switch_blockCall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(Infos.blockCall == 0){
                        Infos.blockCall = 1;
                        Toast.makeText(getContext(), "Filtrage Appels activé sur votre mobile", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Infos.blockCall = 0;
                        Toast.makeText(getContext(), "Attention!!! Filtrage Appels désactivé!!", Toast.LENGTH_LONG).show();
                    }
                }
            });

            switch_allowAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(Infos.allowAlert == 0){
                        Infos.allowAlert = 1;
                        Toast.makeText(getContext(), "Alertes activées!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Infos.allowAlert = 0;
                        Toast.makeText(getContext(), "Attention!!! Alertes désactivées!!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }catch (Exception e){
            Toast.makeText(getContext(),"Methode 2  " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}