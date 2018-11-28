package hu.gergo.kovacs.cfopalgorithms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewStub;
import android.view.textclassifier.TextClassification;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Text;

import hu.gergo.kovacs.cfopalgorithms.util.YoutubePlayerConfig;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class CrossActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer youTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross);
        TextView textView = (TextView)(findViewById(R.id.cross_description));
        textView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

        youTubePlayerView = (YouTubePlayerView)(findViewById(R.id.cross_youtube_view));
        loadVideo();
    }

    public void loadVideo() {
        youTubePlayerView = findViewById(R.id.cross_youtube_view);
        youTubePlayerView.initialize(YoutubePlayerConfig.API_KEY,

                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer ytPlayer, boolean b) {
                        youTubePlayer = ytPlayer;
                        youTubePlayer.setShowFullscreenButton(false);
                        youTubePlayer.cueVideo(getResources().getString(R.string.cross_youtube_video_url));
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(CrossActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
