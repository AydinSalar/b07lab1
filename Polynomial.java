import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Polynomial {

    // field with an array of non_zero_coefficients
    private double[] non_zero_coefficients;
    private int[] exponents;

    // contructor with no arguments
    public Polynomial(){
        this.non_zero_coefficients = new double[]{0};
        this.exponents = new int[]{0};
    }

    // constructor an argument of type double array
    public Polynomial(double[] non_zero_coefficients, int[] exponents){
        this.non_zero_coefficients = non_zero_coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) {
    try {
        Scanner sc = new Scanner(file);
        String line = sc.nextLine();
        sc.close();

        line = line.replace("-", "+-");
        String[] terms = line.split("\\+");

        double[] coeffs = new double[terms.length];
        int[] exps = new int[terms.length];
        int idx = 0;

        for (String t : terms) {
            if (t.equals("")) continue;
            if (t.contains("x")) {
                String[] parts = t.split("x");
                double c = parts[0].equals("") ? 1 : Double.parseDouble(parts[0]);
                int e = parts.length == 1 ? 1 : Integer.parseInt(parts[1]);
                coeffs[idx] = c;
                exps[idx] = e;
            } else {
                coeffs[idx] = Double.parseDouble(t);
                exps[idx] = 0;
            }
            idx++;
        }

        this.non_zero_coefficients = Arrays.copyOf(coeffs, idx);
        this.exponents = Arrays.copyOf(exps, idx);

    } catch (Exception e) {
        // sets polynomial to zero if file can't be read
        this.non_zero_coefficients = new double[]{0};
        this.exponents = new int[]{0};
    }
}

    // method to add two polynomials
    public Polynomial add(Polynomial other){
        int max_size = this.non_zero_coefficients.length + other.non_zero_coefficients.length;

        double[] temp_coefficients = new double[max_size];
        int[] temp_exponents = new int[max_size];
        int index = 0;

        for (int i = 0; i < this.non_zero_coefficients.length; i++) {
            temp_coefficients[index] = this.non_zero_coefficients[i];
            temp_exponents[index] = this.exponents[i];
            index++;
        }

        for (int i = 0; i < other.non_zero_coefficients.length; i++) {
            boolean found = false;
            for (int j = 0; j < index; j++) {
                if (temp_exponents[j] == other.exponents[i]) {
                    temp_coefficients[j] += other.non_zero_coefficients[i];
                    found = true;
                    break;
                }
            }
            if (!found) {
                temp_coefficients[index] = other.non_zero_coefficients[i];
                temp_exponents[index] = other.exponents[i];
                index++;
            }
        }

        double[] new_coefficients = new double[index];
        int[] new_exponents = new int[index];
        for (int i = 0; i < index; i++){
            new_coefficients[i] = temp_coefficients[i];
            new_exponents[i] = temp_exponents[i];
        }

        return new Polynomial(new_coefficients, new_exponents);
    }

    // method to multiply two polynomials
    public Polynomial multiply(Polynomial other) {
        int max_size = this.non_zero_coefficients.length * other.non_zero_coefficients.length;
        double[] temp_coefficients = new double[max_size];
        int[] temp_exponents = new int[max_size];
        int index = 0;

        for (int i = 0; i < this.non_zero_coefficients.length; i++) {
            for (int j = 0; j < other.non_zero_coefficients.length; j++) {
                int exp = this.exponents[i] + other.exponents[j];
                double coeff = this.non_zero_coefficients[i] * other.non_zero_coefficients[j];

                boolean found = false;
                for (int k = 0; k < index; k++) {
                    if (temp_exponents[k] == exp) {
                        temp_coefficients[k] += coeff;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    temp_coefficients[index] = coeff;
                    temp_exponents[index] = exp;
                    index++;
                }
            }
        }

        double[] new_coefficients = new double[index];
        int[] new_exponents = new int[index];
        for (int i = 0; i < index; i++) {
            new_coefficients[i] = temp_coefficients[i];
            new_exponents[i] = temp_exponents[i];
        }

        return new Polynomial(new_coefficients, new_exponents);
    }

    // method to evaluate the polynomial at a given value of x
    public double evaluate(double x){
        double result = 0;
        for (int i = 0; i < this.non_zero_coefficients.length; i++) {
            result += this.non_zero_coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return result;
    }

    // method to determine wether the polynomial has a root at a given value of x
    public boolean hasRoot(double x){
        return this.evaluate(x) == 0;
    }

    // method to save the polynomial to a file in textual format
    public void saveToFile(String filename) {
        try {
            java.io.PrintWriter out = new java.io.PrintWriter(filename);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.non_zero_coefficients.length; i++) {
                double coeff = this.non_zero_coefficients[i];
                int exp = this.exponents[i];

                if (i > 0 && coeff >= 0) {
                    sb.append("+");
                }

                if (exp == 0) {
                    sb.append(coeff);
                } else if (exp == 1) {
                    if (coeff == 1.0) {
                        sb.append("x");
                    } else if (coeff == -1.0) {
                        sb.append("-x");
                    } else {
                        sb.append(coeff).append("x");
                    }
                } else {
                    if (coeff == 1.0) {
                        sb.append("x").append(exp);
                    } else if (coeff == -1.0) {
                        sb.append("-x").append(exp);
                    } else {
                        sb.append(coeff).append("x").append(exp);
                    }
                }
            }

            out.print(sb.toString());
            out.close();
        } catch (Exception e) {
            System.out.println("Error saving polynomial to file: " + e.getMessage());
        }
    }
}