package utils;
import java.util.Random;

public class Expression {
    /**
     * 求两个数的最大公约数
     * @param a
     * @param b
     * @return 两个数的最大公约数
     */
    public static int gcd(int a,int b){
        int r = 1;
        do{
            r = a % b ;
            a = b;
            b = r;
            /*
            辗转相除法
            a对b取余，b赋值给a 余数赋值给b，，直到 b == 0
             */
        }while(b != 0);
        return a;
    }


    /**
     * 四则运算符生成器
     * @return 随机返回一个四则运算符（+、-、×、÷）
     */
    public static char operation(){
        //四则运算符号
        char[] symbol = new char[]{'+','-','×','÷'};

        //生成一个随机整数，范围为[0,3]，用于随机选择出一个运算符
        Random df = new Random();
        int number = df.nextInt(4);

        return symbol[number];
    };


    /**
     * 随机运算数生成器，运算数有三种类型：1.正整数 2.形如2/3的真分数 3.形如1'1/5的真分数
     * @param r 控制题目中数值（自然数、真分数和真分数分母）的范围
     * @return 一个运算数
     */
    public static String randomNum(int r){
        //choice随机取0或1，用于下面的switch选择
        Random df = new Random();
        int choice = df.nextInt(2);

        //number用于存放最终返回值
        String number = "";


        switch (choice){
            //生成一个[0,r-1]的整数
            case 0:
                int num = df.nextInt(r);
                number = Integer.toString(num);
                break;
            //生成形如1/3或2'1/3的真分数
            case 1:
                //分子fz，规定取值范围为[0,r-1]
                int fz = df.nextInt(r);
                //分母fm不能为0，规定取值范围为[1,r]
                int fm = (int)(1+Math.random()*(r-1));
                //分母若为1，则number=fz
                if(fm==1){
                    number = Integer.toString(fz);
                }else if(fz==0){                        //分子为0，number=0
                    number = Integer.toString(0);
                } else if(fz<fm){
                    //判断fz与fm是否能约分
                    if(gcd(fz,fm)==1){
                        number = Integer.toString(fz)+"/"+Integer.toString(fm);
                    }else {
                        int maxgys = gcd(fz,fm);
                        int fz1 = fz/maxgys;
                        int fm1 = fm/maxgys;
                        number = Integer.toString(fz1)+"/"+Integer.toString(fm1);
                    }
                }else if(fz==fm){                       //fz==fm，则number=1
                    number = Integer.toString(1);
                }
                //fz<fm的情况处理
                else {
                    if(gcd(fz,fm)==1){
                        if(fm==1){
                            number = Integer.toString(fz);
                        }else {
                            int dai = fz/fm;
                            int newfz = fz - dai * fm;
                            number = Integer.toString(dai)+"'"+Integer.toString(newfz)+"/"+Integer.toString(fm);
                        }
                    }else {
                        int maxgys = gcd(fz,fm);
                        int fz1 = fz/maxgys;
                        int fm1 = fm/maxgys;
                        if(fm1==1){
                            number = Integer.toString(fz1);
                        }else {
                            int dai = fz1/fm1;
                            int newfz = fz1 - dai * fm1;
                            number = Integer.toString(dai)+"'"+Integer.toString(newfz)+"/"+Integer.toString(fm1);
                        }
                    }
                }
                break;
            default:
                break;
        }
        return number;

    }


    /**
     * 真分数转换为double数值，用于减法中的比较
     * @param num
     * @return 真分数num的double值
     */
    public static double transfer(String num){
        double newnum = 0.0;
        if(num.contains("/")==true && num.contains("'")==false){
            int position = num.indexOf("/");
            String fz = num.substring(0,position);
            String fm = num.substring(position+1,num.length());
            newnum = (double) Integer.parseInt(fz)/Integer.parseInt(fm);
        }else if(num.contains("/")==true && num.contains("'")==true){
            int p1 = num.indexOf("'");
            int p2 = num.indexOf("/");
            String dai = num.substring(0,p1);
            String fz = num.substring(p1+1,p2);
            String fm = num.substring(p2+1,num.length());
            double x = (double) Integer.parseInt(fz)/Integer.parseInt(fm);
            newnum = (double) (Integer.parseInt(dai) + x);
        }else {
            newnum = (double) (Integer.parseInt(num));
        }
        return newnum;
    }

