const env = process.env;

export const MONGO_DB_URL = 
"mongodb+srv://sales-db:sales-db@sales-db.ip9cj.mongodb.net/?retryWrites=true&w=majority&appName=sales-db";

export const API_SECRET = env.API_SECRET 
? env.API_SECRET
 : "YXV0aC1hcGktc2VjcmV0LWRldi0xMjM0NTY=";

export const RABBIT_MQ_URL = env.RABBIT_MQ_URL 
? env.RABBIT_MQ_URL
: "amqps://whecvpal:KMiYc0pxjRT21cyF6wTF1pnrQakTauhv@cow.rmq2.cloudamqp.com/whecvpal";

export const PRODUCT_API_URL = env.PRODUCT_API_URL ? env.PRODUCT_API_URL : 'http://localhost:8081/api/product'