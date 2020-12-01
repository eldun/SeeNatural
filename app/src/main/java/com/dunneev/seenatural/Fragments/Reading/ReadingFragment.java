package com.dunneev.seenatural.Fragments.Reading;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dunneev.seenatural.databinding.FragmentReadingBinding;

public class ReadingFragment extends Fragment {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private FragmentReadingBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentReadingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}