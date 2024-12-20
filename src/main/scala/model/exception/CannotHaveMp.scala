package model.exception

/**
 * This class represents an exception that is thrown when a character cannot have magic points (MP).
 *
 * @param message The detail message explaining the reason for the exception.
 */
class CannotHaveMp(message: String) extends Exception(message)
