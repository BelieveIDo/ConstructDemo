package com.example.constructdemo;

import android.content.Context;

import com.example.constructdemo.utils.PLog;
import com.example.constructdemo.utils.VersionUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 程序崩溃捕捉
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static Thread.UncaughtExceptionHandler mDefaultHandler = null;

    private Context mContext;

    private final String TAG = CrashHandler.class.getSimpleName();

    private CrashHandler(Context context) {
        this.mContext = context;
    }

    /**
     * 初始化,设置该CrashHandler为程序的默认处理器
     */
    public static void init(Context context) {
        CrashHandler crashHandler=new CrashHandler(context);
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.out.println(ex.toString());
        PLog.e(TAG, ex.toString());
        PLog.e(TAG, collectCrashDeviceInfo());
        PLog.e(TAG, getCrashInfo(ex));

        // 调用系统错误机制
        mDefaultHandler.uncaughtException(thread, ex);
    }

    /**
     * 得到程序崩溃的详细信息
     */
    private String getCrashInfo(Throwable ex) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        ex.setStackTrace(ex.getStackTrace());
        ex.printStackTrace(printWriter);
        return result.toString();
    }

    /**
     * 收集程序崩溃的设备信息
     */
    private String collectCrashDeviceInfo() {

        String versionName = VersionUtil.getVersion(mContext);
        String model = android.os.Build.MODEL;
        String androidVersion = android.os.Build.VERSION.RELEASE;
        String manufacturer = android.os.Build.MANUFACTURER;

        return versionName + "  " + model + "  " + androidVersion + "  " + manufacturer;
    }
}
