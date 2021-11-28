import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Scanner;

/**
 * Project name(项目名称)：文件的分割和合并
 * Package(包名): PACKAGE_NAME
 * Class(类名): test
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/11/28
 * Time(创建时间)： 14:51
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class test
{
    public static void fileSplit(Configuration config, String filePath)
    {
        FileInputStream fileInputStream = null;
        try
        {
            fileInputStream = new FileInputStream(filePath);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        byte[] buffer = new byte[config.getSize()];
        int count;
        for (int i = 0; i < config.getCount(); i++)
        {
            String filename = "out\\" + config.getName() + ".part" + (i + 1);
            File file = new File(filename);
            FileOutputStream fileOutputStream = null;
            try                                  //文件流打开，文件读写
            {
                count = fileInputStream.read(buffer, 0, buffer.length);
                fileOutputStream = new FileOutputStream(filename);
                fileOutputStream.write(buffer, 0, count);
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
                    if (fileOutputStream != null)
                    {
                        fileOutputStream.close();
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
        try
        {
            fileInputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void fileMerging(Configuration config)
    {
        if (config.getName() == null)
        {
            Toolkit.getDefaultToolkit().beep();
            System.out.println("数据错误！！！");
            System.exit(1);
        }
        if (config.getLength() <= 0)
        {
            Toolkit.getDefaultToolkit().beep();
            System.out.println("数据错误！！！");
            System.exit(1);
        }
        if (config.getSize() <= 0)
        {
            Toolkit.getDefaultToolkit().beep();
            System.out.println("数据错误！！！");
            System.exit(1);
        }
        if (config.getCount() <= 0)
        {
            Toolkit.getDefaultToolkit().beep();
            System.out.println("数据错误！！！");
            System.exit(1);
        }
        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(config.getName());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        FileInputStream fileInputStream = null;
        for (int i = 0; i < config.getCount(); i++)
        {
            byte[] buffer = new byte[config.getSize()];
            int count = 0;
            String filename = "out\\" + config.getName() + ".part" + (i + 1);
            try                                  //文件流打开，文件读写
            {
                fileInputStream = new FileInputStream(filename);
                count = fileInputStream.read(buffer, 0, buffer.length);
                fileOutputStream.write(buffer, 0, count);
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
        try
        {
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {

        System.out.println("1.文件分割     2.文件合并");
        Scanner input = new Scanner(System.in);
        System.out.print("请输入：");
        char ch = input.next().charAt(0);
        if (ch == '1')
        {
            Configuration config = new Configuration();
            input.nextLine();
            System.out.print("请输入文件名：");
            String filepath = input.nextLine();
            File file = new File(filepath);
            if (!file.exists())
            {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("文件不存在！！！");
                System.exit(1);
            }
            if (!file.isFile())
            {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("不是文件！！！");
                System.exit(1);
            }
            String fileName = file.getName();
            config.setName(fileName);
            File file1 = new File("out");
            boolean result = file1.mkdirs();
            if (!result)
            {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("文件夹创建失败！！！");
                System.out.println("相对路径下可能存在out文件夹");
                System.exit(1);
            }
            int size;
            //控制台输入变量:size
            int errCount = 0;
            while (true)
            {
                try
                {
                    //min:128
                    //max:1024000000
                    System.out.print("请输入要分割的每个文件的大小，单位是字节：");
                    size = input.nextInt();
                    if (size >= 128 && size <= 1024000000)
                    {
                        break;
                    }
                    else
                    {
                        errCount++;
                        Toolkit.getDefaultToolkit().beep();
                        if (errCount > 10)
                        {
                            System.err.println("错误次数过多！！！退出");
                            System.exit(1);
                        }
                        System.out.println("输入的数据不在范围内! 范围：[128,1024000000]");
                    }
                }
                catch (Exception e)
                {
                    errCount++;
                    if (errCount > 5)
                    {
                        Toolkit.getDefaultToolkit().beep();
                        System.err.println("错误次数过多！！！退出");
                        System.exit(1);
                    }
                    else
                    {
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println("输入错误！！！请重新输入！");
                        input.nextLine();
                    }
                }
            }
            long fileLength;
            fileLength = file.length();
            if (fileLength == 0)
            {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("文件长度为空！！！");
                System.exit(1);
            }
            if (fileLength < size)
            {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("文件总大小比单个文件还小！！！");
                System.exit(1);
            }
            System.out.println("文件长度：" + fileLength);
            int count;
            count = (int) (fileLength / (long) size);
            if (fileLength % (long) size != 0)
            {
                count = count + 1;
            }
            System.out.println("分割后每个文件的大小：" + size + "字节");
            System.out.println("文件数量：" + count);
            config.setLength(fileLength);
            config.setSize(size);
            config.setCount(count);
            //开始
            //------------------------------------------------------
            long startTime = System.nanoTime();   //获取开始时间
            //------------------------------------------------------
            fileSplit(config, filepath);
            Configuration.write(config);
            System.out.println("完成！！！");
            System.out.println();
            //------------------------------------------------------
            long endTime = System.nanoTime(); //获取结束时间
            if ((endTime - startTime) < 1000000)
            {
                double final_runtime;
                final_runtime = (endTime - startTime);
                final_runtime = final_runtime / 1000;
                System.out.println("算法运行时间： " + final_runtime + "微秒");
            }
            else if ((endTime - startTime) >= 1000000 && (endTime - startTime) < 10000000000L)
            {
                double final_runtime;
                final_runtime = (endTime - startTime) / 1000;
                final_runtime = final_runtime / 1000;
                System.out.println("算法运行时间： " + final_runtime + "毫秒");
            }
            else
            {
                double final_runtime;
                final_runtime = (endTime - startTime) / 10000;
                final_runtime = final_runtime / 100000;
                System.out.println("算法运行时间： " + final_runtime + "秒");
            }
            Runtime r = Runtime.getRuntime();
            float memory;
            memory = r.totalMemory();
            memory = memory / 1024 / 1024;
            System.out.printf("JVM总内存：%.3fMB\n", memory);
            memory = r.freeMemory();
            memory = memory / 1024 / 1024;
            System.out.printf(" 空闲内存：%.3fMB\n", memory);
            memory = r.totalMemory() - r.freeMemory();
            memory = memory / 1024 / 1024;
            System.out.printf("已使用的内存：%.4fMB\n", memory);
            //------------------------------------------------------
        }
        else if (ch == '2')
        {
            File file = new File("out\\Configuration.ini");
            if (!file.exists())
            {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("在out目录下未找到Configuration.ini文件！！！");
                System.exit(1);
            }
            //------------------------------------------------------
            long startTime = System.nanoTime();   //获取开始时间
            //------------------------------------------------------
            //读配置文件
            Configuration config = Configuration.read();
            System.out.println("文件名：" + config.getName());
            System.out.println("文件大小：" + config.getLength());
            System.out.println("文件数量：" + config.getCount());
            //合成
            fileMerging(config);
            System.out.println("完成");
            System.out.println();
            //------------------------------------------------------
            long endTime = System.nanoTime(); //获取结束时间
            if ((endTime - startTime) < 1000000)
            {
                double final_runtime;
                final_runtime = (endTime - startTime);
                final_runtime = final_runtime / 1000;
                System.out.println("算法运行时间： " + final_runtime + "微秒");
            }
            else if ((endTime - startTime) >= 1000000 && (endTime - startTime) < 10000000000L)
            {
                double final_runtime;
                final_runtime = (endTime - startTime) / 1000;
                final_runtime = final_runtime / 1000;
                System.out.println("算法运行时间： " + final_runtime + "毫秒");
            }
            else
            {
                double final_runtime;
                final_runtime = (endTime - startTime) / 10000;
                final_runtime = final_runtime / 100000;
                System.out.println("算法运行时间： " + final_runtime + "秒");
            }
            Runtime r = Runtime.getRuntime();
            float memory;
            memory = r.totalMemory();
            memory = memory / 1024 / 1024;
            System.out.printf("JVM总内存：%.3fMB\n", memory);
            memory = r.freeMemory();
            memory = memory / 1024 / 1024;
            System.out.printf(" 空闲内存：%.3fMB\n", memory);
            memory = r.totalMemory() - r.freeMemory();
            memory = memory / 1024 / 1024;
            System.out.printf("已使用的内存：%.4fMB\n", memory);
            //------------------------------------------------------

        }
        else
        {
            Toolkit.getDefaultToolkit().beep();
            System.out.println("输入错误！！！退出！");
        }
    }
}
