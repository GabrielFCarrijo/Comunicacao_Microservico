import express from "express";
import { createInicialData } from "./scr/config/initialData.js";
import UserRoutes from "./scr/modules/user/routes/UserRoutes.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

createInicialData();

app.use(express.json());  // Corrigido aqui
app.use(UserRoutes);

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: 'Auth-API',
        status: 'up',
        httpStatus: 200,
    });
});

app.listen(PORT, () => {
    console.info(`Server started successfully at port ${PORT}`);
});
