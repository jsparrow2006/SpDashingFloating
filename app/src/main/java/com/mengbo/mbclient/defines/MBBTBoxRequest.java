package com.mengbo.mbclient.defines;

public enum MBBTBoxRequest {
    BASE_VALUE(48640, "Base value"),
    TBOX_INFO(BASE_VALUE.value + 1, "Get TBOX information");

    private final int value;
    private final String desc;

    MBBTBoxRequest(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
