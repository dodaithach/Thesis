package k2013.fit.hcmus.thesis.id539621.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.asha.vrlib.MDVRLibrary;
import com.asha.vrlib.texture.MD360BitmapTexture;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import k2013.fit.hcmus.thesis.id539621.R;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

public class GamePlayActivity extends MD360PlayerActivity {
    private Uri mCurrentUri;
    private Target mTarget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected MDVRLibrary createVRLibrary() {
        return MDVRLibrary.with(this)
                .displayMode(MDVRLibrary.DISPLAY_MODE_NORMAL)
                .interactiveMode(MDVRLibrary.INTERACTIVE_MODE_TOUCH)
                .asBitmap(new MDVRLibrary.IBitmapProvider() {
                    @Override
                    public void onProvideBitmap(final MD360BitmapTexture.Callback callback) {
                        loadImage(currentUri(), callback);
                    }
                }).build(R.id.gameplay_glview);
    }

    private void loadImage(Uri uri, final MD360BitmapTexture.Callback callback) {
        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // notify if size changed
                getVRLibrary().onTextureResize(bitmap.getWidth(), bitmap.getHeight());

                // texture
                callback.texture(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.with(getApplicationContext()).load(uri).resize(3072, 2048).centerInside().memoryPolicy(NO_CACHE, NO_STORE).into(mTarget);
    }

    private Uri currentUri(){
        if (mCurrentUri == null){
            return getUri();
        } else {
            return mCurrentUri;
        }
    }

    protected Uri getUri() {
        try {
            Uri res = Uri.parse("android.resource://k2013.fit.hcmus.thesis.id539621/drawable/bitmap360");
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}