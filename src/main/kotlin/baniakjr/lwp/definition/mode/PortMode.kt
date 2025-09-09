package baniakjr.lwp.definition.mode

import baniakjr.lwp.definition.Mode

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
    MODE_4(0x04),
    /** Mode 5. Value: 0x05 */
    MODE_5(0x05),
    /** Mode 6. Value: 0x06 */
    MODE_6(0x06),
    /** Mode 7. Value: 0x07 */
    MODE_7(0x07),
    /** Mode 8. Value: 0x08 */
    MODE_8(0x08),
    /** Mode 9. Value: 0x09 */
    MODE_9(0x09),
    /** Mode 10. Value: 0x0A */
    MODE_10(0x0A),
    /** Mode 11. Value: 0x0B */
    MODE_11(0x0B),
    /** Mode 12. Value: 0x0C */
    MODE_12(0x0C),
    /** Mode 13. Value: 0x0D */
    MODE_13(0x0D),
    /** Mode 14. Value: 0x0E */
    MODE_14(0x0E),
    /** Mode 15. Value: 0x0F */
    MODE_15(0x0F);

    companion object {
        const val IN_INFORMATION_MESSAGE_INDEX: Int = 4
    }
}