    /**
     * 比较两个数（包括正整数、真分数）的大小
     * @param num1
     * @param num2
     * @return true/false
     */
    public static boolean islarge(String num1,String num2){
        double x1 = transfer(num1);
        double x2 = transfer(num2);
        if(x1>=x2){
            return true;
        }else return false;
    }


    /**
     * 求两个整数的最小公倍数
     * @param num1
     * @param num2
     * @return num1和num2的最小公倍数
     */
    public static int mingbs(String num1,String num2){
        int n1 = Integer.parseInt(num1);
        int n2 = Integer.parseInt(num2);
        return n1 * n2 / gcd(n1, n2);

    }


    public static String createExpression(String choice){
        String finalexpression = "";

        if(choice.equals("1")) {
            String expression = "";
            String num1 = randomNum(10);
            String num2 = randomNum(10);
            char sym = operation();

            if (String.valueOf(sym).equals("+")) {
                expression = num1 + " + " + num2 + " = ";
            } else if (String.valueOf(sym).equals("-")) {
                if (islarge(num1, num2) == true) {
                    expression = num1 + " - " + num2 + " = ";
                } else {
                    expression = num2 + " - " + num1 + " = ";
                }
            } else if (String.valueOf(sym).equals("×")) {
                expression = num1 + " × " + num2 + " = ";
            } else if (String.valueOf(sym).equals("÷")) {
                if (transfer(num1) == 0 && transfer(num2) != 0) {
                    expression = num1 + " ÷ " + num2 + " = ";
                } else if (transfer(num1) != 0 && transfer(num2) == 0) {
                    expression = num2 + " ÷ " + num1 + " = ";
                } else {
                    expression = num1 + " ÷ " + num2 + " = ";
                }
            }
            finalexpression = expression;
        }else if(choice.equals("2")){
            String expression = "";
            String num1 = randomNum(10);
            String num2 = randomNum(10);
            String num3 = randomNum(10);

            char sym1 = operation();
            char sym2 = operation();

            if (String.valueOf(sym1).equals("+")) {
                expression = num1 + " + " + num2;
            } else if (String.valueOf(sym1).equals("-")) {
                if (islarge(num1, num2) == true) {
                    expression = num1 + " - " + num2;
                } else {
                    expression = num2 + " - " + num1;
                }
            } else if (String.valueOf(sym1).equals("×")) {
                expression = num1 + " × " + num2;
            } else if (String.valueOf(sym1).equals("÷")) {
                if (transfer(num1) == 0 && transfer(num2) != 0) {
                    expression = num1 + " ÷ " + num2;
                } else if (transfer(num1) != 0 && transfer(num2) == 0) {
                    expression = num2 + " ÷ " + num1;
                } else {
                    expression = num1 + " ÷ " + num2;
                }
            }

            String answer1 = Calculator.calculate(expression);

            if(sym2=='+'){
                expression = expression + " + " + num3 + " = ";
            }else if(sym2=='-'){
                if(islarge(answer1,num3)==true){
                    expression = expression + " - " + num3 + " = ";
                }else{
                    expression = num3 + " - " + expression + " = ";
                }
            }else if(sym2=='×'){
                expression = expression + " × " + num3 + " = ";
            }else if(sym2=='÷'){
                if (transfer(answer1) == 0 && transfer(num3) != 0) {
                    expression =  expression+ " ÷ " + num3 + " = ";
                } else if (transfer(answer1) != 0 && transfer(num3) == 0) {
                    expression = num3 + " ÷ " + expression + " = ";
                } else {
                    expression = expression + " ÷ " + num3 + " = ";
                }
            }

            finalexpression = expression;
        }else if(choice.equals("3")){
            String expression = "";
            String num1 = randomNum(10);
            String num2 = randomNum(10);
            String num3 = randomNum(10);
            String num4 = randomNum(10);

            char sym1 = operation();
            char sym2 = operation();
            char sym3 = operation();

            if (String.valueOf(sym1).equals("+")) {
                expression = num1 + " + " + num2;
            } else if (String.valueOf(sym1).equals("-")) {
                if (islarge(num1, num2) == true) {
                    expression = num1 + " - " + num2;
                } else {
                    expression = num2 + " - " + num1;
                }
            } else if (String.valueOf(sym1).equals("×")) {
                expression = num1 + " × " + num2;
            } else if (String.valueOf(sym1).equals("÷")) {
                if (transfer(num1) == 0 && transfer(num2) != 0) {
                    expression = num1 + " ÷ " + num2;
                } else if (transfer(num1) != 0 && transfer(num2) == 0) {
                    expression = num2 + " ÷ " + num1;
                } else {
                    expression = num1 + " ÷ " + num2;
                }
            }

            String answer1 = Calculator.calculate(expression);

            if(sym2=='+'){
                expression = expression + " + " + num3;
            }else if(sym2=='-'){
                if(islarge(answer1,num3)==true){
                    expression = expression + " - " + num3;
                }else{
                    expression = num3 + " - " + expression;
                }
            }else if(sym2=='×'){
                expression = expression + " × " + num3;
            }else if(sym2=='÷'){
                if (transfer(answer1) == 0 && transfer(num3) != 0) {
                    expression =  expression+ " ÷ " + num3;
                } else if (transfer(answer1) != 0 && transfer(num3) == 0) {
                    expression = num3 + " ÷ " + expression;
                } else {
                    expression = expression + " ÷ " + num3;
                }
            }

            String answer2 = Calculator.calculate(expression);
            if(sym3=='+'){
                expression = expression + " + " + num4 + " = ";
            }else if(sym3=='-'){
                if(islarge(answer2,num4)==true){
                    expression = expression + " - " + num4 + " = ";
                }else{
                    expression = num4 + " - " + expression + " = ";
                }
            }else if(sym3=='×'){
                expression = expression + " × " + num4 + " = ";
            }else if(sym3=='÷'){
                if (transfer(answer2) == 0 && transfer(num4) != 0) {
                    expression =  expression+ " ÷ " + num4 + " = ";
                } else if (transfer(answer2) != 0 && transfer(num4) == 0) {
                    expression = num4 + " ÷ " + expression + " = ";
                } else {
                    expression = expression + " ÷ " + num4 + " = ";
                }
            }

            finalexpression = expression;

        }

        return finalexpression;
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            System.out.println(createExpression("3"));
//        }
        test("12/57");
        test("2'12/57");
    }

    public static void test(String f) {
        if (!f.contains("/")) {
            System.out.println("n: " + Integer.toString(Integer.parseInt(f) * 2));
            System.out.println("m: 2");
        } else if (!f.contains("'")) {
            System.out.println("n: " + f.substring(0, f.lastIndexOf("/")));
            System.out.println("m: " + f.substring(f.lastIndexOf("/") + 1));
        } else {
            int N = Integer.parseInt(f.substring(0, f.lastIndexOf("'")));
            System.out.println("N: " + N);
            System.out.println("n: " + Integer.toString(Integer.parseInt(f.substring(f.lastIndexOf("'") + 1, f.lastIndexOf("/"))) +
                    Integer.parseInt(f.substring(f.lastIndexOf("/") + 1)) * N));
            System.out.println("m: " + f.substring(f.lastIndexOf("/") + 1));
        }
    }

}
