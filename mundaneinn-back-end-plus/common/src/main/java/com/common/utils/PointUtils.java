package com.common.utils;

/**
 * @author kcx
 * @description: 将mysql point类型数据转为double[]
 * @date 2022/4/29 21:35
 */
public class PointUtils {
    /**
     * mysql point的字节转java double点坐标
     * @param arr
     * @return
     */
    public static double[] bytesToPoints(byte[] arr){
        if(arr==null){
            return null;
        }
        if(arr.length==25){
            return bytesToOnePoint(arr);
        }
        return bytesToMutiPoints(arr);
    }

    /**
     * 转为一个点坐标double[2],PointLng=double[0],PointLat=double[1]
     * @param arr
     * @return
     */
    private static double[] bytesToOnePoint(byte[] arr){
        return new double[]{bytes2Double(arr,9),bytes2Double(arr,17)};
    }

    /**
     * 转为多个点坐标
     * @param arr
     * @return
     */
    private static double[] bytesToMutiPoints(byte[] arr){
        int len=(arr.length-13)/8;
        double[] result=new double[len];
        for(int i=0;i<result.length;i++){
            result[i]=bytes2Double(arr,13+i*8);
        }
        return result;
    }

    /**
     * Bytes转Double
     * @param arr
     * @param start
     * @return
     */
    private static double bytes2Double(byte[] arr,int start) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[start+i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }

    /**
     * double点转 mysql point字节坐标
     * @param lng
     * @param lat
     * @return
     */
    public static byte[] convert2Point(double lng,double lat){
        byte[] bytelat=double2Bytes(lat);
        byte[] bytelng=double2Bytes(lng);
        byte[] bpoint=new byte[25];
        bpoint[4]=0x01;
        bpoint[5]=0x01;
        for(int i=0;i<8;++i){
            bpoint[9+i]=bytelng[i];
            bpoint[17+i]=bytelat[i];
        }
        return bpoint;
    }

    /**
     * double转Bytes
     * @param d
     * @return
     */
    public static byte[] double2Bytes(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;
    }
}
