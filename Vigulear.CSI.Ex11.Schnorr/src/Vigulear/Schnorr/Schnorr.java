package Vigulear.Schnorr;

import java.math.BigInteger;

public class Schnorr {
    public static void main(String[] args) {
        System.out.println("*** Authentication Protocol ***");
        System.out.println("GENERATING KEYS:");

        System.out.println("Alice choose plain number p:");
        BigInteger p = BigInteger.valueOf(5501);
        System.out.println("\tp = " + p);

        System.out.println("Alice choose plain number q such that q is a divider for p-1:");
        BigInteger q = BigInteger.valueOf(11);
        System.out.println("\tq = " + q);

        System.out.println("Alice calculates g different from 1 that follows the rule g^q = 1 (mod p):");
        BigInteger g = BigInteger.valueOf(2299);
        System.out.println("\tg = " + g);

        System.out.println("Alice chooses a random integer w less than q:");
        BigInteger w = BigInteger.valueOf(7);
        System.out.println("\tw = " + w);

        System.out.println("Alice calculates key y = g^(-w)(mod p):");
        BigInteger y = g.modPow(BigInteger.valueOf(-1), p).modPow(w,p);
        System.out.println("\ty = " + y);

        System.out.println("Alice sends open her Open Key (p, q, g, y: " + "\n\t(" + p + ", " + q + ", " + g + ", " + y + ")");
        System.out.println("Alice keeps her Secret Key w:" + "\n\t w = " + w + "\n");

        System.out.println("CHECKING AUTHENTICATION");
        System.out.println("Alice pick a random number r:");
        BigInteger r = BigInteger.valueOf(428);
        System.out.println("\tr = " + r);

        System.out.println("Alice calculates x = g^r (mod p) and sends it to Bob:");
        BigInteger x = g.modPow(r, p);
        System.out.println("\tx = " + x);

        System.out.println("Bob pick a random number e between 0 and 2^72-1 and sends it to Alice:");
        BigInteger e = BigInteger.valueOf(479);
        System.out.println("\ne = " + e);

        System.out.println("Alice calculates s = (r + w * e) (mod p) and sends it to Bob:");
        BigInteger s = w.multiply(e).add(r).mod(p);
        System.out.println("\ts = " + s);

        System.out.println("Bob calculates z = g^s * y^e (mod p) and identifies Alice:");
        BigInteger z = g.pow(s.intValue()).multiply(y.pow(e.intValue())).mod(p);
        System.out.println("\tz = " + z);

        if (x.equals(z))
            System.out.println("Success! Since x = z -->> " + x + " = " + z + ", Alice is identified!");
        else
            System.out.println("Failure! Since x = z -->> " + x + " != " + z + ", Alice is NOT identified!\n");

        System.out.println("\n\n*** Digital signature Protocol ***");
        System.out.println("GENERATING KEYS:");

        System.out.println("Alice picks a plain number q:");
        BigInteger q1 = BigInteger.valueOf(229);
        System.out.println("\tq = " + q1);

        System.out.println("Alice calculates p = 22q + 1:");
        BigInteger p1 = q1.multiply(BigInteger.valueOf(22)).add(BigInteger.ONE);
        System.out.println("\tp = " + p1);

        System.out.println("Alice calculates f as a primitive root of p:");
        BigInteger f = BigInteger.valueOf(11);
        System.out.println("\tf = " + f);

        System.out.println("Alice calculates g = f^22 mod p:");
        BigInteger g1 = f.modPow(BigInteger.valueOf(22), p1);
        System.out.println("\tg = " + g1);

        System.out.println("Alice picks a random integer w < q:");
        BigInteger w1 = BigInteger.valueOf(149);
        System.out.println("\tw = " + w1);

        System.out.println("Alice calculates key y = g^(-w) (mod p):");
        BigInteger y1 = g1.modPow(BigInteger.valueOf(-1), p1).modPow(w1, p1);
        System.out.println("\ty = " + y1);

        System.out.println("Alice's Open Key: (" + p1 + ", " + q1 + ", " + g1 + ", " + y1 + ")" + "\nHer Private Key: w = " + w1);

        System.out.println("SIGNING THE MESSAGE:");
        System.out.println("Alice is about to sign the message:");
        String m = "1234";
        System.out.println("\tM = " + m);

        System.out.println("Alice picks a random number r < q:");
        BigInteger r1 = BigInteger.valueOf(172);
        System.out.println("\tr = " + r1);

        System.out.println("Alice calculates x = g^r mod p:");
        BigInteger x1 = g1.modPow(r1, p1);
        System.out.println("\tx = " + x1);

        BigInteger s1 = BigInteger.valueOf(500);
        System.out.println("Concatenating message with x = " + m.concat(x1.toString()) +
                ". Let's presuppose that hashing function yields H(" + m.concat(x1.toString()) + ") = 500.\nHence S1 = " + s1);

        System.out.println("Alice calculates S2 = r + w S1 mod q:");
        BigInteger s2 = w1.multiply(s1).mod(q1).add(r1);
        System.out.println("\tS2 = " + s2);

        System.out.println("Alice sends to Bob M = " + m + ", S1 = " + s1 + ", S2 = " + s2);

        System.out.println("SIGNATURE VERIFICATION");
        System.out.println("Bob calculates X = g^S2 y^S1 mod p:");
        BigInteger x2 = g1.pow(s2.intValue()).multiply(y1.pow(s1.intValue())).mod(p1);
        System.out.println("\tX = " + x2);

        System.out.println("Bob verifies if H(M|X) = S1, hence H(" + m.concat(x1.toString()) + ") = " + s1);

    }
}
