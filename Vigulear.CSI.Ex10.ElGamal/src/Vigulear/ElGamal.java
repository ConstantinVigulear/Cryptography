package Vigulear;

import java.math.BigInteger;

public class ElGamal {

    public static BigInteger stringToASCII (String string) {
        StringBuilder sb = new StringBuilder();
        char[] letters = string.toCharArray();

        for (char ch : letters) {
            sb.append((byte) ch);
        }
        return BigInteger.valueOf(Integer.parseInt(sb.toString()));
    }

    public static void main(String[] args) {
        BigInteger P = BigInteger.valueOf(2909);
        System.out.println("\tP = " + P);
        BigInteger G = BigInteger.valueOf(2134);
        System.out.println("\tG = " + G);
        BigInteger X = BigInteger.valueOf(62);
        System.out.println("\tX = " + X);

        System.out.println("Calculating Y ...");
        BigInteger Y = G.modPow(X, P);
        System.out.println("\tY = " + Y);

        String message = "!";
        BigInteger M =  stringToASCII(message);
        System.out.println("Message M: \"" + message + "\" = " + M);

        BigInteger K = BigInteger.valueOf(137);
        System.out.println("\tK = " + K);

        System.out.println("Calculating a ...");
        BigInteger a = G.modPow(K, P);
        System.out.println("\ta = " + a);

        System.out.println("Calculating b ...");
        BigInteger b = Y.pow(K.intValue()).multiply(M).mod(P);
        System.out.println("\tb = " + b);

        System.out.println("\nEncrypting...\nEncrypted message: (" + a + ", " + b + ")");

        System.out.println("Decrypting...");
        BigInteger decM = a.pow(X.intValue()).modPow(BigInteger.valueOf(-1), P).multiply(b).mod(P);
        System.out.println("Decrypted message: " + decM + " = \"" + (char) decM.intValue() + "\"\n");

        System.out.println("Signing the message M ...");
        System.out.println("Calculating r ...");
        BigInteger r = G.modPow(K, P);
        System.out.println("\tr = " + r);


        System.out.println("Calculating s taking H(M) = h = M ...");
        BigInteger s = K.modPow(BigInteger.valueOf(-1), P.subtract(BigInteger.ONE)).multiply(M.subtract(X.multiply(r))).mod(P.subtract(BigInteger.ONE));
        System.out.println("\ts = " + s);
        System.out.println("Signed message S = (r, s): (" + r + ", " + s + ")\n");

        System.out.println("Signature verification ...");

        System.out.println("Calculating V1 ...");
        BigInteger V1 = (Y.pow(r.intValue()).mod(P)).multiply(r.pow(s.intValue()).mod(P)).mod(P);
        System.out.println("\tV1 = " + V1);

        System.out.println("Calculating V2 ...");
        BigInteger V2 = G.modPow(M, P);
        System.out.println("\tV2 = " + V2);
        if (V1.equals(V2)) {
            System.out.println("V1 = V1 hence signature is accepted!");
        }
        else {
            System.out.println("V1 != V2 hence signature is not accepted!");
        }

    }
}


