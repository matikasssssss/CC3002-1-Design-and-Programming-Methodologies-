package model.exception

/**
 * This class represents an exception that is thrown when a character cannot have a name.
 *
 * @param message The detail message explaining the reason for the exception.
 */
class CannotHaveName(message: String) extends Exception(message)