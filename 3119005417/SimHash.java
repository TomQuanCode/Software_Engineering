package homework1papercheck;
import java.util.List;
import java.math.BigInteger;
/*利用SimHash+海明距离算法实现论文查重
 * SimHash接口包含五个方法*/
public interface SimHash {

    //simHash算法核心模块
    BigInteger simHash();

    //计算每个分词的哈希值
    BigInteger hash_value(String word);

    //获取海明距离
    int hammingDistance(SimHashFinish sf);


    //计算海明距离
    double getDistance(String str1, String str2);


    //获取SimHash特征值
    List FiDistance(SimHashFinish sf, int distance);
}
