import OrderRepository from "../repository/OrderRepository.js";
import { sendMessageToProductStockUpdateQueue } from "../../product/rabbitmq/productStockUpdate.js";
import { PENDING } from "../status/OrderStatus.js";
import OrderException from "../exception/OrderException.js";
import { INTERNAL_SERVER_ERROR, BAD_REQUEST, SUCCESS } from "../../../config/constants/httpStatus.js";
import ProductClient from "../../product/client/ProductClient.js";

class OrderService {
    async createOrder(req) {
        try {
            let orderData = req.body;
            this.validateOrderData(orderData);

            const { authUser } = req;
            const { authorization } = req.headers;

            let order = this.createInitialOrderData(orderData, authUser);
            await this.validateProductStock(order, authorization);

            let createdOrder = await OrderRepository.save(order);
            this.sendMessage(createdOrder);

            return {
                status: SUCCESS,
                createdOrder,
            };
        } catch (err) {
            console.error(`Error creating order: ${err.message}`);
            return {
                status: err.status || INTERNAL_SERVER_ERROR,
                message: err.message || "An error occurred while creating the order.",
            };
        }
    }

    async findById(req) {
        const { id } = req.params;
        this.validateInformedId(id);

        try {
            const existingOrder = await OrderRepository.findById(id);
            if (!existingOrder) {
                throw new OrderException(BAD_REQUEST, "The order was not found.");
            }
            return {
                status: SUCCESS,
                order: existingOrder,
            };
        } catch (err) {
            console.error(`Error finding order by ID: ${err.message}`);
            return {
                status: err.status || INTERNAL_SERVER_ERROR,
                message: err.message || "An error occurred while retrieving the order.",
            };
        }
    }

    async findAll(req) {
      try {
        const { serviceid } = req.headers;
        console.info(
          `Request to GET all orders | serviceID: ${serviceid}]`
        );
        const orders = await OrderRepository.findAll();
        if (!orders) {
          throw new OrderException(BAD_REQUEST, "No orders were found.");
        }
        let response = {
          status: SUCCESS,
          orders,
        };
        console.info(
          `Response to GET all orders: ${JSON.stringify(
            response
          )} | serviceID: ${serviceid}]`
        );
        return response;
      } catch (err) {
        return {
          status: err.status ? err.status : INTERNAL_SERVER_ERROR,
          message: err.message,
        };
      }
    }
  
    
  async findbyProductId(req) {
    try {
      const { productId } = req.params;
      const { serviceid } = req.headers;
      console.info(
        `Request to GET orders by productID ${productId} | serviceID: ${serviceid}]`
      );
      this.validateInformedProductId(productId);
      const orders = await OrderRepository.findByProductId(productId);
      if (!orders) {
        throw new OrderException(BAD_REQUEST, "No orders were found.");
      }
      let response = {
        status: SUCCESS,
        salesIds: orders.map((order) => {
          return order.id;
        }),
      };
      console.info(
        `Response to GET orders by productID ${productId}: ${JSON.stringify(
          response
        )} | serviceID: ${serviceid}]`
      );
      return response;
    } catch (err) {
      return {
        status: err.status ? err.status : INTERNAL_SERVER_ERROR,
        message: err.message,
      };
    }
  }

    sendMessage(createdOrder) {
        const message = {
            salesId: createdOrder.id,
            products: createdOrder.products,
        };
        sendMessageToProductStockUpdateQueue(message);
        console.info("Message sent to update product stock:", message);
    }

    createInitialOrderData(orderData, authUser) {
        return {
            status: PENDING,
            user: authUser,
            createdAt: new Date(),
            updatedAt: new Date(),
            products: orderData.products,
        };
    }

    validateInformedId(id) {
        if (!id) {
            throw new OrderException(BAD_REQUEST, "The order ID must be informed.");
        }
    }

    async updateOrder(orderMessage) {
        try {
            const order = JSON.parse(orderMessage);
            if (order.salesId && order.status) {
                let existingOrder = await OrderRepository.findById(order.salesId);
                if (existingOrder && order.status !== existingOrder.status) {
                    existingOrder.status = order.status;
                    existingOrder.updatedAt = new Date();
                    await OrderRepository.save(existingOrder);
                }
            } else {
                console.warn(`The order message was not complete: ${orderMessage}`);
            }
        } catch (err) {
            console.error("Error updating order:", err.message);
        }
    }

    validateOrderData(data) {
        if (!data || !data.products || data.products.length === 0) {
            throw new OrderException(BAD_REQUEST, "The products must be informed.");
        }
    }

    async validateProductStock(order, token) {
        const stockIsOk = await ProductClient.checkProductStock(order, token);
        if (!stockIsOk) {
            throw new OrderException(BAD_REQUEST, "The stock is out for the products.");
        }
    }

    validateInformedProductId(id) {
      if (!id) {
        throw new OrderException(
          BAD_REQUEST,
          "The order's productId must be informed."
        );
      }
    }
}

export default new OrderService();
