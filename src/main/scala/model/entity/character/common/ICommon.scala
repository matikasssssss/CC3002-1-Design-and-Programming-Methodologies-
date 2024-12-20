package model.entity.character.common

import model.entity.character.Entity

/**
 * Trait `ICommon` serves as a marker trait for common character entities in the game.
 * It extends the `Entity` trait, meaning any class implementing `ICommon`
 * will inherit the methods and properties defined in `Entity`.
 *
 * This trait does not define additional methods or fields but serves as a common
 * interface for all characters that share common functionality in the game.
 */
trait ICommon extends Entity {
}

