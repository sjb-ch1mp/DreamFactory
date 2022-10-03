package Story;

import java.util.HashMap;

/**
 * This class pertains to the non player characters that may exist in Rooms that the user can interact with.
 * These NPC's have questions and responses to user input that may help the user progress through the story.
 *
 * @author Shafin Kamal
 */
public class NonPlayerCharacter  implements java.io.Serializable{
    // Variables
    int characterIndex;
    String name;
    String description;
    String greeting;
    boolean waitingForResponse;
    boolean hasQuestion;
    HashMap<String, Response> responses;

    // This is to check if Hero has encountered this NPC before or not.
    boolean isResolved;
    String resolvedGreeting;

    // Constructor
    public NonPlayerCharacter(int characterIndex, String name, String description, String greeting, boolean waitingForResponse,
                              boolean hasQuestion, HashMap<String, Response> responses, boolean isResolved,
                              String resolvedGreeting) {
        this.characterIndex = characterIndex;
        this.name = name;
        this.description = description;
        this.greeting = greeting;
        this.waitingForResponse = waitingForResponse;
        this.hasQuestion = hasQuestion;
        this.responses = responses;
        this.isResolved = isResolved;
        this.resolvedGreeting = resolvedGreeting;
    }

    // Standard getters and setters

    /**
     * Get the index of the Character
     *
     * @author Shafin Kamal
     * @return the int that denotes the index of the character.
     */
    public int getCharacterIndex() {
        return characterIndex;
    }

    /**
     * Set the index of the character.
     *
     * @author Shafin Kamal
     * @param characterIndex the int that denotes the index of the character
     */
    public void setCharacterIndex(int characterIndex) {
        this.characterIndex = characterIndex;
    }

    /**
     * Get the name of the NPC
     *
     * @author Shafin Kamal
     * @return the name of the NPC
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this NPC.
     *
     * @author Shafin Kamal
     * @param name the name of the NPC
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the NPC
     *
     * @author Shafin Kamal
     * @return the description of the NPC
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the NPC
     *
     * @author Shafin Kamal
     * @param description the NPC's description
     */

    /**
     * Set the description of the NPC
     *
     * @author Shafin Kamal
     * @param description the description of the NPC
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the greeting text of the NPC
     *
     * @author Shafin Kamal
     * @return the greeting text of the NPC
     */
    public String getGreeting() {
        return greeting;
    }

    /**
     * Set the greeting text of the NPC
     *
     * @author Shafin Kamal
     * @param greeting the greeting text of the NPC
     */
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    /**
     * Check if the NPC is waiting for a response from the user
     *
     * @author Shafin Kamal
     * @return true if waiting for response, else false.
     */
    public boolean isWaitingForResponse() {
        return waitingForResponse;
    }

    /**
     * Set whether the NPC should wait for response or not.
     *
     * @author Shafin Kamal
     * @param waitingForResponse true if NPC should wait for response, else false.
     */
    public void setWaitingForResponse(boolean waitingForResponse) {
        this.waitingForResponse = waitingForResponse;
    }

    /**
     * check if NPC has a question.
     *
     * @author Shafin Kamal
     * @return true if NPC has a question, else false.
     */
    public boolean isHasQuestion() {
        return hasQuestion;
    }

    /**
     * set whether the NPC should have a question or not
     *
     * @author Shafin Kamal
     * @param hasQuestion true, if the NPC should have a question, else false.
     */
    public void setHasQuestion(boolean hasQuestion) {
        this.hasQuestion = hasQuestion;
    }

    /**
     * Get the responses of the NPC. It is stored in a HashMap where the key is a String of user input.
     *
     * e.g.
     *
     * > Hello NPC! (String)
     *
     * > Hello user! (Response text)
     *
     * @author Shafin Kamal
     *
     * @return HashMap<String, Response>
     */
    public HashMap<String, Response> getResponses() {
        return responses;
    }

    /**
     * Set the responses of the NPC
     *
     * @author Shafin Kamal
     * @param responses HashMap<String, Response> responses
     */
    public void setResponses(HashMap<String, Response> responses) {
        this.responses = responses;
    }

    /**
     * Check if user has interacted with this NPC yet
     *
     * @author Shafin Kamal
     * @return true if the user has encountered this NPC, otherise false.
     */
    public boolean isResolved() {
        return isResolved;
    }

    /**
     * set whether the user has encountered this NPC
     *
     * @author Shafin Kamal
     * @param resolved set to true, if the NPC and user have interacted.
     *                 else false.
     */
    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    /**
     * get the greeting the NPC will produce if the NPC isResolved().
     *
     * @author Shafin Kamal
     * @return the resolved greeting of the NPC
     */
    public String getResolvedGreeting() {
        return resolvedGreeting;
    }

    /**
     * Set the resolved greeting of the NPC
     *
     * @author Shafin Kamal
     * @param resolvedGreeting the resolved greeting of the NPC
     */
    public void setResolvedGreeting(String resolvedGreeting) {
        this.resolvedGreeting = resolvedGreeting;
    }
}
