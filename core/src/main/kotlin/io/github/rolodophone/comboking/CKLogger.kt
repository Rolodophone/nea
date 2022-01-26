package io.github.rolodophone.comboking

import ktx.log.Logger

/**
 * Wrapper for libKTX [Logger] that logs only the tag and the message, without logging the debug level.
 */
class CKLogger(tag: String): Logger("", tag, tag, tag) {
	override fun buildMessage(message: String) = message
}

inline fun <reified T : Any> ckLogger(): Logger = CKLogger(T::class.simpleName ?: "Anonymous")