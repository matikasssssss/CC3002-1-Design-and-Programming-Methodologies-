package model.exception

/**
 * This class represents an exception that is thrown when a weapon cannot be equipped.
 *
 * @param message The detail message explaining the reason for the exception.
 */
class CannotEquipWeapon(message: String) extends Exception(message)
