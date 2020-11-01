package ru.realityfamily.lct_mobile.Fragments;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import ru.realityfamily.lct_mobile.MainActivity;
import ru.realityfamily.lct_mobile.R;
import ru.realityfamily.lct_mobile.Services.*;

public class ContentFragment extends Fragment {
    enum ShiftStatus { need_to_check, checked, started}

    ShiftStatus shiftStatus = ShiftStatus.need_to_check;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_fragment, container, false);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentMapFragment, new MapFragment()).commit();


        LinearLayout llBottomSheet = v.findViewById(R.id.bottomSheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setPeekHeight(480);
        bottomSheetBehavior.setHideable(false);


        ImageView shiftButtonColor = v.findViewById(R.id.shiftButtonColor);
        shiftButtonColor.setImageResource(R.drawable.need_to_correct_shift_button);
        TextView shiftButtonTitle = v.findViewById(R.id.shiftButtonTitle);
        shiftButtonTitle.setText("Начать смену");
        RelativeLayout shiftButton = v.findViewById(R.id.shiftButton);
        LinearLayout polygonCheck = v.findViewById(R.id.polygonCheck);
        RelativeLayout sosSlider = v.findViewById(R.id.sosSlider);
        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myService;
                switch (shiftStatus) {
                    case need_to_check:
                        if (polygonCheck.getVisibility() == View.GONE) {
                            polygonCheck.setVisibility(View.VISIBLE);
                            sosSlider.setVisibility(View.GONE);
                        } else if (polygonCheck.getVisibility() == View.VISIBLE) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                        break;
                    case checked:
                        polygonCheck.setVisibility(View.GONE);
                        sosSlider.setVisibility(View.VISIBLE);
                        shiftStatus = ShiftStatus.started;

                        shiftButtonColor.setImageResource(R.drawable.close_shift_button);
                        shiftButtonTitle.setText("Закрыть смену");

                        myService = new Intent(getContext(), AccelerationService.class);
                        getActivity().startService(myService);
                        break;
                    case started:
                        polygonCheck.setVisibility(View.GONE);
                        sosSlider.setVisibility(View.GONE);
                        shiftStatus = ShiftStatus.need_to_check;

                        shiftButtonColor.setImageResource(R.drawable.need_to_correct_shift_button);
                        shiftButtonTitle.setText("Начать смену");

                        myService = new Intent(getContext(), AccelerationService.class);
                        getActivity().stopService(myService);
                        break;
                }
            }
        });
        RelativeLayout polygonMissed = v.findViewById(R.id.polygonMissed);
        polygonMissed.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                polygonCheck.setVisibility(View.GONE);
                sosSlider.setVisibility(View.GONE);
                shiftStatus = ShiftStatus.need_to_check;
                Toast.makeText(getContext(), "Произошел сбой в системе GPS. Проверьте устройство.", Toast.LENGTH_SHORT);
            }
        });
        RelativeLayout polygonCorrect = v.findViewById(R.id.polygonCorrect);
        polygonCorrect.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                polygonCheck.setVisibility(View.GONE);
                sosSlider.setVisibility(View.GONE);
                shiftStatus = ShiftStatus.checked;

                shiftButtonColor.setImageResource(R.drawable.corrected_shift_button);
            }
        });


        ImageButton shiftSettingsBtn = v.findViewById(R.id.shiftSettingsBtn);
        shiftSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(new SettingsFragment());
            }
        });


        AppCompatSeekBar sosBar = (AppCompatSeekBar) ((RelativeLayout) v.findViewById(R.id.sosSlider)).getChildAt(0);

        View thumbView = inflater.inflate(R.layout.progress_thumb, null);
        thumbView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap_thumb = Bitmap.createBitmap(thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas_thumb = new Canvas(bitmap_thumb);
        thumbView.layout(0, 0, thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight());
        thumbView.draw(canvas_thumb);
        sosBar.setThumb(new BitmapDrawable(getResources(), bitmap_thumb));
        sosBar.setThumbOffset(-10);

        View progressSeek = inflater.inflate(R.layout.progress_seek, null);
        progressSeek.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap_seek = Bitmap.createBitmap(progressSeek.getMeasuredWidth(), progressSeek.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas_seek = new Canvas(bitmap_seek);
        progressSeek.layout(0, 0, progressSeek.getMeasuredWidth(), progressSeek.getMeasuredHeight());
        progressSeek.draw(canvas_seek);
        sosBar.setProgressDrawable(new BitmapDrawable(getResources(), bitmap_seek));

        sosBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() > 75) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                ValueAnimator anim = ValueAnimator.ofInt(seekBar.getProgress(), 0);
                anim.setDuration(1000);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animProgress = (Integer) animation.getAnimatedValue();
                        seekBar.setProgress(animProgress);
                    }
                });
                anim.start();
            }
        });

        return v;
    }
}
