package net.tatans.coeus.alarm.utils;

/**
 * Created by SiLiPing on 2016/5/30.
 */
public class Const {

    public static final String KEY_MINUTE = "分钟";/**分钟*/
    public static final String KEY_MINUTE_STOP = KEY_MINUTE+"后停止";/**多少分钟后停止*/
    public static final String KEY_ALARM_IN_SILENT_MODE = "alarm_in_silent_mode";/**用于存储静音响铃开关*/
    public static final String KEY_ALARM_BELL_TIME = "alarm_bell_time";/**用于存储响铃时间*/
    public static final String[] BELL_TIME_LIST = {"0","1","5","10","15","20","25","30"};/**响铃时间列表*/
    public static final String KEY_NEVER_STOP = "永不停止";
    public static final String KEY_TEN_MINUTE = "10";
    public static final String KEY_TEN_MINUTE_STOP = "10";
    public static final String KEY_ALARM_BELL_INTERVAL_TIME = "alarm_bell_interval_time";/**用于存储稍后响铃时间*/
    public static final String[] BELL_INTERVAL_TIME_LIST = {"1","5","10","15","20","25","30"};/**稍后响铃列表*/
}
