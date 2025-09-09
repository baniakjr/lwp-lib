package baniakjr.lwp.model.command.mode

import baniakjr.lwp.Command
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.ModeInformationType
import baniakjr.lwp.Port
import baniakjr.lwp.PortMode
import baniakjr.lwp.ValueFormat
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortModeInformationCommand

class ValueFormatPortModeInformationCommand internal constructor(
    port: Wrapper<Port>,
    mode: Wrapper<PortMode>,
    val noDatasets: Int,
    val datasetType: Wrapper<ValueFormat>,
    val totalFigures: Int,
    val decimals: Int
): PortModeInformationCommand(port, mode, ModeInformationType.FORMAT.wrap()) {

    override val payload: ByteArray
        get() {
            return byteArrayOf(noDatasets.toByte(), datasetType.value, totalFigures.toByte(), decimals.toByte())
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(!byteArray.isSpecificCommand(Command.PORT_MODE_INFORMATION) ||
                byteArray.size != ModeInformationType.LENGTH_WO_DATA + 4 ||
                !byteArray.isSpecificModeInformationType(ModeInformationType.FORMAT) ) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val mode = Wrapper.wrap(PortMode::class.java, byteArray[PortMode.IN_INFORMATION_MESSAGE_INDEX])
            val data = byteArray.copyOfRange(ModeInformationType.DATA_START_INDEX, byteArray.size)
            return ValueFormatPortModeInformationCommand(port, mode, data[0].toInt(), Wrapper.wrap(ValueFormat::class.java, data[1]), data[2].toInt(), data[3].toInt())
        }
    }

    override fun toString(): String {
        return "PortModeInformationCommand(port=$port, mode=$mode, informationType=${informationType}, Number of datasets=$noDatasets, type=$datasetType, total figures=$totalFigures, decimals=$decimals)"
    }

}