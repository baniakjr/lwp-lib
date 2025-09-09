package baniakjr.lwp.model.command.mode

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.mode.PortMode
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.ModeInformationType
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortModeInformationCommand

class FloatRangePortModeInformationCommand internal constructor(
    port: Wrapper<Port>,
    mode: Wrapper<PortMode>,
    portInformationType: Wrapper<ModeInformationType>,
    rangeMin: ByteArray = byteArrayOf(),
    rangeMax: ByteArray = byteArrayOf()
): PortModeInformationCommand(port, mode, portInformationType) {

    val rangeMin: ByteArray = rangeMin
        get() = field.clone()

    val rangeMax: ByteArray = rangeMax
        get() = field.clone()

    override val payload: ByteArray
        get() {
            return byteArrayOf() + rangeMin + rangeMax
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(!byteArray.isSpecificCommand(Command.PORT_MODE_INFORMATION) ||
                byteArray.size != ModeInformationType.LENGTH_WO_DATA + 8 ||
                    (!byteArray.isSpecificModeInformationType(ModeInformationType.RAW) &&
                        !byteArray.isSpecificModeInformationType(ModeInformationType.PCT) &&
                        !byteArray.isSpecificModeInformationType(ModeInformationType.SI)) ) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val mode = Wrapper.wrap(PortMode::class.java, byteArray[PortMode.IN_INFORMATION_MESSAGE_INDEX])
            val portInformationType = Wrapper.wrap(ModeInformationType::class.java, byteArray[ModeInformationType.IN_MESSAGE_INDEX])
            val rangeMin = byteArray.copyOfRange(ModeInformationType.DATA_START_INDEX, ModeInformationType.DATA_START_INDEX+4)
            val rangeMax = byteArray.copyOfRange(ModeInformationType.DATA_START_INDEX+4, byteArray.size)
            return FloatRangePortModeInformationCommand(port, mode, portInformationType, rangeMin, rangeMax)
        }
    }

    override fun toString(): String {
        return "PortModeInformationCommand(port=$port, mode=$mode, informationType=${informationType}, min=${LWP.convertToFloat(rangeMin)}, max=${LWP.convertToFloat(rangeMax)})"
    }

}