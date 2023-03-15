package com.sikderIthub.slauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;

import com.sikderIthub.slauncher.models.Apps;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Apps> getInstalledAppList(Context context) {
        List<Apps> list = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> untreatedAppList = context.getPackageManager().queryIntentActivities(intent, 0);

        for(ResolveInfo untreatedApp : untreatedAppList){
            String appName = untreatedApp.activityInfo.loadLabel(context.getPackageManager()).toString();
            String appPackageName = untreatedApp.activityInfo.packageName;
            Drawable appImage = untreatedApp.activityInfo.loadIcon(context.getPackageManager());

            Apps app = new Apps(appPackageName, appName, appImage, true);
            if (!list.contains(app))
                list.add(app);
        }
        return list;
    }
}
