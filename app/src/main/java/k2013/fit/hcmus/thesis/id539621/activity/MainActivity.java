package k2013.fit.hcmus.thesis.id539621.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import k2013.fit.hcmus.thesis.id539621.util.JSONResourceReader;
import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.game_operation.GamePlayParams;
import k2013.fit.hcmus.thesis.id539621.model.GameLevel;
import k2013.fit.hcmus.thesis.id539621.sound.BinauralSound;

public class MainActivity extends BaseActivity {

    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1231;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1232;
    private GameLevel[] levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        TextView title = (TextView) findViewById(R.id.main_title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/pacifico-regular.ttf");
        title.setTypeface(tf);

        loadGameSetting();
        loadGameData();

        BinauralSound.openDevice();
        BinauralSound.setListenerOrientation(0,0,-1,0,1,0);
    }

    private void loadGameData(){
        JSONResourceReader reader = new JSONResourceReader(getResources(), R.raw.gamelevel);
        levels = reader.constructUsingGson(GameLevel[].class);

        SharedPreferences sharedPreferences= this.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
        if (sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String jsonString = gson.toJson(levels);
            editor.putString("gameLevels", jsonString);
            editor.apply();
        }
    }

    public static void storeData(Context context){

        File rootDirApp = new File(Environment.getExternalStorageDirectory() + "/TinnitusRelief");
        File output = new File(rootDirApp, ".nomedia");
        try {
            output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int[] mDatas = new int[] { R.raw.bird, R.raw.cat, R.raw.dog, R.raw.mosquito, R.raw.lion, R.raw.owl, R.raw.pig };
        for (int i = 0; i < mDatas.length; i++) {
            try {
                String path = Environment.getExternalStorageDirectory() + "/TinnitusRelief/Package1/Target";
                File dir = new File(path);
                if (dir.mkdirs() || dir.isDirectory()) {
                    String str_song_name = i + ".wav";
                    CopyRAWtoSDCard(context, mDatas[i], path + File.separator + str_song_name);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final int[] mBackgroundSoundDatas = new int[] { R.raw.backgroundrain, R.raw.backgroundocean, R.raw.bgsound01, R.raw.bgsound02,
        R.raw.bgsound03, R.raw.bgsound04, R.raw.bgsound05, R.raw.bgsound06, R.raw.bgsound07, R.raw.bgsound08, R.raw.bgsound09, R.raw.bgsound10};
        for (int i = 0; i < mBackgroundSoundDatas.length; i++) {
            try {
                String path = Environment.getExternalStorageDirectory() + "/TinnitusRelief/Package1/BackgroundSound";
                File dir = new File(path);
                if (dir.mkdirs() || dir.isDirectory()) {
                    String str_song_name = i + ".wav";
                    CopyRAWtoSDCard(context, mBackgroundSoundDatas[i], path + File.separator + str_song_name);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final int[] mDistractDatas = new int[] { R.raw.cow, R.raw.bee, R.raw.duck, R.raw.elephant };
        for (int i = 0; i < mDistractDatas.length; i++) {
            try {
                String path = Environment.getExternalStorageDirectory() + "/TinnitusRelief/Package1/DistractSound";
                File dir = new File(path);
                if (dir.mkdirs() || dir.isDirectory()) {
                    String str_song_name = i + ".wav";
                    CopyRAWtoSDCard(context, mDistractDatas[i], path + File.separator + str_song_name);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadGameSetting()  {
        SharedPreferences sharedPreferences= this.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
        if(sharedPreferences != null)  {
            int gameMode = sharedPreferences.getInt("gameMode", -1);
            if(gameMode == -1) {

                SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
                    gameMode = GamePlayParams.MODE_SENSOR;
                } else {
                    gameMode = GamePlayParams.MODE_TOUCH;
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasSensor", gameMode == GamePlayParams.MODE_SENSOR);
                editor.apply();
            }
            boolean hasData = sharedPreferences.getBoolean("gameData", false);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                if(hasData == false){
                    storeData(this);
                }
                hasData = true;
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("gameMode", gameMode);
            editor.putBoolean("gameData", hasData);
            editor.apply();
        }
    }

    public void mainOnClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_play: {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                } else {
                    Intent intent = new Intent(this, GameSelectionActivity.class);
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(levels);
                    intent.putExtra("GameLevels", jsonString);
                    startActivity(intent);
                }
                break;
            }

            case R.id.main_btn_help: {
                Intent intent = new Intent(this, HowToActivity.class);
                intent.putExtra(getString(R.string.a_howto_intent_from_help), true);
                startActivity(intent);

                break;
            }

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, GamePlayActivity.class);
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(levels);
                    intent.putExtra("GameLevels", jsonString);
                    startActivity(intent);
                }

                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    storeData(this);
                    SharedPreferences sharedPreferences= this.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("gameData", true);
                    editor.apply();
                }

                return;
            }
        }
    }

    private static void CopyRAWtoSDCard(Context context ,int id, String path) throws IOException {
        InputStream in = context.getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BinauralSound.closeDevice();
    }
}
