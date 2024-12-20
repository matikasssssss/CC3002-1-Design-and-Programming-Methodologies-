package model.actions.consume

import api.{Source, Target}
import model.actions.Actions
import model.entity.character.Entity
import model.entity.character.common.ICommon
import model.entity.character.magic.{BlackMage, IMagic, WhiteMage}
import model.exception.{CannotUsePotion, InvalidTarget}
import model.utilizable.Utilizable
import model.utilizable.potions.Potions
import model.utilizable.potions.magic.MagicPotion

import scala.collection.mutable.ListBuffer
import util.Json.{*, given}

/**
 * Class `CPotion` represents a potion consumption action in the game.
 * This class extends the `Actions` trait and enables an entity to consume multiple potions,
 * applying each potion’s effects on a specified target.
 *
 * @constructor Initializes a `CPotion` instance with a given name, unique identifier, and a list of target potions.
 * @param name    The name of the potion consumption action (protected visibility).
 * @param id      The unique identifier for this action.
 * @param targets A list of potions that will be consumed and applied to the target.
 */
class CPotion(protected val name: String, val id: String, val targets: ListBuffer[Potions]) extends Actions {

  /**
   * Handles the interaction between a source and a target when consuming potions.
   * Ensures the source and target meet the necessary conditions and processes each potion's effects.
   *
   * @param source The entity consuming the potions (must implement `Source`).
   * @param target The entity receiving the potion effects (must implement `Target`).
   * @throws InvalidTarget If the source or target is in an invalid state for consuming or receiving potions.
   * @throws CannotUsePotion If the source attempts to consume an invalid potion type.
   */
  override def receiveTarget(source: Source, target: Target): Unit = {
    if (source.getHp <= 0) {
      throw new InvalidTarget(s"${source.getName} has negative HP and cannot consume any potion")
    }
    if (target.getHp <= 0) {
      throw new InvalidTarget(s"${target.getName} is already defeated and cannot receive any potion")
    }
    source match {
      case magicalCharacter: IMagic =>
        targets.foreach { potion =>
          potion.applyPotion(target)
          println(s"${magicalCharacter.getName} has consumed the potion ${potion.getName}")
        }

      case commonCharacter: ICommon =>
        targets.foreach {
          case _: MagicPotion =>
            throw new CannotUsePotion(s"${commonCharacter.getName} cannot use a magic potion")
          case potion =>
            potion.applyPotion(target)
            println(s"${commonCharacter.getName} has consumed the potion ${potion.getName}")
        }
      case _ =>
        throw new CannotUsePotion(s"Enemies cannot consume potions")
    }
  }

  /**
   * Retrieves the name of the potion consumption action.
   *
   * @return The name of this action.
   */
  def getName: String = name

  /**
   * Retrieves the unique identifier for the potion consumption action.
   *
   * @return The ID of this action.
   */
  def getId: String = id

  /**
   * Converts the potion consumption action into a JSON representation.
   *
   * @return A `JsObj` containing the ID and a string representing the action in the form "Consume a potion→name".
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "action" -> s"Consume a potion→$name"
  )

  /**
   * Checks equality between this potion consumption action and another object.
   * Two `CPotion` actions are considered equal if they share the same name.
   *
   * @param other The object to compare.
   * @return `true` if the other object is a `CPotion` with the same name, `false` otherwise.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: CPotion => name == other.getName
      case _ => false
    }
  }
}
