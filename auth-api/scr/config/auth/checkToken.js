import jwt from "jsonwebtoken";
import {promisify} from "util";

import AuthException from "./AuthException.js";
import * as secrets from "../../config/constants/secrets.js"
import * as httpStatus from "../../config/constants/HttpStatus.js"

const bearer = "bearer";

export default async (req, res, next) => {
    try {
        const { authorization } = req.headers;

        if (!authorization) {
            throw new AuthException(
                httpStatus.UNAUTHORIZED,
                "Access token was not informed"
            );
        }
        const access = authorization;
        if (access.toLowerCase().includes(bearer)) {
            access = access.replace(bearer, "")
        }
        const decoded = await promisify(jwt.verify)(
            access,
            secrets.API_SECRET
        );
        req.authUser = decoded.authUser;
        return next();
    } catch (error) {
        return res.status(error.status).json({
            status: error.status ? error.status : HttpStatus.INTERNAL_SERVER_ERROR,
            message: error.message,
        });
    }
    
}