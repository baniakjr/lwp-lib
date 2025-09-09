package baniakjr.lwp.model.command.output

import baniakjr.lwp.HubLedColor
import baniakjr.lwp.HubLedMode
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.Port
import baniakjr.lwp.PortMode
import baniakjr.lwp.StartupCompletion
import baniakjr.lwp.model.Wrapper

class HubLedCommand internal constructor(action: Wrapper<StartupCompletion>,
                                         mode: Wrapper<PortMode>,
                                         modePayload: ByteArray) :
    WriteDirectModeCommand(Port.HUB_LED.wrap(), action, mode, modePayload) {

    companion object {

        @JvmStatic
        @JvmOverloads
        fun build(
            payload: ByteArray,
            hubLedMode: HubLedMode = HubLedMode.COLOR,
            action: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
        ): HubLedCommand {
            return HubLedCommand(action.wrap(), hubLedMode.toPortMode().wrap(), payload)
        }

        @JvmStatic
        @JvmOverloads
        fun color(
            color: HubLedColor,
            action: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
        ): HubLedCommand {
            return HubLedCommand(action.wrap(), HubLedMode.COLOR.toPortMode().wrap(), byteArrayOf(color.value))
        }

        @JvmStatic
        @JvmOverloads
        fun rgb(
            red: Byte,
            green: Byte,
            blue: Byte,
            action: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
        ): HubLedCommand {
            return HubLedCommand(action.wrap(), HubLedMode.RGB.toPortMode().wrap(), byteArrayOf(red, green, blue))
        }

    }

}