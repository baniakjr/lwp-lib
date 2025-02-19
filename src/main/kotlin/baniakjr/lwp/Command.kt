package baniakjr.lwp

/**
 * Command byte values for LWP messages
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#message-types](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#message-types)
 *
 * @property value Byte value of the command.
 */
enum class Command(override val value: Byte) : LWPByteValue {
    /** Hub property. Value 0x01 */
    HUB_PROPERTY(0x01),
    /** Hub action. Value 0x02 */
    HUB_ACTION(0x02),
    /** Alert. Value 0x03 */
    ALERT(0x03),
    /** Hub Attached IO. Value 0x04 */
    ATTACHED_IO(0x04),
    /** Generic Error Messages. Value 0x05 */
    ERROR(0x05),
    /** Hardware Network. Value 0x08 */
    HW_NETWORK(0x08),
    /** Port Information Request. Value 0x21 */
    PORT_INFORMATION_REQUEST(0x21),
    /** Port Mode Information Request. Value 0x22 */
    PORT_MODE_INFORMATION_REQUEST(0x22),
    /** Port Input Format Setup Single. Value 0x41 */
    PORT_INPUT_FORMAT_SETUP_SINGLE(0x41),
    /** Port Input Format Setup Combined. Value 0x42 */
    PORT_INPUT_FORMAT_SETUP_COMBINED(0x42),
    /** Port Information. Value 0x43 */
    PORT_INFORMATION(0x43),
    /** Port Mode Information. Value 0x44 */
    PORT_MODE_INFORMATION(0x44),
    /** Port Value Single. Value 0x45 */
    PORT_VALUE_SINGLE(0x45),
    /** Port Value Combined. Value 0x46 */
    PORT_VALUE_COMBINED(0x46),
    /** Port Input Format Single. Value 0x47 */
    PORT_INPUT_FORMAT_SINGLE(0x47),
    /** Port Input Format Combined. Value 0x48 */
    PORT_INPUT_FORMAT_COMBINED(0x48),
    /** Virtual Port Setup. Value 0x61 */
    VIRTUAL_PORT_SETUP(0x61),
    /** Port Output Command. Value 0x81 */
    PORT_OUTPUT(0x81.toByte()),
    /** Port Output Command Feedback. Value 0x82 */
    PORT_OUTPUT_COMMAND_FEEDBACK(0x82.toByte());

    companion object {
        /** Index of command byte in typical message */
        const val IN_MESSAGE_INDEX: Int = 2
    }
}
