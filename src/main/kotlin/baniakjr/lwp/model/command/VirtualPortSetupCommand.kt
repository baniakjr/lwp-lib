package baniakjr.lwp.model.command

import baniakjr.lwp.Command
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.Port
import baniakjr.lwp.VirtualPortSetupMode
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class VirtualPortSetupCommand internal constructor(
    val mode: Wrapper<VirtualPortSetupMode>,
    val portA: Wrapper<Port>,
    val portB: Wrapper<Port> = Wrapper.empty()
) : LWPCommand {

    override val command: Wrapper<Command> = Command.VIRTUAL_PORT_SETUP.wrap()

    override val byteValue: ByteArray
        get() {
            return when(mode.enum) {
                VirtualPortSetupMode.CONNECT -> byteArrayOf(command.value, mode.value, portA.value, portB.value).createCommand()
                VirtualPortSetupMode.DISCONNECT -> byteArrayOf(command.value, mode.value, portA.value).createCommand()
                else -> byteArrayOf(command.value, mode.value, portA.value).createCommand()
            }
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            val size = byteArray.size
            if(size != VirtualPortSetupMode.CONNECT_MSG_LENGTH && size != VirtualPortSetupMode.DISCONNECT_MSG_LENGTH) {
                return MalformedCommand(byteArray)
            }
            if(!byteArray.isSpecificCommand(Command.VIRTUAL_PORT_SETUP)) {
                return MalformedCommand(byteArray)
            }
            val mode = Wrapper.wrap(VirtualPortSetupMode::class.java, byteArray[VirtualPortSetupMode.IN_MESSAGE_INDEX])
            return when(size) {
                VirtualPortSetupMode.CONNECT_MSG_LENGTH -> VirtualPortSetupCommand(mode, Wrapper.wrap(Port::class.java, byteArray[4]), Wrapper.wrap(Port::class.java, byteArray[5]))
                VirtualPortSetupMode.DISCONNECT_MSG_LENGTH -> VirtualPortSetupCommand(mode, Wrapper.wrap(Port::class.java, byteArray[4]))
                else -> MalformedCommand(byteArray)
            }
        }

        @JvmStatic
        fun connect(portA: Port, portB: Port): VirtualPortSetupCommand {
            return VirtualPortSetupCommand(VirtualPortSetupMode.CONNECT.wrap(), portA.wrap(), portB.wrap())
        }

        @JvmStatic
        fun disconnect(port: Port): VirtualPortSetupCommand {
            return VirtualPortSetupCommand(VirtualPortSetupMode.DISCONNECT.wrap(), port.wrap())
        }
    }

    override fun toString(): String {
        return when(mode.enum) {
            VirtualPortSetupMode.CONNECT -> "VirtualPortSetupCommand(mode=$mode, portA=$portA, portB=$portB)"
            VirtualPortSetupMode.DISCONNECT -> "VirtualPortSetupCommand(mode=$mode, port=$portA)"
            else -> "VirtualPortSetupCommand(mode=$mode, portA=$portA, portB=$portB)"
        }
    }

}