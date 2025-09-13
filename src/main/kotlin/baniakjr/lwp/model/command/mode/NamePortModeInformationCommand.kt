package baniakjr.lwp.model.command.mode

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.mode.PortMode
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.ModeInformationType
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortModeInformationCommand

class NamePortModeInformationCommand internal constructor(
    port: Wrapper<Port>,
    mode: Wrapper<PortMode>,
    payload: ByteArray = byteArrayOf()
): PortModeInformationCommand(port, mode, ModeInformationType.NAME.wrap()) {

    override val payload: ByteArray = payload
        get() = field.clone()

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(!byteArray.isSpecificCommand(Command.PORT_MODE_INFORMATION) ||
                byteArray.size < ModeInformationType.LENGTH_WO_DATA + 1 ||
                byteArray.size > ModeInformationType.LENGTH_WO_DATA + 11 ||
                !byteArray.isSpecificModeInformationType(ModeInformationType.NAME)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val mode = Wrapper.wrap(PortMode::class.java, byteArray[PortMode.IN_INFORMATION_MESSAGE_INDEX])
            val payload = byteArray.copyOfRange(ModeInformationType.DATA_START_INDEX, byteArray.size)
            return NamePortModeInformationCommand(port, mode, payload)
        }
    }

    override fun toString(): String {
        return "PortModeInformationCommand(port=$port, mode=$mode, informationType=${ModeInformationType.NAME}, value=${LWP.convertToString(payload)})"
    }

}