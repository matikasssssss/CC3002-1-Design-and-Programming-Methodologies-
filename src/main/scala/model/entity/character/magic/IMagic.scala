package model.entity.character.magic

import model.entity.character.Entity

/**
 * Trait `IMagic` represents a character that specializes in magic in the game.
 * It extends the `Entity` trait, inheriting common attributes and behaviors,
 * while specifically defining behaviors and properties related to magic users.
 *
 * `IMagic` is used as a base trait for characters that utilize magical abilities,
 * such as Black Mage and White Mage, providing them with magic-specific actions and attributes.
 *
 * This trait does not define any specific behavior but serves as a marker interface for magic users.
 */
trait IMagic extends Entity {

}

