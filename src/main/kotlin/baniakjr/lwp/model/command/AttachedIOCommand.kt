package baniakjr.lwp.model.command

import baniakjr.lwp.*
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class AttachedIOCommand internal constructor(val port: Wrapper<Port>,
                                             val event: Wrapper<IOEvent>,
                                             ioType: ByteArray = byteArrayOf(),
                                             val portA: Wrapper<Port> = Wrapper.empty(),
                                             val portB: Wrapper<Port> = Wrapper.empty(),
                                             hwRev: ByteArray = byteArrayOf(),
                                             swRev: ByteArray = byteArrayOf()
) : LWPCommand {

    val ioType: ByteArray = ioType
        get() = field.clone()

    val hwRev: ByteArray = hwRev
        get() = field.clone()

    val swRev: ByteArray = swRev
        get() = field.clone()

    override val command: Wrapper<Command> = Command.ATTACHED_IO.wrap()

    override val byteValue: ByteArray
        get() {
            return when(event.enum) {
                IOEvent.DETACHED -> LWP.createCommand(byteArrayOf(command.value, port.value, event.value))
                IOEvent.ATTACHED -> LWP.createCommand(byteArrayOf(command.value, port.value, event.value) + ioType + hwRev + swRev)
                IOEvent.ATTACHED_VIRTUAL -> LWP.createCommand(byteArrayOf(command.value, port.value, event.value) + ioType + byteArrayOf(portA.value, portB.value))
                else -> LWP.createCommand(byteArrayOf(command.value, port.value, event.value))
            }
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            val size = byteArray.size
            if(size != IOEvent.ATTACHED_MSG_LENGTH &&
                size != IOEvent.DETACHED_MSG_LENGTH &&
                size != IOEvent.ATTACHED_VIRTUAL_MSG_LENGTH) {
                return MalformedCommand(byteArray)
            }
            if(!byteArray.isSpecificCommand(Command.ATTACHED_IO)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[IOEvent.PORT_IN_MESSAGE_INDEX])
            val event = Wrapper.wrap(IOEvent::class.java, byteArray[IOEvent.IN_MESSAGE_INDEX])
            return when(size) {
                IOEvent.DETACHED_MSG_LENGTH -> AttachedIOCommand(port, event)
                IOEvent.ATTACHED_MSG_LENGTH -> AttachedIOCommand(port, event, byteArray.copyOfRange(IOEvent.IO_TYPE_IN_MESSAGE_INDEX, IOEvent.IO_TYPE_IN_MESSAGE_INDEX+2), hwRev = byteArray.copyOfRange(IOEvent.HW_REV_IN_MESSAGE_INDEX, IOEvent.HW_REV_IN_MESSAGE_INDEX+4), swRev = byteArray.copyOfRange(IOEvent.SW_REV_IN_MESSAGE_INDEX, IOEvent.SW_REV_IN_MESSAGE_INDEX+4))
                IOEvent.ATTACHED_VIRTUAL_MSG_LENGTH -> AttachedIOCommand(port, event, byteArray.copyOfRange(IOEvent.IO_TYPE_IN_MESSAGE_INDEX, IOEvent.IO_TYPE_IN_MESSAGE_INDEX+2), Wrapper.wrap(Port::class.java, byteArray[IOEvent.V_PORT_A_IN_MESSAGE_INDEX]), Wrapper.wrap(Port::class.java, byteArray[IOEvent.V_PORT_B_IN_MESSAGE_INDEX]))
                else -> MalformedCommand(byteArray)
            }
        }
    }

    override fun toString(): String {
        return when(event.enum) {
            IOEvent.DETACHED -> "AttachedIOCommand(port=$port, event=$event)"
            IOEvent.ATTACHED -> "AttachedIOCommand(port=$port, event=$event, ioType=${LWP.convertToHexString(ioType)}, hwRev=${LWP.convertToVersionNumber(hwRev)}, swRev=${LWP.convertToVersionNumber(swRev)})"
            IOEvent.ATTACHED_VIRTUAL -> "AttachedIOCommand(port=$port, event=$event, ioType=${LWP.convertToHexString(ioType)}, portA=$portA, portB=$portB)"
            else -> "AttachedIOCommand(port=$port, event=$event)"
        }
    }


}