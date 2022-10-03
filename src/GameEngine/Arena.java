package GameEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import Story.Command;
import Story.Enemy;
import Story.Hero;
import Story.Item;
import Story.Mode;

/**
 * The Arena class contains all the necessary data and functions required
 * to simulate combat in the GameEngine. When the player starts combat, the 
 * Hero and target Enemy are stored in an Arena object. During combat, the GameEngine's
 * Mode is locked so that only the Hero/Enemy in the Arena are modified. When
 * combat finishes, the Hero and Enemy are returned to the GameState.
 * 
 * The valid actions that a player can take in combat are: ATTACK, BLOCK & DODGE.
 * 
 * The valid actions that an enemy can take in combat are: ATTACK & POWER_UP.
 * 
 * Enemies and players may 'critically' hit - this is determined randomly.
 * 
 * @author Samuel J. Brookes (u5380100)
 */
public class Arena implements Serializable {
    
    private boolean heroHasInitiative;
    private Enemy enemy;
    private Hero hero;
    private ArrayList<Item> equippedItems;
    private boolean heroEscaped;
    private boolean enemyPoweringUp;

    /**
     * The constructor for the Arena class takes the Hero, a list of
     * all Items from the Hero's inventory that are equipped (this is so 
     * that the full list of Items does not need to be passed to the Arena
     * whenever there is combat), and the Enemy that has been targeted for combat.
     * 
     * @param hero Story.Hero
     * @param equippedItems ArrayList<Item> 
     * @param enemy Story.Enemy
     */
    public Arena(Hero hero, ArrayList<Item> equippedItems, Enemy enemy){
        this.hero = hero;
        this.equippedItems = equippedItems;
        this.enemy = enemy;
        this.heroHasInitiative = rollD20() >= rollD20(); // Roll for initiative
        heroEscaped = false;
        enemyPoweringUp = false;
    }

    /**
     * Utility function that generates a random number
     * between 1 & 20.
     * 
     * @return (int) randomInteger between 1 & 20
     */
    private int rollD20(){
        Random random = new Random();
        return random.nextInt(20) + 1;
    }

    /**
     * Utility function that calculates the Hero's defence. This
     * is the hero's base defence PLUS the defence of all equipped 
     * items in the heros inventory.
     * 
     * @return (int) hero's current defence stat 
     */
    private int getHeroDefence(){
        int defenceModifier = 0;
        for(Item i : equippedItems){
            defenceModifier += i.getDefence();
        }
        return hero.getDefense() + defenceModifier;
    }

    /**
     * Utility function that calculates the Hero's attack power. This
     * is the hero's base attack power PLUS the attack power of all
     * equipped items in the hero's inventory. 
     * 
     * @return (int) hero's current attack power stat
     */
    private int getHeroAttackPower(){
        int APModifier = 0;
        for(Item i : equippedItems){
            APModifier += i.getAttackPower();
        }
        return hero.getAttackPower() + APModifier;
    }

    /**
     * The rollForDamage() function will generate a value
     * that represents how much damage was done by an attack
     * from the Hero or the target Enemy. This may be critical damage
     * (2 * attack power) or 'normal' damage (random integer between 1
     * and attack power).
     *  
     * @param herosTurn (boolean) indicates if it's the hero's turn
     * @param criticalHit (boolean) indicates if the damage should be critical
     * @return (int) a value representing how much health should be removed from the target of the attack
     */
    private int rollForDamage(boolean herosTurn, boolean criticalHit){
        Random random = new Random();
        int attackPower = herosTurn ? getHeroAttackPower() : enemy.getAttackPower();
        int variance = criticalHit ? 2 * attackPower : random.nextInt(attackPower) + 1;
        return variance + attackPower;
    }

    /**
     * The attack() function calculates whether an attack made by the 
     * Hero or the target Enemy is successful, whether it was a critical hit and
     * whether the enemy will take a POWER_UP action this turn.
     * 
     * @param herosTurn (boolean) indicates if it's the hero's turn
     * @param consequence (Consequenc) the consequence to update with the results of the attack
     * @return (Consequence) the updated consequence
     */
    private Consequence attack(boolean herosTurn, Consequence consequence){
        int attackRoll = 0;
        int dmg = 0;

        if(herosTurn){
            attackRoll = rollD20();
            if((attackRoll + getHeroAttackPower()) >= enemy.getDefence()){
                dmg = (attackRoll == 20) ? rollForDamage(herosTurn, true) : rollForDamage(herosTurn, false);
                enemy.setHealth(enemy.getHealth() - dmg);
                consequence.addToConsequence(
                    String.format(
                        "You %shit %s for %d damage!", 
                        (attackRoll == 20) ? "critically " : "",
                        enemy.getName(),
                        dmg
                    )
                );
            }else{
                consequence.addToConsequence(String.format("You try to hit %s, but you miss!", enemy.getName()));
            }
        }else{
            if(rollD20() >= 15 && !enemyPoweringUp){
                enemyPowersUp(consequence);
            }else{
                if(enemyPoweringUp){
                    attackRoll = 20;
                    enemyPoweringUp = false;
                    consequence.addToConsequence(String.format("%s unleashes a vicious attack!", enemy.getName()));
                }else{
                    attackRoll = rollD20();
                }
                if((attackRoll + enemy.getAttackPower()) >= getHeroDefence()){
                    dmg = (attackRoll == 20) ? rollForDamage(herosTurn, true) : rollForDamage(herosTurn, false);
    
                    hero.setHealth(hero.getHealth() - dmg);
                    consequence.addToConsequence(
                        String.format(
                            "%s %shits you for %d damage!", 
                            enemy.getName(),
                            (attackRoll == 20) ? "critically " : "",
                            dmg
                        )
                    );
                }else{
                    consequence.addToConsequence(String.format("%s tries to hit you, but they miss!", enemy.getName()));
                }
            }
        }
        
        return consequence;
    }

