package controller

import api.{GameApi, IGameController, Observer, Source}
import model.actions.attack.Attack
import model.actions.consume.CPotion
import model.actions.equip.AWeapon
import model.actions.magic.{Healing, Meteor, Purification, Thunder}
import model.actions.movement.Move
import model.entity.Global
import model.entity.character.common.Knight
import model.panels.{IPanel, Panel}
import model.player.Player
import model.scheduler.TurnScheduler
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.Sword
import spray.json.{JsArray, JsValue}
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

object GameController extends IGameController with Observer {

  /**
   * Finds a unit by its ID among the active players.
   *
   * @param id The ID of the unit.
   * @return `Some(unit)` if the unit is found, `None` otherwise.
   */
  def findUnitById(id: String): Option[Global] = {
    val units = player1.getUnits ++ player2.getUnits
    units.find(_.getId == id)
  }

  /**
   * Handles updates received from the `TurnScheduler`.
   *
   * @param message The message received from the `TurnScheduler`.
   * This method processes messages indicating player defeat and turn changes.
   */
  override def update(message: String): Unit = {
    println(message)
    if (message.contains("defeated")) {
      val outPlayer = message.split("")(1)
      if (outPlayer == player1.getId) {
        println(s"Player 1 ${player1.getId} has been defeated!")
      } else if (outPlayer == player2.getId){
        println(s"Player 2 ${player2.getId} has been defeated!")
      }
      println("Game Over!")
    } else if (message.contains("Next turn")) {
      println(message)
    }
  }

  /**
   * The main entry point of the game. Initializes the game and starts the scheduler.
   *
   * @param args The command-line arguments.
   */
  def main(args: Array[String]): Unit = {
    GameApi.run(args)
    scheduler.addObserver(this)
  }

  // Define sample units
  private val Relm: Global = new Knight("c1", "Relm", 100, 15, 60)
  val Setzer: Global = new Knight("c2", "Setzer", 150, 10, 75)
  private val Terra: Global = new Knight("c3", "Terra", 200, 20, 100)

  // Define sample weapons
  val swordRelm: Weapons = new Sword("RelmSword", "Sword Relm", 85, 40, Relm)
  private val swordSetzer: Weapons = new Sword("SetzerSword", "Sword Setzer", 85, 40, Setzer)
  private val swordTerra: Weapons = new Sword("TerraSword", "Sword Terra", 85, 40, Terra)

  // Equip weapons
  Relm.doAction(AWeapon("Knight Equip Weapon", "KnightAWeapon1", ListBuffer(swordRelm)), Relm)
  Setzer.doAction(AWeapon("Knight Equip Weapon", "KnightAWeapon1", ListBuffer(swordSetzer)), Setzer)
  Terra.doAction(AWeapon("Knight Equip Weapon", "KnightAWeapon1", ListBuffer(swordTerra)), Terra)

  // Define players
  val player1: Player = new Player("p1", ListBuffer(Relm, Setzer, Terra))
  val player2: Player = new Player("p2", ListBuffer())

  // Define the turn scheduler and add units to it
  val scheduler: TurnScheduler = new TurnScheduler()
  scheduler.addUnit(Relm)
  scheduler.addUnit(Setzer)
  scheduler.addUnit(Terra)
  scheduler.calculateMaxActionBar()

  /**
   * Retrieves the current state of the players as a JSON value.
   *
   * @return A JSON array representing the current state of player1 and player2.
   */
  def getPlayers: JsVal =
    JsArr(player1.toJson, player2.toJson)

  // Define panels for the game
  val Panel1: IPanel = new Panel("p1", 1, 1)
  val Panel2: IPanel = new Panel("p2", 2, 1)
  val Panel3: IPanel = new Panel("p3", 2, 2)
  val Panel4: IPanel = new Panel("p4", 1, 2)

  // Add units to panels
  Panel1.addUnits(Relm)
  Panel2.addUnits(Setzer)
  Panel4.addUnits(Terra)

  /**
   * Retrieves the current state of all panels as a JSON value.
   *
   * @return A JSON array representing the state of all game panels.
   */
  def getGamePanels: JsVal =
    JsArr(Panel1.toJson, Panel2.toJson, Panel3.toJson, Panel4.toJson)

  /**
   * Retrieves the ID of the current unit whose turn it is.
   *
   * @return The ID of the current unit.
   * @throws IllegalStateException If no unit is currently active.
   */
  override def getCurrentGameUnitId: String = {
    scheduler.getUnitTurn match {
      case Some(unit) => unit.getId
      case None => throw new IllegalStateException(s"No active units")
    }
  }

  /**
   * Decides the next unit to take its turn and updates the action bars.
   *
   * @return The ID of the unit whose turn it is next.
   */
  def decideNextGameUnitId: String = {
    scheduler.increaseAllActionBar(50)
    scheduler.getUnitTurn match {
      case Some(unit) => unit.getId
      case None => ""
    }
  }

  /**
   * Executes a specified action between a source and a target unit.
   *
   * @param actionId The ID of the action to be executed.
   * @param sourceId The ID of the source unit (performing the action).
   * @param targetId The ID of the target unit (receiving the action).
   * @return A message indicating the result of the action.
   */
  def doAction(actionId: String, sourceId: String, targetId: String): String = {
    val source: Option[Global] = findUnitById(sourceId)
    val target: Option[Global] = findUnitById(targetId)

    (source, target) match {
      case (Some(sor), Some(tar)) =>
        val action = sor.findActionById(actionId)
        action match {
          case Some(act) =>
            sor.doAction(act, tar)
            scheduler.resetActionBar(sor)
            if (tar.getHp <= 0) {
              scheduler.removeUnit(tar)
              player1.removeUnit(tar)
              player2.removeUnit(tar)
              println(s"Unit ${tar.getName} has been defeated!")
            }
            "Action executed!"
          case None =>
            "Action not found"
        }
      case _ => "Source or Target not found"
    }
  }

  /**
   * Retrieves the available actions for a unit by its ID.
   *
   * @param id The ID of the unit.
   * @return A JSON array representing the available actions of the unit.
   */
  def findActionsByGameUnitId(id: String): Option[JsValue] = {
    findUnitById(id).map { unit =>
      val actionsJson: Seq[JsValue] = unit.availableActions.map {
        case attack: Attack => attack.toJson
        case cPotion: CPotion => cPotion.toJson
        case aWeapon: AWeapon => aWeapon.toJson
        case healing: Healing => healing.toJson
        case meteor: Meteor => meteor.toJson
        case purification: Purification => purification.toJson
        case thunder: Thunder => thunder.toJson
        case move: Move => move.toJson
      }
      JsArray(actionsJson: _*)
    }
  }

  /**
   * Resets the game state to its initial configuration.
   *
   * This method clears all panels, resets the action bars of all units, 
   * and re-adds the initial units to the panels.
   */
  def reset(): Unit = {
    scheduler.getUnit.foreach(unit => scheduler.resetActionBar(unit))
    Panel1.clearUnits()
    Panel2.clearUnits()
    Panel3.clearUnits()
    Panel4.clearUnits()

    Panel1.addUnits(Relm)
    Panel2.addUnits(Setzer)
    Panel4.addUnits(Terra)

    println(s"Game has been reset!")
  }
}
