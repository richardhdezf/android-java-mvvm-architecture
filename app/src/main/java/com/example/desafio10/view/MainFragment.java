package com.example.desafio10.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.desafio10.databinding.MainFragmentBinding;
import com.example.desafio10.viewModel.PasswordViewModel;

public class MainFragment extends Fragment {
    private MainFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PasswordViewModel passwordViewModel =
                new ViewModelProvider(requireActivity()).get(PasswordViewModel.class);

        binding = MainFragmentBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setPasswordModel(passwordViewModel);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}