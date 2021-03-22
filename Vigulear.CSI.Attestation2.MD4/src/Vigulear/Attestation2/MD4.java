package Vigulear.Attestation2;

import java.math.*;

public class MD4 {

    public static String stringToBinary (String message) {
        byte[] bytes = message.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            //binary.append(' ');
        }
        return binary.toString();
    }

    public static String binaryToAsciiToText(String message) {
        StringBuilder s2 = new StringBuilder();

        for (int i = 0; i < message.length()/8; i++) {

            int a = Integer.parseInt(message.substring(8*i,(i+1)*8),2);
            s2.append((char)(a));
        }
        return s2.toString();
    }

    public static void main(String[] args) {
        String m = "ZAElectronicaDig";
        System.out.println("Message: " + m);
        System.out.println("Message in binary: " + stringToBinary(m));

        String stringInBinary = stringToBinary(m);

        String a = "";
        BigInteger aInt;
        String b = "";
        BigInteger bInt;
        String c = "";
        BigInteger cInt;
        String d = "";
        BigInteger dInt;

        // setting A, B, C, D
        int i = 0;
        while (i < stringInBinary.length()) {
            a = stringInBinary.substring(i, i+32);
            i += 32;
            b = stringInBinary.substring(i, i+32);
            i += 32;
            c = stringInBinary.substring(i, i+32);
            i += 32;
            d = stringInBinary.substring(i, i+32);
            i += 32;
        }

        // cast A, B, C, and D to BigInteger
        aInt = BigInteger.valueOf(Integer.parseInt(a, 2));
        bInt = BigInteger.valueOf(Integer.parseInt(b, 2));
        cInt = BigInteger.valueOf(Integer.parseInt(c, 2));
        dInt = BigInteger.valueOf(Integer.parseInt(d, 2));

        System.out.println("A = " + a + " (" + aInt + ")" +
                "\nB = " + b + " (" + bInt + ")" +
                "\nC = " + c + " (" + cInt + ")" +
                "\nD = " + d + " (" + dInt + ")");

        // BigInteger for 2^32
        BigInteger modulus = BigInteger.valueOf(2).pow(32);

        int[] shifts = {3, 7, 11, 19};

        // Algorithm
        for (int mi = 0; mi < 16; mi++) {


            BigInteger p1 = bInt.xor(cInt).xor(dInt);
            System.out.println("\n*** ROUND " + (mi + 1) +" ***\n" +
                    "1. F= B xor C xor D\n" + b + " xor " + c + " xor " + d + " = " + p1 + " = " + p1.toString(2));

            BigInteger p2 = p1.add(aInt).mod(modulus);
            System.out.println("2. A+F mod 2^32 = \n" + aInt + " + " + p1 + " mod 2^32 = " + p2 + " = " + p2.toString(2));

            BigInteger p3 = p2.add(BigInteger.valueOf(mi)).mod(modulus);
            System.out.println("3. P(2)+Mi mod 2^32 = " + p2 + " + " + mi + " mod 2^32 = " + p3 + " = " + p3.toString(2));

            BigInteger p4 = p3.shiftLeft(shifts[mi%4]);
            System.out.println("4. " + p3 + " <<< " + shifts[mi%4] + " = " + p4 + " = " + p4.toString(2));

            a = d;
            d = c;
            c = b;
            b = p4.toString(2);

            aInt = dInt;
            dInt = cInt;
            cInt = bInt;
            bInt = p4;

            System.out.println("5.  D=>A, A = " + a + " (" + aInt + ") " + "\n\tA=>B, B = " + b + " (" + bInt + ") "+
                    "\n\tB=>C, C = " + c + " (" + cInt + ") " + "\n\tC=>D, D = " + d + " (" + dInt + ") ");
        }
        String binaryEncryption = a.concat(b).concat(c).concat(d);
        String encryption = binaryToAsciiToText(binaryEncryption);
        System.out.println("Binary: " + binaryEncryption);
        System.out.println("\"" + m + "\"" + " encrypted = \"" + encryption + "\"" );
    }
}
