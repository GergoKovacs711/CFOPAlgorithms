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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import hu.gergo.kovacs.cfopalgorithms.model.Cases;
import hu.gergo.kovacs.cfopalgorithms.util.AlgoritmType;
import hu.gergo.kovacs.cfopalgorithms.util.JSONManager;
import hu.gergo.kovacs.cfopalgorithms.util.YoutubePlayerConfig;
import hu.gergo.kovacs.cfopalgorithms.util.adapter.AlgorithmAdapter;

public class YouTube extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView;
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
        youTubePlayerView = findViewById(R.id.f2l_youtube_view);

        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                int heightPx = findViewById(R.id.f2l_toolbar_layout).getHeight();
                setAppBarOffset((int) (heightPx * 0.8));
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    // Collapsed
                } else if (verticalOffset == 0) {
                    youTubePlayerView.setVisibility(View.VISIBLE);
                } else {
                    // Somewhere in between
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.f2l_toolbar);
        setActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YouTube.this, MainActivity.class);
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

        YouTubePlayerView youTubePlayerView =  findViewById(R.id.f2l_youtube_view);

        youTubePlayerView.initialize(YoutubePlayerConfig.API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo("5xVh-7ywKpE");
                        // or to play immediately
                        // youTubePlayer.loadVideo("5xVh-7ywKpE");
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(YouTube.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                    }
                });

        cases = JSONManager.GSONDeserialzer(this, "f2l.json");
        setupListView();

    }

    public void setupListView() {

        String[] title = cases.getTitles().toArray(new String[0]);
        String[] descriptions = cases.getFirstAlg().toArray(new String[0]);
        listView = findViewById(R.id.f2l_listView);

        algorithmAdapter = new AlgorithmAdapter(this, title, descriptions, AlgoritmType.F2L);
        listView.setAdapter(algorithmAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(YouTube.this, AlgorithmDetail.class);
                intent.putExtra("position", position);
                intent.putExtra("Cases", OLLActivity.getCases());
                intent.putExtra("json", "f2l.json");
                intent.putExtra("algType", "F2L");
                startActivity(intent);
            }
        });
    }

    public static Cases getCases() {
        return cases;
    }

    private void setAppBarOffset(int offsetPx) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, offsetPx, new int[]{0, 0}, 0);
    }
}