package baniakjr.lwp.model.command

import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.definition.value.PortOutputSubCommand
import baniakjr.lwp.definition.value.StartupCompletion
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.output.GenericPortOutputCommand
import baniakjr.lwp.model.command.output.GotoAbsPosDualCommand
import baniakjr.lwp.model.command.output.GotoAbsPosSingleCommand
import baniakjr.lwp.model.command.output.SetTimeCommandBase
import baniakjr.lwp.model.command.output.StartPowerDualCommand
import baniakjr.lwp.model.command.output.StartPowerSingleCommand
import baniakjr.lwp.model.command.output.StartSpeedDualCommand
import baniakjr.lwp.model.command.output.StartSpeedForDegDualCommand
import baniakjr.lwp.model.command.output.StartSpeedForDegSingleCommand
import baniakjr.lwp.model.command.output.StartSpeedForTimeDualCommand
import baniakjr.lwp.model.command.output.StartSpeedForTimeSingleCommand
import baniakjr.lwp.model.command.output.StartSpeedSingleCommand
import baniakjr.lwp.model.command.output.WriteDirectCommand
import baniakjr.lwp.model.command.output.WriteDirectModeCommand

abstract class PortOutputCommand internal constructor(
    val port: Wrapper<Port>,
    val action: Wrapper<StartupCompletion>,
    val subCommand: Wrapper<PortOutputSubCommand>,
) : LWPCommand {

    override val command: Wrapper<Command> = Command.PORT_OUTPUT.wrap()

    abstract val payload: ByteArray

    override val byteValue: ByteArray
        get() = (byteArrayOf(command.value, port.value, action.value, subCommand.value) + payload).createCommand()

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size < PortOutputSubCommand.LENGTH_WO_PAYLOAD) {
                return MalformedCommand(byteArray)
            }
            val subCommand = Wrapper.wrap(PortOutputSubCommand::class.java, byteArray[PortOutputSubCommand.IN_MESSAGE_INDEX])
            return when(subCommand.enum) {
                PortOutputSubCommand.WRITE_DIRECT_MODE -> WriteDirectModeCommand.fromByteArray(byteArray)
                PortOutputSubCommand.START_POWER_SINGLE -> StartPowerSingleCommand.fromByteArray(byteArray)
                PortOutputSubCommand.START_POWER_DUAL -> StartPowerDualCommand.fromByteArray(byteArray)
                PortOutputSubCommand.SET_ACC_TIME -> SetTimeCommandBase.fromByteArray(byteArray)
                PortOutputSubCommand.SET_DEC_TIME -> SetTimeCommandBase.fromByteArray(byteArray)
                PortOutputSubCommand.START_SPEED_SINGLE -> StartSpeedSingleCommand.fromByteArray(byteArray)
                PortOutputSubCommand.START_SPEED_DUAL -> StartSpeedDualCommand.fromByteArray(byteArray)
                PortOutputSubCommand.START_SPEED_FOR_TIME_SINGLE -> StartSpeedForTimeSingleCommand.fromByteArray(byteArray)
                PortOutputSubCommand.START_SPEED_FOR_TIME_DUAL -> StartSpeedForTimeDualCommand.fromByteArray(byteArray)
                PortOutputSubCommand.START_SPEED_FOR_DEG_SINGLE -> StartSpeedForDegSingleCommand.fromByteArray(byteArray)
                PortOutputSubCommand.START_SPEED_FOR_DEG_DUAL -> StartSpeedForDegDualCommand.fromByteArray(byteArray)
                PortOutputSubCommand.GOTO_ABS_POS_SINGLE -> GotoAbsPosSingleCommand.fromByteArray(byteArray)
                PortOutputSubCommand.GOTO_ABS_POS_DUAL -> GotoAbsPosDualCommand.fromByteArray(byteArray)
                PortOutputSubCommand.WRITE_DIRECT -> WriteDirectCommand.fromByteArray(byteArray)
                else -> GenericPortOutputCommand.fromByteArray(byteArray)
            }
        }

        @JvmStatic
        fun ByteArray.isSpecificSubCommand(subCommand: PortOutputSubCommand): Boolean {
            return this.size >= PortOutputSubCommand.LENGTH_WO_PAYLOAD && this[PortOutputSubCommand.IN_MESSAGE_INDEX] == subCommand.value
        }
    }
}