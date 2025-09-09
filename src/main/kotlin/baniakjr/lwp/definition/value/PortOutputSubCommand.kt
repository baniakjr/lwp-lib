package baniakjr.lwp.definition.value

import baniakjr.lwp.definition.LWPByteValue

/**
 * Port output subcommand used with [Port Output Command][Command.PORT_OUTPUT]
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#output-command-0x81-motor-sub-commands-0x01-0x3f](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#output-command-0x81-motor-sub-commands-0x01-0x3f)
 *
 * @property value Byte value of the port output subcommand.
 */
enum class PortOutputSubCommand(override val value: Byte) : LWPByteValue {
    /** Start Power Single. Value: 0x01 */
    START_POWER_SINGLE(0x01),
    /** Start Power Dual. Value: 0x02 */
    START_POWER_DUAL(0x02),
    /** Set Acc Time. Value: 0x05 */
    SET_ACC_TIME(0x05),
    /** Set Dec Time. Value: 0x06 */
    SET_DEC_TIME(0x06),
    /** Start Speed Single. Value: 0x07 */
    START_SPEED_SINGLE(0x07),
    /** Start Speed Dual. Value: 0x08 */
    START_SPEED_DUAL(0x08),
    /** Start Speed for Time Single. Value: 0x09 */
    START_SPEED_FOR_TIME_SINGLE(0x09),
    /** Start Speed for Time Dual. Value: 0x0A */
    START_SPEED_FOR_TIME_DUAL(0x0a),
    /** Start Speed for Degrees Single. Value: 0x0B */
    START_SPEED_FOR_DEG_SINGLE(0x0b),
    /** Start Speed for Degrees Dual. Value: 0x0C */
    START_SPEED_FOR_DEG_DUAL(0x0c),
    /** Goto Absolute Position Single. Value: 0x0D */
    GOTO_ABS_POS_SINGLE(0x0d),
    /** Goto Absolute Position Dual. Value: 0x0E */
    GOTO_ABS_POS_DUAL(0x0e),
    /** Write Direct. Value: 0x50 */
    WRITE_DIRECT(0x50),
    /** Write Direct Mode. Value: 0x51 */
    WRITE_DIRECT_MODE(0x51);

    companion object {
        const val IN_MESSAGE_INDEX: Int = 5

        const val LENGTH_WO_PAYLOAD: Int = 6
    }

}
