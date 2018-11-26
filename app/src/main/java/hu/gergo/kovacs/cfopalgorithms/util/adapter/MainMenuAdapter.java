package hu.gergo.kovacs.cfopalgorithms.util.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hu.gergo.kovacs.cfopalgorithms.R;

public class MainMenuAdapter extends BasicAdapter {

    public MainMenuAdapter(Context context, String[] title, String[] description) {
        super(context, title, description);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.activity_main_item, null);

        }

        title = (TextView) view.findViewById(R.id.main_item_title);
        description = (TextView) view.findViewById(R.id.main_item_description);
        imageView = (ImageView) view.findViewById(R.id.main_item_image);

        title.setText(titleArray[position]);
        description.setText(descriptionArray[position]);


        if (titleArray[position].equalsIgnoreCase("CFOP")) {
            imageView.setImageResource(R.drawable.cfop);
        } else if (titleArray[position].equalsIgnoreCase("Cross")) {
            imageView.setImageResource(R.drawable.cross);
        } else if (titleArray[position].equalsIgnoreCase("F2L")) {
            imageView.setImageResource(R.drawable.f2l);
        } else if (titleArray[position].equalsIgnoreCase("OLL")) {
            imageView.setImageResource(R.drawable.oll);
        } else if (titleArray[position].equalsIgnoreCase("PLL")) {
            imageView.setImageResource(R.drawable.pll);
        } else {
            imageView.setImageResource(R.drawable.placeholdercube);
        }

        return view;
    }
}
