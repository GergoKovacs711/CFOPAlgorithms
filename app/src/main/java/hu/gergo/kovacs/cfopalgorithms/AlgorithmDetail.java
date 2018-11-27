package hu.gergo.kovacs.cfopalgorithms;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hu.gergo.kovacs.cfopalgorithms.model.Cases;
import hu.gergo.kovacs.cfopalgorithms.util.AlgoritmType;
import hu.gergo.kovacs.cfopalgorithms.util.JSONManager;
import hu.gergo.kovacs.cfopalgorithms.util.adapter.AlgorithmDetailAdapter;

public class AlgorithmDetail extends AppCompatActivity {
    public static ArrayList<String> algsForWeb;
    private ImageView imageView;
    private TextView textView;
    private ListView listView;
    private Cases cases;
    public int position;
    private AlgorithmDetailAdapter algorithmDetailAdapter;
    private AlgoritmType algType;
    private static String[] webviewURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_detail);
        webviewURL =  getResources().getStringArray(R.array.webview_url);

        cases = (Cases) getIntent().getSerializableExtra("Cases");
        position = getIntent().getIntExtra("position", 0);
        if (cases == null) {
            String json = getIntent().getStringExtra("json");
            cases = JSONManager.GSONDeserialzer(this, json);
        }

        String type = getIntent().getStringExtra("algType");
        setAlgorithType(type);
        setupViews();

    }

    private void setupViews() {
        setupListView();
        setTextView();
        setImageView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int currentPosition, long l) {
                Intent intent = new Intent(AlgorithmDetail.this, WebView.class);
                intent.putExtra("url", algToWebUrl(position, currentPosition));
                startActivity(intent);
            }
        });
    }

    public void setupListView() {
        listView = findViewById(R.id.algorithm_detail_list);
        ArrayList<String> descriptions = cases.getAlgorithmsOfCase(position);
        String[] title = new String[descriptions.size()];
        algorithmDetailAdapter = new AlgorithmDetailAdapter(this, title, descriptions.toArray(new String[descriptions.size()]));
        listView.setAdapter(algorithmDetailAdapter);
    }

    private void setAlgorithType(String type) {
        if (type.equalsIgnoreCase(getResources().getStringArray(R.array.main_list)[2])) {
            algType = AlgoritmType.F2L;
        } else if (type.equalsIgnoreCase(getResources().getStringArray(R.array.main_list)[3])) {
            algType = AlgoritmType.OLL;
        } else if (type.equalsIgnoreCase(getResources().getStringArray(R.array.main_list)[4])) {
            algType = AlgoritmType.PLL;
        }
    }

    private void setTextView() {
        textView = findViewById(R.id.algorithm_detail_title);
        String title = cases.getTitles().toArray(new String[0])[position];
        textView.setText(title);
    }

    private void setImageView() {
        final int iconListID;
        imageView = (ImageView) findViewById(R.id.algorithm_detail_image);
        switch (algType) {
            case F2L:
                iconListID = R.array.f2l_list_icons;
                break;

            case OLL:
                iconListID = R.array.oll_list_icons;
                break;

            case PLL:
                iconListID = R.array.pll_list_icons;
                break;

            default:
                iconListID = R.array.oll_list_icons;
                break;
        }
        final TypedArray iconList = getResources().obtainTypedArray(iconListID);
        imageView.setImageResource(iconList.getResourceId(position, -1));
    }

    public String algToWebUrl(int caseNumber, int algNumber){
        String alg = cases.getSingleAlgOfCase(caseNumber, algNumber);
        String url = webviewURL[0] + algType.toString() + webviewURL[1] + algConverter(alg);
        return url;
    }

    private String algConverter(String alg) {
        alg = alg.replace(' ','_');
        alg = alg.replace('\'', '-');
        return alg;
    }

}
