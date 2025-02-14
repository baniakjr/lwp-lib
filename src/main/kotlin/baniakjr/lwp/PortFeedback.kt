package baniakjr.lwp

enum class PortFeedback(override val value: Byte) : LWPMaskValue {
    IN_PROGRESS(0x01),
    COMPLETED(0x02),
    DISCARDED(0x04),
    IDLE(0x08),
    BUSY_FULL(0x10);
}
