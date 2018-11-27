package hu.gergo.kovacs.cfopalgorithms;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import hu.gergo.kovacs.cfopalgorithms.model.Cases;
import hu.gergo.kovacs.cfopalgorithms.util.AlgoritmType;
import hu.gergo.kovacs.cfopalgorithms.util.JSONManager;
import hu.gergo.kovacs.cfopalgorithms.util.adapter.AlgorithmAdapter;

public class F2LActivity extends AppCompatActivity {
    private ListView listView;
    private static Cases cases;
    private AlgorithmAdapter algorithmAdapter;
    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private TextView appBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f2l);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.f2l_coordinator);
        appBarLayout = (AppBarLayout) findViewById(R.id.f2l_appbar);
        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                int heightPx = findViewById(R.id.f2l_toolbar_layout).getHeight();
                setAppBarOffset((int )(heightPx * 0.8));
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.f2l_toolbar);
        setActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(F2LActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        appBarText = (TextView) findViewById(R.id.f2l_appbar_text);
        appBarText.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);

        cases = JSONManager.GSONDeserialzer(this, "f2l.json");
        setupListView();

    }

    public void setupListView() {

        String[] title = cases.getTitles().toArray(new String[0]);
        String[] descriptions = cases.getFirstAlg().toArray(new String[0]);
        listView =  findViewById(R.id.f2l_listView);

        algorithmAdapter = new AlgorithmAdapter(this, title, descriptions, AlgoritmType.F2L);
        listView.setAdapter(algorithmAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(F2LActivity.this, AlgorithmDetail.class);
                intent.putExtra("position", position);
                intent.putExtra("Cases", OLLActivity.getCases());
                intent.putExtra("json", "f2l.json");
                intent.putExtra("algType" , "F2L");
                startActivity(intent);
            }
        });
    }

    public static Cases getCases(){
        return cases;
    }

    private void setAppBarOffset(int offsetPx){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, offsetPx, new int[]{0, 0}, 0);
    }

}
