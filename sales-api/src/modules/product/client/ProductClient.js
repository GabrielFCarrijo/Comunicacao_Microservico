import axios from "axios";
import { PRODUCT_API_URL } from "../../../config/secrets/secrets.js";

class ProductClient {
    async checkProductStock(productsData, token) { // Corrected method name
        try {
            const headers = {
                Authorization: token, // Corrected header for authorization
            };
            console.info(`Sending request to Product API with data: ${JSON.stringify(productsData)}`);
            
            const response = await axios.post(
                `${PRODUCT_API_URL}/check-stock`,  // Corrected endpoint
                productsData,  // Pass the products data as the body of the request
                { headers }    // Pass headers as the third parameter
            );
            
            console.info(`Success response from Product-API. Response: ${response.data}`);
            return true; // Stock is okay

        } catch (err) {
            console.error(`Error response from Product-API. Message: ${err.message}`);
            return false; // Stock check failed
        }
    }
}

export default new ProductClient();