package baniakjr.lwp.model.command.output

import baniakjr.lwp.LWP
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.LWPMask
import baniakjr.lwp.Port
import baniakjr.lwp.PortMode
import baniakjr.lwp.SixLed
import baniakjr.lwp.StartupCompletion
import baniakjr.lwp.model.Wrapper

class SixLedCommand internal constructor(action: Wrapper<StartupCompletion>,
                                         mode: Wrapper<PortMode>,
                                         modePayload: ByteArray) :
    WriteDirectModeCommand(Port.SIX_LED.wrap(), action, mode, modePayload) {

    companion object {

        @JvmStatic
        @JvmOverloads
        fun build(
            payload: ByteArray,
            action: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
        ): SixLedCommand {
            return SixLedCommand(action.wrap(), PortMode.MODE_0.wrap(), payload)
        }

        @JvmStatic
        @JvmOverloads
        fun setBrightness(
            mask: LWPMask<SixLed>,
            brightness: Byte,
            action: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
        ): SixLedCommand {
            return SixLedCommand(action.wrap(), PortMode.MODE_0.wrap(), byteArrayOf(mask.value, brightness))
        }

        @JvmStatic
        @JvmOverloads
        fun turnOff(
            mask: LWPMask<SixLed>,
            action: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
        ): SixLedCommand {
            return SixLedCommand(action.wrap(), PortMode.MODE_0.wrap(), byteArrayOf(mask.value, LWP.MIN_POWER_BYTES))
        }

        @JvmStatic
        @JvmOverloads
        fun turnOn(
            mask: LWPMask<SixLed>,
            action: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
        ): SixLedCommand {
            return SixLedCommand(action.wrap(), PortMode.MODE_0.wrap(), byteArrayOf(mask.value, LWP.MAX_POWER_BYTES))
        }

    }

}