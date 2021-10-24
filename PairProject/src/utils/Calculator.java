package utils;

import entity.Fraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 表达式计算工具类，调用{@link #calculate(String)}即可计算
 *
 * @author wizardk
 * @email ozx1341530199@gmail.com
 */
public class Calculator {
    /**
     * 加法运算符
     */
    public static final String OP_ADD = "+";
    /**
     * 减法运算符
     */
    public static final String OP_MINUS = "-";
    /**
     * 乘法运算符
     */
    public static final String OP_MULTIPLY = "*";
    /**
     * 除法运算符
     */
    public static final String OP_DIVIDE = "÷";

    /**
     * 根据生成的表达式计算结果
     *
     * @param expression 待计算的表达式
     * @return 返回计算结果
     */
    public static String calculate(String expression) {
        List<String> infixList = parse2List(expression);
        List<String> polandList = parse2Poland(infixList);
        return calculate(polandList);
    }

    /**
     * 接收逆波兰式来计算表达式结果
     *
     * @param polandExp List形式的逆波兰表达式
     * @return 返回计算结果
     */
    private static String calculate(List<String> polandExp) {
        String res;
        String num1;
        String num2;
        Stack<String> s1 = new Stack<>();
        for (String item : polandExp) {
            if (isNum(item)) {
                if (isProperFraction(item)) {
                    s1.push(new Fraction(item).toString());
                } else {
                    s1.push(item);
                }
            } else {
                num1 = s1.pop();
                num2 = s1.pop();
                //处理真分数计算
                if (isProperFraction(num1) || isProperFraction(num2)) {
                    res = calculatePf(item, num1, num2);
                } else {
                    res = switch (item) {
                        case OP_ADD -> Integer.toString(Integer.parseInt(num1) + Integer.parseInt(num2));
                        case OP_MINUS -> Integer.toString(Integer.parseInt(num2) - Integer.parseInt(num1));
                        case OP_MULTIPLY -> Integer.toString(Integer.parseInt(num1) * Integer.parseInt(num2));
                        case OP_DIVIDE -> Integer.toString(Integer.parseInt(num1) / Integer.parseInt(num2));
                        default -> throw new RuntimeException("运算符有误");
                    };
                }
                s1.push(res);
            }
        }
        return s1.pop();
    }

    /**
     * 计算含真分数的式子
     *
     * @param op  运算符
     * @param pf1 操作数1（在运算符前）
     * @param pf2 操作数2（在运算符后）
     * @return 以真分数或整数形式返回结果
     * @throws IllegalArgumentException 若op无法识别
     */
    private static String calculatePf(String op, String pf1, String pf2) {
        Fraction impf1 = new Fraction(pf1);
        Fraction impf2 = new Fraction(pf2);
        Fraction result;
        result = switch (op) {
            case OP_ADD -> new Fraction(impf1.getN() * impf2.getM() + impf2.getN() * impf1.getM(), impf1.getM() * impf2.getM());
            case OP_MINUS -> new Fraction(impf2.getN() * impf1.getM() - impf1.getN() * impf2.getM(), impf1.getM() * impf2.getM());
            case OP_MULTIPLY -> new Fraction(impf1.getN() * impf2.getN(), impf1.getM() * impf2.getM());
            case OP_DIVIDE -> new Fraction(impf1.getN() * impf2.getM(), impf1.getM() * impf2.getN());
            default -> throw new IllegalArgumentException(op + "is unrecognizable," + " the operator must be one of the \"+\" \"- \" \"*\" \"÷\"");
        };

        return result.toString();
    }

    /**
     * 将中缀表达式解析成List
     *
     * @param infixExp 待解析的中缀表达式
     * @return 返回List形式的中缀表达式
     */
    private static List<String> parse2List(String infixExp) {
        //剔除空格
        infixExp = infixExp.replaceAll("\\s+|=", "");
        List<String> list = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < infixExp.length(); i++) {
            if (isSymbol(infixExp.charAt(i))) {
                list.add(infixExp.charAt(i) + "");
            } else if (infixExp.charAt(i) >= '0' && infixExp.charAt(i) <= '9' || infixExp.charAt(i) == '/' || infixExp.charAt(i) == '\'') {
                temp.append(infixExp.charAt(i));
                if (i == infixExp.length() - 1) {
                    list.add(temp.toString());
                } else if (isSymbol(infixExp.charAt(i + 1))) {
                    list.add(temp.toString());
                    temp = new StringBuilder();
                }
            }
        }
        return list;
    }


    /**
     * 将中缀表达式转为逆波兰表达式
     *
     * @param infix List形式的中缀表达式
     * @return 返回转换后的逆波兰表达式
     * @see #parse2List(String)
     */
    private static List<String> parse2Poland(List<String> infix) {
        List<String> list = new ArrayList<>();
        Stack<String> oper = new Stack<>();
        for (String string : infix) {
            if (isNum(string)) {
                list.add(string);
            } else if ("(".equals(string)) {
                oper.push(string);
            } else if (")".equals(string)) {
                while (!"(".equals(oper.peek())) {
                    list.add(oper.pop());
                }
                oper.pop();
            } else {
                while (oper.size() != 0 && priority(string) <= priority(oper.peek())) {
                    list.add(oper.pop());
                }
                oper.push(string);
            }
        }
        while (!oper.empty()) {
            list.add(oper.pop());
        }
        return list;
    }

    /**
     * 判断是否为真分数，形如：2/3, 1/4, 1'1/2
     *
     * @param s 待判断的数
     * @return 若为真分数则返回 {@code true}；否则返回{@code false}
     */
    private static boolean isProperFraction(String s) {
        return s.contains("'") || s.contains("/");
    }

    /**
     * 判断字符是否为运算符
     *
     * @param c 待判断的字符
     * @return 如果c是运算符，返回{@code true}；否则返回{@code false}
     */
    private static boolean isSymbol(char c) {
        return c == '+' || c == '-' || c == '*' || c == '÷' || c == '(' || c == ')';
    }

    /**
     * 判断字符是否为数值
     *
     * @param s 待判断的字符
     * @return 若为数值则返回 {@code true}；否则返回{@code false}
     */
    private static boolean isNum(String s) {
        return s.matches("^\\d+.?\\d*$") || isProperFraction(s);
    }

    /**
     * 计算运算符优先级
     *
     * @param c 待判断的运算符
     * @return
     */
    private static int priority(String c) {
        if (OP_ADD.equals(c) || OP_MINUS.equals(c)) {
            return 1;
        }
        if (OP_MULTIPLY.equals(c) || OP_DIVIDE.equals(c)) {
            return 2;
        }
        return 0;
    }


}
