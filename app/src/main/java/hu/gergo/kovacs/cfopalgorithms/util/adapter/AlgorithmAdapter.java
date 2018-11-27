package hu.gergo.kovacs.cfopalgorithms.util.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hu.gergo.kovacs.cfopalgorithms.R;
import hu.gergo.kovacs.cfopalgorithms.util.AlgoritmType;

public class AlgorithmAdapter extends BasicAdapter {

    AlgoritmType algType;

    public AlgorithmAdapter(Context context, String[] title, String [] description, AlgoritmType algType) {
        super(context, title, description);
        this.algType = algType;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = layoutInflater.inflate(R.layout.activity_algorithm_item, null);
        }

        title = (TextView)view.findViewById(R.id.alg_item_title);
        description = (TextView)view.findViewById(R.id.alg_item_description);
        imageView = (ImageView)view.findViewById(R.id.alg_item_image);

        title.setText(titleArray[position]);
        description.setText(descriptionArray[position]);

        TypedArray iconList = null;
        switch (algType){
            case F2L:
                iconList = context.getResources().obtainTypedArray(R.array.f2l_list_icons);
                break;

            case OLL:
                iconList = context.getResources().obtainTypedArray(R.array.oll_list_icons);
                break;

            case PLL:
                iconList = context.getResources().obtainTypedArray(R.array.pll_list_icons);
                break;
        }

        imageView.setImageResource(iconList.getResourceId(position, -1));
        return view;
    }
}
