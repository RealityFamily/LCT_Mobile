package ru.realityfamily.lct_mobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.realityfamily.lct_mobile.Fragments.AuthNavigationFragment;
import ru.realityfamily.lct_mobile.MainActivity;
import ru.realityfamily.lct_mobile.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    List<SettingsContent> elements;
    MainActivity mainActivity;

    public RecyclerAdapter(List<SettingsContent> elements, MainActivity mainActivity) {
        this.elements = elements;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder mvh = new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_element, parent, false));
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Title.setText(elements.get(position).Title);
        holder.Arrow.setVisibility(elements.get(position).Type == SettingsContent.Status.button
                ? View.VISIBLE : View.GONE);
        if (elements.get(position).Title == "Выход") {
            holder.Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.changeFragment(new AuthNavigationFragment());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        ImageView Arrow;
        RelativeLayout Button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.settingsElementTitle);
            Arrow = itemView.findViewById(R.id.settingsElementArrow);
            Button = itemView.findViewById(R.id.settingsElementButton);
        }
    }

    public static class SettingsContent {
        public enum Status {
            info,
            button
        }

        public SettingsContent(String title, Status type) {
            Title = title;
            Type = type;
        }

        public String Title;
        public Status Type;

        public static List<SettingsContent> getList() {
            List<SettingsContent> list = new ArrayList<>();
            list.add(new SettingsContent("Язык", Status.button));
            list.add(new SettingsContent("Виброуведомления", Status.button));
            list.add(new SettingsContent("Сменить пароль", Status.button));
            list.add(new SettingsContent("Редактировать личные данные", Status.button));
            list.add(new SettingsContent("Прикрепленные устройства", Status.button));
            list.add(new SettingsContent("Деп.Строй v0.0.1", Status.info));
            list.add(new SettingsContent("Выход", Status.button));

            return list;
        }
    }
}
