package com.dunneev.seenatural.Fragments.Staff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dunneev.seenatural.R;
import com.dunneev.seenatural.databinding.FragmentStaffBinding;

public class StaffFragment extends Fragment {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private FragmentStaffBinding binding;
    StaffView staffView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStaffBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpStaff();
    }

    private void setUpStaff() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}