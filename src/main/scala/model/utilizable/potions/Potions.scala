package model.utilizable.potions

import api.{Source, Target}
import model.utilizable.{AbstractUtilizable, Utilizable}

/**
 * Trait `Potions` represents potion items that can be utilized within the game.
 * It extends the `Utilizable` trait, inheriting its properties and methods.
 * Potions can be used by a `Target`, typically applying effects like healing or boosting attributes.
 */
trait Potions extends AbstractUtilizable {

  /**
   * Gets the name of the potion.
   *
   * @return The name of the potion as a string.
   */
  def getName: String

  /**
   * Gets the unique identifier of the potion.
   *
   * @return The ID of the potion as a string.
   */
  def getId: String

  /**
   * Applies the potion to a target.
   * This method defines how the potion affects the target, such as healing, boosting attributes, etc.
   *
   * @param target The target to which the potion will be applied.
   */
  def applyPotion(target: Target): Unit
}
