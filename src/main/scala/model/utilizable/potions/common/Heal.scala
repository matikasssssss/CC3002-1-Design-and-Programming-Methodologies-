package model.utilizable.potions.common

import api.{Source, Target}

/**
 * The `Heal` class represents a healing potion in the game.
 * It extends the `CommonPotion` trait, inheriting the basic properties and methods of potions.
 * This class defines the behavior of the potion when applied to a target, specifically healing the target's health.
 *
 * @param id   A unique identifier for the healing potion.
 * @param name The name of the healing potion.
 */
class Heal(val id: String, val name: String)
  extends CommonPotion {

  /**
   * Applies the healing potion to a target.
   * The target's health is increased by 20% of their maximum health points.
   * The amount of healing is calculated based on the target's max health, rounded to an integer.
   *
   * @param target The target to which the healing potion will be applied.
   */
  override def applyPotion(target: Target): Unit = {
    val healAmount: Int = (target.getMaxHp * 0.2).toInt
    target.increaseHp(healAmount)
    println(s"${target.getName} has increased their health by $healAmount points.")
  }
}
