package services;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSMessaging {

    private  String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // default broker URL is : tcp://localhost:61616"

    private  String subject = "LOGINQ"; //Queue Name
    //private  String subject = "ActiveMQ.Advisory.Consumer.Queue.jms.topic.LOGINQ"; //Queue Name

    public boolean SendMessage(String msg) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection("server", "password");
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(subject);
            System.out.println(topic);
            MessageConsumer consumer = session.createConsumer(topic);

            // Publish
            String payload = msg;
            Message message = session.createTextMessage(payload);
            MessageProducer producer = session.createProducer(topic);
            System.out.println("Sending text '" + payload + "'");

            producer.send(message);
            connection.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String RecieveMessage() {
        try {

            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic(subject);

            // Consumer1 subscribes to customerTopic
            MessageConsumer consumer = session.createConsumer(topic);

            Message message = consumer.receive();
            String msg = "";
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                msg = textMessage.getText();
                System.out.println("Received message '" + textMessage.getText() + "'");
            }
            connection.close();
            return msg;
        } catch (Exception ex) {
            return "";
        }
    }
}