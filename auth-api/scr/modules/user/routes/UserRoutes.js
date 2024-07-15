import { Router } from "express";
import UserController from "../controller/UserController.js";

const router = new Router();

router.post('/api/user/email/:email', UserController.findByEmail);
router.get('/api/user/auth', UserController.getAccessToken);

export default router;
