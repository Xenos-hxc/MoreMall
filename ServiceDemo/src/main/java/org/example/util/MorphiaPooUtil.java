package org.example.util;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.InputStream;
import java.util.Properties;

public class MorphiaPooUtil {
    private static final Properties props = new Properties();
    private static final MongoClient client;
    static {
        InputStream is = null;
        try{
            is=MorphiaPooUtil.class.getResourceAsStream("/mongo.properties");
            props.load(is);
            //在连接池中获得一个客户端
            //从创建一个mongo客户端参数的拼接器
            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
            //设置客户端的最大连接数
            builder.connectionsPerHost(Integer.parseInt(props.getProperty("connectionsPerHost")));
            //如果连接全部占用，新线程需要等待的最长时间
            builder.maxConnectionLifeTime(Integer.parseInt(props.getProperty("maxWaitTime")));
            //设置每个连接 连接mongo服务器的最大延迟时间
            builder.connectTimeout(Integer.parseInt(props.getProperty("connectTimeout")));
            //使用构造器获得一个mongo的客户端配置参数对象
            MongoClientOptions options = builder.build();
            client = new MongoClient(new ServerAddress(env("MONGO_HOST", "ip"),Integer.parseInt(env("MONGO_PORT", "port"))), options);
        }catch (Exception e){
            throw new RuntimeException("初始化mongo客户端配置文件异常",e);
        }
    }
    private static String env(String name, String property) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? props.getProperty(property) : value;
    }
    //获取客户端 client
    public static MongoClient getClient(){
        return client;
    }

    //获取database
    public static Datastore getDb(){
        MongoClient client = getClient();
        Datastore db = new Morphia().createDatastore(client, env("MONGO_DATABASE", "database"));
        return db;
    }
}
