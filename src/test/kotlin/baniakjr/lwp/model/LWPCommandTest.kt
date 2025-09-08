package baniakjr.lwp.model

import baniakjr.lwp.model.command.*
import baniakjr.lwp.model.command.mode.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class LWPCommandTest {

    @Test
    fun notACommand() {
        val inputArray = byteArrayOf(0x01, 0x00)

        val result = LWPCommand.fromByteArray(inputArray)

        assertThat(result).isExactlyInstanceOf(NotCommand::class.java).extracting { it.byteValue }.isEqualTo(inputArray)
    }

    @Test
    fun unknownCommand() {
        val inputArray = byteArrayOf(0x04, 0x00, 0xFF.toByte(), 0xFF.toByte())

        val result = LWPCommand.fromByteArray(inputArray)

        assertThat(result).isExactlyInstanceOf(UnknownCommand::class.java).extracting { it.byteValue }.isEqualTo(inputArray)
    }

    @ParameterizedTest
    @MethodSource("commandConversionCheck")
    fun commandConversionCheck(inputArray: ByteArray, commandClass: Class<LWPCommand>) {
        val result = LWPCommand.fromByteArray(inputArray)

        assertThat(result).isInstanceOf(commandClass).extracting { it.byteValue }.isEqualTo(inputArray)
    }

    companion object {
        @JvmStatic
        fun commandConversionCheck(): List<Arguments> {
            return listOf(
                Arguments.of(byteArrayOf(0x01, 0x00), NotCommand::class.java),
                Arguments.of(byteArrayOf(0x04, 0x00, 0xFF.toByte(), 0xFF.toByte()), UnknownCommand::class.java),
                Arguments.of(byteArrayOf(0x06,0x00,0x03,0x01,0x04,0xff.toByte()), AlertCommand::class.java),
                Arguments.of(byteArrayOf(0x0f,0x00,0x04,0x32,0x01,0x56,0x00,0x01,0x00,0x00,0x00,0x02,0x00,0x00,0x00), AttachedIOCommand::class.java),
                Arguments.of(byteArrayOf(0x05,0x00,0x05,0x01,0x06 ), ErrorCommand::class.java),
                Arguments.of(byteArrayOf(0x09,0x00,0x01,0x04,0x06,0x00,0x00,0x00,0x03), HubPropertyCommand::class.java),
                Arguments.of(byteArrayOf(0x0b,0x00,0x43,0x34,0x01,0x03,0x05,0x0d,0x00,0x1f,0x00), PortInformationCommand::class.java),
                Arguments.of(byteArrayOf(0x0b,0x00,0x43,0x34,0x01,0x03,0x05,0x0d,0x00,0x1f,0x00), ModePortInformationCommand::class.java),
                Arguments.of(byteArrayOf(0x05,0x00,0x21,0x35,0x01), PortInformationRequestCommand::class.java),
                Arguments.of(byteArrayOf(0x0a,0x00,0x41,0x36,0x00,0x01,0x00,0x00,0x00,0x01), PortInputFormatSetupCommand::class.java),
                Arguments.of(byteArrayOf(0x0a,0x00,0x47,0x36,0x00,0x01,0x00,0x00,0x00,0x01), PortInputFormatCommand::class.java),
                Arguments.of(byteArrayOf(0x0b,0x00,0x44,0x3f,0x00,0x00,0x43,0x4f,0x4c,0x20,0x30), PortModeInformationCommand::class.java),
                Arguments.of(byteArrayOf(0x0b,0x00,0x44,0x3f,0x00,0x00,0x43,0x4f,0x4c,0x20,0x30), NamePortModeInformationCommand::class.java),
                Arguments.of(byteArrayOf(0x0e,0x00,0x44,0x3f,0x00,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0xc8.toByte(),0x41), FloatRangePortModeInformationCommand::class.java),
                Arguments.of(byteArrayOf(0x07,0x00,0x44,0x3f,0x00,0x07,0x04), BiasPortModeInformationCommand::class.java),
                Arguments.of(byteArrayOf(0x08,0x00,0x44,0x3f,0x00,0x05,0x04,0x00), GenericPortModeInformationCommand::class.java),
                Arguments.of(byteArrayOf(0x0b,0x00,0x44,0x3f,0x00,0x04,0x49,0x44,0x58,0x00,0x00), SymbolPortModeInformationCommand::class.java),
                Arguments.of(byteArrayOf(0x0a,0x00,0x44,0x3f,0x00,0x80.toByte(),0x01,0x01,0x02,0x00), ValueFormatPortModeInformationCommand::class.java),
                Arguments.of(byteArrayOf(0x06,0x00,0x22,0x3f,0x00,0x01), PortModeInformationRequestCommand::class.java)
                //060061013233 virtual port setup
                //050082360a portoutputfeedback
                //0c0045360301050100000000 port value single
                //0d00813611510003002c000200 playvm output
            )
        }
    }

}