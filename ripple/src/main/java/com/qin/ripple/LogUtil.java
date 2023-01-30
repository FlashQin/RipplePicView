package com.qin.ripple;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Locale;


public class LogUtil {
    /**
     * 日志标签
     */
    private static String APP_TAG = "amt-log";
    private static final boolean DEBUG_MODE = true;
    private static HashMap<String, String> sCachedTag = new HashMap<>();
    private static JsonFormatter sJsonFormatter = new DefaultFormatter();

    private LogUtil() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " cannot be instantiated");
    }

    public static void Init(@NonNull String appTag, @NonNull JsonFormatter formatter) {
        APP_TAG = appTag;
        sJsonFormatter = formatter;
    }

    public static void i(String message) {
        if (DEBUG_MODE)
            Log.i(buildTag(APP_TAG), buildMessage(message));
    }

    public static void d(String message, Context context) {
        Toast.makeText(context,"dsfsd",Toast.LENGTH_SHORT).show();
        if (DEBUG_MODE)
            Log.d(buildTag(APP_TAG), buildMessage(message));
    }
    public static void dall(String msg) {
        if (!DEBUG_MODE) {
            return;
        }
        if (msg.length() > 3000) {
            for (int i = 0; i < msg.length(); i += 3000) {
                //当前截取的长度<总长度则继续截取最大的长度来打印
                if (i + 3000 < msg.length()) {
                    Log.d(i + "哈哈", msg.substring(i, i + 3000));
                } else {
                    //当前截取的长度已经超过了总长度，则打印出剩下的全部信息
                    Log.d(APP_TAG, i + "哈哈" + msg.substring(i, msg.length()));
                }
            }
        } else {
            //直接打印
            Log.d(APP_TAG, msg);
        }


    }
    public static void w(String message) {
        if (DEBUG_MODE)
            Log.w(buildTag(APP_TAG), buildMessage(message));
    }

    public static void e(String message) {
        if (DEBUG_MODE)
            Log.e(buildTag(APP_TAG), buildMessage(message));
    }

    public static void v(String message) {
        if (DEBUG_MODE)
            Log.v(buildTag(APP_TAG), buildMessage(message));
    }

    public static void wtf(String message) {
        if (DEBUG_MODE)
            Log.wtf(buildTag(APP_TAG), buildMessage(message));
    }

    public static void json(String message) {
        if (DEBUG_MODE)
            Log.v(buildTag(APP_TAG), buildMessage(message));
    }

    public static void i(@NonNull String tag, String message) {
        Log.i(buildTag(tag), buildMessage(message));
    }

    public static void d(@NonNull String tag, String message) {
        Log.d(buildTag(tag), buildMessage(message));
    }

    public static void w(@NonNull String tag, String message) {
        Log.w(buildTag(tag), buildMessage(message));
    }

    public static void e(@NonNull String tag, String message) {
        Log.e(buildTag(tag), buildMessage(message));
    }

    public static void v(@NonNull String tag, String message) {
        Log.v(buildTag(tag), buildMessage(message));
    }

    public static void wtf(@NonNull String tag, String message) {
        Log.wtf(buildTag(tag), buildMessage(message));
    }

    public static void json(@NonNull String tag, String content) {
        Log.v(buildTag(tag), buildMessage(formatJson(content)));
    }

    private static String buildTag(@NonNull String tag) {
        String key = String.format(Locale.US, "%s@%s", tag, Thread.currentThread().getName());

        if (!sCachedTag.containsKey(key)) {
            if (APP_TAG.equals(tag)) {
                sCachedTag.put(key, String.format(Locale.US, "|%s|%s|",
                        tag,
                        Thread.currentThread().getName()
                ));
            } else {
                sCachedTag.put(key, String.format(Locale.US, "|%s_%s|%s|",
                        APP_TAG,
                        tag,
                        Thread.currentThread().getName()
                ));
            }
        }

        return sCachedTag.get(key);
    }

    private static String buildMessage(String message) {
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();

        if (traceElements == null || traceElements.length < 4) {
            return message;
        }
        StackTraceElement traceElement = traceElements[4];

        return String.format(Locale.US, "%s.%s(%s:%d) %s",
                traceElement.getClassName().substring(traceElement.getClassName().lastIndexOf(".") + 1),
                traceElement.getMethodName(),
                traceElement.getFileName(),
                traceElement.getLineNumber(),
                message
        );
    }

    private static String formatJson(String content) {
        return String.format(Locale.US, "\n%s", sJsonFormatter.formatJson(content));
    }

    public interface JsonFormatter {
        String formatJson(String content);
    }

    private static class DefaultFormatter implements JsonFormatter {
        @Override
        public String formatJson(String content) {
            return content;
        }
    }
}