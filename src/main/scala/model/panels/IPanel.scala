package model.panels

import model.entity.Global
import scala.collection.mutable.ListBuffer
import api.{GameObject, Target}

/**
 * Trait `IPanel` represents a panel in the game map that can store entities.
 *
 * Extends the `GameObject` trait to include game-related functionalities.
 */
trait IPanel extends GameObject with Target {

  /** Unique identifier for the panel. */
  def id: String

  /** The x-coordinate of the panel on the map. */
  def x: Int

  /** The y-coordinate of the panel on the map. */
  def y: Int

  /** A mutable list that stores entities located in this panel. */
  def storage: ListBuffer[Global]

  /**
   * Retrieves the unique identifier of the panel.
   *
   * @return The panel's unique ID as a `String`.
   */
  def getId: String

  /**
   * Retrieves the x-coordinate of the panel.
   *
   * @return The x-coordinate as an `Int`.
   */
  def getX: Int

  /**
   * Retrieves the y-coordinate of the panel.
   *
   * @return The y-coordinate as an `Int`.
   */
  def getY: Int

  /**
   * Adds a unit to the panel's storage.
   *
   * @param unit The unit to be added to the panel.
   */
  def addUnits(unit: Global): Unit

  /**
   * Removes a unit from the panel's storage.
   *
   * @param unit The unit to be removed from the panel.
   */
  def removeUnits(unit: Global): Unit

  /**
   * Retrieves the list of entities stored in the panel.
   *
   * @return A mutable list of `Global` objects contained within the panel.
   */
  def getStorage: ListBuffer[Global]


  /**
   * Clears all units from the panel's storage.
   */
  def clearUnits(): Unit

  /**
   * Checks if the specified panel is a neighbor of this panel.
   *
   * @param panel The panel to be checked.
   * @return True if the specified panel is a neighbor, otherwise false.
   */
  def isNeighbor(panel: IPanel): Boolean

}
