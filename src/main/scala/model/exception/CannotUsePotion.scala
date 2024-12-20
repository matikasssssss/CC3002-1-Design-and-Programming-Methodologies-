package model.exception

/**
 * Exception thrown when a character attempts to use a potion that is not allowed or invalid.
 *
 * This exception is used to handle cases where game rules restrict the usage of a particular potion
 * or when a character does not meet the conditions required to use the potion.
 *
 * @param message A detailed message explaining the reason for the exception.
 */
class CannotUsePotion(message: String) extends Exception(message)
