import java.awt.*;
import java.io.*;

/**
 * Project name(项目名称)：文件的分割和合并
 * Package(包名): PACKAGE_NAME
 * Class(类名): Configuration
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/11/28
 * Time(创建时间)： 14:55
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class Configuration implements Serializable
{
    private String name;      //文件名
    private long length;    //文件长度
    private int size;       //每个文件的大小
    private int count;      //文件数量

    public Configuration()
    {

    }

    public Configuration(String name, long length, int size, int count)
    {
        this.name = name;
        this.length = length;
        this.size = size;
        this.count = count;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getLength()
    {
        return length;
    }

    public void setLength(long length)
    {
        this.length = length;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)    //引用同一个对象
        {
            return true;
        }
        if (o == null)    //检测obj是否为null
        {
            return false;
        }
        //if(!(otherObject instanceof ClassName)) //如果所有的子类都拥有统一的语义
        if (this.getClass() != o.getClass())   //比较this与obj是否属于同一个类
        {
            return false;
        }
        //Object类向下转型

        Configuration that = (Configuration) o;

        if (length != that.length)
        {
            return false;
        }
        if (size != that.size)
        {
            return false;
        }
        if (count != that.count)
        {
            return false;
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + (int) (length ^ (length >>> 32));
        result = 31 * result + size;
        result = 31 * result + count;
        return result;
    }

    public static void write(Configuration config)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream ObjectOutputStream = null;
        try                                  //文件流打开，文件读写
        {
            fileOutputStream = new FileOutputStream("out\\Configuration.ini");
            ObjectOutputStream = new ObjectOutputStream(fileOutputStream);
            ObjectOutputStream.writeObject(config);
        }
        catch (FileNotFoundException e)      //文件未找到
        {
            Toolkit.getDefaultToolkit().beep();
            System.err.println("文件未找到！！！  " + "\n错误内容：" + e.toString());
        }
        catch (Exception e)                  //其它异常
        {
            Toolkit.getDefaultToolkit().beep();
            e.printStackTrace();
        }
        finally
        {
            try                              //关闭流
            {
                if (fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
                if (ObjectOutputStream != null)
                {
                    ObjectOutputStream.close();
                }
            }
            catch (NullPointerException e)    //空指针异常
            {
                Toolkit.getDefaultToolkit().beep();
                System.err.println("文件已经被关闭，无法再次关闭！！！");
            }
            catch (Exception e)              //其它异常
            {
                Toolkit.getDefaultToolkit().beep();
                e.printStackTrace();
            }
        }
    }

    public static Configuration read()
    {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try                                  //文件流打开，文件读写
        {
            fileInputStream = new FileInputStream("out\\Configuration.ini");
            objectInputStream = new ObjectInputStream(fileInputStream);
            Configuration configuration = (Configuration) objectInputStream.readObject();
            return configuration;
        }
        catch (FileNotFoundException e)      //文件未找到
        {
            Toolkit.getDefaultToolkit().beep();
            System.err.println("文件未找到！！！  " + "\n错误内容：" + e.toString());
            System.exit(1);
        }
        catch (Exception e)                  //其它异常
        {
            Toolkit.getDefaultToolkit().beep();
            e.printStackTrace();
            System.exit(1);
        }
        finally
        {
            try                              //关闭流
            {
                if (fileInputStream != null)
                {
                    fileInputStream.close();
                }
                if (objectInputStream != null)
                {
                    objectInputStream.close();
                }
            }
            catch (NullPointerException e)    //空指针异常
            {
                Toolkit.getDefaultToolkit().beep();
                System.err.println("文件已经被关闭，无法再次关闭！！！");
            }
            catch (Exception e)              //其它异常
            {
                Toolkit.getDefaultToolkit().beep();
                e.printStackTrace();
            }
        }
        return null;
    }
}
