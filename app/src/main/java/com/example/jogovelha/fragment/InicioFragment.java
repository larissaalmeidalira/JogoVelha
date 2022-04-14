package com.example.jogovelha.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jogovelha.R;
import com.example.jogovelha.databinding.FragmentFragmentinicioBinding;


public class InicioFragment extends Fragment {

   private FragmentFragmentinicioBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFragmentinicioBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();

        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        minhaActivity.getSupportActionBar().hide();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InicioFragment.this)
                        .navigate(R.id.action_inicioFragment_to_jogoFragment);
            }
        });
    }
}