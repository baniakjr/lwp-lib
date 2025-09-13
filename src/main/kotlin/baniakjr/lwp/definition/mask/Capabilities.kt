package baniakjr.lwp.definition.mask

import baniakjr.lwp.definition.LWPMaskValue

/**
 * Enum class representing the possible port mode capabilities. [PortInformationCommand][baniakjr.lwp.definition.value.Command.PORT_INFORMATION].
 * Implements [LWPMaskValue]. These are bit mask values used with [baniakjr.lwp.definition.LWPMask].
 */
enum class Capabilities(override val value: Byte) : LWPMaskValue {

    /** Output (seen from Hub). Value: 0x01 */
    OUTPUT(1),
    /** Input (seen from Hub). Value: 0x02 */
    INPUT(2),
    /** Logical Combinable. Value: 0x04 */
    COMBINABLE(4),
    /** Logical Synchronizable. Value: 0x08 */
    SYNCHRONIZABLE(8);

}