package com.example.practice14;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<WifiInfo> {

    private List<WifiInfo> wifiInfos;
    Context Context;

    public CustomAdapter(List<WifiInfo> wifiInfos, Context context) {
        super(context, R.layout.item_wifi, wifiInfos);
        this.wifiInfos = wifiInfos;
        this.Context = context;
    }

    @Override
    public int getCount() {
        return wifiInfos.size();
    }

    @Override
    public WifiInfo getItem(int i) {
        return wifiInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_wifi, null);
        }
        view.setPadding(0, 10, 0, 10);

        WifiInfo wifiInfo = wifiInfos.get(position);

        TextView wifiNameTextView = (TextView) view.findViewById(R.id.WifiNameTextView);
        wifiNameTextView.setText(wifiInfo.getSSID());

        TextView RSSITextView = (TextView) view.findViewById(R.id.RSSITextView);
        RSSITextView.setText(String.valueOf(wifiInfo.getRSSI()));

        TextView LevelTextView = (TextView) view.findViewById(R.id.LevelTextView);
        LevelTextView.setText(String.valueOf(wifiInfo.getLevel()));

        TextView MacAddressTextView = (TextView) view.findViewById(R.id.BSSIDTextView);
        MacAddressTextView.setText(wifiInfo.getMacAddress());

        return view;
    }

}
