package com.yashpawar.HST.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yashpawar.HST.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> ID;
    private ArrayList<String> CYLINDER_NUMBER;
    private ArrayList<String> CYLINDER_TYPE;
    private ArrayList<String> DATE_OF_TASTE;
    private ArrayList<String> LAST_HYDRO_TASTE;
    private ArrayList<String> REMARK;

    public ListAdapter(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> cylinder_number,
            ArrayList<String> cylinder_type
            /*ArrayList<String> date_of_test,
            ArrayList<String> last_hydro_test,
            ArrayList<String> remark*/
    )
    {
        this.context = context2;
        this.ID = id;
        this.CYLINDER_NUMBER = cylinder_number;
        this.CYLINDER_TYPE = cylinder_type;
        /*this.DATE_OF_TASTE = date_of_test;
        this.LAST_HYDRO_TASTE = last_hydro_test;
        this.REMARK = remark;*/

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return ID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return ID.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            child = layoutInflater.inflate(R.layout.detail, null);

            holder = new Holder();

            holder.ID =  child.findViewById(R.id.ID);
            holder.cylinder_number =  child.findViewById(R.id.cylinder_number);
            holder.cylinder_type =  child.findViewById(R.id.type);
            /*holder.date_of_test =  child.findViewById(R.id.date_of_test);
            holder.last_hydro_test =  child.findViewById(R.id.last_hydro_test);
            holder.remark =  child.findViewById(R.id.remark);*/
            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }

        holder.ID.setText(ID.get(position));
        holder.cylinder_number.setText(CYLINDER_NUMBER.get(position));
        holder.cylinder_type.setText(CYLINDER_TYPE.get(position));
        /*holder.date_of_test.setText(DATE_OF_TASTE.get(position));
        holder.last_hydro_test.setText(LAST_HYDRO_TASTE.get(position));
        holder.remark.setText(REMARK.get(position));*/
        notifyDataSetChanged();
        return child;
    }

    public class Holder {

        TextView ID;
        TextView cylinder_number;
        TextView cylinder_type;
        TextView date_of_test;
        TextView last_hydro_test;
        TextView remark;
    }

}