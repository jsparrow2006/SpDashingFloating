package com.mengbo.mbclient.defines;

public enum MBFCommonRequest {
    BASE_VALUE(65024, "Base value"),
    OBSERVER_APP_START(BASE_VALUE.value + 1, "Enable APP notifications");

    private final int value;
    private final String desc;

    MBFCommonRequest(int value, String desc) {
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
