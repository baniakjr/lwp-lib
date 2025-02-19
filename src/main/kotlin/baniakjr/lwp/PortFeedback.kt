package baniakjr.lwp

/**
 * Port feedback values.
 * Implements [LWPMaskValue]. These are bit mask values used with [LWPMask].
 */
enum class PortFeedback(override val value: Byte) : LWPMaskValue {
    IN_PROGRESS(0x01),
    COMPLETED(0x02),
    DISCARDED(0x04),
    IDLE(0x08),
    BUSY_FULL(0x10);
}
