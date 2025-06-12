package baniakjr.lwp.model.command.mode

import baniakjr.lwp.*
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortModeInformationCommand

class GenericPortModeInformationCommand internal constructor(
    port: Wrapper<Port>,
    mode: Wrapper<PortMode>,
    informationType: Wrapper<ModeInformationType>,
    payload: ByteArray = byteArrayOf()
): PortModeInformationCommand(port, mode, informationType) {

    override val payload: ByteArray = payload
        get() = field.clone()

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(!byteArray.isSpecificCommand(Command.PORT_MODE_INFORMATION) || byteArray.size < ModeInformationType.LENGTH_WO_DATA + 1) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val mode = Wrapper.wrap(PortMode::class.java, byteArray[PortMode.IN_INFORMATION_MESSAGE_INDEX])
            val informationType = Wrapper.wrap(ModeInformationType::class.java, byteArray[ModeInformationType.IN_MESSAGE_INDEX])
            val payload = byteArray.copyOfRange(ModeInformationType.DATA_START_INDEX, byteArray.size)
            return GenericPortModeInformationCommand(port, mode, informationType, payload)
        }
    }

    override fun toString(): String {
        return "PortModeInformationCommand(port=$port, mode=$mode, informationType=$informationType, value=${LWP.convertToHexString(payload)})"
    }

}