package com.dam.favorloop.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.dam.favorloop.R;
import com.google.android.material.tabs.TabLayout;

public class AddFragment extends Fragment {

    TabLayout tabLayoutAdd;
    FrameLayout flAddContainer;
    Fragment selectedFragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        tabLayoutAdd = view.findViewById(R.id.tabLayoutAdd);
        tabLayoutAdd.addOnTabSelectedListener(listener);
        flAddContainer = view.findViewById(R.id.flAddContainer);

        selectedFragment = new PublicarPostFragment();
        getFragmentManager().beginTransaction().replace(R.id.flAddContainer, selectedFragment).commit();

        return view;
    }

    private TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getPosition() == 0) {
                selectedFragment = new PublicarPostFragment();
                getFragmentManager().beginTransaction().replace(R.id.flAddContainer, selectedFragment).commit();

            } else if (tab.getPosition() == 1) {
                selectedFragment = new PublicarEventoFragment();
                getFragmentManager().beginTransaction().replace(R.id.flAddContainer, selectedFragment).commit();
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}