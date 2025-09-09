package baniakjr.lwp.model.command

import baniakjr.lwp.Command
import baniakjr.lwp.LWP
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.Wrapper

class GenericCommand internal constructor(override val command: Wrapper<Command>, payload: ByteArray = byteArrayOf()) : LWPCommand {

    val payload: ByteArray = payload
        get() = field.clone()

    override val byteValue: ByteArray
        get() {
            return (byteArrayOf(command.value) + payload).createCommand()
        }

    override fun toString(): String {
        return "GenericCommand(command=$command byteValue=${LWP.convertToHexString(byteValue)})"
    }

    companion object {

        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size <= Command.IN_MESSAGE_INDEX) {
                return MalformedCommand(byteArray)
            }
            val command = Wrapper.wrap(Command::class.java, byteArray[Command.IN_MESSAGE_INDEX])
            return GenericCommand(command, byteArray.copyOfRange(3, byteArray.size))
        }

        @JvmStatic
        @JvmOverloads
        fun build(command: Command, payload: ByteArray = byteArrayOf()): GenericCommand {
            return GenericCommand(command.wrap(), payload)
        }
    }

}