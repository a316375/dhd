package xyxgame.dhd.Save;

import android.content.Context;
import android.content.SharedPreferences;

public class SavePoint {
    public static int  getShowA(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "SaveSetting", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("showA",0);
    }
    public static int  saveShowA(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "SaveSetting", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("showA",0);
    }
}
