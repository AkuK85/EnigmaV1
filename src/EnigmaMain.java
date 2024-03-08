import java.io.Serializable;
import java.util.*;

public class EnigmaMain {

    public static void main(String[] args) {

        // The encrypted message to be decrypted
        String cryptedMessage = "LZELFLXÖGC GFZHCCS DCACC AFWQEES FOC-CWXEEOEES, SZZS OCSFLLXXS PZC AFWQZFFS." +
                " OCCLCGXXHEWWC LCZ AYNLELLYGNNHEWWY LCÖAFZLELCCS OZLY, ELLY LZELF FS OCCLCGZWWC OZWWFZS AXS OZLY" +
                " LCÖGZLCCS. WXFLLCQXAOEWWZOXXHEWWC LCÖAFZLELCCS OZLY, ELLY LZELFC GFZGCL AYOZLEWWY CZSFCOLCCS ÄESAZWUL," +
                " DFZWWC FS OZZÄES FZAEXO. EÄENHEWWY LCÖAFZLELCCS OZLY, ELLY LZELF EZ OCC QXXLLXC LCZ CZSCAZS ELLY" +
                " QXXLFO ÄCGCZLCCS. LYQY GZEOLZ FS OCWCLLX LZELFLXÖGCAXÖOOZWWE NAOZCCAAFOLFOCWCCDCWWC.";

        // Create a new KeyMaker with the encrypted message
        KeyMaker keyMaker = new KeyMaker(cryptedMessage);

        //These are just a few examples of the most frequent words in Finnish language and dialects to help out manual correction
        //Most frequent words in Finnish language
        //olla, ja, se, ei, joka, että, tämä, hän, voida, saada.
        //Most frequent word in every Finnish dialect
        //se, olla, ja, niin, sitten, kun, ne, ei, minä, että.

        // Count the frequency of each character in the encrypted message
        Map<Character, Integer> frequencyMap = keyMaker.countFrequency(cryptedMessage);
        List<Map.Entry<Character, Integer>> frequencyList = new ArrayList<>(frequencyMap.entrySet());
        frequencyList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Print the frequency of each character in the encrypted message
        for (int i = 0; i < frequencyList.size(); i++) {
            Map.Entry<Character, Integer> entry = frequencyList.get(i);
            char c = entry.getKey();
            int frequency = entry.getValue();
            Serializable fiLetter = i < ValuesTable.FI_LetterFrequency.length() ? ValuesTable.FI_LetterFrequency.charAt(i) : "N/A";
            System.out.println(c + ":" + frequency + " -> " + fiLetter);
        }

        // Create a map of adjustments for the key
        Map<Character, Character> adjustments = new HashMap<>();
        //adjustments.put <- this is used for manual correction of the key
        adjustments.put('Z', 'I');
        adjustments.put('S', 'N');
        adjustments.put('F', 'O');
        adjustments.put('G', 'V');
        adjustments.put('H', 'D');
        adjustments.put('O', 'S');
        adjustments.put('E', 'E');
        adjustments.put('Y', 'Ä');
        adjustments.put('L', 'T');
        adjustments.put('X', 'U');
        adjustments.put('Ö', 'R');
        adjustments.put('C', 'A');
        adjustments.put('A', 'K');
        adjustments.put('D', 'J');
        adjustments.put('W', 'L');
        adjustments.put('Q', 'M');
        adjustments.put('P', 'C');
        adjustments.put('N', 'Y');
        adjustments.put('Ä', 'H');

        // Adjust the key with the adjustments map
        keyMaker.adjust(adjustments);

        // Decrypt the message with the adjusted key
        String decryptedMessage = keyMaker.decryptMessage();

        // Print the decrypted and original messages
        System.out.println("Decrypted message: " + decryptedMessage);
        System.out.println("Original message: " + cryptedMessage);

        // Create and print the final key
        String finalKey = keyMaker.createFinalKey();
        System.out.println("Final key: \n" + finalKey);

        // Create and print the stripped final key
        String strippedFinalKey = keyMaker.createStrippedFinalKey();
        System.out.println("Frequency key: " + keyMaker.getKey());
        System.out.println("Valid key: " + keyMaker.getValidKeyMap());
        System.out.println("Alphabetical key: " + strippedFinalKey);

    }
}
