package baniakjr.lwp.model

import baniakjr.lwp.Command
import baniakjr.lwp.LWP
import baniakjr.lwp.LWPByteValue
import baniakjr.lwp.model.command.*

/**
 * Main interface of Object oriented implementation of LWP
 * Represents command/message send or received by hub
 * @property command - Wrapper for [Command enum][Command]
 * @property byteValue - byteArray containing byte representation of command. Raw data send to or received from hub
 *
 * @see [LWP](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html)
 */
interface LWPCommand {

    val command: Wrapper<Command>

    val byteValue: ByteArray

    companion object {

        /**
         * Method to create Command object from raw data
         * @param byteArray - Raw command data
         * @return LWPCommand - Object implementing LWPCommand interface that represents given [Command][Command]
         */
        @JvmStatic
        fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size < LWP.MINIMAL_MSG_LENGTH) {
                return NotCommand(byteArray)
            }
            val command = LWPByteValue.fromByte(Command::class.java, byteArray[Command.IN_MESSAGE_INDEX])
            val lwpCommand = when(command) {
                Command.HUB_PROPERTY -> HubPropertyCommand.fromByteArray(byteArray)
                Command.HUB_ACTION -> HubActionCommand.fromByteArray(byteArray)
                Command.ALERT -> AlertCommand.fromByteArray(byteArray)
                Command.ATTACHED_IO -> AttachedIOCommand.fromByteArray(byteArray)
                Command.ERROR -> ErrorCommand.fromByteArray(byteArray)
                Command.PORT_INFORMATION_REQUEST -> PortInformationRequestCommand.fromByteArray(byteArray)
                Command.PORT_MODE_INFORMATION_REQUEST -> PortModeInformationRequestCommand.fromByteArray(byteArray)
                Command.PORT_INPUT_FORMAT_SETUP_SINGLE -> PortInputFormatSetupCommand.fromByteArray(byteArray)
                Command.PORT_INFORMATION -> PortInformationCommand.fromByteArray(byteArray)
                Command.PORT_MODE_INFORMATION -> PortModeInformationCommand.fromByteArray(byteArray)
                Command.PORT_VALUE_SINGLE -> PortValueSingleCommand.fromByteArray(byteArray)
                Command.PORT_INPUT_FORMAT_SINGLE -> PortInputFormatCommand.fromByteArray(byteArray)
                Command.VIRTUAL_PORT_SETUP -> VirtualPortSetupCommand.fromByteArray(byteArray)
                Command.PORT_OUTPUT -> PortOutputCommand.fromByteArray(byteArray)
                Command.PORT_OUTPUT_COMMAND_FEEDBACK -> PortOutputFeedbackCommand.fromByteArray(byteArray)
                else -> UnknownCommand(byteArray)
            }
            return lwpCommand
        }

        /**
         * Check whether raw data is specific [Command][Command]
         */
        @JvmStatic
        fun ByteArray.isSpecificCommand(command: Command): Boolean {
            return this.size >= LWP.MINIMAL_MSG_LENGTH && this[Command.IN_MESSAGE_INDEX] == command.value
        }

    }

}