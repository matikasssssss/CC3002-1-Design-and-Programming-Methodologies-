package model.exception

/**
 * The `IllegalArgument` class represents an exception that is thrown 
 * when a method or operation receives an argument that is not valid for its intended use.
 * This exception is typically used to signal that the provided input does not meet the expected criteria.
 *
 * @param message The error message that provides details about the exception.
 */
class IllegalArgument(message: String) extends Exception(message)
