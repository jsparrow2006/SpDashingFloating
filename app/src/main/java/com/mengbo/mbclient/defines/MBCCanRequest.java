package com.mengbo.mbclient.defines;

public enum MBCCanRequest {
    BASE_VALUE(52736, "Base value"),
    CAN_VIN_CODE(BASE_VALUE.value + 7, "Get VIN"),
    CAN_SOFT_CONFIG(BASE_VALUE.value + 9, "Get soft config"),
    CAN_GEAR(BASE_VALUE.value + 11, "Get Gear"),
    CAN_OPEN_WINDOW(BASE_VALUE.value + 13, "Get open window"),
    CAN_GET_ALARM_MODE(BASE_VALUE.value + 15, "Get alarm mode"),
    CAN_RESET_SOC_FACTORY(BASE_VALUE.value + 17, "Factory reset"),
    CAN_SET_FRAGRANCE(BASE_VALUE.value + 19, "Set fragrance"),
    CAN_GET_FRAGRANCE(BASE_VALUE.value + 21, "Get fragrance"),
    CAN_PLAY_POP_SOUND(BASE_VALUE.value + 23, "Play Can sound"),
    CAN_GET_WINDOW_STATE(BASE_VALUE.value + 25, "Get window state"),
    CAN_SET_VEHICLE_PROPERTY(BASE_VALUE.value + 27, "Set vehicle property"),
    CAN_GET_VEHICLE_PROPERTY(BASE_VALUE.value + 29, "Get vehicle property");

    private final int value;
    private final String desc;

    MBCCanRequest(int value, String desc) {
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
