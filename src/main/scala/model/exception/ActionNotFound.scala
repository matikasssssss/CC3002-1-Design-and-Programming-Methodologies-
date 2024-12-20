package model.exception

/**
 * This class represents an exception that is thrown when an action cannot be found.
 *
 * @param message The detail message explaining the reason for the exception.
 */
class ActionNotFound(message: String) extends Exception(message)