package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Model.Police;

import java.util.ArrayList;

public class AdapterSearchviewSuggestion extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Police> mPoliceInfo;

    public AdapterSearchviewSuggestion(Context mContext, ArrayList<Police> mPoliceInfo) {
        this.mContext = mContext;
        this.mPoliceInfo = mPoliceInfo;

        inflater = LayoutInflater.from(mContext);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return mPoliceInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mPoliceInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_view_items, null);
            holder.name = convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(mPoliceInfo.get(position).getRankName() + mPoliceInfo.get(position).getFirstName() + " " + mPoliceInfo.get(position).getLastName());

        return convertView;
    }
}
