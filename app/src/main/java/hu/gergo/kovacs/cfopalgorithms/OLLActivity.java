package hu.gergo.kovacs.cfopalgorithms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import hu.gergo.kovacs.cfopalgorithms.model.Cases;
import hu.gergo.kovacs.cfopalgorithms.util.AlgoritmType;
import hu.gergo.kovacs.cfopalgorithms.util.JSONManager;
import hu.gergo.kovacs.cfopalgorithms.util.adapter.AlgorithmAdapter;

public class OLLActivity extends AppCompatActivity {
    private ListView listView;
    private static Cases cases;
    private AlgorithmAdapter algorithmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oll);

        Toolbar toolbar = (Toolbar) findViewById(R.id.oll_toolbar);
        setActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OLLActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        cases = JSONManager.GSONDeserialzer(this, "oll.json");

        setupListView();
    }

    public void setupListView() {

        String[] title = cases.getTitles().toArray(new String[0]);
        String[] descriptions = cases.getFirstAlg().toArray(new String[0]);
        listView =  findViewById(R.id.oll_listView);

        algorithmAdapter = new AlgorithmAdapter(this, title, descriptions, AlgoritmType.OLL);
        listView.setAdapter(algorithmAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(OLLActivity.this, AlgorithmDetail.class);
                intent.putExtra("position", position);
                intent.putExtra("Cases", OLLActivity.getCases());
                intent.putExtra("json", "oll.json");
                intent.putExtra("algType" , "OLL");
                startActivity(intent);
            }
        });
    }

    public static Cases getCases(){
        return cases;
    }
}
