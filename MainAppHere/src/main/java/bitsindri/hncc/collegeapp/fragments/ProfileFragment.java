package bitsindri.hncc.collegeapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import bitsindri.hncc.collegeapp.R;
import bitsindri.hncc.collegeapp.activities.LoginActivity;


public class ProfileFragment extends Fragment {

    public TextView log_out;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        log_out.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        return  view;
    }
}