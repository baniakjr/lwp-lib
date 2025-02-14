package baniakjr.lwp

enum class PlayVmOperation(override val value: Byte) : LWPMaskValue {

    BREAKS(1),
    POWER_LIMIT(2),
    LIGHTS_OFF(4),
    CALIBRATE(8),
    INIT(16);

}
