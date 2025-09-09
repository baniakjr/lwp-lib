package baniakjr.lwp

/**
 * Enum class representing the six LEDs on the Move Hub that can be used in commands for the [Six LED port][Port.SIX_LED].
 * Implements [LWPMaskValue]. These are bit mask values used with [LWPMask].
 *
 * @property value The byte value of the enum.
 */
enum class SixLed(override val value: Byte) : LWPMaskValue {
    /** Front outer left LED. Value: 0x01 */
    FRONT_OUTER_LEFT(1),
    /** Front inner right LED. Value: 0x02 */
    FRONT_INNER_RIGHT(2),
    /** Front inner left LED. Value: 0x04 */
    FRONT_INNER_LEFT(4),
    /** Front outer right LED. Value: 0x08 */
    FRONT_OUTER_RIGHT(8),
    /** Back left LED. Value: 0x10 */
    BACK_LEFT(16),
    /** Back right LED. Value: 0x20 */
    BACK_RIGHT(32);
}