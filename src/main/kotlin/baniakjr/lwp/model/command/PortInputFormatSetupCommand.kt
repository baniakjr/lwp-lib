package baniakjr.lwp.model.command

import baniakjr.lwp.Command
import baniakjr.lwp.LWP
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.Port
import baniakjr.lwp.PortMode
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class PortInputFormatSetupCommand internal constructor(
    val port: Wrapper<Port>,
    val mode: Wrapper<PortMode>,
    delta: ByteArray,
    val notification: Boolean
) : LWPCommand {

    val delta: ByteArray = delta
        get() = field.clone()

    override val command: Wrapper<Command> = Command.PORT_INPUT_FORMAT_SETUP_SINGLE.wrap()

    override val byteValue: ByteArray
        get() {
            return (byteArrayOf(command.value, port.value, mode.value) + delta + (if(notification) 0x01 else 0x00)).createCommand()
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size != 10 || !byteArray.isSpecificCommand(Command.PORT_INPUT_FORMAT_SETUP_SINGLE)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val mode = Wrapper.wrap(PortMode::class.java, byteArray[PortMode.IN_INFORMATION_MESSAGE_INDEX])
            val delta = byteArray.copyOfRange(5,9)
            val notification = byteArray[9] == 0x01.toByte()
            return PortInputFormatSetupCommand(port, mode, delta, notification)
        }

        @JvmStatic
        @JvmOverloads
        fun build(port: Port, mode: PortMode, delta: ByteArray, notification: Boolean = true): PortInputFormatSetupCommand {
            return PortInputFormatSetupCommand(port.wrap(), mode.wrap(), delta, notification)
        }
    }

    override fun toString(): String {
        return "PortInputFormatSetupCommand(port=$port, mode=$mode, delta=${delta.contentToString()} H[${LWP.convertToHexString(delta)}], notification=$notification)"
    }


}