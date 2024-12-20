package model.utilizable.potions.magic

import api.{Source, Target}

/**
 * The `Manna` class represents a specific type of magical potion in the game that restores mana.
 * It extends the `MagicPotion` trait, inheriting the basic properties and methods of magical potions.
 *
 * @param id   A unique identifier for the manna potion.
 * @param name The name of the manna potion.
 */
class Manna(val id: String, val name: String)
  extends MagicPotion {

  /**
   * Applies the manna potion to a target.
   * The potion restores 30% of the target's maximum mana points.
   * The amount of mana restored is calculated based on the target's max mana, rounded to an integer.
   *
   * @param target The target to which the manna potion will be applied.
   */
  override def applyPotion(target: Target): Unit = {
    val mannaRestore: Int = (target.getMaxMp * 0.30).toInt
    target.increaseMp(mannaRestore)
    println(s"${target.getName} has restored their mana by $mannaRestore points.")
  }
}
