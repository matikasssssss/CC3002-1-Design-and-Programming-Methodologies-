package model.actions.movement

import model.actions.Actions
import model.entity.Global
import model.entity.character.common.Archer
import model.exception.{IllegalArgument, InvalidTarget}
import model.panels.{IPanel, Panel}
import munit.FunSuite
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `MoveTest` is a suite of unit tests for the `Move` action,
 * verifying the functionality of its methods and properties related to movement actions.
 *
 * The tests include checking attributes, equality comparisons,
 * and the JSON representation of the move action.
 */
class MoveTest extends FunSuite {

  /**
   * Move action instances
   */
  val moveLeft1: Move = new Move("Left", "G1")
  val moveLeft2: Move = new Move("Left", "G1")
  val moveRight: Move = new Move("Right", "G1")

  /**
   * Test case to verify the attributes of the Move action.
   */
  test("Move Attributes") {
    assertEquals(moveLeft1.getName, "Left")
    assertEquals(moveLeft1.getId, "G1")
  }

  /**
   * Test case to verify equality of two Move instances with the same name.
   */
  test("Equals should return true if the action have the same name") {
    assertEquals(moveLeft1, moveLeft2)
  }

  /**
   * Test case to verify that a Move instance is equal to itself.
   */
  test("Equals should return true when comparing the same action") {
    assertEquals(moveLeft1, moveLeft1)
  }

  /**
   * Test case to verify inequality of two Move instances with different names.
   */
  test("Equals should return false if the action don't have the same name") {
    assertEquals(moveLeft1 == moveRight, false)
  }

  /**
   * Test case to verify inequality when comparing a Move instance with a non-action.
   */
  test("Equals should return false when comparing with a non-action") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(moveLeft1 == nonHeal, false)
  }

  test("An entity can move only in neighbor panels"){
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val panel12: IPanel = new Panel("p1", 1, 2)
    val panel22: IPanel = new Panel("p3", 2, 2)
    val panel44: IPanel = new Panel("p3", 4, 4)
    panel12.addUnits(archer)
    println(s"Panel 12 units: ${panel12.getStorage}")
    println(s"Panel 22 units: ${panel22.getStorage}")
    println(s"Archer is in panel: ${archer.getPanel}")
    val moveArcher: Actions = new Move("Movement Archer")
    archer.doAction(moveArcher, panel22)
    println(s"Archer is in panel: ${archer.getPanel}")
    println(s"Panel 12 units: ${panel12.getStorage}")
    println(s"Panel 22 units: ${panel22.getStorage}")
    assertEquals(panel12.getStorage, ListBuffer.empty)
    assertEquals(panel22.getStorage, ListBuffer(archer))

    intercept[IllegalArgument] {
      archer.doAction(moveArcher, panel44)
    }
  }

  test("An entity that have negative/null hp can not perform movement action") {
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val panel12: IPanel = new Panel("p1", 1, 2)
    val panel22: IPanel = new Panel("p3", 2, 2)
    val panel44: IPanel = new Panel("p3", 4, 4)
    panel12.addUnits(archer)
    val moveArcher: Actions = new Move("Movement Archer")
    archer.decreaseHp(1000)
    val exception = intercept[InvalidTarget] {
      archer.doAction(moveArcher, panel22)
    }
    println(exception)
  }

  test("Target should be a panel"){
    val archer1: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val archer2: Global = new Archer("archer2", "Legolas2", 70, 50, 65)
    val moveArcher: Actions = new Move("Movement Archer")
    intercept[IllegalArgument] {
      archer1.doAction(moveArcher, archer2)
    }
  }

  /**
   * Test case to verify the JSON representation of a Move action instance.
   */
  test("toJson should return correct JSON for the action") {
    val expectedJson: JsObj = JsObj(
      "id" -> "G1",
      "action" -> s"Move→Left"
    )
    assertEquals(moveLeft1.toJson, expectedJson)
  }
}
