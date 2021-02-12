package com.dunneev.seenatural.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.dunneev.seenatural.R;
import com.dunneev.seenatural.databinding.FragmentReadingTabBinding;

public class ReadingTabFragment extends Fragment {

    private FragmentReadingTabBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonToPianoFragment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(ReadingTabFragment.this)
//                        .navigate(R.id.);
//            }
//        });
//
//        binding.buttonToReadingFragment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(ReadingTabFragment.this)
//                        .navigate(R.id.);
//            }
//        });
//
//        binding.buttonToStaffFragment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavHostFragment.findNavController(ReadingTabFragment.this)
//                        .navigate(R.id.);
//            }
//        });
//
//        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavHostFragment.findNavController(ReadingTabFragment.this)
//                        .navigate(R.id.);
//            }
//        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}