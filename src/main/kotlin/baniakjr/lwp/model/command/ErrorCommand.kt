package baniakjr.lwp.model.command

import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.ErrorCode
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class ErrorCommand internal constructor(
    val commandType: Wrapper<Command>,
    val errorCode: Wrapper<ErrorCode>
) : LWPCommand {

    override val command: Wrapper<Command> = Command.ERROR.wrap()

    override val byteValue: ByteArray
        get() {
            return byteArrayOf(command.value, commandType.value, errorCode.value).createCommand()
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size < ErrorCode.ERROR_MSG_LENGTH || !byteArray.isSpecificCommand(Command.ERROR)) {
                return MalformedCommand(byteArray)
            }
            val cmdType = Wrapper.wrap(Command::class.java, byteArray[ErrorCode.COMMAND_IN_MESSAGE_INDEX])
            val errorCode = Wrapper.wrap(ErrorCode::class.java, byteArray[ErrorCode.IN_MESSAGE_INDEX])
            return ErrorCommand(cmdType, errorCode)
        }
    }

    override fun toString(): String {
        return "ErrorCommand(command=$commandType, error=$errorCode)"
    }


}