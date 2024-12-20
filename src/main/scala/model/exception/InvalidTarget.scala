package model.exception

/**
 * Exception thrown when a target is invalid for a specific action or spell.
 *
 * @param message Detailed message about the invalid target condition.
 */

class InvalidTarget (message: String) extends Exception(message)
