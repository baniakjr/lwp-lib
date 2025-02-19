package baniakjr.lwp

/**
 * Enum class representing HubProperties for [Hub Property Command][Command.HUB_PROPERTY]
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-properties](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-properties)
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-property-and-operation](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-property-and-operation)
 *
 * @property value Byte value of the HubProperty.
 */
enum class HubProperty(override val value: Byte) : LWPByteValue {
    /** Name. Value: 0x01 */
    NAME(0x01),
    /** Button. Value: 0x02 */
    BUTTON(0x02),
    /** FW Version. Value: 0x03 */
    FW(0x03),
    /** HW Version. Value: 0x04 */
    HW(0x04),
    /** RSSI. Value: 0x05 */
    RSSI(0x05),
    /** Battery Level. Value: 0x06 */
    BATTERY_LEVEL(0x06),
    /** Battery Type. Value: 0x07 */
    BATTERY_TYPE(0x07),
    /** Manufacturer. Value: 0x08 */
    MANUFACTURER(0x08),
    /** Radio FW Version. Value: 0x09 */
    RADIO_FW(0x09),
    /** LWP Version. Value: 0x0A */
    LWP(0x0A),
    /** System Type. Value: 0x0B */
    SYSTEM_TYPE(0x0B),
    /** HW Network ID. Value: 0x0C */
    HW_NETWORK_ID(0x0C),
    /** MAC 1. Value: 0x0D */
    MAC_1(0x0D),
    /** MAC 2. Value: 0x0E */
    MAC_2(0x0E),
    /** Network Family. Value: 0x0F */
    HW_NETWORK_FAMILY(0x0F);

    companion object {
        const val MSG_WO_DATA_LENGTH: Int = 5
        const val MINIMAL_MSG_LENGTH: Int = MSG_WO_DATA_LENGTH + 1
        const val DATA_START_INDEX: Int = MSG_WO_DATA_LENGTH

        /** Index of HubProperty byte in [Hub Property Command message][Command.HUB_PROPERTY] */
        const val IN_MESSAGE_INDEX: Int = 3
    }
}
