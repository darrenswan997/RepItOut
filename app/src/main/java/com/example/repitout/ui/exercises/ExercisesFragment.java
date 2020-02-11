package com.example.repitout.ui.exercises;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.repitout.R;
import com.example.repitout.Workout;

import java.util.ArrayList;

public class ExercisesFragment extends Fragment {

    private ExercisesViewModel mViewModel;
    private ListView exercise_lv;
    private ArrayList<Workout> Wlist;

    public static ExercisesFragment newInstance() {
        return new ExercisesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.exercises_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ExercisesViewModel.class);
        // TODO: Use the ViewModel

    }



}
