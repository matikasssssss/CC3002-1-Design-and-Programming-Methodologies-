package model.scheduler

import api.AbstractSubject
import controller.GameController
import model.entity.Global
import model.player.Player

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

/**
 * The `TurnScheduler` class manages the turn order and action bar mechanics for game units.
 * It handles adding, removing, and updating units in the turn order, along with
 * their action bar calculations.
 */
class TurnScheduler extends AbstractSubject {

  /** List of units managed by the turn scheduler */
  private val units: ListBuffer[Global] = ListBuffer()

  /** The current unit whose turn it is */
  private var currentTurn: Option[Global] = None

  /** Maximum action bar for each unit */
  private val maxActionBar: mutable.Map[Global, Double] = mutable.Map()

  /** Current action bar for each unit */
  private val currentActionBar: mutable.Map[Global, Double] = mutable.Map()

  /**
   * Retrieves the list of units managed by the turn scheduler.
   *
   * @return A `ListBuffer` of `Global` objects representing the units in the game.
   */
  def getUnit: ListBuffer[Global] = units

  /**
   * Retrieves the maximum action bar value for a specific unit.
   *
   * @param unit The entity whose maximum action bar is being queried.
   * @return An `Option[Double]` representing the maximum action bar, or `None` if the unit is not found.
   */
  def getMaxActionBar(unit: Global): Option[Double] = maxActionBar.get(unit)

  /**
   * Retrieves the current action bar value for a specific unit.
   *
   * @param unit The entity whose current action bar is being queried.
   * @return A `Double` representing the current action bar of the unit.
   */
  def getCurrentActionBar(unit: Global): Double = {
    currentActionBar.getOrElse(unit, 0.0)
  }

  /**
   * Adds a unit to the turn scheduler.
   * Initializes the unit's current action bar to 0.0.
   *
   * @param unit The entity to be added.
   */
  def addUnit(unit: Global): Unit = {
    units += unit
    currentActionBar(unit) = 0.0
  }

  /**
   * Removes a unit from the turn scheduler.
   * Also removes the unit's current and maximum action bar values.
   *
   * @param unit The entity to be removed.
   */
  def removeUnit(unit: Global): Unit = {
    units -= unit
    currentActionBar.remove(unit)
    maxActionBar.remove(unit)
  }

  /**
   * Calculates the maximum action bar for all units based on their weight and weapon weight.
   * Updates the `maxActionBar` map for each unit.
   */
  def calculateMaxActionBar(): Unit = {
    units.foreach { unit =>
      val weapon = unit.getWeapon
      val weaponWeight = weapon.map(_.getWeight).getOrElse(0.0)
      val actionBar = unit.getWeight + (0.5 * weaponWeight)
      maxActionBar.update(unit, actionBar)
    }
  }

  /**
   * Resets the current action bar for a specific unit to 0.0.
   *
   * @param unit The entity whose action bar is to be reset.
   */
  def resetActionBar(unit: Global): Unit = {
    currentActionBar(unit) = 0.0
  }

  /**
   * Increases the current action bar for all units by a specified amount.
   *
   * @param amount The amount to increase the action bars by.
   */
  def increaseAllActionBar(amount: Double): Unit = {
    for ((unit, actionBar) <- currentActionBar) {
      currentActionBar(unit) = actionBar + amount
    }
  }

  /**
   * Checks if a specific unit has completed its action bar.
   * A unit has completed its action bar if its current action bar is greater than or equal to its maximum action bar.
   *
   * @param unit The entity to check.
   * @return `true` if the unit has completed its action bar; otherwise, `false`.
   */
  def hasCompletedActionBar(unit: Global): Boolean = {
    maxActionBar.get(unit).exists(max => currentActionBar.getOrElse(unit, 0.0) >= max)
  }

  /**
   * Retrieves a list of units that have a full action bar.
   *
   * @return A `ListBuffer` of units with full action bars, sorted by their remaining action bar capacity.
   */
  private def getUnitsWithFullActionBar: ListBuffer[Global] = {
    val fullUnits = units.filter(unit => currentActionBar(unit) >= maxActionBar(unit))
    ListBuffer(fullUnits.toList.sortBy(unit => maxActionBar(unit) - currentActionBar(unit)): _*)
  }

  /**
   * Retrieves the current unit whose turn it is based on action bar completion.
   * Updates the `currentTurn` variable to the next unit that has a full action bar.
   *
   * @return An `Option[Global]` representing the unit whose turn it is, or `None` if no units are ready.
   */
  def getUnitTurn: Option[Global] = {
    val fullUnits = getUnitsWithFullActionBar
    currentTurn = fullUnits.headOption
    currentTurn
  }

  /**
   * Checks if a player has been defeated and notifies accordingly.
   *
   * @param player The player to check for defeat.
   */
  private def checkPlayerIsDefeated(player: Player): Unit = {
    if (player.isDefeated) {
      notify(s"Player ${player.getId} has been defeated")
    }
  }

  /**
   * Ends the current turn and checks if any players have been defeated.
   * Notifies the next unit's turn.
   */
  def endTurn(): Unit = {
    checkPlayerIsDefeated(GameController.player1)
    checkPlayerIsDefeated(GameController.player2)

    getUnitTurn.foreach { unit =>
      notify(s"Next turn is for ${unit.getName}")
    }
  }
}
