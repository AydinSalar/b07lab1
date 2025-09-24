import java.io.File;

public class Driver {
    public static void main(String[] args) {
        // zero polynomial
        Polynomial z = new Polynomial();
        System.out.println("z(-2) = " + z.evaluate(-2));

        // pA(x) = -4 + 2x + 3x^5
        double[] aCoeff = {-4, 2, 3};
        int[]    aExp   = { 0, 1, 5};
        Polynomial pA = new Polynomial(aCoeff, aExp);

        // pB(x) = 7 + 0.5x - x^3
        double[] bCoeff = {7, 0.5, -1};
        int[]    bExp   = {0,   1,   3};
        Polynomial pB = new Polynomial(bCoeff, bExp);

        // add & multiply
        Polynomial sum = pA.add(pB);      // combine like terms by exponent
        Polynomial prod = pA.multiply(pB); // merged exponents in multiply

        // evaluate at a few points
        double x1 = -1.0;
        double x2 = 0.5;
        System.out.println("pA(" + x1 + ") = " + pA.evaluate(x1));
        System.out.println("pB(" + x2 + ") = " + pB.evaluate(x2));
        System.out.println("sum(0) = " + sum.evaluate(0));
        System.out.println("prod(1) = " + prod.evaluate(1));

        // hasRoot checks (not expecting roots here)
        System.out.println("pA has root at 0? " + pA.hasRoot(0));
        System.out.println("pB has root at 2? " + pB.hasRoot(2));

        // file round-trip with a different filename
        try {
            String fname = "poly_test_out.txt";
            prod.saveToFile(fname);
            Polynomial readBack = new Polynomial(new File(fname));
            // compare at two points to ensure parse/save symmetry
            System.out.println("prod(-1) vs file(-1): " + prod.evaluate(-1) + " | " + readBack.evaluate(-1));
            System.out.println("prod(3) vs file(3): " + prod.evaluate(3) + " | " + readBack.evaluate(3));
        } catch (Exception e) {
            System.out.println("IO test skipped: " + e.getMessage());
        }
    }
}