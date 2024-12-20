package model.exception

/**
 * This class represents an exception that is thrown when a character cannot heal.
 *
 * @param message The detail message explaining the reason for the exception.
 */
class CannotHeal(message: String) extends Exception(message)
