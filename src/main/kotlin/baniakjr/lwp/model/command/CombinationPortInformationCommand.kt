package baniakjr.lwp.model.command

import baniakjr.lwp.Command
import baniakjr.lwp.LWP
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.Port
import baniakjr.lwp.PortInformationType
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class CombinationPortInformationCommand internal constructor(
    port: Wrapper<Port>,
    val combinations: List<Combination>
) : PortInformationCommand(port) {

    override val command: Wrapper<Command> = Command.PORT_INFORMATION.wrap()

    override val byteValue: ByteArray
        get() {
            val combArray = combinations.map { it.asArray() }.fold(byteArrayOf()){ acc, next -> acc + next }
            return (byteArrayOf(command.value, port.value, PortInformationType.COMBINATIONS.value) + combArray).createCommand()
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size < 7 ||
                byteArray.size > 21 ||
                byteArray.size % 2 == 0 ||
                !byteArray.isSpecificCommand(Command.PORT_INFORMATION)) {
                return MalformedCommand(byteArray)
            }
            val mode = Wrapper.wrap(PortInformationType::class.java, byteArray[PortInformationType.IN_MESSAGE_INDEX])
            if(mode.enum != PortInformationType.COMBINATIONS) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val payload = byteArray.copyOfRange(5,byteArray.size)
            val combinations: MutableList<Combination> = mutableListOf()
            for(index in 0..14 step 2) {
                combinations.add(Combination(payload[index], payload[index+1]))
            }
            return  CombinationPortInformationCommand(port, combinations)
        }
    }


    class Combination(val byte1: Byte, val byte2: Byte) {

        override fun toString(): String {
            return LWP.convertToBinaryString(byte1) + LWP.convertToBinaryString(byte2)
        }

        fun asArray(): ByteArray {
            return byteArrayOf(byte1, byte2)
        }
    }

    override fun toString(): String {
        return "PortInformationCommand(port=$port, type=${PortInformationType.COMBINATIONS}, combinations=$combinations)"
    }
}