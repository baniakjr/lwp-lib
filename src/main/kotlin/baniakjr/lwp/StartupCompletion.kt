package baniakjr.lwp

enum class StartupCompletion(override val value: Byte) : LWPByteValue {
    BUFFER_NO_FEEDBACK(0x00),
    BUFFER_WITH_FEEDBACK(0x01),
    IMMEDIATE_NO_FEEDBACK(0x10),
    IMMEDIATE_WITH_FEEDBACK(0x11);
}
