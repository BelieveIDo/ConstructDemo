package com.example.constructdemo.utils;

public class DoubleClickExit {
    /**
     * 双击退出检测, 阈值 1000ms
     */
    public static long mLastClick = 0L;
    private static final int THRESHOLD = 2000;// 1000ms

    public static boolean check() {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick < THRESHOLD;
        mLastClick = now;
        return b;
    }
}
