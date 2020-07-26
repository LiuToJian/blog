package com.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.PrintConversionEvent;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void TestCel() {
        int i, k;
        i = 3;
        k = i++ + i++;
        System.out.println(k);
    }

    @Test
    void findNumber() {
        String[] array = {"1", "5", "33", "7", "8", "27", "5", "90", "2", "6", "25"};
        int result=convert(array);
    }
    /**
     * 将string类型数组转换成int类型并找出最大值和最小值
     * @param array string类型数组
     * @return 返回最大值和最小值的乘积
     */
    private int convert(String[] array){
    int [] result=new int[array.length];
    for(int i=0;i<array.length;i++){
        result[i]=Integer.parseInt(array[i]);
    }
    int max=result[0];
    int min=result[0];
    for(int j=1;j<result.length;j++){
        if(result[j]>max){
            max=result[j];
        }
        if(result[j]<min){
            min=result[j];
        }
    }
   return max*min;
}
    @Test
    void contextLoads() {
        Integer f1 = 100, f2 = 100, f3 = 200, f4 = 200;
        System.out.println(f1 == f2);
        System.out.println(f3 == f4);
    }

    @Test
    void testRunnable() {
        Object o1 = new Object();
        Object o2 = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o1) {
                    System.out.println("拿到了1资源");
                    System.out.println("想要2资源");
                    synchronized (o2) {
                        System.out.println("拿到了2资源");
                    }
                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o2) {
                    System.out.println("拿到了2资源");
                    System.out.println("想要1资源");
                    synchronized (o1) {
                        System.out.println("拿到了1资源");
                    }
                }

            }
        }).start();

    }

}
