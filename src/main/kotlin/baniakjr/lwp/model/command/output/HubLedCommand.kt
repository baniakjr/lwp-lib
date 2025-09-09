package baniakjr.lwp.model.command.output

import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.mode.HubLedMode
import baniakjr.lwp.definition.mode.PortMode
import baniakjr.lwp.definition.value.HubLedColor
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.definition.value.StartupCompletion
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