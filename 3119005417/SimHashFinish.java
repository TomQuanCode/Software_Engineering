package homework1papercheck;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
//SimHash_Finish类实现SimHash接口
public class SimHashFinish implements SimHash{
    private String tokens;

    //BigInteger支持任意精度的整数，且数字范围很大
    private BigInteger intSimHash;

    private String strSimHash;

    //hash值位数设置为64位
    private int hashbits = 64;

    //构造方法
    public SimHashFinish(String tokens, int hashbits) {
        this.tokens = tokens;
        this.hashbits = hashbits;
        this.intSimHash = this.simHash();
    }

    //simHash模块
    public BigInteger simHash() {
        // 特征值数组，长度为hash值位数
        int array[] = new int[this.hashbits];
        //StringTokenizer类用于对字符串进行分词
        StringTokenizer stringTokens = new StringTokenizer(this.tokens);
        //hasMoreTokens()方法在字符串中匹配默认的分隔符
        while (stringTokens.hasMoreTokens()) {
            //分词
            String temp = stringTokens.nextToken();
            //调用hash_value()方法，计算出每个分词的hash值（64位）（整数）
            BigInteger sequence = this.hash_value(temp);
            for (int i = 0; i < this.hashbits; i++) {
                //生成每一位数的hash值
                BigInteger bitjudge = new BigInteger("1").shiftLeft(i);
                /*对于每个分词对应的64位hash值， 对于每个位，若该位为0，则array数组对应位置-1
                 * 若该位为1，则array数组对应位+1，将所有分词对应的hash值判断完*/
                if (sequence.and(bitjudge).signum()!=0) {
                    array[i] =array[i]+1;
                } else {
                    array[i] =array[i]-1;
                }
            }
        }

        //初始化simhash签名
        BigInteger simhashsign = new BigInteger("0");
        StringBuffer simHashBuffer = new StringBuffer();
        for (int i = 0; i < this.hashbits; i++) {
            //降维，array数组中大于等于0的置为1，小于0的置为0，最后可以得到一串64位的simhash签名
            if (array[i]>=0) {
                simhashsign = simhashsign.add(new BigInteger("1").shiftLeft(i));
                simHashBuffer.append("1");
            }else{
                simHashBuffer.append("0");
            }
        }
        //转化为字符串输出
        this.strSimHash = simHashBuffer.toString();
        setStrSimHash(strSimHash);
        //返回simhash签名
        return simhashsign;
    }

    //获取simhash签名
    public String getStrSimHash() {
        return strSimHash;
    }

    //设置simhash签名
    public void setStrSimHash(String strSimHash) {
        this.strSimHash = strSimHash;
    }

    //计算分词word的hash值
    public BigInteger hash_value(String word) {
        //若分词为空或长度为0，则其hash值为0
        if (word==null || word.length()==0) {
            BigInteger answer = new BigInteger("0");
            return answer;
        } else {
            //将分词（String型）分解，构成字符数组（char)
            char[] wordArray = word.toCharArray();
            //将字符（char）转化为BigInteger型
            BigInteger r = BigInteger.valueOf(((long) wordArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
            //对于wordArray数组中的每一个项，先转换为BigInteger型
            for (char item : wordArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                //逻辑乘、异或、与运算
                r = r.multiply(m).xor(temp).and(mask);
            }
            r = r.xor(new BigInteger(String.valueOf(word.length())));
            //若r值为-1，则置r置为-2
            if (r.equals(new BigInteger("-1"))) {
                r = new BigInteger("-2");
            }
            //返回hash值
            return r;
        }
    }

    //获取海明距离
    public int hammingDistance(SimHashFinish sf) {

        BigInteger x = this.intSimHash.xor(sf.intSimHash);
        int count = 0;

        //统计x中二进制位数为1的个数
        //若x不为0，则distance+1
        while (x.signum() != 0) {
            count = count + 1;
            //x减去1，再和原本的x进行与操作，能得到减少一个位后的x
            //x能循环的次数=x中二进制位数不等于0的个数
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return count;
    }

    //计算两个字符串的海明距离
    public double getDistance(String str1, String str2) {
        double distance;
        if (str1.length() != str2.length()) {
            distance = -1;
        } else {
            distance = 0;
            for (int i = 0; i < str1.length(); i++) {
                //两个字符串进行逐个字符的比较，若不相等，则海明距离+1
                if (str1.charAt(i) != str2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    //获取simhash特征值
    public List FiDistance(SimHashFinish sf, int distance){
        //分组长度
        int numEach = this.hashbits/(distance+1);
        //特征值列表
        List characters = new ArrayList();
        //字符缓冲区
        StringBuffer buffer = new StringBuffer();

        int k = 0;
        for(int i = 0; i < this.intSimHash.bitLength(); i++){
            //testBit()方法从simhash签名的低位开始进行判断，若该位不为1，则返回true；否则，返回false
            boolean tag = sf.intSimHash.testBit(i);

            if(tag==true){
                buffer.append("1");
            }
            else{
                buffer.append("0");
            }

            if((i+1)%numEach == 0){
                //将二进制转为BigInteger
                BigInteger eachValue = new BigInteger(buffer.toString(),2);
                buffer.delete(0, buffer.length());
                //向特征值列表中添加每个分词的特征值
                characters.add(eachValue);
            }
        }
        return characters;
    }
}
