package ru.realityfamily.lct_mobile.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ru.realityfamily.lct_mobile.Adapters.AuthCollectionAdapter;
import ru.realityfamily.lct_mobile.R;

public class AuthNavigationFragment extends Fragment {
    ViewPager2 pager2;
    AuthCollectionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.auth_navigation_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new AuthCollectionAdapter(this);
        pager2 = view.findViewById(R.id.authPager);
        pager2.setAdapter(adapter);

        String[] titles = {getString(R.string.auth_title), getString(R.string.register_title)};

        TabLayout tabLayout = view.findViewById(R.id.authTabLayout);
        new TabLayoutMediator(tabLayout, pager2,
                (tab, position) -> tab.setText(titles[position])
        ).attach();
    }
}
