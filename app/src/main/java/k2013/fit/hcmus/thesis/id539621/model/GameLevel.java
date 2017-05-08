package k2013.fit.hcmus.thesis.id539621.model;

import java.io.Serializable;

/**
 * Created by Trieu on 4/5/2017.
 */

public class GameLevel implements Serializable {
    int level;
    int time;
    boolean has_background_sound;
    int distract_sound;
    boolean has_horizontal;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isHas_background_sound() {
        return has_background_sound;
    }

    public void setHas_background_sound(boolean has_background_sound) {
        this.has_background_sound = has_background_sound;
    }

    public int getDistract_sound() {
        return distract_sound;
    }

    public void setDistract_sound(int distract_sound) {
        this.distract_sound = distract_sound;
    }

    public boolean isHas_horizontal() {
        return has_horizontal;
    }

    public void setHas_horizontal(boolean has_horizontal) {
        this.has_horizontal = has_horizontal;
    }
}
