package com.ushahidi.android.presentation.view.ui.adapter;

import com.ushahidi.android.R;
import com.ushahidi.android.presentation.model.FormModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for {@link com.orhanobut.dialogplus.DialogPlus} to display forms
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;

    private final List<FormModel> mItems = new ArrayList<>();

    public FormAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    public void setItems(List<FormModel> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public FormModel getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.grid_form_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.form_title);
            viewHolder.description = (TextView) view.findViewById(R.id.form_description);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(mItems.get(position).getName());
        viewHolder.description.setText(mItems.get(position).getDescription());
        return view;
    }

    static class ViewHolder {

        TextView title;

        TextView description;
    }

}
