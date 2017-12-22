package mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class MqttSubsribeSample {
//    private static final String BROKER = "tcp://iot.eclipse.org:1883";
    private static final String BROKER = "tcp://localhost:1883";

//    private static final String BROKER = "tcp://0.0.0.0:1883";
    private static final String TOPIC = "/journey/b";

    private static final String CLIENT_ID = "clientIdGoesHere";
    private static final int QOS = 2;

    public static void main(String[] args) {
        try {
            MqttClient client = new MqttClient(BROKER, CLIENT_ID);

            MqttCallback callback = new MqttCallback() {
                public void connectionLost(Throwable cause) {
                    System.out.println("----> connectionLost");
                }

                public void messageArrived(MqttTopic mqttTopic, MqttMessage mqttMessage) throws Exception {
                    System.out.println("----> messageArrived");

                    System.out.println("===> Topic " + mqttTopic.getName());
                    System.out.println("===> Message <" + mqttMessage.toString() + ">");
                }

                public void deliveryComplete(MqttDeliveryToken token) {
                    // Not needed as we are only receiving MQTT messages.
                    System.out.println("----> deliveryComplete");
                }
            };

            client.setCallback(callback);

            MqttConnectOptions connectOptions = new MqttConnectOptions();
            System.out.println("Connect to broker " + BROKER);
            client.connect(connectOptions);

            System.out.println("Subscribe to topic " + TOPIC);
            client.subscribe(TOPIC, QOS);

            Thread.sleep(5000);

            System.out.println("Disconnect");
            client.disconnect();
            System.exit(0);
        } catch (MqttException me) {
            System.out.println("MqttException Reason " + me.getReasonCode());
            System.out.println("Message " + me.getMessage());
            System.out.println("LocalizedMessage " + me.getLocalizedMessage());
            System.out.println("Cause " + me.getCause());
            System.out.println("Exception " + me);
            me.printStackTrace();
        } catch (InterruptedException ie) {
            System.out.println("InterruptedException " + ie);
            ie.printStackTrace();
        }
    }
}