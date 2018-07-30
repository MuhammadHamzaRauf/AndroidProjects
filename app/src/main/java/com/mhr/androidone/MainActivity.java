package com.mhr.androidone;

        import android.content.Context;
        import android.content.res.Resources;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.MediaController;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.VideoView;

        import java.util.Random;
        import java.util.Timer;
        import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    SeekBar audioProgress;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Popular", "This is created ");
        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.videoplayback );
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
//        videoView.start();

        mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(maxVolume);
        seekBar.setProgress(currentVolume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Popular", "onProgressChanged: " + progress);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        audioProgress = (SeekBar) findViewById(R.id.seekBar2);
        audioProgress.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audioProgress.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,1000);

        audioProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void startSound(View view){
        mediaPlayer.start();
    }

    public void pauseSound(View view){
        mediaPlayer.pause();
    }


    public void showMessage(View view){

        EditText editText1 = (EditText) findViewById(R.id.editText1);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        ImageView imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView1.setImageResource(R.drawable.screenshot2);
        TextView textViewheading = (TextView) findViewById(R.id.textViewheading);
        Random random = new Random();
        textViewheading.setText(random.nextInt(20) + " ");

        Toast.makeText(MainActivity.this,textViewheading.getText(),Toast.LENGTH_SHORT).show();

        Log.i("Popular", "User name: " + editText1.getText());
        Log.i("Popular", "User password: " + editTextPassword.getText());

        Log.i("Popular", "At last");
    }


    public void fadingOut(View view){
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
//        imageView1.animate().alpha(0f).setDuration(5000)
        imageView1.animate().x(1000).setDuration(3000);
    }

    public void genericBehaviour(View view){

        int backId = view.getId();
        String frontId = " ";
        frontId = view.getResources().getResourceEntryName(backId);

        int resourceId = getResources().getIdentifier(frontId,"raw", getPackageName());

        MediaPlayer mediaPlayer1 = MediaPlayer.create(this, resourceId);
        mediaPlayer1.start();

        Log.i("Popular", " " + frontId);
    }

    public void changesAreMade(View view){
        Log.i("Popular", "Changes have been made in branch");
    }


}
