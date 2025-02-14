package baniakjr.lwp

enum class SixLed(override val value: Byte) : LWPMaskValue {
    FRONT_OUTER_LEFT(1),
    FRONT_INNER_RIGHT(2),
    FRONT_INNER_LEFT(4),
    FRONT_OUTER_RIGHT(8),
    BACK_LEFT(16),
    BACK_RIGHT(32);
}
