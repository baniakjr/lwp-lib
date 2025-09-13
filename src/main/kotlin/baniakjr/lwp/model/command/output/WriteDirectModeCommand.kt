package baniakjr.lwp.model.command.output

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.mode.PortMode
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.definition.value.PortOutputSubCommand
import baniakjr.lwp.definition.value.StartupCompletion
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortOutputCommand

open class WriteDirectModeCommand internal constructor(port: Wrapper<Port>,
                                                       action: Wrapper<StartupCompletion>,
                                                       val mode: Wrapper<PortMode>,
                                                       modePayload: ByteArray
) : PortOutputCommand(port, action, PortOutputSubCommand.WRITE_DIRECT_MODE.wrap()) {

    val modePayload: ByteArray = modePayload
        get() = field.clone()

    override val payload: ByteArray
        get() = byteArrayOf( mode.value ) + modePayload

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(!byteArray.isSpecificCommand(Command.PORT_OUTPUT) ||
                !byteArray.isSpecificSubCommand(PortOutputSubCommand.WRITE_DIRECT_MODE)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            if(port.enum == Port.PLAYVM) {
                return PlayVMCommand.fromByteArray(byteArray)
            }
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val mode = Wrapper.wrap(PortMode::class.java, byteArray[6])
            val modePayload = byteArray.copyOfRange(7, byteArray.size)
            return WriteDirectModeCommand(port, action, mode, modePayload)
        }

        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            mode: PortMode,
            payload: ByteArray
        ): WriteDirectModeCommand {
            return WriteDirectModeCommand(port.wrap(), action.wrap(), mode.wrap(), payload)
        }

        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            mode: PortMode,
            value: Int
        ): WriteDirectModeCommand {
            return WriteDirectModeCommand(port.wrap(), action.wrap(), mode.wrap(), byteArrayOf(value.toByte()))
        }

        @JvmStatic
        @JvmOverloads
        fun setPower(
            port: Port,
            power: Int,
            mode: PortMode = PortMode.MODE_0,
            action: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
        ): WriteDirectModeCommand {
            return WriteDirectModeCommand(port.wrap(), action.wrap(), mode.wrap(), byteArrayOf(power.toByte()))
        }

        @JvmStatic
        @JvmOverloads
        fun setValue(
            port: Port,
            value: ByteArray,
            mode: PortMode = PortMode.MODE_0,
            action: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
        ): WriteDirectModeCommand {
            return WriteDirectModeCommand(port.wrap(), action.wrap(), mode.wrap(), value)
        }
    }

    override fun toString(): String {
        return "WriteDirectModeCommand(port=$port, sc=$action, mode=$mode, payload=${modePayload.contentToString()} H[${LWP.convertToHexString(modePayload)}])"
    }


}