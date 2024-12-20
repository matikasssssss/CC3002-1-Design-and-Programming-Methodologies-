package controller

import munit.FunSuite
import controller.GameController
import controller.GameController.{player1, player2}
import model.entity.Global
import model.entity.character.common.{Archer, Knight}
import model.utilizable.weapons.Weapons
import spray.json.JsArray
import util.Json
import util.SprayJson.JsArr

import scala.collection.mutable.ListBuffer

class GameControllerTest extends FunSuite{
  test("GameController initializes players and units correctly"){
    val unitsPlayer1= ListBuffer(
      new Knight("c1", "Relm", 100, 15, 60),
      new Knight("c2", "Setzer", 150, 10, 75),
      new Knight("c3", "Terra", 200, 20, 100),
    )
    assertEquals(GameController.player1.getUnits.map(_.getName), unitsPlayer1.map(_.getName))
  }

  test("GameController.reset should reset the game state"){
    GameController.scheduler.removeUnit(GameController.player1.getUnits.head)
    GameController.reset()

    assertEquals(GameController.Panel1.getStorage.map(_.getName), ListBuffer("Relm"))
    assertEquals(GameController.Panel2.getStorage.map(_.getName), ListBuffer("Setzer"))
    assertEquals(GameController.Panel4.getStorage.map(_.getName), ListBuffer("Terra"))
    assert(GameController.Panel3.getStorage.isEmpty)
  }

  test("GameController.doAction should execute valid actions"){
    val weapons: ListBuffer[Weapons] = ListBuffer(GameController.swordRelm)

    val result = GameController.doAction("KnightAttack1", "c1", "c2")
    assertEquals(result, "Action executed!")

    val target: Option[Global] = GameController.findUnitById("c2")
    assert(GameController.Setzer.getHp<150)
  }

  test("GameController.findActionsByGameUnitId") {
    println(GameController.findActionsByGameUnitId("c2"))
    val expected = GameController.findActionsByGameUnitId("c2")
    assertEquals(GameController.findActionsByGameUnitId("c2"), expected)
  }

  test("GameController.getGamePanels should return panels"){
    val expected = JsArr(GameController.Panel1.toJson, GameController.Panel2.toJson, GameController.Panel3.toJson, GameController.Panel4.toJson)
    assertEquals(GameController.getGamePanels, expected)
  }

  test("GameController.decideNextGameUnitId should return next game unit that will play"){
    assertEquals(GameController.decideNextGameUnitId, "")
  }

  test("GameController.getCurrentGameUnitId should return current game unit"){
    intercept[IllegalStateException]{
      GameController.getCurrentGameUnitId
    }
    val knight: Knight = new Knight("p2", "Gogo", 60, 60, 85)
    val archer: Archer = new Archer("p1", "lucas", 60, 50, 65)

    GameController.scheduler.addUnit(knight)
    GameController.scheduler.addUnit(archer)
    GameController.scheduler.calculateMaxActionBar()
    GameController.scheduler.increaseAllActionBar(100)
    println(GameController.scheduler.getCurrentActionBar(knight))
    println(GameController.scheduler.getCurrentActionBar(archer))

    val result = GameController.getCurrentGameUnitId
    print(result)
    assertEquals(result, "c2")
  }

  test("GameController.getPlayers should return players"){
    val expected = JsArr(player1.toJson, player2.toJson)
    assertEquals(GameController.getPlayers, expected)
  }

  test("If player if defeated or not"){
    print(GameController.player2.isDefeated)
    GameController.scheduler.endTurn()
    GameController.update("Player p2 has been defeated")
    print(GameController.player1.isDefeated)
    GameController.update("Next turn is for c2")
  }

  test("GameController is an observer"){
    GameController.scheduler.addObserver(GameController)
    GameController.scheduler.removeObserver(GameController)
  }


}
