package baniakjr.lwp.model.command

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.mode.PortMode
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class PortInputFormatCommand internal constructor(
    val port: Wrapper<Port>,
    val mode: Wrapper<PortMode>,
    delta: ByteArray,
    val notification: Boolean
) : LWPCommand {

    val delta: ByteArray = delta
        get() = field.clone()

    override val command: Wrapper<Command> = Command.PORT_INPUT_FORMAT_SINGLE.wrap()

    override val byteValue: ByteArray
        get() {
            return (byteArrayOf(command.value, port.value, mode.value) + delta + (if(notification) 0x01 else 0x00)).createCommand()
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size != 10 || !byteArray.isSpecificCommand(Command.PORT_INPUT_FORMAT_SINGLE)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val mode = Wrapper.wrap(PortMode::class.java, byteArray[PortMode.IN_INFORMATION_MESSAGE_INDEX])
            val delta = byteArray.copyOfRange(5,9)
            val notification = byteArray[9] == 0x01.toByte()
            return PortInputFormatCommand(port, mode, delta, notification)
        }
    }

    override fun toString(): String {
        return "PortInputFormatCommand(port=$port, mode=$mode, delta=${delta.contentToString()} H[${LWP.convertToHexString(delta)}], notification=$notification)"
    }


}