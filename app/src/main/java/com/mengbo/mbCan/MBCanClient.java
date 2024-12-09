package com.mengbo.mbCan;

import com.mengbo.mbCan.entity.MBAirCondition;
import com.mengbo.mbCan.entity.MBCanBookChargeTime;
import com.mengbo.mbCan.entity.MBCanSubscribeBase;
import com.mengbo.mbCan.entity.MBCanVehicleWindow;
import com.mengbo.mbCan.entity.MBMusicCloundness;
import com.mengbo.mbCan.interfaces.ICanBaseCallback;
import java.util.List;

/* loaded from: classes.dex */
public class MBCanClient {
    public static native void nativeCanUnInit();

    public native int nativeCanAudioGet(int i);

    public native int nativeCanAudioMix(byte b, byte b2, byte b3);

    public native int nativeCanAudioSet(int i, int i2);

    public native int nativeCanDmsGet(int i);

    public native int nativeCanDmsSet(int i, int i2);

    public native String nativeCanGetVersion(int i);

    public native int nativeCanInit(ICanBaseCallback iCanBaseCallback);

    public native int nativeCanInstumentSet(int i, byte[] bArr, int i2);

    public native int nativeCanRadioSet(int i, int i2);

    public native int nativeCanSetAirConditioner(MBAirCondition mBAirCondition);

    public native int nativeCanSetDateTime(int i, byte b, byte b2, byte b3, byte b4, byte b5, short s);

    public native int nativeCanSetKeyMode(int i);

    public native int nativeCanSetMusicLoudness(MBMusicCloundness mBMusicCloundness);

    public native int nativeCanSimulationStart(String str, int i);

    public native int nativeCanSimulationStop();

    public native int nativeCanSubscribe(List<MBCanSubscribeBase> list);

    public native int nativeCanUartRequestResult();

    public native int nativeCanUartTest(int i);

    public native int nativeCanUnSubscribe(List<MBCanSubscribeBase> list);

    public native int nativeCanUpgrade(int i, String str);

    public native int nativeCanVehicleGet(int i);

    public native String nativeCanVehicleGetString(int i);

    public native byte[] nativeCanVehicleGetValue(int i);

    public native int nativeCanVehicleSet(int i, int i2);

    public native int nativeCanVehicleSetBTParam(byte[] bArr);

    public native int nativeCanVehicleSetBookChg(MBCanBookChargeTime mBCanBookChargeTime);

    public native int nativeCanVehicleSetDvrParam(byte[] bArr);

    public native int nativeCanVehicleSetString(int i, String str);

    public native int nativeCanVehicleSetWindowSts(MBCanVehicleWindow mBCanVehicleWindow);

    public native Object nativeGetCanDataWithType(int i);

    public native List<String> nativeGetSimulationList();

    public native int nativeSetAPACoordinate(short s, short s2);
}
