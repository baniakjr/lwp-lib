package baniakjr.lwp.model.command

import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.definition.value.PortInformationType
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

abstract class PortInformationCommand internal constructor(
    val port: Wrapper<Port>
) : LWPCommand {

    override val command: Wrapper<Command> = Command.PORT_INFORMATION.wrap()

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size <= 5 || !byteArray.isSpecificCommand(Command.PORT_INFORMATION)) {
                return MalformedCommand(byteArray)
            }
            val mode = Wrapper.wrap(PortInformationType::class.java, byteArray[PortInformationType.IN_MESSAGE_INDEX])
            return when(mode.enum) {
                PortInformationType.MODE -> ModePortInformationCommand.fromByteArray(byteArray)
                PortInformationType.COMBINATIONS -> CombinationPortInformationCommand.fromByteArray(byteArray)
                else -> MalformedCommand(byteArray)
            }
        }
    }
}