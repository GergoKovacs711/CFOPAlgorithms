package hu.gergo.kovacs.cfopalgorithms.util.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hu.gergo.kovacs.cfopalgorithms.R;

public class OLLAdapter extends BasicAdapter {

    public OLLAdapter(Context context, String[] title, String [] description) {
        super(context, title, description);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = layoutInflater.inflate(R.layout.activity_oll_item, null);
        }

        title = (TextView)view.findViewById(R.id.oll_item_title);
        description = (TextView)view.findViewById(R.id.oll_item_description);
        imageView = (ImageView)view.findViewById(R.id.oll_item_image);

        title.setText(titleArray[position]);
        description.setText(descriptionArray[position]);
        final TypedArray iconList = context.getResources().obtainTypedArray(R.array.oll_list_icons);

        imageView.setImageResource(iconList.getResourceId(position, -1));
        return view;
    }
}
