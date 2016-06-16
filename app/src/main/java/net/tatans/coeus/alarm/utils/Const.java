package net.tatans.coeus.alarm.utils;

import net.tatans.coeus.alarm.R;

/**
 * Created by SiLiPing on 2016/5/30.
 */
public class Const {

    public static final int MINUTE = 60*1000;/**1分钟*/
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
    public static final String KEY_CODE = "key_code";/**按键设置 0,1,2：无、稍后、关闭*/
    public static final String[] REPEAT_MODEL_LIST = {"只响一次","每天","法定工作日","周一至周五","自定义"};/**重复模式*///"法定工作日",暂时移出来
    public static final int REQUEST_TIME = 1;
    public static final int REQUEST_REPEAT = 2;
    public static final int REQUEST_ALERT = 3;
    public static final int REQUEST_CUSTOM_WEEK = 4;
    public static final String REPEAT_PREF = "repeat_pref";/**0,1,2,3,4 与重复模式对应 */
    public static final int[] BELL_ID = {R.raw.bell0,R.raw.bell1,R.raw.bell2,R.raw.bell3,R.raw.bell4,R.raw.bell5,R.raw.bell6,R.raw.bell7,R.raw.bell8};/**铃声 */
    public static final String[] BELL_NAME = {"晨光","晨曦","嘀嗒","黎明","每一天","鸟语花香","轻快","清新","跳跃"};/**名称 */
    public static final String[] WEEKDAY = {"周一","周二","周三","周四","周五","周六","周日"};

    public static final String NO_STR = "。未选中。点按可切换";
    public static final String YES_STR = "。已选中。点按可切换";

}
