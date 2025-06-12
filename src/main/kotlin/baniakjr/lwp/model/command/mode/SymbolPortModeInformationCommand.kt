package baniakjr.lwp.model.command.mode

import baniakjr.lwp.*
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortModeInformationCommand

class SymbolPortModeInformationCommand internal constructor(
    port: Wrapper<Port>,
    mode: Wrapper<PortMode>,
    payload: ByteArray = byteArrayOf()
): PortModeInformationCommand(port, mode, ModeInformationType.SYMBOL.wrap()) {

    override val payload: ByteArray = payload
        get() = field.clone()

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(!byteArray.isSpecificCommand(Command.PORT_MODE_INFORMATION) || byteArray.size < ModeInformationType.LENGTH_WO_DATA + 1 || byteArray.size > ModeInformationType.LENGTH_WO_DATA + 5 || !byteArray.isSpecificModeInformationType(ModeInformationType.SYMBOL)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val mode = Wrapper.wrap(PortMode::class.java, byteArray[PortMode.IN_INFORMATION_MESSAGE_INDEX])
            val payload = byteArray.copyOfRange(ModeInformationType.DATA_START_INDEX, byteArray.size)
            return SymbolPortModeInformationCommand(port, mode, payload)
        }
    }

    override fun toString(): String {
        return "PortModeInformationCommand(port=$port, mode=$mode, informationType=${ModeInformationType.SYMBOL}, value=${LWP.convertToString(payload)})"
    }

}