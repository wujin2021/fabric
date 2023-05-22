package com.example.fabricfabcar.coderUtils;

import java.math.BigInteger;
import java.util.Random;
/*


 */

public class DGHV {
    /**
     * p是私钥，是一个大奇数
     * r,q都是正随机数, 长度分别为a、b，
     * 其中的要求是q > p,且q是公开的 , r是一个随机小整数（可为负数）
     * Encrypto(pk,m)=m+2r+pq
     * Decrypto(sk,c)=(c mod p) mod 2
     */
    private BigInteger p,q,r;
    private int a;
    private int b=2;


    private void  KeyGenerator(int bitLength,int certainty) {
        //certainty 代表是素数的概率，越大是素数的概率越小  1-1/2^certainty
        // bitLength 代表bit长度
        // 比如本例输出为23,二进制表示为11101
        //5位最大表示为25，所以最大为25
       a = bitLength *2;
       p = new BigInteger(bitLength,certainty,new Random());
       if(p.mod(new BigInteger("2")).intValue()==0) {
           p=p.add(new BigInteger("1"));}
       q = new BigInteger(bitLength*2,certainty,new Random());
       while (q.subtract(p).longValue() < 0) {
           q = q.add(p);
       }
       System.out.println("q="+q);
       System.out.println("p="+p);
       System.out.println("r="+r);
       Random random = new Random();
       random.setSeed(System.currentTimeMillis());
       int i = random.nextInt(98)+1;
       //r属于1到99
        String s =Integer.toString(i);
        r= new BigInteger(s);
        System.out.println("q="+q);
        System.out.println("p="+p);
        System.out.println("r="+r);
    }
    /*
    *  Encrypto(pk,m)=m+2r+pk*q
     */
    private BigInteger encrypto(BigInteger pk,BigInteger m) {
        return m.add(r.add(r)).add(p.multiply(q));
    }

    /*
    * Decrypto(sk,c)=(c mod sk) mod 2
     */
    private BigInteger decrypt(BigInteger sk,BigInteger c) {
        return c.mod(p).mod(new BigInteger("2"));
    }
    public static void main(String[] args){
      //
        DGHV dghv=new DGHV();
        dghv.KeyGenerator(128,64);
        String m1 =  "0";
        String m2 =  "1";
        BigInteger c1 = dghv.encrypto(null,new BigInteger(m1));
        System.out.println("m1密文："+c1);
        BigInteger c2 = dghv.encrypto(null,new BigInteger(m2));
        System.out.println("m2密文："+c2);
        BigInteger cm1 = dghv.decrypt(null,c1);
        System.out.println("c1解密："+cm1);
        BigInteger cm2 = dghv.decrypt(null,c2);
        System.out.println("c2解密："+cm2);

    }
}
