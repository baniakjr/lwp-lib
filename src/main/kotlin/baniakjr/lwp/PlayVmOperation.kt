package baniakjr.lwp

/**
 * Enum class representing the possible operations that can be used in commands for the [Play VM port][Port.PLAYVM].
 * Implements [LWPMaskValue]. These are bit mask values used with [LWPMask].
 */
enum class PlayVmOperation(override val value: Byte) : LWPMaskValue {

    /** Breaks applied. Value: 0x01 */
    BREAKS(1),
    /** Power limit turned on. Value: 0x02 */
    POWER_LIMIT(2),
    /** Lights turned off. Value: 0x04 */
    LIGHTS_OFF(4),
    /** Auto Calibrate. Should be used separately, preceded by [PlayVmOperation.INIT] Value: 0x08 */
    CALIBRATE(8),
    /** Initialize. Should be used separately, followed by [PlayVmOperation.CALIBRATE] Value: 0x10 */
    INIT(16);

}
