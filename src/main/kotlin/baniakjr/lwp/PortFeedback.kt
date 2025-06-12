package baniakjr.lwp

/**
 * Port feedback values.
 * Implements [LWPMaskValue]. These are bit mask values used with [LWPMask].
 *
 * @property value The byte value of the enum.
 */
enum class PortFeedback(override val value: Byte) : LWPMaskValue {
    /** In progress. Value: 0x01 */
    IN_PROGRESS(0x01),
    /** Completed. Value: 0x02 */
    COMPLETED(0x02),
    /** Discarded. Value: 0x04 */
    DISCARDED(0x04),
    /** Idle. Value: 0x08 */
    IDLE(0x08),
    /** Busy full. Value: 0x10 */
    BUSY_FULL(0x10);
}
