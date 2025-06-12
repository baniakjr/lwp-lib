package baniakjr.lwp.model.command

import baniakjr.lwp.*
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.mode.*

abstract class PortModeInformationCommand internal constructor(
    val port: Wrapper<Port>,
    val mode: Wrapper<PortMode>,
    val informationType: Wrapper<ModeInformationType>
) : LWPCommand {

    override val command: Wrapper<Command> = Command.PORT_MODE_INFORMATION.wrap()

    abstract val payload: ByteArray

    override val byteValue: ByteArray
        get() {
            return LWP.createCommand(byteArrayOf(command.value, port.value, mode.value, informationType.value) + payload)
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size <= ModeInformationType.LENGTH_WO_DATA) {
                return MalformedCommand(byteArray)
            }
            val informationType = Wrapper.wrap(ModeInformationType::class.java, byteArray[ModeInformationType.IN_MESSAGE_INDEX])
            return when(informationType.enum) {
                ModeInformationType.NAME -> NamePortModeInformationCommand.fromByteArray(byteArray)
                ModeInformationType.RAW -> FloatRangePortModeInformationCommand.fromByteArray(byteArray)
                ModeInformationType.PCT -> FloatRangePortModeInformationCommand.fromByteArray(byteArray)
                ModeInformationType.SI -> FloatRangePortModeInformationCommand.fromByteArray(byteArray)
                ModeInformationType.SYMBOL -> SymbolPortModeInformationCommand.fromByteArray(byteArray)
                ModeInformationType.BIAS -> BiasPortModeInformationCommand.fromByteArray(byteArray)
                ModeInformationType.FORMAT -> ValueFormatPortModeInformationCommand.fromByteArray(byteArray)
                else -> GenericPortModeInformationCommand.fromByteArray(byteArray)
            }
        }

        @JvmStatic
        fun ByteArray.isSpecificModeInformationType(modeInformationType: ModeInformationType): Boolean {
            return this.size >= (ModeInformationType.LENGTH_WO_DATA) && this[ModeInformationType.IN_MESSAGE_INDEX] == modeInformationType.value
        }
    }


}