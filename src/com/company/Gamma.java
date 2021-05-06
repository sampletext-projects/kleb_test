package com.company;

public class Gamma {

    public static void main(String[] args) {
        String source = "Hello world";

        System.out.println(getBits(source));

        byte[] keyBytes = "my ultimate key".getBytes();

        byte[] sourceBytes = source.getBytes();

        byte[] serializedBytes = encode(sourceBytes, keyBytes);

        String serializedString = new String(serializedBytes);

        System.out.println(serializedString);

        byte[] deserializedBytes = encode(serializedBytes, keyBytes);

        String deserializedString = new String(deserializedBytes);

        System.out.println(deserializedString);
    }


    public static String getBits(String src) {

        StringBuilder result = new StringBuilder();

        byte[] bytes = src.getBytes();

        for (int i = 0; i < bytes.length; i++) {
            for (int j = 7; j >= 0; j--) {
                result.append((bytes[i] >> j & 0x1));
            }
            result.append("\n");
        }

        return result.toString();
    }

    public static byte XOR(byte b1, byte b2) {
        return (byte) (b1 ^ b2);
    }

    public static byte[] encode(byte[] src, byte[] key) {
        byte[] dst = new byte[src.length];

        for (int i = 0, k = 0; i < src.length; i++, k = ++k == key.length ? 0 : k) {
            dst[i] = XOR(src[i], key[k]);
        }

        return dst;
    }
}
