package StoryParser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Story.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This Parser accepts a json file and return a story instance.
 * 
 * This is useful because the information of the story stores in the json file,
 * and the gameEngine needs the story instance to initialize.
 * 
 * The Parser will throw a IOException if the Parser.parse() receive a wrong path.
 * 
 * @author Anqi Chang (u7268891)
 */
public class Parser  implements Serializable{
    
    /**
     * @param storyPath: the path of the json file, eg: "res/story/story.json"
     * @return (Story) the story instance contains the all the story information
     * @throws IOException
     */
    public static Story parse(String storyPath) throws IOException{
        HashMap<Integer, Room> rooms = new HashMap<>();
        HashMap<Integer, Container> containers = new HashMap<>();
        HashMap<Integer, Item> items = new HashMap<>();
        HashMap<Integer, Enemy> enemies = new HashMap<>();
        HashMap<Integer, NonPlayerCharacter> nonPlayerCharacters = new HashMap<>();
        HashMap<Integer, Passage> passages = new HashMap<>();

        // get root node of story file
        File file = new File(storyPath);
        String jsonString = new String(Files.readAllBytes(Paths.get(file.getPath())));
        JsonNode rootNode = objectMapper.readTree(jsonString);

        // parse title, introduction, hero health, attack power, hero defence
        String title = rootNode.get("title").asText();
        String intro = rootNode.get("introduction").asText();
        int initialHeroHealth = rootNode.get("initial_hero_stats").get("health").asInt();
        int initialHeroAttackPower = rootNode.get("initial_hero_stats").get("attack_power").asInt();
        int initialHeroDefence = rootNode.get("initial_hero_stats").get("defence").asInt();

        // parse rooms
        JsonNode roomsNode = rootNode.get("rooms");
        for (JsonNode rn : roomsNode) {
            int index = rn.get("index").asInt();
            String name = rn.get("name").asText();
            String description = rn.get("description").asText();
            // parse passages in the room
            JsonNode rnPassagesNode = rn.get("passages");
            HashMap<Direction, Integer> roomPassagesMap = new HashMap<>();
            for (JsonNode rmp : rnPassagesNode) {
                Direction direction = Direction.valueOf(rmp.get("direction").asText());
                int passageIndex = rmp.get("index").asInt();
                roomPassagesMap.put(direction, passageIndex);
            }
            // parse characters in the room
            JsonNode rnCharacters = rn.get("characters");
            ArrayList<Integer> roomCharactersList = new ArrayList<>();
            for (JsonNode i: rnCharacters) {
                roomCharactersList.add(i.asInt());
            }
            // parse enemies in the room
            JsonNode rnEnemies = rn.get("enemies");
            ArrayList<Integer> roomEnemiesList = new ArrayList<>();
            for (JsonNode i: rnEnemies) {
                roomEnemiesList.add(i.asInt());
            }
            // parse containers in the room
            JsonNode rnContainers = rn.get("containers");
            ArrayList<Integer> roomContainersList = new ArrayList<>();
            for (JsonNode i: rnContainers) {
                roomContainersList.add(i.asInt());
            }
            // parse isLastRoom in the room
            boolean isLastRoom = rn.get("is_final_room").asBoolean();

            // create the room instance (isLastRoom is set to false by default)
            Room r = new Room(index, name, description, roomCharactersList, roomPassagesMap, roomEnemiesList, roomContainersList);
            // if the room isLastRoom
            if (isLastRoom) { r.makeLastRoom(); }

            // add the room instance to the rooms map
            rooms.put(index, r);
        }

        // parse characters
        JsonNode charactersNode = rootNode.get("characters");
        for (JsonNode cn: charactersNode) {
            int characterIndex = cn.get("index").asInt();
            String name = cn.get("name").asText();
            String description = cn.get("description").asText();
            String greeting = cn.get("greeting").asText();
            boolean hasQuestion = cn.get("has_question").asBoolean();
            String resolvedGreeting = cn.get("resolved_greeting").asText();

            // parse responses in the NPC
            HashMap<String, Response> responsesMap = new HashMap<>();
            // if have positive_response
            if (cn.get("positive_response") != null) {
                JsonNode cnPR = cn.get("positive_response");
                String key = cnPR.get("key").asText();
                Response.ResponseType type;
                String typeRaw = cnPR.get("type").asText();
                if (typeRaw.equals("TEXT_ONLY")) {
                    type = Response.ResponseType.TEXT;
                } else {
                    type = Response.ResponseType.valueOf(typeRaw);
                }
                String responseText = cnPR.get("text").asText();
                int itemIndex = cnPR.get("item_index").asInt();
                int health = cnPR.get("health").asInt();

                Response r = new Response(type, responseText, itemIndex, health);
                responsesMap.put(key, r);
            }
            // if have negative_response
            if (cn.get("negative_response") != null) {
                JsonNode cnNR = cn.get("negative_response");
                String key = cnNR.get("key").asText();
                Response.ResponseType type;
                String typeRaw = cnNR.get("type").asText();
                if (typeRaw.equals("TEXT_ONLY")) {
                    type = Response.ResponseType.TEXT;
                } else {
                    type = Response.ResponseType.valueOf(typeRaw);
                }
                String responseText = cnNR.get("text").asText();
                int itemIndex = cnNR.get("item_index").asInt();
                int health = cnNR.get("health").asInt();

                Response r = new Response(type, responseText, itemIndex, health);
                responsesMap.put(key, r);
            }

            // create NPC instance
            NonPlayerCharacter npc = new NonPlayerCharacter(characterIndex, name, description, greeting, false, hasQuestion, responsesMap, false, resolvedGreeting);
            // add the instance to the npcs map
            nonPlayerCharacters.put(characterIndex, npc);
        }

        // parse passages
        JsonNode passagesNode = rootNode.get("passages");
        for (JsonNode pn: passagesNode) {
            int index = pn.get("index").asInt();
            Passage.PassageType type = Passage.PassageType.valueOf(pn.get("type").asText());
            String description_locked = pn.get("description_locked").asText();
            String description_action = pn.get("description_action").asText();
            String description_unlocked = pn.get("description_unlocked").asText();
            String key = pn.get("key").asText();
            int roomIndex = pn.get("room_index").asInt();
            boolean isLocked = type == Passage.PassageType.PASSAGE_LOCKED || type == Passage.PassageType.PASSAGE_HIDDEN;

            // create the passage instance
            Passage p;
            if (type != Passage.PassageType.PASSAGE_NONE) {
                p = new Passage(index, type, description_locked, description_unlocked, isLocked, key, roomIndex, description_action);
            } else {
                p = new Passage(index, type, description_locked, description_unlocked, false, key, description_action);
            }

            // add the instance to the passages map
            passages.put(index, p);
        }

        // parse enemies
        JsonNode enemiesNode = rootNode.get("enemies");
        for (JsonNode en: enemiesNode) {
            int index = en.get("index").asInt();
            String enemyName = en.get("name").asText();
            String description_alive = en.get("description_alive").asText();
            String description_dead = en.get("description_dead").asText();
            int health = en.get("health").asInt();
            int attack_power = en.get("attack_power").asInt();
            int defence = en.get("defence").asInt();
            boolean has_loot = en.get("has_loot").asBoolean();
            int item_index = en.get("item_index").asInt();

            Enemy e = new Enemy(index, enemyName, description_dead, description_alive, health, attack_power, defence, item_index, has_loot);

            enemies.put(index, e);
        }

        // parse containers
        JsonNode containersNode = rootNode.get("containers");
        for (JsonNode cn: containersNode) {
            int index = cn.get("index").asInt();
            String containerName = cn.get("name").asText();
            String description_locked = cn.get("description_locked").asText();
            String description_action = cn.get("description_action").asText();
            String description_unlocked = cn.get("desctiption_unlocked").asText();
            boolean is_locked = cn.get("is_locked").asBoolean();
            String key = cn.get("key").asText();
            int item_index = cn.get("item_index").asInt();

            Container c = new Container(index, containerName, description_unlocked, description_locked, is_locked, key, item_index, false, description_action);

            containers.put(index, c);
        }

        // parse items
        JsonNode itemsNode = rootNode.get("items");
        for (JsonNode in: itemsNode) {
            int index = in.get("index").asInt();
            String itemName = in.get("name").asText();
            String description = in.get("description").asText();
            boolean is_equippable = in.get("is_equippable").asBoolean();
            boolean is_consumable = in.get("is_consumable").asBoolean();
            int health = in.get("health").asInt();
            int attack_power = in.get("attack_power").asInt();
            int defence = in.get("defence").asInt();

            Item i = null;
            if(is_consumable){
                i = new Item(index, itemName, description, attack_power, defence, health);
            }else{
                i = new Item(index, itemName, description, is_equippable, attack_power, defence);    
            }
            
            items.put(index, i);
        }

        return new Story(title, intro, initialHeroHealth, initialHeroAttackPower, initialHeroDefence, rooms, containers, items, enemies, nonPlayerCharacters, passages);
    }

    /**
     * This is used to map the string to a JsonNode
     * 
     * @return (ObjectMapper) the objectMapper
     */
    private static ObjectMapper getDefaultObjectMapper() {

        return new ObjectMapper();
    }

    /**
     * Call getDefaultObjectMapper method to initialize the objectMapper
     */
    private static final ObjectMapper objectMapper = getDefaultObjectMapper();
}
