package baniakjr.lwp

/**
 * Enum representing the startup completion mode of the commands.
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#st-comp](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#st-comp)
 *
 * @property value Byte value of mode.
 */
enum class StartupCompletion(override val value: Byte) : LWPByteValue {
    /** Buffer command no feedback. Value: 0x00 */
    BUFFER_NO_FEEDBACK(0x00),
    /** Buffer command with feedback. Value: 0x01 */
    BUFFER_WITH_FEEDBACK(0x01),
    /** Execute command immediately no feedback. Value: 0x10 */
    IMMEDIATE_NO_FEEDBACK(0x10),
    /** Execute command immediately with feedback. Value: 0x11 */
    IMMEDIATE_WITH_FEEDBACK(0x11);

    companion object {
        const val IN_MESSAGE_INDEX: Int = 4
    }
}
