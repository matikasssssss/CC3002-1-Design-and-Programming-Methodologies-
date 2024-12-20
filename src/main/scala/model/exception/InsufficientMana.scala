package model.exception

/**
 * Exception thrown when a magic character tries to cast a spell
 * without having sufficient mana points.
 *
 * @param message Detailed message about the insufficient mana condition.
 */

class InsufficientMana(message: String) extends Exception(message)
