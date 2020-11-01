package ru.realityfamily.lct_mobile.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.realityfamily.lct_mobile.Adapters.RecyclerAdapter;
import ru.realityfamily.lct_mobile.MainActivity;
import ru.realityfamily.lct_mobile.R;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragment, container, false);

        RecyclerView rv = v.findViewById(R.id.settingsRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(new RecyclerAdapter(RecyclerAdapter.SettingsContent.getList(), (MainActivity) getActivity()));

        ImageButton settingsBackButton = v.findViewById(R.id.settingsBackButton);
        settingsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(new ContentFragment());
            }
        });

        return v;
    }
}
