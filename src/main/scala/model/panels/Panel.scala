package model.panels

import model.entity.Global
import model.exception.*
import model.utilizable.weapons.Weapons
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `Panel` represents a panel on the game map, which can store entities and provide access to its properties.
 *
 * @param id      The unique identifier for the panel.
 * @param x       The x-coordinate of the panel on the map.
 * @param y       The y-coordinate of the panel on the map.
 */
class Panel(val id: String, val x: Int, val y: Int) extends IPanel {

  // Helper methods to throw exceptions when certain actions cannot be performed
  private def cannotEquipWeapon(): Nothing =
    throw new CannotEquipWeapon(s"${this.getId} can not equip weapon")

  private def cannotHeal(): Nothing =
    throw new CannotHeal(s"${this.getId} can not heal")

  private def cannotHaveMp(): Nothing =
    throw new CannotHaveMp(s"${this.getId} can not have mp")

  private def cannotHaveDef(): Nothing =
    throw new CannotHaveDef(s"${this.getId} can not have def")

  private def cannotUseMagicSpells(): Nothing =
    throw new CannotUseMagicSpells(s"${this.getId} can not use magic spells")

  private def cannotHaveName(): Nothing =
    throw new CannotHaveName(s"${this.getId} can not have name")

  /**
   * This method always returns `false` and throws an exception indicating that weapons cannot be equipped by this panel.
   *
   * @param weapon The weapon to equip.
   * @return Always throws an exception.
   */
  override def canEquipWeapon(weapon: Weapons): Boolean = {
    cannotEquipWeapon()
  }

  /**
   * This method throws an exception indicating that the panel cannot equip a weapon.
   *
   * @param weapon The weapon to equip.
   * @return Always throws an exception.
   */
  override def equipWeapon(weapon: Weapons): Unit = {
    cannotEquipWeapon()
  }

  /**
   * This method throws an exception indicating that health points cannot be decreased on this panel.
   *
   * @param amount The amount to decrease the health points.
   * @return Always throws an exception.
   */
  override def decreaseHp(amount: Int): Unit = {
    cannotHeal()
  }

  /**
   * This method throws an exception indicating that magic points cannot be decreased on this panel.
   *
   * @param amount The amount to decrease the magic points.
   * @return Always throws an exception.
   */
  override def decreaseMp(amount: Int): Unit = {
    cannotHaveMp()
  }

  /**
   * This method throws an exception indicating that defense points cannot be increased on this panel.
   *
   * @param amount The amount to increase the defense points.
   * @return Always throws an exception.
   */
  override def increaseDef(amount: Int): Unit = {
    cannotHaveDef()
  }

  /**
   * This method throws an exception indicating that health points cannot be increased on this panel.
   *
   * @param amount The amount to increase the health points.
   * @return Always throws an exception.
   */
  override def increaseHp(amount: Int): Unit = {
    cannotHeal()
  }

  /**
   * This method throws an exception indicating that magic points cannot be increased on this panel.
   *
   * @param amount The amount to increase the magic points.
   * @return Always throws an exception.
   */
  override def increaseMp(amount: Int): Unit = {
    cannotHaveMp()
  }

  /**
   * This method throws an exception indicating that magic boosts cannot be activated on this panel.
   *
   * @return Always throws an exception.
   */
  override def isMagicBoostActivate: Boolean = {
    cannotUseMagicSpells()
  }

  /**
   * This method throws an exception indicating that magic boosts cannot be activated on this panel.
   *
   * @return Always throws an exception.
   */
  override def activateMagicBoost(): Unit = {
    cannotUseMagicSpells()
  }

  /**
   * This method throws an exception indicating that magic boosts cannot be deactivated on this panel.
   *
   * @return Always throws an exception.
   */
  override def deactivateMagicBoost(): Unit = {
    cannotUseMagicSpells()
  }

  /**
   * This method throws an exception indicating that defense points cannot be retrieved from this panel.
   *
   * @return Always throws an exception.
   */
  override def getDef: Int = {
    cannotHaveDef()
  }

  /**
   * This method throws an exception indicating that health points cannot be retrieved from this panel.
   *
   * @return Always throws an exception.
   */
  override def getHp: Int = {
    cannotHeal()
  }

  /**
   * This method throws an exception indicating that maximum magic points cannot be retrieved from this panel.
   *
   * @return Always throws an exception.
   */
  override def getMaxMp: Int = {
    cannotHaveMp()
  }

  /**
   * This method throws an exception indicating that maximum health points cannot be retrieved from this panel.
   *
   * @return Always throws an exception.
   */
  override def getMaxHp: Int = {
    cannotHeal()
  }

  /**
   * This method throws an exception indicating that the name cannot be retrieved from this panel.
   *
   * @return Always throws an exception.
   */
  override def getName: String = {
    cannotHaveName()
  }

  /**
   * A mutable list buffer that stores entities of type `Global`.
   * This buffer is initially empty and can be used to store and manage
   * entities within a panel or other structure.
   */
  val storage: ListBuffer[Global] = ListBuffer.empty

  /**
   * Retrieves the x-coordinate of the panel.
   *
   * @return The x-coordinate as an `Int`.
   */
  def getX: Int = x

  /**
   * Retrieves the y-coordinate of the panel.
   *
   * @return The y-coordinate as an `Int`.
   */
  def getY: Int = y

  /**
   * Retrieves the unique identifier of the panel.
   *
   * @return The panel's unique ID as a `String`.
   */
  def getId: String = id

  /**
   * Retrieves the list of entities stored in the panel.
   *
   * @return A mutable list of `Global` objects contained within the panel.
   */
  def getStorage: ListBuffer[Global] = storage

  /**
   * Adds a unit to the panel's storage.
   *
   * @param unit The `Global` unit to be added to the panel.
   */
  def addUnits(unit: Global): Unit = {
    if (!storage.contains(unit)) {
      storage += unit
      unit.setPanel(this)
    } else {
      throw new InvalidTarget(s"${unit.getName} is in panel already")
    }
  }

  /**
   * Removes a unit from the panel's storage.
   *
   * @param unit The `Global` unit to be removed.
   * @throws UnitNotInPanel If the unit is not found in the panel's storage.
   */
  def removeUnits(unit: Global): Unit = {
    if (storage.contains(unit)) {
      storage -= unit
    } else {
      throw new UnitNotInPanel(s"${unit.getName} not in panel")
    }
  }

  /**
   * Clears all units from the panel's storage.
   */
  def clearUnits(): Unit = {
    storage.clear()
  }

  /**
   * Checks if a given panel is a neighbor of the current panel.
   * A panel is considered a neighbor if the difference in their x and y coordinates is exactly 1.
   *
   * @param panel The `IPanel` to check.
   * @return `true` if the panel is a neighbor, `false` otherwise.
   */
  def isNeighbor(panel: IPanel): Boolean = {
    if ((panel.x - this.x).abs + (panel.y - this.y).abs == 1) {
      true
    } else {
      false
    }
  }

  /**
   * Converts the panel and its attributes into a JSON object for serialization.
   *
   * @return A `JsObj` representing the panel's data.
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "x" -> x,
    "y" -> y,
    "storage" -> JsArr(storage.map(_.toJson))
  )

  /**
   * Checks equality with another object.
   *
   * @param other The object to compare with.
   * @return `true` if the two panels are equal based on their storage and coordinates; otherwise, `false`.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: IPanel => this.storage == other.getStorage &&
        this.x == other.x && this.y == other.y && this.id == other.getId
      case _ => false
    }
  }
}
