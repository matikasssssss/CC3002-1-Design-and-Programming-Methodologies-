package model.actions.magic

import api.{Source, Target}
import model.entity.character.magic.WhiteMage
import model.exception.{CannotUseMagicSpells, InsufficientMana, InvalidTarget}

/**
 * Class `Healing` represents a specific magic action for healing in the game.
 * It extends the `AbstractMagic` class, and the name of this action is set to "Healing" by default.
 *
 * @constructor Creates an instance of the `Healing` class with a default name "Healing" and a unique identifier.
 * @param id The unique identifier of the healing action.
 */
class Healing(id: String) extends AbstractMagic(id) {

  /**
   * Defines the name of the magic action.
   *
   * @return The name of this magic action, which is "Healing".
   */
  override protected def name: String = "Healing"

  /**
   * Performs the healing action on a target using a magical source.
   * Validates the state of the source and target, and ensures sufficient mana is available.
   *
   * @param source The entity performing the healing action (must be a `WhiteMage` with sufficient mana).
   * @param target The entity receiving the healing effect.
   * @throws InvalidTarget If the source or target is in an invalid state.
   * @throws InsufficientMana If the source lacks the mana required to perform the healing action.
   * @throws CannotUseMagicSpells If the source cannot perform the healing spell.
   */
  override def receiveTarget(source: Source, target: Target): Unit = {
    if (source.getHp <= 0) {
      throw new InvalidTarget(s"${source.getName} has negative HP and cannot perform healing")
    }
    if (target.getHp <= 0) {
      throw new InvalidTarget(s"${target.getName} is already defeated and cannot receive healing")
    }

    source match {
      case whiteMage: WhiteMage =>
        val healAmount: Int = (target.getMaxHp * 0.2 + whiteMage.getAtkm / 4).toInt
        if (whiteMage.getMp >= 15) {
          target.increaseHp(healAmount)
          whiteMage.decreaseMp(15)
          println(s"${target.getName} has received $healAmount healing points")
          println(s"${whiteMage.getName} has used 15 MP")
          whiteMage.deactivateMagicBoost()
        } else {
          throw new InsufficientMana(s"${whiteMage.getName} does not have enough mana to cast ${this.getName}")
        }
      case _ =>
        throw new CannotUseMagicSpells(s"${source.getName} cannot perform this spell")
    }
  }
}
