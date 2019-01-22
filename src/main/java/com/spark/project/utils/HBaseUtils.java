package com.spark.project.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * HBase操作工具类:Java工具类建议采用单例模式封装
 */
public class HBaseUtils {

    HBaseAdmin admin = null;
    Configuration configurtion = null;
    /**
     * 私有构造方法
     */
    private HBaseUtils() throws IOException {
        configurtion = new Configuration();
        configurtion.set("hbase.zookeeper.quorum","192.168.1.145:2181");
        configurtion.set("hbase.rootdir","hdfs://192.168.1.145:8020/hbase");

        admin = new HBaseAdmin(configurtion);
    }
    private static HBaseUtils instance = null;
    public static HBaseUtils getInstance() throws IOException {
        if(null == instance){
            instance = new HBaseUtils();
        }
        return instance;
    }

    /**
     * 根据表名获取到HTable实例
     * @param tableName
     * @return
     */
    public HTable getTable(String tableName) throws IOException {
        HTable table = null;
        table = new HTable(configurtion,tableName);
        return table;
    }

    /**
     * 添加一条记录到HBase表
     * @param tableName HBase表名
     * @param rowkey HBase表的rowkey
     * @param cf  HBase的columnfamily
     * @param column HBase表的列
     * @param value 写入Hbase表的值
     * @return
     */
    public Boolean put(String tableName, String rowkey ,String cf ,String column,String value) throws IOException {
        HTable table = getTable(tableName);
        Put put = new Put(Bytes.toBytes(rowkey));
        put.add(Bytes.toBytes(cf),Bytes.toBytes(column),Bytes.toBytes(value));
        table.put(put);
        return true;
    }
    public static void main(String[] args) throws IOException {
//        HTable wzl_course_clickcount = HBaseUtils.getInstance().getTable("wzl_course_clickcount");
//        System.out.println(wzl_course_clickcount.getName().getNameAsString());
        String tableName = "wzl_course_clickcount";
        String rowkey = "20190121_1";
        String cf = "info";
        String column = "click_count";
        String value = "2";
        Boolean put = HBaseUtils.getInstance().put(tableName, rowkey, cf, column, value);
        System.out.println(put);
    }
}
