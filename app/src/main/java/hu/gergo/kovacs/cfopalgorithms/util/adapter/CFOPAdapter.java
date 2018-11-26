package hu.gergo.kovacs.cfopalgorithms.util.adapter;

import android.content.Context;

import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hu.gergo.kovacs.cfopalgorithms.R;

public class CFOPAdapter extends BasicAdapter {

    public CFOPAdapter(Context context, String[] title, String [] description) {
        super(context, title, description);
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = layoutInflater.inflate(R.layout.activity_cfop_item, null);

        }

        title = (TextView)view.findViewById(R.id.cfop_item_title);
        description = (TextView)view.findViewById(R.id.cfop_item_description);
        description.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        imageView = (ImageView)view.findViewById(R.id.cfop_item_image);

        title.setText(titleArray[position]);
        description.setText(descriptionArray[position]);

        if(titleArray[position].equalsIgnoreCase("Cross")) {
            imageView.setImageResource(R.drawable.cross);
        } else if(titleArray[position].equalsIgnoreCase("F2L")) {
            imageView.setImageResource(R.drawable.f2l);
        } else if(titleArray[position].equalsIgnoreCase("OLL")){
            imageView.setImageResource(R.drawable.oll);
        } else if(titleArray[position].equalsIgnoreCase("PLL")){
            imageView.setImageResource(R.drawable.pll);
        }else {
            imageView.setImageResource(R.drawable.placeholdercube);
        }

        return view;
    }
}
