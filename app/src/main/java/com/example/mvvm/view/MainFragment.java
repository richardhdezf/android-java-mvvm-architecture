package com.example.mvvm.view;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvm.Injection;
import com.example.mvvm.databinding.MainFragmentBinding;
import com.example.mvvm.model.source.PasswordRepository;
import com.example.mvvm.viewModel.PasswordViewModel;

public class MainFragment extends Fragment {
    private MainFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = MainFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        PasswordRepository passwordRepository = Injection
                .providePasswordRepository(binding.getRoot().getContext());
        PasswordViewModel.Factory factory = new PasswordViewModel.Factory(passwordRepository);
        final PasswordViewModel passwordViewModel = new ViewModelProvider(this, factory)
                .get(PasswordViewModel.class);

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setPasswordModel(passwordViewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}