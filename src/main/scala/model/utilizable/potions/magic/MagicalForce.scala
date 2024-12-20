package model.utilizable.potions.magic

import api.{Source, Target}

/**
 * The `MagicalForce` class represents a specific type of magical potion in the game.
 * This potion applies a magic boost to a target when used.
 * It extends the `MagicPotion` trait, inheriting the basic properties and methods of magical potions.
 *
 * @param id   A unique identifier for the magical force potion.
 * @param name The name of the magical force potion.
 */
class MagicalForce(val id: String, val name: String)
  extends MagicPotion {

  /**
   * Applies the magical force potion to a target.
   * The potion activates a magic boost on the target, enhancing their magical abilities.
   *
   * @param target The target to which the magical force potion will be applied.
   */
  override def applyPotion(target: Target): Unit = {
    target.activateMagicBoost()
    println(s"${target.getName} has activated the magical force boost.")
  }
}
