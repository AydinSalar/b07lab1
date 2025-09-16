public class Polynomial {

    // field with an array of coefficients
    private double[] coefficients;

    // contructor with no arguments
    public Polynomial(){
        this.coefficients = new double[]{0};
    }

    // constructor an argument of type double array
    public Polynomial(double[] coefficients){
        this.coefficients = coefficients;
    }

    // method to add two polynomials
    public Polynomial add(Polynomial other){
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        double[] resultCoefficients = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            double thisCo, otherCo;
            if (i < this.coefficients.length) {
                thisCo = this.coefficients[i];
            } else {
                thisCo = 0;
            }
            if (i < other.coefficients.length) {
                otherCo = other.coefficients[i];
            } else {
                otherCo = 0;
            }
            resultCoefficients[i] = thisCo + otherCo;
        }

        return new Polynomial(resultCoefficients);
    }

    // method to evaluate the polynomial at a given value of x
    public double evaluate(double x){
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    // method to determine wether the polynomial has a root at a given value of x
    public boolean hasRoot(double x){
        return this.evaluate(x) == 0;
    }
}