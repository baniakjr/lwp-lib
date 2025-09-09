package baniakjr.lwp.model.command.mode

import baniakjr.lwp.Command
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.ModeInformationType
import baniakjr.lwp.Port
import baniakjr.lwp.PortMode
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortModeInformationCommand

class BiasPortModeInformationCommand internal constructor(
    port: Wrapper<Port>,
    mode: Wrapper<PortMode>,
    val value: Int
): PortModeInformationCommand(port, mode, ModeInformationType.BIAS.wrap()) {

    override val payload: ByteArray
        get() = byteArrayOf(value.toByte())

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(!byteArray.isSpecificCommand(Command.PORT_MODE_INFORMATION) ||
                byteArray.size != ModeInformationType.LENGTH_WO_DATA + 1 ||
                !byteArray.isSpecificModeInformationType(ModeInformationType.BIAS)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val mode = Wrapper.wrap(PortMode::class.java, byteArray[PortMode.IN_INFORMATION_MESSAGE_INDEX])
            val value = byteArray[ModeInformationType.DATA_START_INDEX].toInt()
            return BiasPortModeInformationCommand(port, mode, value)
        }
    }

    override fun toString(): String {
        return "PortModeInformationCommand(port=$port, mode=$mode, informationType=$informationType, value=${value})"
    }

}