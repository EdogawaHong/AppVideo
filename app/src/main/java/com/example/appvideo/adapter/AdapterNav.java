package com.example.appvideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appvideo.R;
import com.example.appvideo.model.ItemMenuNav;

import java.util.List;

public class AdapterNav extends BaseAdapter {
    private Context context;
    private int layout;
    List<ItemMenuNav> menuNavList;

    public AdapterNav(Context context, int layout, List<ItemMenuNav> menuNavList) {
        this.context = context;
        this.layout = layout;
        this.menuNavList = menuNavList;
    }

    @Override
    public int getCount() {
        return menuNavList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHoder{
        TextView tvNav;
        ImageView imgNav;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder;
        if (view==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            viewHoder=new ViewHoder();
            viewHoder.tvNav=view.findViewById(R.id.tvNav);
            viewHoder.imgNav=view.findViewById(R.id.iconNav);

            view.setTag(viewHoder);
        }else {
            viewHoder= (ViewHoder) view.getTag();
        }
        viewHoder.tvNav.setText(menuNavList.get(i).getName());
        viewHoder.imgNav.setImageResource(menuNavList.get(i).getIcon());
        return view;
    }
}
