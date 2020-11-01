package ru.realityfamily.lct_mobile.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import ru.realityfamily.lct_mobile.R;

public class PinFragment extends Fragment {
    LinearLayout pinCirclesLinearLayout;
    String pin = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pin_fragment, container, false);

        pinCirclesLinearLayout = v.findViewById(R.id.pinCirclesContainer);
        DrawCircles(false, false, false, false);

        GridLayout pinButtonContainer = v.findViewById(R.id.pinButtonContainer);
        for (int i = 0 ; i < pinButtonContainer.getChildCount(); i++) {
            try {
                RelativeLayout rl = (RelativeLayout) (pinButtonContainer.getChildAt(i));
                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddNumToPin(Integer.parseInt(((TextView)((RelativeLayout) (v)).getChildAt(1)).getText().toString()));
                    }
                });
            } catch (Exception e){}
        }

        ImageButton pinButtonBack = (ImageButton) v.findViewById(R.id.pinButtonBack);
        pinButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveNumToPin();
            }
        });

        return v;
    }

    void DrawCircles (boolean fill1, boolean fill2, boolean fill3, boolean fill4) {
        pinCirclesLinearLayout.removeAllViews();

        for (int i = 0; i < 4; i++) {
            ImageView circle = new ImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30, 0, 30, 0);
            circle.setLayoutParams(lp);

            switch (i) {
                case 0:
                    circle.setImageResource(fill1 ? R.drawable.fill_pin_circle : R.drawable.empty_pin_circle);
                    break;
                case 1:
                    circle.setImageResource(fill2 ? R.drawable.fill_pin_circle : R.drawable.empty_pin_circle);
                    break;
                case 2:
                    circle.setImageResource(fill3 ? R.drawable.fill_pin_circle : R.drawable.empty_pin_circle);
                    break;
                case 3:
                    circle.setImageResource(fill4 ? R.drawable.fill_pin_circle : R.drawable.empty_pin_circle);
                    break;
            }

            pinCirclesLinearLayout.addView(circle);
        }
    }

    void AddNumToPin(int num) {
        if (pin.length() < 4) {
            pin += String.valueOf(num);

            switch (pin.length()) {
                case 1:
                    DrawCircles(true, false, false, false);
                    break;
                case 2:
                    DrawCircles(true, true, false, false);
                    break;
                case 3:
                    DrawCircles(true, true, true, false);
                    break;
                case 4:
                    DrawCircles(true, true, true, true);
                    //Проверка пин кода
                    break;
            }
        }
    }

    void RemoveNumToPin() {
        if (pin.length() > 0) {
            pin = pin.substring(0, pin.length() - 1);

            switch (pin.length()) {
                case 0:
                    DrawCircles(false, false, false, false);
                    break;
                case 1:
                    DrawCircles(true, false, false, false);
                    break;
                case 2:
                    DrawCircles(true, true, false, false);
                    break;
                case 3:
                    DrawCircles(true, true, true, false);
                    break;
            }
        }
    }
}
