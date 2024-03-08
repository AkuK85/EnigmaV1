import java.util.*;
import java.util.stream.Collectors;

public class KeyMaker {

    private String message;
    private String key;
    private Map<Character, Character> validKeyMap;

    // Constructor for KeyMaker class
    public KeyMaker(String message) {
        if (message == null || message.isEmpty()) {
            this.message = "No message provided.";
        } else {
            this.message = message;
            buildKey(this.message);
        }

        if (key == null || key.isEmpty()) {
            this.validKeyMap = new HashMap<>();
        } else {
            buildValidKey();
        }
    }

    // Method to count the frequency of each character in the message
    public Map<Character, Integer> countFrequency(String message) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : message.toCharArray()) {
            if (ValuesTable.FI_Alphabet.indexOf(c) != -1) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }

        return frequencyMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    // Method to build the key based on the message
    public void buildKey(String message) {
        message = message.toUpperCase();

        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : message.toCharArray()) {

            if (ValuesTable.FI_Alphabet.indexOf(c) != -1) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }

        List<Map.Entry<Character, Integer>> list = new ArrayList<>(frequencyMap.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        StringBuilder keyBuilder = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : list) {
            keyBuilder.append(entry.getKey());
        }

        this.key = keyBuilder.toString();
    }

    // Method to build a valid key map
    private void buildValidKey() {
        this.validKeyMap = new HashMap<>();

        for (int i = 0; i < this.key.length(); i++) {
            this.validKeyMap.put(this.key.charAt(i), ValuesTable.FI_LetterFrequency.charAt(i));
        }
    }

    // Method to decrypt the message using the valid key map
    public String decryptMessage() {
        StringBuilder decryptedMessage = new StringBuilder();

        for (char c : this.message.toCharArray()) {
            if (validKeyMap.containsKey(c)) {
                decryptedMessage.append(validKeyMap.get(c));
            } else {
                decryptedMessage.append(c);
            }
        }
        return decryptedMessage.toString();
    }

    // Method to adjust the valid key map based on the adjustments
    public void adjust(Map<Character, Character> adjustments) {
        for (Map.Entry<Character, Character> entry : adjustments.entrySet()) {
            char originalChar = entry.getKey();
            char newChar = entry.getValue();

            if (validKeyMap.containsKey(originalChar)) {
                validKeyMap.put(originalChar, newChar);
            }
        }
    }

    // Method to create the final key
    public String createFinalKey() {
        StringBuilder finalKeyBuilder = new StringBuilder();
        for (char c : ValuesTable.FI_Alphabet.toCharArray()) {
            finalKeyBuilder.append(c);
            finalKeyBuilder.append(" -> ");
            if (validKeyMap.containsKey(c)) {
                finalKeyBuilder.append(validKeyMap.get(c));
            } else {
                finalKeyBuilder.append("_");
            }
            finalKeyBuilder.append("\n");
        }
        return finalKeyBuilder.toString();
    }

    // Method to create a stripped version of the final key
    public String createStrippedFinalKey() {
        StringBuilder strippedFinalKeyBuilder = new StringBuilder();
        for (char c : ValuesTable.FI_Alphabet.toCharArray()) {
            if (validKeyMap.containsKey(c)) {
                strippedFinalKeyBuilder.append(validKeyMap.get(c));
            } else {
                strippedFinalKeyBuilder.append("_");
            }
        }
        return strippedFinalKeyBuilder.toString();
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getKey() {
        return key;
    }

    public Map<Character, Character> getValidKeyMap() {
        return validKeyMap;
    }

    // Setters
    public void setMessage(String message) {
        this.message = message;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValidKeyMap(Map<Character, Character> validKeyMap) {
        this.validKeyMap = validKeyMap;
    }
}
