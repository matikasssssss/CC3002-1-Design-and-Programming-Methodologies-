package model.actions.movement

import api.{Source, Target}
import model.actions.Actions
import model.panels.IPanel
import util.Json.{*, given}
import model.entity.Global
import model.exception.{IllegalArgument, InvalidTarget}

/**
 * Class `Move` represents a specific movement action in the game. It extends the `Actions` trait.
 * Each move action has a name and a unique identifier. The movement action allows a unit to move between adjacent panels.
 *
 * @constructor Creates an instance of the `Move` class with a specified name and unique identifier.
 * @param name The name of the movement action (protected).
 * @param id   The unique identifier of the movement action.
 */
class Move(protected val name: String, val id: String = "2")
  extends Actions {

  /**
   * Executes the movement action by moving a unit to a target panel.
   * The target panel must be adjacent to the current panel of the unit.
   *
   * @param source The entity performing the move (must be a `Global` object representing a unit).
   * @param target The target of the move (must be an `IPanel`).
   * @throws InvalidTarget If the source unit has negative HP.
   * @throws IllegalArgument If the target panel is not adjacent to the current panel of the unit.
   */
  override def receiveTarget(source: Source, target: Target): Unit = {
    if (source.getHp <= 0) {
      throw new InvalidTarget(s"${source.getName} has negative HP, and can not move")
    }
    (source, target) match {
      case (unit: Global, targetPanel: IPanel) =>
        val currentPanel: IPanel = unit.getPanel
        if (currentPanel.isNeighbor(targetPanel)) {
          currentPanel.removeUnits(unit)
          targetPanel.addUnits(unit)
          unit.setPanel(targetPanel)
          println(s"${unit.getName} move to ${targetPanel.getId}")
        } else {
          throw new IllegalArgument(s"Target panel is not adjacent to the current panel")
        }
      case _ =>
        throw new IllegalArgument(s"Source or target invalids")
    }
  }

  /**
   * Returns the name of the movement action.
   *
   * @return The name of the movement action.
   */
  def getName: String = name

  /**
   * Returns the unique identifier of the movement action.
   *
   * @return The unique identifier of the movement action.
   */
  def getId: String = id

  /**
   * Converts the movement action into a JSON object representation.
   * The JSON contains the ID and a description of the action in the form "Move→name".
   *
   * @return A `JsObj` containing the ID and the action name.
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "action" -> s"Move→$name"
  )

  /**
   * Checks if this movement action is equal to another object.
   * Two movement actions are considered equal if they have the same name.
   *
   * @param other The object to compare to.
   * @return `true` if the other object is a `Move` with the same name, `false` otherwise.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: Move => name == other.getName
      case _ => false
    }
  }
}
