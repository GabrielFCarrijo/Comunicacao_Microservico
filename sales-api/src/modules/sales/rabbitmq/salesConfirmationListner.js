import amqp from "amqplib/callback_api.js";
import { RABBIT_MQ_URL } from "../../../config/secrets/secrets.js";
import { SALES_CONFIRMATION_QUEUE } from "../../../config/rabbitmq/queue.js";
import OrderService from "../service/OrderService.js"; // Adjust this path as necessary

export function listenToSalesConfirmationQueue() {
    amqp.connect(RABBIT_MQ_URL, (error, connection) => {
        if (error) {
            throw error;
        }
        console.info("Listening to Sales Confirmation Queue...");
        connection.createChannel((error, channel) => {
            if (error) {
                throw error;
            }
            channel.consume(
                SALES_CONFIRMATION_QUEUE,
                (message) => {
                    console.info(
                        `Receiving message from queue: ${message.content.toString()}`
                    );
                    OrderService.updateOrder(message.content.toString());
                },
                {
                    noAck: true,
                }
            );
        });
    });
}
