package baniakjr.lwp.model.command

import baniakjr.lwp.*
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class HubPropertyCommand internal constructor(
    val hubProperty: Wrapper<HubProperty>,
    val operation: Wrapper<HubPropertyOperation>,
    payload: ByteArray = byteArrayOf()
) : LWPCommand {

    val payload: ByteArray = payload
        get() = field.clone()

    override val command: Wrapper<Command> = Command.HUB_PROPERTY.wrap()

    override val byteValue: ByteArray
        get() = LWP.createCommand(byteArrayOf(command.value, hubProperty.value, operation.value) + payload)

    override fun toString(): String {
        return if (payload.isEmpty())
            "HubPropertyCommand(property=$hubProperty, operation=$operation)"
        else
            "HubPropertyCommand(property=$hubProperty, operation=$operation, payload=${payloadToString()})"
    }

    fun payloadToString(): String {
        return when (hubProperty.enum) {
            HubProperty.NAME -> LWP.convertToString(payload)
            HubProperty.MANUFACTURER -> LWP.convertToString(payload)
            HubProperty.RADIO_FW -> LWP.convertToString(payload)
            HubProperty.FW -> LWP.convertToVersionNumber(payload)
            HubProperty.HW -> LWP.convertToVersionNumber(payload)
            HubProperty.LWP -> LWP.convertToLWPVersion(payload)
            HubProperty.BATTERY_LEVEL -> "${payload[0]}%"
            else -> "${payload.contentToString()} H[${LWP.convertToHexString(payload)}]"
        }
    }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (byteArray.size < HubProperty.MSG_WO_DATA_LENGTH || !byteArray.isSpecificCommand(Command.HUB_PROPERTY)) {
                return MalformedCommand(byteArray)
            }
            val hubProperty = Wrapper.wrap(HubProperty::class.java, byteArray[HubProperty.IN_MESSAGE_INDEX])
            val hubOperation =
                Wrapper.wrap(HubPropertyOperation::class.java, byteArray[HubPropertyOperation.IN_MESSAGE_INDEX])
            return HubPropertyCommand(
                hubProperty,
                hubOperation,
                byteArray.copyOfRange(HubProperty.DATA_START_INDEX, byteArray.size)
            )
        }

        @JvmStatic
        fun build(
            hubProperty: HubProperty,
            operation: HubPropertyOperation,
            payload: ByteArray = byteArrayOf()
        ): HubPropertyCommand {
            return HubPropertyCommand(hubProperty.wrap(), operation.wrap(), payload)
        }
    }
}