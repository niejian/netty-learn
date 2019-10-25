package cn.com.code4fun.netty;

/**
 * @desc: cn.com.code4fun.netty.NettyTest
 * @author: niejian9001@163.com
 * @date: 2019-10-23 10:54
 */
public class NettyTest {

    public static void main(String[] args) {
        int port = 9001;
        ArithmeticException exception = null;
//        NettyServer.start(port);

//        NettyClient.start(port);
        new ChatNettyClient("localhost", 9002).run();

//        for (int i = 0; i < 10; i++) {
//            try {
//                if (i == 5) {
//                    i = i / 0;
//                }
//                System.out.println(i);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                exception = new ArithmeticException();
//            }finally {
//
//            }
//
//            if (null != exception) {
//
//                throw exception;
//
//            }
//
//
//
//        }


    }
}
