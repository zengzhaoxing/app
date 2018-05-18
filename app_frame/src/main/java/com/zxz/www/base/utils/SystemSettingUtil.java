package com.zxz.www.base.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.Settings;

import com.zxz.www.base.app.BaseApp;

/**
 * Created by 曾宪梓 on 2017/10/21.
 */

public class SystemSettingUtil {

    private static AudioManager mAudioManager = (AudioManager) BaseApp.getContext().getSystemService(Context.AUDIO_SERVICE);

    public static int getMusicVolume() {
        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public static int getMusicMaxVolume() {
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public static float getMusicProgress() {
        float current = getMusicVolume();
        float max = getMusicMaxVolume();
        return current / max;
    }

    public static void upMusicValue() {
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_PLAY_SOUND);
    }

    public static void downMusicValue() {
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_PLAY_SOUND);
    }

    public static void saveBrightness(float progress) {
        int value = (int) (progress * 255);
        Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        Settings.System.putInt(BaseApp.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
        BaseApp.getContext().getContentResolver().notifyChange(uri, null);
    }

    public static void upMusicValue(float progress) {
        int value = (int) (progress * getMusicMaxVolume() + getMusicVolume());
        value = Math.min(value, getMusicMaxVolume());
        value = Math.max(0, value);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,value,AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_PLAY_SOUND);
    }


}
