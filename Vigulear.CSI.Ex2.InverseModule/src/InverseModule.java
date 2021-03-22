import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InverseModule {
    public static void main(String[] args) {
        int b, n, n0 , b0, q, r, t0, t, temp;

        System.out.println(" b^-1 mod n ");
        System.out.print("Input b: ");
        BufferedReader b1 = new BufferedReader(new InputStreamReader(System.in));
        String inp = "";
        try {
            inp = b1.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inp.equals("")) {
            System.out.println("Nothing was input.");
            System.exit(0);
        }

        b = Integer.parseInt(inp);

        System.out.print("Input n: ");
        try {
            inp = b1.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inp.equals("")) {
            System.out.println("Nothing was input.");
            System.exit(0);
        }

        n = Integer.parseInt(inp);

        if (b <= 1 || n <= 0) {
            System.out.println("b and n have to be greater than 0.");
            System.exit(0);
        }

        // ################################################

        System.out.println("b = " + b + ", n = "
                + n + "\n");

        n0 = n;
        b0 = b;
        t0 = 0;
        t = 1;
        q = n0 / b0;
        r = n0 - (q * b0);

        while (r > 0) {
            temp = t0 - q * t;
            if (temp >= 0)
                temp = temp % n;
            else
                temp = n - ((-temp) % n);
            n0 = b0;
            b0 = r;
            t0 = t;
            t = temp;
            q = n0 / b0;
            r = n0 - (q * b0);
        }
        if (b0 != 1)
            System.out.println("No way to b module n");
        else
            System.out.println("Result: " + t);
    }
}
