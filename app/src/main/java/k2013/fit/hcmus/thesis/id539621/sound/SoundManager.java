package k2013.fit.hcmus.thesis.id539621.sound;


import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trieu on 4/7/2017.
 */

public class SoundManager {

    private MediaPlayer mediaPlayer;
    private boolean isPaused = false;
    private int pausedPosition = -1;

    int loopMode = 0; // 0: No Loop, 1: Loop 1 track, 2: Loop list

    private List<File> soundList;
    private int currentPos;

    public SoundManager(){
        mediaPlayer = new MediaPlayer();
        soundList = getListFiles(new File(Environment.getExternalStorageDirectory() + "/TinnitusRelief/Sounds"));
        currentPos = 0;
    }

    private void play(final String fileName) {
        if (mediaPlayer != null) {
            if (!mediaPlayer.isPlaying()) {
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(fileName);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            switch (loopMode){
                                case 1:
                                    play(fileName);
                                    break;
                                case 2:
                                    playNextSoundTrack();
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    mediaPlayer.start();
                } catch (Exception e) {

                }
            }
        }
    }

    public void pause() {
        if(isPaused)
            return;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                pausedPosition = mediaPlayer.getCurrentPosition();
                isPaused = true;
            }
        }
    }

    public boolean play() {
        if (mediaPlayer != null) {
            if (!mediaPlayer.isPlaying()) {
                if (isPaused) {
                    mediaPlayer.seekTo(pausedPosition);
                    mediaPlayer.start();
                    pausedPosition = -1;
                    isPaused = false;
                    return true;
                } else if(!soundList.isEmpty()){
                    play(soundList.get(currentPos).getPath());
                    return true;
                }
            }
        }
        return false;
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();

        if(files == null){
            files = parentDir.listFiles();
            if(files == null){
                return inFiles;
            }
        }
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".ogg")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }

    public void playNextSoundTrack() {
        if (soundList.size() != 0) {
            currentPos = (currentPos + 1)%soundList.size();
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
            play(soundList.get(currentPos).getPath());
        }
    }

    public void playPrevSoundTrack() {
        if (soundList.size() != 0) {
            currentPos = (currentPos - 1 + soundList.size())%soundList.size();
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
            play(soundList.get(currentPos).getPath());
        }
    }

    public void setVolume(float volume){
        if(mediaPlayer != null){
            mediaPlayer.setVolume(volume, volume);
        }
    }

    public void finish(){
        mediaPlayer.release();
    }
}
