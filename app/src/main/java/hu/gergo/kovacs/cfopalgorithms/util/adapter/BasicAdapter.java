package hu.gergo.kovacs.cfopalgorithms.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BasicAdapter extends BaseAdapter {
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected TextView title;
    protected TextView description;
    protected String[] titleArray;
    protected String[] descriptionArray;
    protected ImageView imageView;

    public BasicAdapter(Context context, String[] title, String [] description) {
        this.context = context;
        titleArray = title;
        descriptionArray = description;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public Object getItem(int position) {

        return titleArray[position];
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

}
