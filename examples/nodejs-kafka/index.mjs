import { Kafka } from 'kafkajs';

const bootstrap = process.env.KAFKA_BOOTSTRAP_SERVERS || 'localhost:9092';
const topic = process.env.TOPIC || 'devportal.example';

const kafka = new Kafka({ clientId: 'node-example', brokers: [bootstrap] });
const producer = kafka.producer();
const consumer = kafka.consumer({ groupId: 'node-example-group' });

await producer.connect();
await consumer.connect();
await consumer.subscribe({ topic, fromBeginning: true });

consumer.run({
  eachMessage: async ({ message }) => {
    console.log('Consumed:', message.value?.toString());
  }
});

setInterval(async () => {
  const value = `hello from node at ${new Date().toISOString()}`;
  await producer.send({ topic, messages: [{ value }] });
  console.log('Produced:', value);
}, 3000);
