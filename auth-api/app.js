import express from "express";
import { createInicialData } from "./scr/config/initialData.js";
import UserRoutes from "./scr/modules/user/routes/UserRoutes.js";
import checkToken from "./scr/config/auth/checkToken.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

createInicialData();

app.use(express.json());
app.use(UserRoutes);
app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: 'Auth-API',
        status: 'up',
        httpStatus: 200,
    });
});

app.use(checkToken);


app.listen(PORT, () => {
    console.info(`Server started successfully at port ${PORT}`);
});
