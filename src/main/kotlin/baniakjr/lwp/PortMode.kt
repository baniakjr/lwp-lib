package baniakjr.lwp

/**
 * Enum class representing the different modes of the port.
 */
enum class PortMode(override val value: Byte) : Mode {
    /** Mode 0. Value: 0x00 */
    MODE_0(0x00),
    /** Mode 1. Value: 0x01 */
    MODE_1(0x01),
    /** Mode 2. Value: 0x02 */
    MODE_2(0x02),
    /** Mode 3. Value: 0x03 */
    MODE_3(0x03),
    /** Mode 4. Value: 0x04 */
    MODE_4(0x04);
}
