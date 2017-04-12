package com.hq.app.olaf.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hq.app.olaf.ui.base.HqPayApplication;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;

/**
 * Created by huwentao on 16-4-25.
 */
public class SharedPrefUtil {
    public final static String DEFAULT_CONFIG_NAME = "DEFAULT_CONFIG_NAME";
    private SharedPreferences sharedPref = null;

    /**
     * @param context
     * @param prefName
     * @return
     */
    public SharedPrefUtil init(Context context, String prefName) {
        if (TextUtils.isEmpty(prefName)) {
            SharedPreferences defaultPref = context.getSharedPreferences(DEFAULT_CONFIG_NAME, Context.MODE_PRIVATE);
            sharedPref = defaultPref;
        } else {
            sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        }
        return this;
    }

    /**
     * 保存一个对象到sharedPreference
     *
     * @param obj
     * @return
     */
    public SharedPrefUtil save(Object obj) {
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        Field tempField = null;
        if (fields.length > 0) {
            try {
                SharedPreferences.Editor editor = sharedPref.edit();
                for (Field field : fields) {
                    tempField = field;
                    field.setAccessible(true);
                    if (String.class.isAssignableFrom(field.getType())) {
                        editor.putString(field.getName(), (String) field.get(obj));
                    } else if (Boolean.class.isAssignableFrom(field.getType())
                            || boolean.class.isAssignableFrom(field.getType())) {
                        editor.putBoolean(field.getName(), field.getBoolean(obj));
                    } else if (Long.class.isAssignableFrom(field.getType())
                            || long.class.isAssignableFrom(field.getType())) {
                        editor.putLong(field.getName(), field.getLong(obj));
                    } else if (Integer.class.isAssignableFrom(field.getType()) ||
                            int.class.isAssignableFrom(field.getType())) {
                        editor.putInt(field.getName(), field.getInt(obj));
                    } else if (Float.class.isAssignableFrom(field.getType()) ||
                            float.class.isAssignableFrom(field.getType())) {
                        editor.putFloat(field.getName(), field.getFloat(obj));
                    }
                }
                editor.apply();
            } catch (IllegalAccessException e) {
                Logger.e(e.getMessage(), tempField, obj, e);
            }
        }
        return this;
    }

    /**
     * 读取一个指定值
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T loadObj(Class<T> tClass) {
        Field[] fields = tClass.getDeclaredFields();
        Field tempField = null;
        try {
            T t = tClass.newInstance();
            if (fields.length > 0) {
                try {
                    for (Field field : fields) {
                        tempField = field;
                        field.setAccessible(true);
                        if (String.class.isAssignableFrom(field.getType())) {
                            field.set(t, sharedPref.getString(field.getName(), ""));
                        } else if (Boolean.class.isAssignableFrom(field.getType())
                                || boolean.class.isAssignableFrom(field.getType())) {
                            field.set(t, sharedPref.getBoolean(field.getName(), false));
                        } else if (Long.class.isAssignableFrom(field.getType())
                                || long.class.isAssignableFrom(field.getType())) {
                            field.set(t, sharedPref.getLong(field.getName(), 0));
                        } else if (Integer.class.isAssignableFrom(field.getType()) ||
                                int.class.isAssignableFrom(field.getType())) {
                            field.set(t, sharedPref.getInt(field.getName(), 0));
                        } else if (Float.class.isAssignableFrom(field.getType()) ||
                                float.class.isAssignableFrom(field.getType())) {
                            field.set(t, sharedPref.getFloat(field.getName(), 0));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return t;
        } catch (Exception e) {
            Logger.e(e, "exception fielde=%s", tempField);
        }
        return null;
    }

    /**
     * 清除保存的像数据
     *
     * @param tClass
     * @param <T>
     */
    public <T> void clearObj(Class<T> tClass) {
        Field[] fields = tClass.getDeclaredFields();
        try {
            T t = tClass.newInstance();
            if (fields.length > 0) {
                for (Field field : fields) {
                    clear(field.getName());
                }
            }
        } catch (Exception e) {
            Logger.e(e, "%s没有默认的构造方法", tClass.getSimpleName());
            Logger.e(e, e.getMessage());
        }
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public SharedPrefUtil save(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
        return this;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public SharedPrefUtil save(String key, Long value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        editor.apply();
        return this;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public SharedPrefUtil save(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
        return this;
    }

    /**
     * @param key
     * @param dvalue
     * @return
     */
    public boolean load(String key, Boolean dvalue) {
        return sharedPref.getBoolean(key, dvalue);
    }

    /**
     * @param key
     * @param dvalue
     * @return
     */
    public long load(String key, Long dvalue) {
        return sharedPref.getLong(key, dvalue);
    }

    /**
     * @param key
     * @return
     */
    public String load(String key) {
        return sharedPref.getString(key, null);
    }

    /**
     * @param key
     */
    public void clear(String key) {
        sharedPref.edit().remove(key).apply();
    }

    private static class SingletonHolder {
        private static SharedPrefUtil instance = new SharedPrefUtil();
    }

    public static SharedPrefUtil getInstance() {
        SharedPrefUtil prefUtil = SingletonHolder.instance;
        prefUtil.init(HqPayApplication.getAppContext(), null);
        return prefUtil;
    }

    public static SharedPrefUtil getInstance(String fileName) {
        SharedPrefUtil prefUtil = SingletonHolder.instance;
        prefUtil.init(HqPayApplication.getAppContext(), fileName);
        return prefUtil;
    }
}
