package baniakjr.lwp.model.command

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.HubProperty
import baniakjr.lwp.definition.value.HubPropertyOperation
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
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
        get() = (byteArrayOf(command.value, hubProperty.value, operation.value) + payload).createCommand()

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
        @JvmOverloads
        fun build(
            hubProperty: HubProperty,
            operation: HubPropertyOperation,
            payload: ByteArray = byteArrayOf()
        ): HubPropertyCommand {
            return HubPropertyCommand(hubProperty.wrap(), operation.wrap(), payload)
        }

        @JvmStatic
        fun getValue(hubProperty: HubProperty): HubPropertyCommand {
            return build(hubProperty, HubPropertyOperation.REQUEST_UPDATE)
        }
    }
}