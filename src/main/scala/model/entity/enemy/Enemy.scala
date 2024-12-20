package model.entity.enemy

import api.{Source, Target}
import model.actions.Actions
import model.actions.attack.Attack
import model.actions.magic.{Healing, Meteor, Purification, Thunder}
import model.actions.movement.Move
import model.entity.Global
import model.exception.{*, given}
import model.panels.{IPanel, Panel}
import model.utilizable.potions.common.{Heal, Strength}
import model.utilizable.potions.magic.{MagicalForce, Manna}
import model.utilizable.weapons.Weapons
import util.Json.{*, given}

/**
 * The `Enemy` class represents an enemy entity in the game, implementing the `IEnemy` trait.
 * It holds the characteristics of an enemy, such as health, attack, defense, and weight.
 * The enemy can perform actions like attack and movement.
 *
 * @constructor Creates an instance of the `Enemy` class with specified attributes.
 * @param id      The unique identifier of the enemy.
 * @param name    The name of the enemy.
 * @param hp      The current health points of the enemy.
 * @param atk     The attack value of the enemy.
 * @param defense The defense value of the enemy.
 * @param weight  The weight of the enemy.
 */
class Enemy(val id: String, val name: String, var hp: Int, protected var atk: Int, protected var defense: Int, protected val weight: Int)
  extends IEnemy {

  /**
   * A list of actions available to the enemy, including attack and movement actions.
   */
  override val actions: List[Actions] = List(
    new Attack("Enemy Attack", "AttackEnemy1"),
    new Move("Enemy Movement", "EnemyMove2")
  )

  /**
   * The maximum health points (HP) of the character,
   * initialized with the initial value of `hp`.
   */
  val maxHp: Int = hp

  // Maximum defense (initialized with the given value).
  val maxDef: Int = defense

  // Maximum magic points (MP) of the enemy. In this case, it is 0.
  val maxMp: Int = 0

  /**
   * Retrieves the name of the enemy.
   *
   * @return The name of the enemy.
   */
  def getName: String = name

  /**
   * Retrieves the current health points of the enemy.
   *
   * @return The current health points of the enemy.
   */
  override def getHp: Int = hp

  /**
   * Retrieves the maximum health points of the character.
   *
   * @return The maximum health points.
   */
  override def getMaxHp: Int = maxHp

  /**
   * Retrieves the maximum defense value of the enemy.
   *
   * @return The maximum defense value.
   */
  override def getMaxDef: Int = defense

  /**
   * Retrieves the attack value of the enemy.
   *
   * @return The attack value of the enemy.
   */
  override def getAtk: Int = atk

  /**
   * Retrieves the defense value of the enemy.
   *
   * @return The defense value of the enemy.
   */
  override def getDef: Int = defense

  /**
   * Retrieves the magical attack value of the enemy.
   * Since enemies do not possess magical abilities, this is set to 0.
   *
   * @return The magical attack value (which is 0 for the enemy).
   */
  override def getAtkm: Int = 0

  /**
   * Retrieves the weight of the enemy.
   *
   * @return The weight of the enemy.
   */
  override def getWeight: Double = weight

  /**
   * Retrieves the unique identifier of the enemy.
   *
   * @return The unique identifier of the enemy.
   */
  override def getId: String = id

  /**
   * Decreases the enemy's health points by a specified damage amount.
   * Ensures that health points do not fall below zero.
   *
   * @param damage The amount of damage to apply to the enemy's health points.
   */
  override def decreaseHp(damage: Int): Unit = {
    hp = (hp - damage).max(0)
  }

  /**
   * Executes an action for the enemy, applying it to the target.
   *
   * @param action The action to perform.
   * @param target The target of the action.
   */
  override def doAction(action: Actions, target: Target): Unit = {
    action.receiveTarget(this, target)
  }

  /**
   * Compares this enemy instance with another object for equality.
   *
   * @param other The other object to compare with.
   * @return True if the objects are equal, otherwise false.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: IEnemy =>
        name == other.getName && hp == other.getHp &&
          atk == other.getAtk && defense == other.getDef &&
          weight == other.getWeight
      case _ => false
    }
  }

  /**
   * Converts this enemy instance to a JSON object representation.
   *
   * @return A JSON object representing the enemy.
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "attributes" -> JsArr(
      JsObj("name" -> "name", "value" -> name),
      JsObj("name" -> "hp", "value" -> hp),
      JsObj("name" -> "atk", "value" -> atk),
      JsObj("name" -> "def", "value" -> defense),
      JsObj("name" -> "weight", "value" -> weight)
    ),
    "img" -> s"$name.gif"
  )
}
