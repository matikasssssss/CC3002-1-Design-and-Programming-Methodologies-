package model.exception

/**
 * The `ShouldHaveWeapon` class represents an exception that is thrown when 
 * an entity (such as a character or enemy) is expected to have a weapon but does not.
 * This exception is typically used in situations where a weapon is necessary but missing.
 *
 * @param message The error message that provides details about the exception.
 */
class ShouldHaveWeapon(message: String) extends Exception(message)
