package Kafka;

import junit.framework.TestCase;

public class KafkaProduceTest extends TestCase {
    public static void main(String[] args) {
        new KafkaProduce(KafkaProperties.TOPIC).start();
    }
}