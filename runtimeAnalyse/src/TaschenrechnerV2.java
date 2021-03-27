// 
// Decompiled by Procyon v0.5.36
// 

public class TaschenrechnerV2
{
    public String[][] separated;
    String[] operators;
    String[] timesDivide;
    String[] plusMinus;
    
    public TaschenrechnerV2() {
        this.timesDivide = new String[] { "*", "/" };
        this.plusMinus = new String[] { "+", "-" };
        this.operators = new String[] { "*", "/", "-", "+" };
    }
    
    public double work(final String[][] input) {
        this.separated = input;
        return this.calculate();
    }
    
    public void rearrange(final int index) {
        for (int j = index; j < this.separated.length; ++j) {
            if (j + 1 == this.separated.length) {
                this.separated[j][0] = null;
                this.separated[j][1] = null;
                return;
            }
            this.separated[j][0] = this.separated[j + 1][0];
            this.separated[j][1] = this.separated[j + 1][1];
        }
    }
    
    public double calculate() {
        for (int i = 0; i < this.separated.length; ++i) {
            for (int j = 0; j < this.timesDivide.length; ++j) {
                if (this.separated[i][0] != null && this.separated[i][0].equals(this.timesDivide[j])) {
                    final double result = this.add(Double.valueOf(this.separated[i - 1][1]), Double.valueOf(this.separated[i][1]), this.timesDivide[j]);
                    this.separated[i - 1][1] = String.valueOf(result);
                    this.rearrange(i);
                    i = 0;
                }
            }
        }
        for (int i = 0; i < this.separated.length; ++i) {
            for (int j = 0; j < this.plusMinus.length; ++j) {
                if (this.separated[i][0] != null && this.separated[i][0].equals(this.plusMinus[j])) {
                    final double result = this.add(Double.valueOf(this.separated[i - 1][1]), Double.valueOf(this.separated[i][1]), this.plusMinus[j]);
                    this.separated[i - 1][1] = String.valueOf(result);
                    this.rearrange(i);
                    i = 0;
                }
            }
        }
        double finalResult = 0.0;
        for (int l = 0; l < this.separated.length; ++l) {
            if (this.separated[l][1] != null) {
                finalResult = Double.valueOf(this.separated[l][1]);
            }
        }
        return finalResult;
    }
    
    public double add(final double a, final double b, final String OP) {
        switch (OP) {
            case "*": {
                return a * b;
            }
            case "+": {
                return a + b;
            }
            case "-": {
                return a - b;
            }
            case "/": {
                return a / b;
            }
            default:
                break;
        }
        return 0.0;
    }
}
