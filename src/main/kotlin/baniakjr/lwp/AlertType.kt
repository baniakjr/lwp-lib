package baniakjr.lwp

/**
 * Enum class representing alert types for [Hub Alert Command][Command.ALERT]
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#alert-type](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#alert-type)
 *
 * @property value Byte value of the alert type.
 */
enum class AlertType(override val value: Byte) : LWPByteValue {
    /** Low Voltage. Value: 0x01 */
    LOW_VOLTAGE(0x01),
    /** High Current. Value: 0x02 */
    HIGH_CURRENT(0x02),
    /** Low Signal. Value: 0x03 */
    LOW_SIGNAL(0x03),
    /** OVER_POWER. Value: 0x04 */
    OVER_POWER(0x04),
    /** Unknown Alert. Value 0x06
     *
     * Used by Control+ app with LEGO Porsche 42176 (LEGO Technic Move Hub 88019)
     */
    ALERT_06(0x06),
    /** Unknown Alert. Value 0x07
     *
     * Used by Control+ app with LEGO Porsche 42176 (LEGO Technic Move Hub 88019)
     */
    ALERT_07(0x07);

    companion object {
        const val MSG_WO_DATA_LENGTH: Int = 5
        const val MAX_MSG_LENGTH: Int = MSG_WO_DATA_LENGTH + 1
        const val DATA_INDEX: Int = MSG_WO_DATA_LENGTH

        /** Status ERROR byte value 0xFF*/
        const val STATUS_ERROR: Byte = 0xFF.toByte()
        /** Status OK byte value 0x00*/
        const val STATUS_OK: Byte = 0x00.toByte()

        /** Index of alert type byte in Alert Command message */
        const val IN_MESSAGE_INDEX: Int = 3
    }
}
