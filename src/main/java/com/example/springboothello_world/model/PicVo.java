package com.example.springboothello_world.model;

/**
 * @author yicha
 * @date 2022/4/4
 * @time 20:47
 */
public class PicVo {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public PicVo(String id, String baseCode) {
        this.id = id;
        this.baseCode = baseCode;
    }

    String id;
    String baseCode;

    public PicVo() {
    }
}
