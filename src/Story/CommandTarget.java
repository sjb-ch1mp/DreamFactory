package Story;

/**
 * Enum Class to denote which targets a command can be directed towards
 */
public enum CommandTarget  implements java.io.Serializable{
    /**
     * A 'COMMAND' can only be directed towards the following CommandTargets.
     */
    CHARACTER, ENEMY, CONTAINER, ITEM, DIRECTION, ROOM, HERO, NONE
}