    /**
     * Utility function to update the consequence when an
     * enemy is using the POWER_UP ability.
     * 
     * @param consequence (Consequence) the consequence to update
     * @return (Consequence) the updated consequence
     */
    private Consequence enemyPowersUp(Consequence consequence){
        consequence.addToConsequence(String.format(
            "%s looks furious!",
            enemy.getName()
        ));
        enemyPoweringUp = true;
        return consequence;
    }

    /**
     * The primary function of the Arena class, fight() takes the action
     * taken by the player (ATTACK, DODGE, BLOCK or ESCAPE) and determines if it's 
     * success, then updates the Hero, Enemy and Consequence object with the results.
     * 
     * Player's will 'fumble' and lose a turn if they enter an invalid command (no such command or not valid for mode COMBAT)
     * 
     * @param action (Action) the action taken by the player
     * @param consequence (Consequence) the consequence to update with the results
     * @return (Consequence) the updated consequence
     */
    public Consequence fight(Action action, Consequence consequence){
        if(!action.isValid() || action.getCommand().getValidMode() != Mode.COMBAT){
            consequence.addToConsequence("You fumble!");
            consequence = attack(false, consequence);
        }else if(action.getCommand() == Command.ATTACK_ENEMY){
            consequence = attack(heroHasInitiative, consequence);
            if(!enemyIsDead() && !heroIsDead()){
                consequence = attack(!heroHasInitiative, consequence);
            }
        }else if(action.getCommand() == Command.DODGE){
            int dodgeRoll = rollD20();
            if(dodgeRoll >= 11){
                consequence.addToConsequence(String.format("%s tries to attack you, but you dodge out of the way!", enemy.getName()));
                if(enemyPoweringUp){
                    enemyPoweringUp = false;
                }
            }else{
                consequence.addToConsequence(String.format("You try to dodge %s, but you're too slow!", enemy.getName()));
                consequence = attack(false, consequence);
            }
        }else if(action.getCommand() == Command.BLOCK){
            int baseDefense = hero.getDefense();
            hero.setDefense(baseDefense + (int) ((double) baseDefense / 2.0));
            consequence.addToConsequence("You try to defend yourself as best you can!");
            consequence = attack(false, consequence);
            hero.setDefense(baseDefense);
        }else if(action.getCommand() == Command.ESCAPE){
            int escapeRoll = rollD20();
            if(heroHasInitiative){
                if(escapeRoll >= 12){
                    consequence.addToConsequence(String.format("You manage to escape from %s!", enemy.getName()));
                    heroEscaped = true;
                }else{
                    consequence.addToConsequence(String.format("You try to escape from %s, but they're too quick!", enemy.getName()));
                    consequence = attack(false, consequence);
                }
            }else{
                consequence = attack(false, consequence);
                if(!heroIsDead() && escapeRoll >= 12){
                    consequence.addToConsequence(String.format("You manage to escape from %s!", enemy.getName()));
                    heroEscaped = true;
                }
            }
        }

        return consequence;
    }

    /**
     * Utility function that returns a value indicating if the
     * hero has successfully escaped combat.
     * 
     * @return (boolean) value indicating if the hero has escaped combat
     */
    public boolean heroHasEscaped(){
        return heroEscaped;
    }

    /**
     * Utility function that returns a value indicating if the
     * hero's health is <= 0 (that is - they're dead)
     * 
     * @return (boolean) value indicating if the hero is dead.
     */
    public boolean heroIsDead(){
        return hero.getHealth() <= 0;
    }

    /**
     * Utility function that returns a value indicating if the
     * enemy's health is <=0 (this is - they're dead)
     * 
     * @return (boolean) value indicating if the enemy is dead.
     */
    public boolean enemyIsDead(){
        return enemy.getHealth() <= 0;
    }

    /**
     * Uility function that returns the Hero object
     * stored in the Arena.
     * 
     * @return (Hero) the hero
     */
    public Hero getHero(){
        return hero;
    }

    /**
     * Utility function that replaces the Hero object
     * stored in the Arena with a different Heor object.
     * 
     * @param hero (Hero) the new hero object
     */
    public void updateHero(Hero hero){
        this.hero = hero;
    }

    /**
     * Utility function that returns the Enemy object
     * stored by the Arena. 
     * 
     * @return (Enemy) the enemy targeted in combat
     */
    public Enemy getEnemy(){
        return enemy;
    }
}
