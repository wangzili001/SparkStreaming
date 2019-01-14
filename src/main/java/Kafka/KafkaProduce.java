package Kafka;

import kafka.producer.KeyedMessage;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;


import java.util.Properties;
import java.util.Random;

public class KafkaProduce extends Thread {
    private String topic;
    private Producer<Integer,String> producer;

    public KafkaProduce(String topic){
        this.topic = topic;

        Properties properties = new Properties();

        properties.put("metadata.broker.list",KafkaProperties.BROKER_LIST);
        properties.put("serializer.class","kafka.serializer.StringEncoder");
        properties.put("request.required.acks","1");

        producer = new Producer<Integer, String>(new ProducerConfig(properties));
    }

    @Override
    public void run() {
        while (true){
            Random random = new Random();
            int i = random.nextInt(64)+30;
            String itemID = getItemID(i);
            String message = "message_"+itemID;
            producer.send(new KeyedMessage<Integer,String>(topic,message));
            System.out.println("Sent:   "+message);
            try{
                Thread.sleep(2000);
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }

    private static String getItemID( int n )
    {
        String val = "";
        Random random = new Random();
        for ( int i = 0; i < n; i++ )
        {
            String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
            if ( "char".equalsIgnoreCase( str ) )
            { // 产生字母
                int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                val += (char) ( nextInt + random.nextInt( 26 ) );
            }
            else if ( "num".equalsIgnoreCase( str ) )
            { // 产生数字
                val += String.valueOf( random.nextInt( 10 ) );
            }
        }
        return val;
    }
}
