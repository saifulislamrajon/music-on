package com.example.saiful.musicon;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Player extends Fragment {
    TextView songName,time;
    Button btPv, btFv, btPlay, btFF, btNxt;
    SeekBar seekBar;
    String path = "";
    static Uri uri;
    MediaPlayer mediaPlayer;
    String message = "please you first play music";
    int position;
    int size;
    List list = new ArrayList();
    Thread updateSeekbar;
//    int currentPosition=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.player, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        songName = getActivity().findViewById(R.id.songName);
        time=getActivity().findViewById(R.id.time);
        btPv = getActivity().findViewById(R.id.btPv);
        btPv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    position = (position - 1 < 0) ? list.size() - 1 : position - 1;
                    ContactSong contactSong = (ContactSong) getItem(position);
                    path = contactSong.getPath();
                    String name = path.replace("http://saifulrajoncom.000webhostapp.com/music_folder/", "");
                    uri = Uri.parse(path);
                    songName.setText(name);
                    mediaPlayer = MediaPlayer.create(getActivity(), uri);
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());


                }
            }
        });
        btFv = getActivity().findViewById(R.id.btFV);
        btFv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
                }
            }
        });
        btPlay = getActivity().findViewById(R.id.btPlay);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } else {
                    if (mediaPlayer.isPlaying()) {
                        btPlay.setText(">");
                        mediaPlayer.pause();
                    } else {
                        btPlay.setText("||");
                        mediaPlayer.start();
                    }
                }
            }
        });
        btFF = getActivity().findViewById(R.id.btFF);
        btFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
                }
            }
        });
        btNxt = getActivity().findViewById(R.id.btNxt);
        btNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    position = (position + 1) % list.size();
                    ContactSong contactSong = (ContactSong) getItem(position);
                    path = contactSong.getPath();
                    String name = path.replace("http://saifulrajoncom.000webhostapp.com/music_folder/", "");
                    uri = Uri.parse(path);
                    songName.setText(name);
                    mediaPlayer = MediaPlayer.create(getActivity(), uri);
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                }
            }
        });
        seekBar = getActivity().findViewById(R.id.seekkBar);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
//            mediaPlayer.stop();
            mediaPlayer.release();
            Log.d("onPause", "when mediaplayer is back it's call");
        }

    }

    public void setSongName(String name, String path, ArrayList list) {
        songName.setText(name);
        this.path = path;
        this.list = list;
//        Thread
        updateSeekbar = new Thread() {
            @Override
            public void run() {
                super.run();
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while (currentPosition < totalDuration) {
                    try {
                        sleep(1000);
                        try {
                            currentPosition = mediaPlayer.getCurrentPosition();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        uri = Uri.parse(path);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(getActivity(), uri);
        mediaPlayer.start();
        updateSeekbar.start();


        btPlay.setText("||");
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btPlay.setText(">");
            }
        });
    }

    public Object getItem(int position) {
        return list.get(position);
    }
}
