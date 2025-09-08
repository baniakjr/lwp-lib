package baniakjr.lwp.model.command

import baniakjr.lwp.*
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class PortValueSingleCommand internal constructor(
    val port: Wrapper<Port>,
    payload: ByteArray = byteArrayOf()
) : LWPCommand {

    val payload: ByteArray = payload
        get() = field.clone()

    override val command: Wrapper<Command> = Command.PORT_VALUE_SINGLE.wrap()

    override val byteValue: ByteArray
        get() = LWP.createCommand(byteArrayOf(command.value, port.value) + payload)

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (byteArray.size < 5 || !byteArray.isSpecificCommand(Command.PORT_VALUE_SINGLE)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            return PortValueSingleCommand(port, byteArray.copyOfRange(4, byteArray.size))
        }
    }

    override fun toString(): String {
        return "PortValueSingleCommand(port=$port, payload=${payload.contentToString()} H[${LWP.convertToHexString(payload)}])"
    }


}