package model.exception

/**
 * This class represents an exception that is thrown when a character cannot use magic spells.
 *
 * @param message The detail message explaining the reason for the exception.
 */
class CannotUseMagicSpells(message: String) extends Exception(message)
