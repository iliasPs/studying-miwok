package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    //first we create an instance of the AudioManager
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if ((focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)){
                        //in both cases (loss transient or loss transient can duck we need to pause the player
                        //Pause playback
                        mMediaPlayer.pause();
                        //Start the audio track from the start since our files are to small in duration
                        mMediaPlayer.seekTo(0);

                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        //this means we gained focus so we start(); the media player
                        mMediaPlayer.start();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        //we lost focus so we stop playback and clear resources through our custom method.
                        releaseMediaPlayer();
                    }
                }
            };


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {//we added the completion listener implementation to a global variable
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        //this is how you get a reference to the audio system service
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>(); //we created a new arraylist from our new class Word
        // now we need to pass the words to be translated. Remember the Word class expects 4 inputs.

//        Word w = new Word("one", "lutti");
//        words.add(w); ---- this is one way to do it but there is a more concise way to do it
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));//this is the shortest way to create a new Word object and in the same time add it to the ArrayList words
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));


        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);//passing the ArrayList into the adapter -- it was String but now contains Word instances
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = words.get(position);

                releaseMediaPlayer();// we are releasing the memory usage at the start and in the end of the media played.

                //since we initialized the AudioManager instance we can now requestFocus on it - which returns an int and it is a constant value - and we do it after the release
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener //we need a focusListener
                        , AudioManager.STREAM_MUSIC, //use the music stream
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT); // and how long - in this case is temporary
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) { //we are checking to see if we got the focus
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getSound());
                    mMediaPlayer.start();
                    // we are releasing the memory usage at the start and in the end of the media played.
                    //also check the mCompletionListener
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });


    }



    @Override
    protected void onStop() {
        super.onStop(); 
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }


    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.

            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            //abandon audio focus when playback is complete.
            //unregisters the AudioFocusChangeListener so we dont get anymore callbacks
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}

