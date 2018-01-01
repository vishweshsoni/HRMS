package com.hrm.vishwesh.hrms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vishwesh on 30/12/17.
 */

public class trialFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView txtViewXyz= rootView.findViewById(R.id.home_txt);
        txtViewXyz.setText("Fragment runs completely fine!");
        return rootView;
    }
}
