package com.yl.newtaobaounion.utils;
//带有时间的缓存json数据类
public class CacheWithDuration {
    private String json;
    private long duration;

    public CacheWithDuration() {
    }

    public CacheWithDuration(String json, long duration) {
        this.json = json;
        this.duration = duration;
    }

    /**
     * 获取
     * @return json
     */
    public String getJson() {
        return json;
    }

    /**
     * 设置
     * @param json
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * 获取
     * @return duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * 设置
     * @param duration
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String toString() {
        return "CacheWithDuration{json = " + json + ", duration = " + duration + "}";
    }
}
