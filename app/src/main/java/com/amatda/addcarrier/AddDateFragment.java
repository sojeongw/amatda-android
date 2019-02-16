package com.amatda.addcarrier;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amatda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDateFragment extends Fragment {

    private Button buttonAddDateNext;

    public AddDateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_date, container, false);

        buttonAddDateNext = view.findViewById(R.id.buttonAddDateNext);
        buttonAddDateNext.setOnClickListener(v -> {
            AddOptionFragment fragment = new AddOptionFragment();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.layoutAddCarrier, fragment);
            transaction.commit();
        });

        return view;
    }

}