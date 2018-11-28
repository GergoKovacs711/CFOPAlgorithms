package hu.gergo.kovacs.cfopalgorithms;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

public class F2LActivity extends YouTubeBaseActivity {
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;
    private ListView listView;
    private static Cases cases;
    private AlgorithmAdapter algorithmAdapter;
    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private TextView appBarText;
    private Animation animFadeIn;
    private Animation animFadeOut;

    boolean wasPlaying = false;
    boolean videoIsLoaded = false;

    private YouTubePlayer.OnInitializedListener onInitializedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f2l);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.f2l_coordinator);
        appBarLayout = (AppBarLayout) findViewById(R.id.f2l_appbar);
        youTubePlayerView = findViewById(R.id.f2l_youtube_view);
        youTubePlayerView.setVisibility(View.GONE);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.video_fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.video_fade_out);

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
                if(-verticalOffset < 150 && -verticalOffset > 170 && !(-verticalOffset % 10 == 0 ))
                    return;


                int range = appBarLayout.getTotalScrollRange();
                Log.i("range", Integer.toString(range));
                Log.i("offset", Integer.toString(verticalOffset));
//                if (Math.abs(verticalOffset) == range) {
////                    // Collapsed
////
////                } else

                if (-verticalOffset < 160) {
                    if(!videoIsLoaded){
                        youTubePlayerView.startAnimation(animFadeIn);
                        videoIsLoaded = true;
                    } else {
                        youTubePlayerView.setVisibility(View.VISIBLE);
                    }


                    if (youTubePlayer != null) {
                        if (wasPlaying && !youTubePlayer.isPlaying()) {
                            Toast.makeText(F2LActivity.this, "wasPlaying", Toast.LENGTH_SHORT).show();
                            wasPlaying = false;
                            youTubePlayer.play();
                        } else {
                            Toast.makeText(F2LActivity.this, "wasn'tPlaying", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (videoIsLoaded && -verticalOffset > 160){
                    if (youTubePlayer != null) {
                        if (youTubePlayer.isPlaying()) {
                            Toast.makeText(F2LActivity.this, "Playing", Toast.LENGTH_SHORT).show();

                            wasPlaying = true;
                            youTubePlayer.pause();
                        } else {
                            Toast.makeText(F2LActivity.this, "Invisible", Toast.LENGTH_SHORT).show();

                            wasPlaying = false;
                        }
                    }
                    youTubePlayerView.startAnimation(animFadeOut);
                    videoIsLoaded = false;
                } else {
                    youTubePlayerView.setVisibility(View.INVISIBLE);
                }
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

        YouTubePlayerView youTubePlayerView = findViewById(R.id.f2l_youtube_view);

        youTubePlayerView.initialize(YoutubePlayerConfig.API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer ytPlayer, boolean b) {
                        youTubePlayer = ytPlayer;
                        youTubePlayer.cueVideo(getResources().getString(R.string.f2l_youtube_video_url));
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(F2LActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(F2LActivity.this, AlgorithmDetail.class);
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
