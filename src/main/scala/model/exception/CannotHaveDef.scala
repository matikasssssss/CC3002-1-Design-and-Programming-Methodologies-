package model.exception

/**
 * The `CannotHaveDef` class represents an exception that is thrown 
 * when an operation attempting to modify or interact with a character's defense 
 * value is not allowed, typically because the character is not able to have a defense value.
 *
 * @param message The error message that provides details about the exception.
 */
class CannotHaveDef(message: String) extends Exception(message)
