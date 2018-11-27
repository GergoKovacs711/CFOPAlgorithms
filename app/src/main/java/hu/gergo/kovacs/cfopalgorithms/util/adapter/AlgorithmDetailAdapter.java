package hu.gergo.kovacs.cfopalgorithms.util.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.gergo.kovacs.cfopalgorithms.R;

public class AlgorithmDetailAdapter extends BasicAdapter {

    public AlgorithmDetailAdapter(Context context, String[] title, String[] description) {
        super(context, title, description);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.activity_algorithm_detail_item, null);
        }

        description = (TextView) view.findViewById(R.id.algorithm_detail_item_text);
        description.setText(descriptionArray[position]);

        return view;
    }
}
