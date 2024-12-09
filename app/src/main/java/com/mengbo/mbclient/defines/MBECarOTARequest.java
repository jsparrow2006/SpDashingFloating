package com.mengbo.mbclient.defines;

public enum MBECarOTARequest {
    BASE_VALUE(60928, "Base value"),
    INSTALL_MCU(BASE_VALUE.value + 1, "Upgrading MCU"),
    INSTALL_CLUSTER(BASE_VALUE.value + 3, "Upgrading MCU"),
    RESTART_MCU(BASE_VALUE.value + 5, "MCU upgrade completed and restarted"),
    GET_MCU_VERSION(BASE_VALUE.value + 7, "Get the MCU version number"),
    GET_CLUSTER_VERSION(BASE_VALUE.value + 9, "Get the instrument version number"),
    INSTALL_IVIDISP(BASE_VALUE.value + 11, "Upgrade central control screen"),
    GET_IVIDISP_VERSION(BASE_VALUE.value + 13, "Get the central control screen version number"),
    INSTALL_CLUSTERDISP(BASE_VALUE.value + 15, "Upgraded instrument screen"),
    GET_CLUSTERDISP_VERSION(BASE_VALUE.value + 17, "Get the instrument screen version number");

    private final int value;
    private final String desc;

    MBECarOTARequest(int value, String desc) {
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
