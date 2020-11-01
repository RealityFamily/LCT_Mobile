package ru.realityfamily.lct_mobile.Fragments;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.realityfamily.lct_mobile.MainActivity;
import ru.realityfamily.lct_mobile.R;

public class AuthFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.auth_fragment, container, false);

        EditText editPass = (EditText) v.findViewById(R.id.authPassEditText);
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.authShowPass);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    editPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Button authButton = (Button) v.findViewById(R.id.authButton);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.changeFragment(new ContentFragment());
            }
        });

        return v;
    }
}
