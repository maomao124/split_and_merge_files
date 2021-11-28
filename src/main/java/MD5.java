import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Project name(项目名称)：文件的分割和合并
 * Package(包名): PACKAGE_NAME
 * Class(类名): MD5
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/11/28
 * Time(创建时间)： 21:54
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class MD5
{
    public static String getFileMD5(String filePath)  //获得文件的MD5值
    {
        try
        {
            InputStream fis = new FileInputStream(filePath);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1)
            {
                md.update(buffer, 0, length);
            }
            fis.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);
            return bigInt.toString(16);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args)
    {
        String t1;
        String t;
        t = getFileMD5("test.txt");
        t1 = getFileMD5("test1.txt");
        System.out.println("test.txt :" + t);
        System.out.println("test1.txt:" + t1);
        if (t.equals(t1))
        {
            System.out.println("一致");
        }
        else
        {
            System.out.println("不一致");
        }
    }
}
