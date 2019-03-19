package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.policephonebook2019.R;

public class AdapterMapList extends BaseAdapter {

    Context mContext;
    String[] strStation;
    String[] strDesc;
    int[] resId;

    public AdapterMapList(Context mContext, String[] strStation, String[] strDesc, int[] resId) {
        this.mContext = mContext;
        this.strStation = strStation;
        this.strDesc = strDesc;
        this.resId = resId;
    }

    @Override
    public int getCount() {
        return strStation.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_map_list, parent, false);
        }

        TextView tvStation = convertView.findViewById(R.id.tvStationPosition);
        TextView tvStationDesc = convertView.findViewById(R.id.tvArea);
        ImageView imgView = convertView.findViewById(R.id.imgStation);

        tvStation.setText(strStation[position]);
        tvStationDesc.setText(strDesc[position]);
        imgView.setBackgroundResource(resId[position]);

        return convertView;
    }
}
