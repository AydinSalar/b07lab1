import java.io.File;

public class Driver {
    public static void main(String[] args) {
        // 1) Default constructor (zero polynomial)
        Polynomial p0 = new Polynomial();
        System.out.println("p0(3) = " + p0.evaluate(3)); // expect 0.0

        // 2) Build using non-zero coefficients and matching exponents
        // p1(x) = 6 + 5x^3
        double[] c1 = {6, 5};
        int[] e1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, e1);

        // p2(x) = -2x - 9x^4
        double[] c2 = {-2, -9};
        int[] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);

        // 3) Test add
        Polynomial s = p1.add(p2); // s(x) = 6 + 5x^3 - 2x - 9x^4
        System.out.println("s(0.1) = " + s.evaluate(0.1));

        // 4) hasRoot test at x = 1
        if (s.hasRoot(1)) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }

        // 5) Test multiply
        // m(x) = p1(x) * p2(x) = (6 + 5x^3) * (-2x - 9x^4)
        //     = -12x - 54x^4 - 10x^4 - 45x^7
        //     = -12x - 64x^4 - 45x^7
        Polynomial m = p1.multiply(p2);
        System.out.println("m(1) = " + m.evaluate(1)); // expect -12 - 64 - 45 = -121

        // 6) Test saveToFile and File constructor round-trip
        try {
            String fname = "poly_out.txt";
            s.saveToFile(fname);
            Polynomial fromFile = new Polynomial(new File(fname));

            // Compare evaluations at a couple of points
            double x1 = 0.0;
            double x2 = 2.0;
            System.out.println("s(0) vs fromFile(0): " + s.evaluate(x1) + " vs " + fromFile.evaluate(x1));
            System.out.println("s(2) vs fromFile(2): " + s.evaluate(x2) + " vs " + fromFile.evaluate(x2));
        } catch (Exception ex) {
            System.out.println("File round-trip test failed: " + ex.getMessage());
        }
    }
}