package model.entity.enemy

import model.entity.AbstractGlobal

/**
 * Trait `IEnemy` represents the structure for enemy entities in the game.
 * It extends the `GameObject` interface, defining common attributes and behaviors
 * that all enemy types must implement.
 */
trait IEnemy extends AbstractGlobal {
  /**
   * The attack value of the enemy.
   */
  protected def atk: Int

  /**
   * Retrieves the attack value of the enemy.
   *
   * @return the attack value of the enemy.
   */
  def getAtk: Int
}
