package model.utilizable.potions.common

import api.{Source, Target}

/**
 * The `Strength` class represents a strength potion in the game.
 * It extends the `CommonPotion` trait, inheriting the basic properties and methods of potions.
 * This class defines the behavior of the potion when applied to a target, specifically increasing the target's defense.
 *
 * @param id   A unique identifier for the strength potion.
 * @param name The name of the strength potion.
 */
class Strength(val id: String, val name: String)
  extends CommonPotion {

  /**
   * Applies the strength potion to a target.
   * The target's defense is increased by 15% of their current defense value.
   * The amount of defense increase is calculated based on the target's defense, rounded to an integer.
   *
   * @param target The target to which the strength potion will be applied.
   */
  override def applyPotion(target: Target): Unit = {
    val defenseAmount: Int = (target.getDef * 0.15).toInt
    target.increaseDef(defenseAmount)
    println(s"${target.getName} has increased their defense by $defenseAmount points.")
  }
}
