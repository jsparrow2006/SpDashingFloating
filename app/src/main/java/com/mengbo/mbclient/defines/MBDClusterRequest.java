package com.mengbo.mbclient.defines;

public enum MBDClusterRequest {
    BASE_VALUE(56832, "Base value"),
    CLUSTER_TYPE_VERSION(BASE_VALUE.value + 1, "Get instrument version information"),
    CLUSTER_TYPE_TIMEFORMAT(BASE_VALUE.value + 3, "Request instrument time format"),
    CLUSTER_TYPE_THEME(BASE_VALUE.value + 4, "Request Instrument Theme"),
    CLUSTER_TYPE_LANGUAGE(BASE_VALUE.value + 5, "Instrument language setting"),
    CLUSTER_TYPE_MAP(BASE_VALUE.value + 7, "Send map status");

    private final int value;
    private final String desc;

    MBDClusterRequest(int value, String desc) {
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
