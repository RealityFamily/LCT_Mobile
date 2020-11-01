package ru.realityfamily.lct_mobile.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import ru.realityfamily.lct_mobile.Fragments.AuthFragment;
import ru.realityfamily.lct_mobile.Fragments.RegisterFragment;

public class AuthCollectionAdapter extends FragmentStateAdapter {
    List<Fragment> elements;

    public AuthCollectionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new AuthFragment();
                break;
            case 1:
                fragment = new RegisterFragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
