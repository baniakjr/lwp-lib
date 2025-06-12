package baniakjr.lwp.model.command.output

import baniakjr.lwp.Command
import baniakjr.lwp.Port
import baniakjr.lwp.StartupCompletion
import baniakjr.lwp.model.command.PortOutputCommand
import baniakjr.lwp.PortOutputSubCommand
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand

/**
 * Abstract base class for commands that set acceleration or deceleration time.
 */
abstract class SetTimeCommandBase(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    val time: Int,
    val profile: Byte,
    subCommand: Wrapper<PortOutputSubCommand>
) : PortOutputCommand(port, action, subCommand) {

    override val payload: ByteArray = byteArrayOf(
        (time and 0xFF).toByte(),
        ((time shr 8) and 0xFF).toByte(),
        profile
    )

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) ||
                (!byteArray.isSpecificSubCommand(PortOutputSubCommand.SET_DEC_TIME) &&
                        !byteArray.isSpecificSubCommand(PortOutputSubCommand.SET_ACC_TIME))
            ) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val time = (byteArray[7].toInt() shl 8) or (byteArray[6].toInt() and 0xFF)
            val profile = byteArray[8]
            if( byteArray.isSpecificSubCommand(PortOutputSubCommand.SET_ACC_TIME) ) {
                return SetAccTimeCommand(port, action, time, profile)
            }
            return SetDecTimeCommand(port, action, time, profile)
        }
    }

    override fun toString(): String {
        return "${this::class.simpleName}(port=$port, sc=$action, time=$time, profile=$profile H[${baniakjr.lwp.LWP.convertToHexString(profile)}])"
    }
}
