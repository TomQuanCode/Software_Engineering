package entity;

/**
 * 分数实体类
 *
 * @author wizardk
 * @email ozx1341530199@gmail.com
 */
public class Fraction {

    /**
     * 分子
     */
    String numerator;
    /**
     * 分母
     */
    String denominator;

    /**
     * 直接将传入的分子分母构造成分数
     *
     * @param n 分子
     * @param m 分母
     */
    public Fraction(int n, int m) {
        this.numerator = Integer.toString(n);
        this.denominator = Integer.toString(m);
        simplify();
    }

    /**
     * 将传入的数(包括整数，不包括小数)构造成分数
     *
     * @param f 传入的分数({@code String}形式)
     */
    public Fraction(String f) {
        if (!f.contains("/")) {
            this.numerator = Integer.toString(Integer.parseInt(f) * 2);
            this.denominator = "2";
        } else if (!f.contains("'")) {
            this.numerator = f.substring(0, f.lastIndexOf("/"));
            this.denominator = f.substring(f.lastIndexOf("/") + 1);
        } else {
            int N = Integer.parseInt(f.substring(0, f.lastIndexOf("'")));
            this.denominator = f.substring(f.lastIndexOf("/") + 1);
            this.numerator = Integer.toString(Integer.parseInt(f.substring(f.lastIndexOf("'") + 1, f.lastIndexOf("/"))) +
                    Integer.parseInt(denominator) * N);
        }
        simplify();
    }

    /**
     * 将分数约分至最简
     */
    private void simplify() {
        if ("0".equals(numerator)) {
            return;
        }
        int n = Integer.parseInt(numerator);
        int m = Integer.parseInt(denominator);
        int b = Math.max(n, m);
        int s = Math.min(n, m);
        int r = b;
        while (r != 0) {
            r = b % s;
            b = s;
            if (r != 0) {
                s = r;
            }
        }
        numerator = Integer.toString(n / s);
        denominator = Integer.toString(m / s);
    }

    /**
     * 获取分子
     *
     * @return 返回int形式的分子
     */
    public int getN() {
        return Integer.parseInt(numerator);
    }

    /**
     * 获取分母
     *
     * @return 返回int形式的分母
     */
    public int getM() {
        return Integer.parseInt(denominator);
    }

    @Override
    public String toString() {
        return parse2Pf(numerator, denominator);
    }

    /**
     * 将分数转为真分数
     *
     * @param n 分子
     * @param m 分母
     * @return 返回等价的真分数
     */
    private String parse2Pf(String n, String m) {
        int denominatorI = Integer.parseInt(m);
        int numeratorI = Integer.parseInt(n);
        if (numeratorI < denominatorI) {
            return n + "/" + m;
        } else if (numeratorI == denominatorI) {
            return "1";
        }
        return numeratorI / denominatorI + "'" + numeratorI % denominatorI + "/" + m;
    }
}
