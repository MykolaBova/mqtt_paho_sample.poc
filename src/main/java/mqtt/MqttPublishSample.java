package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import java.text.SimpleDateFormat;

public class MqttPublishSample {

	public static final String TOPIC_BOX_TO_DISPLAY_EVENT = "profidriver_144_omboxletofout_event";
	public static final String TOPIC_BOX_TO_DISPLAY_OVERVIEW = "profidriver_144_omboxletofout_overview";
	public static final String TOPIC_BOX_TO_DISPLAY_CONFIG = "profidriver_144_omboxletofout_config";

	public static void main(String[] args) {


		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		String format = simpleDateFormat.format(System.currentTimeMillis());

		String topicName = "/journey/b";
//		String topicName = TOPIC_BOX_TO_DISPLAY_EVENT;

//		String broker = "tcp://iot.eclipse.org:1883";
		String broker = "tcp://localhost:1883";

		String content = "Message from MqttPublichSample " + format;
		int qos = 2;
		String clientId = "JavaSample";

		try {
			MqttClient sampleClient = new MqttClient(broker, clientId);
			MqttConnectOptions connectOptions = new MqttConnectOptions();
			connectOptions.setCleanSession(true);

			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect();
			System.out.println("Connected");

			System.out.println("===> Publishing message: <" + content + ">");
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			message.setRetained(true);

			MqttTopic topic = sampleClient.getTopic(topicName);
			topic.publish(message);

			System.out.println("Message published");

			sampleClient.disconnect();
			System.out.println("Disconnected");
			System.exit(0);
		} catch (MqttException me) {
			System.out.println("MqttException " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}
}