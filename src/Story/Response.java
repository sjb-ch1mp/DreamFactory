package Story;

/**
 * Response class pertains to the replies given to the user after user input is parsed.
 *
 * @author Shafin Kamal
 */
public class Response  implements java.io.Serializable{

    //Variables
    ResponseType type;
    String responseText;
    int itemIndex;
    int health;

    // Constructor
    public Response(ResponseType type, String responseText, int itemIndex, int health) {
        this.type = type;
        this.responseText = responseText;
        this.itemIndex = itemIndex;
        this.health = health;
    }

    // Getters and Setters

    /**
     * get the response type
     *
     * @author Shafin Kamal
     * @return ResponseType
     */
    public ResponseType getType() {
        return type;
    }

    /**
     * Set response type
     *
     * @author Shafin Kamal
     * @param type ResponseType
     */
    public void setType(ResponseType type) {
        this.type = type;
    }

    /**
     * get the Response text
     *
     * @author Shafin Kamal
     * @return Response Text
     */
    public String getResponseText() {
        return responseText;
    }

    /**
     * set the response text: String
     *
     * @author Shafin Kamal
     * @param responseText String responseText
     */
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }


    /**
     * get the index of the item that can be dropped assuming correct
     * user input is given.
     *
     * @author Shafin Kamal
     * @return the item index
     */
    public int getItemIndex() {
        return itemIndex;
    }

    /**
     * set Item index that can be dropped assuming correct user input is given
     *
     *  @author Shafin Kamal
     *  @param itemIndex index item
     */
    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    /**
     * Get health that can affect the user if the response is ALTER_HEALTH
     *
     * @author Shafin Kamal
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set the health that can affect the user assuming correct response type.
     * @author Shafin Kamal
     * @param health int health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * The different types of Responses.
     */
    public enum ResponseType {
        TEXT, ITEM, ALTER_HEALTH
    }
}
