package baniakjr.lwp.model.command.output

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.LWPMask
import baniakjr.lwp.definition.mask.SixLed
import baniakjr.lwp.definition.mode.PortMode
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.definition.value.StartupCompletion
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