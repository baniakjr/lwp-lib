package baniakjr.lwp.model.command.output

import baniakjr.lwp.Command
import baniakjr.lwp.LWP
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.LWPMask
import baniakjr.lwp.PlayVmOperation
import baniakjr.lwp.Port
import baniakjr.lwp.PortMode
import baniakjr.lwp.PortOutputSubCommand
import baniakjr.lwp.StartupCompletion
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand

open class PlayVMCommand internal constructor(action: Wrapper<StartupCompletion>,
                                              mode: Wrapper<PortMode>,
                                              modePayload: ByteArray
) : WriteDirectModeCommand(Port.PLAYVM.wrap(), action, mode, modePayload) {

    val speed: Byte?
        get() {
            if(modePayload.size >= 6) {
                val speed = modePayload[2]
                return speed
            }
            return null
        }

    val steer: Byte?
        get() {
            if(modePayload.size >= 6) {
                val speed = modePayload[3]
                return speed
            }
            return null
        }

    val options: LWPMask<PlayVmOperation>?
        get() {
            if(modePayload.size >= 6) {
                return LWPMask.fromByte(PlayVmOperation::class.java, modePayload[4])
            }
            return null
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(!byteArray.isSpecificCommand(Command.PORT_OUTPUT) ||
                !byteArray.isSpecificSubCommand(PortOutputSubCommand.WRITE_DIRECT_MODE)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            if(port.enum != Port.PLAYVM) {
                return MalformedCommand(byteArray)
            }
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val mode = Wrapper.wrap(PortMode::class.java, byteArray[6])
            val modePayload = byteArray.copyOfRange(7, byteArray.size)
            return PlayVMCommand(action, mode, modePayload)
        }

        @JvmStatic
        @JvmOverloads
        fun build(
            speed: Int,
            steer: Int,
            options: LWPMask<PlayVmOperation>,
            action: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
        ): PlayVMCommand {
            val payload = byteArrayOf(0x03, 0x00, speed.toByte(), steer.toByte(), options.value, 0x00)
            return PlayVMCommand(action.wrap(), PortMode.MODE_0.wrap(), payload)
        }

        @JvmStatic
        fun initialize(): PlayVMCommand {
            return build(0,0,LWPMask(PlayVmOperation.INIT))
        }

        @JvmStatic
        fun calibrate(): PlayVMCommand {
            return build(0,0,LWPMask(PlayVmOperation.CALIBRATE))
        }
    }

    override fun toString(): String {
        if(modePayload.size >= 6 && mode.enum == PortMode.MODE_0) {
            val speed = modePayload[2]
            val steer = modePayload[3]
            val mask = LWPMask.fromByte(PlayVmOperation::class.java, modePayload[4])
            return "WriteDirectModeCommand(port=${Port.PLAYVM}, sc=$action, speed=$speed H[${LWP.convertToHexString(speed)}], steer=$steer H[${LWP.convertToHexString(steer)}], options=$mask)"
        }
        return "WriteDirectModeCommand(port=${Port.PLAYVM}, sc=$action, mode=$mode, payload=${modePayload.contentToString()} H[${LWP.convertToHexString(modePayload)}])"
    }




}