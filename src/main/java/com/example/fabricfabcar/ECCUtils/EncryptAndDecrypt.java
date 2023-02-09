package com.example.fabricfabcar.ECCUtils;

import org.ethereum.crypto.ECIESCoder;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptAndDecrypt {

    //对数据进行非对称加密
    public static String encrypt(String source, String publicKey) {
        try {
            //加密前先把原始数据 转为bytes
            byte[] preEncryBytes = source.getBytes(StandardCharsets.UTF_8);
            //将 publicKey 转为 ECKey 类型的 pub
            ECKey pub = ECKey.fromPublicOnly(Hex.decode(publicKey));
            System.out.println("加密前字符串： " + new String(preEncryBytes));

            //加密后 bytes cipherText
            byte[] cipherText = ECIESCoder.encryptSimple(pub.getPubKeyPoint(), preEncryBytes);
            System.out.println("加密之后字符串： " + Hex.toHexString(cipherText));


            //将 加密后cipherText 进行 base64 编码
            byte[] encoded = Base64.getEncoder().encode(cipherText);
            //编码后转String
            System.out.println(" 加密之后 encrypt base64 后转string: " + new String(encoded));
            String encryptStr = new String(encoded);
            return encryptStr;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //非对称加密后数据解密
    public static String decrypt(String source, String privateKey) {
        try {
            // 加密数据先转为 bytes， 再base64 decode
            byte[] preDecryptBytes = Base64.getDecoder().decode(source.getBytes(StandardCharsets.UTF_8));
            System.out.println("解密前字符串： " +  Hex.toHexString(preDecryptBytes));
            //私钥转换为ECkey 类型
            ECKey priv = ECKey.fromPrivate(Hex.decode(privateKey));
            //解密
            byte[] decryptResult = ECIESCoder.decryptSimple(priv.getPrivKey(), preDecryptBytes);
            String deStr = new String(decryptResult);
            System.out.println("解密后字符串 " + deStr);
            return deStr;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String getPubkeyHexString(String privkeystr){
        BigInteger privkey = new BigInteger(privkeystr,16);
        ECKey ecKey = ECKey.fromPrivate(privkey);
        System.out.println("公钥为："+Hex.toHexString(ecKey.getPubKey()));
        return Hex.toHexString(ecKey.getPubKey());
    }

    public static String getPrivkeyHexString(String privkeystr){
        BigInteger privkey = new BigInteger(privkeystr,16);
        ECKey ecKey = ECKey.fromPrivate(privkey);
        System.out.println("私钥为："+Hex.toHexString(ecKey.getPrivKeyBytes()));
        return Hex.toHexString(ecKey.getPrivKeyBytes());
    }

    public static String add(String plaintext1,String plaintext2){
        byte[] t1 = Base64.getDecoder().decode(plaintext1.getBytes(StandardCharsets.UTF_8));
        byte[] t2 = Base64.getDecoder().decode(plaintext2.getBytes(StandardCharsets.UTF_8));
        System.out.println("t1:"+Hex.toHexString(t1));
        System.out.println("t2:"+Hex.toHexString(t2));
        BigInteger num1 = new BigInteger(Hex.toHexString(t1),16);
        BigInteger num2 = new BigInteger(Hex.toHexString(t2),16);
        System.out.println("num1:"+num1);
        System.out.println("num2:"+num2);
        System.out.println("num1+num2:"+num1.add(num2));
        String result = Base64.getEncoder().encodeToString(num1.add(num2).toString().getBytes(StandardCharsets.UTF_8));
        return result;
    }

    public static String encode(String text){
        String prikeystr = "111111111111111111111111111111111";
        String privkey = getPrivkeyHexString(prikeystr);
        String pubkey = getPubkeyHexString(prikeystr);
        String result= encrypt(text,pubkey);
        return result;
    }

    public static void main(String[] args){

        String prikeystr = "111111111111111111111111111111111";
        String text1 = "1";
        String text2 = "2";


        String privkey = getPrivkeyHexString(prikeystr);
        String pubkey = getPubkeyHexString(prikeystr);
        EncryptAndDecrypt encryptAndDecrypt = new EncryptAndDecrypt();
    //    String t1 = encryptAndDecrypt.encrypt(text1, pubkey);
    //    String t2 = encryptAndDecrypt.encrypt(text2, pubkey);
       // String result = add(t1,t2);
       // System.out.println(result);
        String plaintext3 = encryptAndDecrypt.encrypt("yuxiujieasdgdgdf,sgd 我 你 @", pubkey);
        encryptAndDecrypt.decrypt(plaintext3,privkey);
       // String plain3 = encryptAndDecrypt.decrypt(result,privkey);





    }

    public void test(){
        BigInteger privKey = new BigInteger("5e173f6ac3c669587538e7727cf19b782a4f2fda07c1eaa662c593e5e85e3051", 16);
        byte[] payload = Hex.decode("1122334455");
        ECKey ecKey =  ECKey.fromPrivate(privKey);
        ECPoint pubKeyPoint = ecKey.getPubKeyPoint();
        byte[] cipher = new byte[0];
        try {
            cipher = ECIESCoder.encrypt(pubKeyPoint, payload);
        } catch (Throwable e) {e.printStackTrace();}
        System.out.println(Hex.toHexString(cipher));
        byte[] decrypted_payload = new byte[0];
        try {
            decrypted_payload = ECIESCoder.decrypt(privKey, cipher);
        } catch (Throwable e) {e.printStackTrace();}
        System.out.println(Hex.toHexString(decrypted_payload));
    }

}
