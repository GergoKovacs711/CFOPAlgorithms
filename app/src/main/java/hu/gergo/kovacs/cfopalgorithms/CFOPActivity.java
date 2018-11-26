package hu.gergo.kovacs.cfopalgorithms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import hu.gergo.kovacs.cfopalgorithms.util.adapter.CFOPAdapter;
import hu.gergo.kovacs.cfopalgorithms.util.adapter.MainMenuAdapter;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;


public class CFOPActivity extends AppCompatActivity {

    public TextView title;
    public ImageView image;
    public TextView description;

    public TextView title1;
    public TextView paragraph1;

    public TextView title2;
    public TextView paragraph2;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cfop);
        initViews();
        initListView();

    }

    private void initViews(){
        //
        // finView() methods
        //
        title = findViewById(R.id.cfop_main_title);
        description = findViewById(R.id.cfop_description);
        image = findViewById(R.id.cfop_image);

        title1 = findViewById(R.id.cfop_first_title);
        paragraph1 = findViewById(R.id.cfop_first_paragraph);

        title2 = findViewById(R.id.cfop_second_title);
        paragraph2 = findViewById(R.id.cfop_second_paragraph);


        //
        // setText() methods
        //
        title.setText(R.string.cfop_title);
        description.setText(R.string.cfop_description);
        image.setImageResource(R.drawable.cfop);

        title1.setText(R.string.cfop_first_title);
        paragraph1.setText(R.string.cfop_first_paragraph);

        title2.setText(R.string.cfop_second_title);
        paragraph2.setText(R.string.cfop_second_paragraph);

        //
        setJustification();
    }

    private void initListView(){
        listView = findViewById(R.id.cfopListView);

        String[] title = getResources().getStringArray(R.array.CFOPList);
        String[] descriptions = getResources().getStringArray(R.array.CFOPList_Descriptions);

        CFOPAdapter cfopAdapter = new CFOPAdapter(this, title, descriptions);
        listView.setAdapter(cfopAdapter);
    }

    private void setJustification(){
        paragraph1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        paragraph2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
    }


}
