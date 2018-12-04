package hu.gergo.kovacs.cfopalgorithms.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.text.Layout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import hu.gergo.kovacs.cfopalgorithms.R;
import hu.gergo.kovacs.cfopalgorithms.model.Cases;
import hu.gergo.kovacs.cfopalgorithms.util.AlgoritmType;
import hu.gergo.kovacs.cfopalgorithms.util.JSONManager;
import hu.gergo.kovacs.cfopalgorithms.util.YoutubePlayerConfig;
import hu.gergo.kovacs.cfopalgorithms.util.adapter.AlgorithmAdapter;

public class PLLActivity extends YouTubeBaseActivity {

    private ListView listView;
    private static Cases cases;
    private AlgorithmAdapter algorithmAdapter;

    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private TextView appBarText;

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;

    private float appbarOffsetScale;
    private final int videoHeight = 160;

    private boolean firstLoadOfAppbar = true;
    private boolean videoIsLoaded = false;
    private boolean videoIsVisible = false;
    private boolean videoWasPlaying = false;
    private boolean videoSetupTickTock = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pll);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pll_coordinator);
        appBarLayout = (AppBarLayout) findViewById(R.id.pll_appbar);

        TypedValue outValue = new TypedValue();
        getResources().getValue(R.dimen.pll_toolbar_offset_scale, outValue, true);
        appbarOffsetScale = outValue.getFloat();

        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                int heightPx = findViewById(R.id.pll_toolbar_layout).getHeight();
                setAppBarOffset((int) (heightPx * appbarOffsetScale));
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // if it's the first run exit because verticalOffset give 0 at its first run
                if (firstLoadOfAppbar) {
                    firstLoadOfAppbar = false;
                    return;
                }

                /*Log.i("range", Integer.toString(range));
                Log.i("offset", Integer.toString(verticalOffset));*/

                // videoSetupTickTock is used to run the setup for the corresponding..
                // region (video show, below the videoHeight / video hide above, the videoHeight)..
                // only once. Even if the verticalOffset value is within the..
                // given range, the video view will only be changed once the appbar has moved to..
                // the other region
                if (-verticalOffset <= videoHeight) {
                    if (videoSetupTickTock) {
                        if (!videoIsLoaded) {
                            loadVideo();
                            videoIsLoaded = true;

                            youTubePlayerView.setVisibility(View.VISIBLE);
                            videoIsVisible = true;
                        }

                        if (!videoIsVisible) {
                            showVideoAnimation();
                            videoIsVisible = true;
                        }

                        if (youTubePlayer != null) {
                            if (videoWasPlaying) {
                                youTubePlayer.play();
                            }
                        }
                        videoSetupTickTock = false;
                    }

                } else {
                    if (!videoSetupTickTock) {
                        if (videoIsLoaded) {
                            if (youTubePlayer != null) {
                                if (youTubePlayer.isPlaying()) {
                                    videoWasPlaying = true;
                                    youTubePlayer.pause();

                                } else {
                                    videoWasPlaying = false;
                                }
                            }

                            if (videoIsVisible) {
                                hideVideoAnimation();
                                videoIsVisible = false;
                            }
                        }
                        videoSetupTickTock = true;
                    }
                }
            }

            public void loadVideo() {
                ViewStub viewStub = (ViewStub) findViewById(R.id.pll_youtube_view_stub);
                viewStub.inflate();

                youTubePlayerView = findViewById(R.id.youtube_view);
                youTubePlayerView.initialize(YoutubePlayerConfig.API_KEY,

                        new YouTubePlayer.OnInitializedListener() {
                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                YouTubePlayer ytPlayer, boolean b) {
                                youTubePlayer = ytPlayer;
                                youTubePlayer.setShowFullscreenButton(false);
                                youTubePlayer.cueVideo(getResources().getString(R.string.pll_youtube_video_url));
                            }

                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                YouTubeInitializationResult youTubeInitializationResult) {
                                Toast.makeText(PLLActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }


            private void showVideoAnimation() {
                youTubePlayerView.setAlpha(0f);
                youTubePlayerView.setVisibility(View.VISIBLE);
                youTubePlayerView.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .setListener(null);

            }

            private void hideVideoAnimation() {
                youTubePlayerView.animate()
                        .alpha(0f)
                        .setDuration(200)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                youTubePlayerView.setVisibility(View.INVISIBLE);
                            }
                        });
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.pll_toolbar);
        setActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PLLActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        appBarText = (TextView) findViewById(R.id.pll_appbar_text);
        appBarText.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);

        cases = JSONManager.GSONDeserialzer(this, "pll.json");
        setupListView();

    }

    public void setupListView() {

        String[] title = cases.getTitles().toArray(new String[0]);
        String[] descriptions = cases.getFirstAlg().toArray(new String[0]);
        listView = findViewById(R.id.pll_listView);

        algorithmAdapter = new AlgorithmAdapter(this, title, descriptions, AlgoritmType.PLL);
        listView.setAdapter(algorithmAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(PLLActivity.this, AlgorithmDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("Cases", cases);
                intent.putExtra("json", "pll.json");
                intent.putExtra("algType", "PLL");
                startActivity(intent);
            }
        });
    }



    private void setAppBarOffset(int offsetPx) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, offsetPx, new int[]{0, 0}, 0);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PLLActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
