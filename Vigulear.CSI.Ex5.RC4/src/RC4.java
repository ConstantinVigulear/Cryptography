import java.util.*;

public class RC4 {
    public static void main(String[] args) {
        String message;
        int key, n, i, j, t;
        Vector<Integer> S = new Vector<>();
        Vector<Integer> digits = new Vector<>();
        Vector<Integer> keyVector = new Vector<>();

        Scanner input = new Scanner(System.in);

        // Input Data
        System.out.println("Enter message to be encrypted: ");
        message = input.nextLine();
        System.out.println("Message to be encrypted: " + message);
        System.out.println("Enter n: ");
        n = input.nextInt();
        System.out.println("Entered n = " + n);

        for (i = 0; i < Math.pow(2,n); i++) {
            S.add(i);
        }
        System.out.println("Creating vector S: " + S.toString());

        System.out.println("Enter the key: ");
        key = input.nextInt();
        System.out.println("Entered key = " + key);

        // acquiring digits from key
        while (key > 0) {
            digits.add(key % 10);
            key = key / 10;
        }
        Collections.reverse(digits);

        // converting digits into ASCII
        char character1 = Character.forDigit(digits.get(0), 10);
        char character2 = Character.forDigit(digits.get(1), 10);
        System.out.println("Key code in ASCII:" + digits.get(0) + " -> " + (int)character1 + ", " + digits.get(1) + " -> " + (int)character2);
        for (i = 0; i < Math.pow(2,n) / 2; i++) {
            keyVector.add((int)character1);
            keyVector.add((int)character2);
        }
        System.out.println("Key vector is: " + keyVector.toString());

        // Applying permutations
         j = 0;
        for (i = 0; i < Math.pow(2, n); i++) {
            j = (int)((j + keyVector.get(i) + S.get(j)) % Math.pow(2, n));
            Collections.swap(S, i, j);
            System.out.println("Iteration " + (i+1));
            System.out.println(S.toString());
            System.out.println(keyVector.toString());
        }

        // Calculating t
        i = 0;
        j = 0;
        i = (int)((i + 1) % Math.pow(2, n));
        j = (int)((j + S.get(i)) % Math.pow(2, n));
        t = (int)((S.get(i) + S.get(j)) % Math.pow(2, n));
        System.out.println("t = " + t);

        // Calculating K[t]
        System.out.println("K[t] = K[" + t + "] = " + keyVector.get(t) + " = " + Integer.toBinaryString(keyVector.get(t)));

        ArrayList<Character> m = new ArrayList<>();
        i = 0;
        for (char ch : message.toCharArray()) {
            m.add(ch);
            System.out.println("m[" + i + "] = " + m.get(i) + " -> (" + (int)m.get(i) + ") = (" + Integer.toBinaryString(m.get(i)) + ")");
            i++;
        }

        // Encrypting
        System.out.println("\n");
        i = 0;
        for (char ignored : m) {
            System.out.println("r[" + i + "] = " + Integer.toBinaryString(m.get(i)) + " XOR "
                    + Integer.toBinaryString(keyVector.get(t)) + " = (" + Integer.toBinaryString(m.get(i) ^ keyVector.get(t)) + ") = "
                    + " (" + Integer.toBinaryString(m.get(i) ^ keyVector.get(t)) + ") = "
                    + ((int)m.get(i)^ keyVector.get(t)) + " -> " + (char)((int)m.get(i)^ keyVector.get(t)));
            m.set(i, (char)((int)m.get(i)^ keyVector.get(t)));
            i++;
        }
        String encryptedMessage = "";
        for (char ch : m)
            encryptedMessage = encryptedMessage.concat(Character.toString(ch));

        System.out.println("\nYour encrypted word is : " + encryptedMessage);
    }
}
