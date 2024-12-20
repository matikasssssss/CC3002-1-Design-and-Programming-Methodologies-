package model.actions.magic

import api.{Source, Target}
import model.entity.Global
import model.entity.character.magic.BlackMage
import model.exception.{InsufficientMana, InvalidTarget}
import model.panels.IPanel

import scala.collection.mutable.ListBuffer

/**
 * Class `Meteor` represents a specific magic action for casting a Meteor spell in the game.
 * It extends the `AbstractMagic` class, and the name of this action is set to "Meteor" by default.
 *
 * @constructor Creates an instance of the `Meteor` class with a default name "Meteor" and a unique identifier.
 * @param id The unique identifier of the meteor spell action.
 */
class Meteor(id: String = "3") extends AbstractMagic(id) {

  /**
   * Defines the name of the magic action.
   *
   * @return The name of this magic action, which is "Meteor".
   */
  override protected def name: String = "Meteor"

  /**
   * Executes the Meteor spell targeting a specific panel.
   * The spell affects all units within the panel's storage.
   *
   * @param source The entity casting the spell (must be a `BlackMage` with sufficient MP).
   * @param target The target of the spell, which must be a panel (`IPanel`).
   * @throws InvalidTarget If the source has zero or negative HP, or the target is invalid.
   * @throws InsufficientMana If the source lacks sufficient MP to cast the spell.
   */
  override def receiveTarget(source: Source, target: Target): Unit = {
    if (source.getHp <= 0) {
      throw new InvalidTarget(s"${source.getName} has negative HP, and can not perform meteor spell")
    }

    source match {
      case magicalCharacter: BlackMage =>
        target match {
          case panel: IPanel =>
            panel.getStorage.foreach { unit =>
              if (unit.getHp <= 0) {
                throw new InvalidTarget(s"${unit.getName} have zero or negative hp, and can not receive a meteor spell")
              }
              val meteorDamage: Int = source.getAtkm
              if (source.getMp >= 50) {
                unit.decreaseHp(meteorDamage)
                println(s"${unit.getName} has received $meteorDamage damage")
                source.decreaseMp(50)
                println(s"${source.getName} has decrease 50 mp")
                source.deactivateMagicBoost()
              } else {
                throw new InsufficientMana(s"${source.getName} does not have enough mana to cast ${this.getName}")
              }
            }
          case _ =>
            throw new InvalidTarget(s"Meteor must target a panel with units")
        }
      case _ =>
        throw new InvalidTarget(s"${source.getName} can not perform this spell")
    }
  }
}
