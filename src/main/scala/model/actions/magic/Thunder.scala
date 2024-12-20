package model.actions.magic

import api.{Source, Target}
import model.entity.character.magic.BlackMage
import model.exception.{InsufficientMana, InvalidTarget}

/**
 * Class `Thunder` represents a specific magic action for casting a Thunder spell in the game.
 * This spell targets a single entity and deals magic damage equal to the caster's magic attack (`atkm`).
 * It is exclusive to the `BlackMage` class.
 *
 * @constructor Creates an instance of the `Thunder` class with a default name "Thunder" and a unique identifier.
 * @param id The unique identifier of the thunder spell action.
 */
class Thunder(id: String) extends AbstractMagic(id) {

  /**
   * Defines the name of the magic action.
   *
   * @return The name of this magic action, which is "Thunder".
   */
  override def name: String = "Thunder"

  /**
   * Executes the Thunder spell, targeting a specific entity.
   * This spell deals damage to the target based on the caster's magic attack (`atkm`).
   *
   * @param source The entity casting the spell (must be a `BlackMage` with sufficient MP).
   * @param target The target of the spell (must have HP greater than 0).
   * @throws InvalidTarget If the source or target has zero or negative HP, or if the source is not a `BlackMage`.
   * @throws InsufficientMana If the source lacks sufficient MP to cast the spell.
   */
  override def receiveTarget(source: Source, target: Target): Unit = {
    if (source.getHp <= 0) {
      throw new InvalidTarget(s"${source.getName} has negative HP, and can not perform thunder spell")
    }
    if (target.getHp <= 0) {
      throw new InvalidTarget(s"${target.getName} already defeated and can not receive thunder")
    }
    source match {
      case magicalCharacter: BlackMage =>
        val damageReceive: Int = source.getAtkm
        if (source.getMp >= 20) {
          target.decreaseHp(damageReceive)
          source.decreaseMp(20)
          println(s"${target.getName} has received $damageReceive damage")
          println(s"${source.getName} has decrease 20 mp")
          source.deactivateMagicBoost()
        } else {
          throw new InsufficientMana(s"${source.getName} does not have enough mana to cast ${this.getName}")
        }
      case _ =>
        throw new InvalidTarget(s"${source.getName} cannot perform this spell")
    }
  }
}
