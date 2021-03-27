import java.io.FileNotFoundException;
import java.io.PrintWriter;

// 
// Decompiled by Procyon v0.5.36
// 

public class GUIV2
{
    TaschenrechnerV2 calc;
    exceptions exceptionHandle;
    char[] operators;
    String[][] sorted;
    PrintWriter outputStream;
    
    public GUIV2(final String S, final String S2) {
        this.initOther();
        this.speedTest(Integer.parseInt(S), S2);
    }
    
    public void initOther() {
        this.sorted = new String[1000][2];
        this.operators = new char[] { '*', '/', '+', '-' };
        this.calc = new TaschenrechnerV2();
        this.exceptionHandle = new exceptions();
    }
    
    private void analyse(final String input) {
        boolean isMinus = true;
        final char[] rawInput = input.toCharArray();
        int index = 0;
        for (int i = 0; i < rawInput.length; ++i) {
            if (isMinus) {
                if (rawInput[i] == '-') {
                    this.sorted[index][1] = String.valueOf('-');
                }
                else {
                    isMinus = false;
                }
            }
            if (!isMinus) {
                boolean tmp = true;
                for (int j = 0; j < this.operators.length; ++j) {
                    if (rawInput[i] == this.operators[j]) {
                        index++;
                        if(index > this.sorted.length-1) {
                        	expandSorted();
                        }
                        this.sorted[index][0] = "" + this.operators[j];
                        isMinus = true;
                        tmp = false;
                    }
                }
                if (tmp) {
                	if(this.sorted[index][1] == null) 
                		this.sorted[index][1] = String.valueOf(rawInput[i]);
                	else
                		this.sorted[index][1] = String.valueOf(this.sorted[index][1]) + rawInput[i];
                }
            }
        }
        trimSorted(index);
    }
    
    private void expandSorted() {
        final String[][] newArray = new String[this.sorted.length + 1000][this.sorted[0].length];
        System.arraycopy(this.sorted, 0, newArray, 0, this.sorted.length);
        this.sorted = newArray;
    }
    
    private void trimSorted(final int i) {
    	final String[][] newArray = new String[i][2];
    	System.arraycopy(this.sorted, 0, newArray, 0, i);
    	this.sorted = newArray;
    }
    
    public boolean searchException(final String input) {
        if (input == "") {
            return false;
        }
        final char[] rawInput = input.toCharArray();
        for (int i = 0; i < rawInput.length - 1; ++i) {
            if (rawInput[i] == '.') {
                if (rawInput[i + 1] == '.') {
                    return false;
                }
                for (int j = 0; j < this.operators.length; ++j) {
                    if (rawInput[i + 1] == this.operators[j]) {
                        return false;
                    }
                }
            }
            for (int k = 0; k < this.operators.length; ++k) {
                if (rawInput[i] == this.operators[k]) {
                    if (rawInput[i + 1] == '.') {
                        return false;
                    }
                    for (int l = 0; l < this.operators.length - 1; ++l) {
                        if (rawInput[i + 1] == this.operators[l]) {
                            return false;
                        }
                    }
                    if (this.operators[k] == '-' && rawInput[i + 1] == '-') {
                        return false;
                    }
                }
            }
        }
        for (int m = 0; m < this.operators.length; ++m) {
            if (rawInput[rawInput.length - 1] == this.operators[m]) {
                return false;
            }
        }
        if (rawInput[rawInput.length - 1] == '.') {
            return false;
        }
        for (int n = 0; n < this.sorted.length; ++n) {
            int dotCounter = 0;
            final String number = this.sorted[n][1];
            final char[] numberArr = number.toCharArray();
            for (int o = 0; o < numberArr.length; ++o) {
                if (numberArr[o] == '.') {
                    ++dotCounter;
                }
            }
        }
        return true;
    }
    
    public double round(double input) {
        input *= 10.0;
        input = (double)Math.round(input);
        return input / 10.0;
    }
    
    public void speedTest(final int runden, final String fileName) {
        final String[] minus = { "", "-" };
        try {
            this.outputStream = new PrintWriter(fileName);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final String[][] result = new String[runden][3];
        final long startTime = System.nanoTime();
        for (int i = 1; i < runden + 1; ++i) {
            String s = "1";
            for (int j = 0; j < i; ++j) {
                final double a = this.round(Math.random() * 9.0);
                final char c = this.operators[(int)(Math.random() * 4.0)];
                final String m = minus[(int)(Math.random() * 2.0)];
                s = String.valueOf(s) + c + m + a;
            }
            this.sorted = new String[1000][2];
            System.out.println(i);
            final long timer1 = System.nanoTime();
            this.analyse(s);
            //this.searchException(s);
            //final double res = this.round(this.calc.work(this.sorted));
            final long timer2 = System.nanoTime();
            final long deltaTime = timer2 - timer1;
            result[i - 1][2] = String.valueOf(deltaTime);
        }
        final long endTime = System.nanoTime();
        final long nano = endTime - startTime;
        final long mikro = nano / 1000L;
        final long milli = mikro / 1000L;
        final long sek = milli / 1000L;
        final long min = sek / 60L;
        System.out.println(String.valueOf(nano) + "nano");
        System.out.println(String.valueOf(mikro) + "mikro");
        System.out.println(String.valueOf(milli) + "milli");
        System.out.println(String.valueOf(sek) + "sek");
        System.out.println(String.valueOf(min) + "min");
        for (int k = 0; k < result.length; ++k) {
        	this.outputStream.println(result[k][2]);
        }
        this.outputStream.close();
    }
}
