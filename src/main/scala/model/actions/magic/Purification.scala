package model.actions.magic

import api.{Source, Target}
import model.entity.character.magic.WhiteMage
import model.entity.enemy.IEnemy
import model.exception.{CannotUseMagicSpells, InsufficientMana, InvalidTarget}

/**
 * Class `Purification` represents a specific magic action for casting a Purification spell in the game.
 * This spell targets enemies and reduces their HP. It is exclusive to the `WhiteMage` class.
 *
 * @constructor Creates an instance of the `Purification` class with a default name "Purification" and a unique identifier.
 * @param id The unique identifier of the purification spell action.
 */
class Purification(id: String) extends AbstractMagic(id) {

  /**
   * Defines the name of the magic action.
   *
   * @return The name of this magic action, which is "Purification".
   */
  override def name: String = "Purification"

  /**
   * Executes the Purification spell, targeting a specific enemy.
   * This spell deals damage to the enemy based on their HP and the caster's magic attack (`atkm`).
   *
   * @param source The entity casting the spell (must be a `WhiteMage` with sufficient MP).
   * @param target The target of the spell, which must be an enemy (`IEnemy`).
   * @throws InvalidTarget If the source or target has zero or negative HP, or if the target is not an enemy.
   * @throws InsufficientMana If the source lacks sufficient MP to cast the spell.
   */
  override def receiveTarget(source: Source, target: Target): Unit = {
    if (source.getHp <= 0) {
      throw new InvalidTarget(s"${source.getName} has negative HP, and can not perform purification spell")
    }
    if (target.getHp <= 0) {
      throw new InvalidTarget(s"${target.getName} already defeated and can not receive purification")
    }
    source match {
      case magicalCharacter: WhiteMage =>
        target match {
          case enemy: IEnemy =>
            val hpDecrease: Int = (enemy.getHp * 0.15 + source.getAtkm / 2).toInt
            if (source.getMp >= 25) {
              enemy.decreaseHp(hpDecrease)
              println(s"${enemy.getName} has received $hpDecrease damage")
              source.decreaseMp(25)
              println(s"${source.getName} has decrease 25 mp")
              source.deactivateMagicBoost()
            } else {
              throw new InsufficientMana(s"${source.getName} does not have enough mana to cast ${this.getName}")
            }
          case _ =>
            throw new InvalidTarget(s"Purification can only be used on enemies, but ${target.getName} is not an enemy")
        }
      case _ =>
        throw new InvalidTarget(s"${source.getName} can not perform this spell")
    }
  }
}
