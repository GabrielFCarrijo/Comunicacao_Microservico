import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

import UserRepository from "../repository/UserRepository.js";
import * as httpStatus  from "../../../config/constants/httpStatus.js";
import UserException from "../exception/UserException.js";
import * as secrets from "../../../config/constants/secrets.js";

class UserService {

    async findByEmail(req) {
        try {
            const { email } = req.params;
            const { authUser } = req;
            this.validarDadosRequisicao(email);
            let user = await UserRepository.findByEmail(email); 
            this.validateUserNotFound(user);
            this.validadeAuthenticatedUser(user, authUser);
            if (!user) {
                throw new UserException(httpStatus .NOT_FOUND, "User not found.");
            }
            return {
                status: httpStatus .SUCCESS,
                user: {
                    id: user.id,
                    name: user.name,
                    email: user.email,
                },
            };
        } catch (error) {
            return {
                status: error.status ? error.status : httpStatus .INTERNAL_SERVER_ERROR,
                message: error.message,
            };
        }
    }

    async getAccessToken(req) {
        try {
            const { transactionid, serviceid } = req.headers;
            console.info(
              `Request to POST login with data ${JSON.stringify(
                req.body
              )} | [transactionID: ${transactionid} | serviceID: ${serviceid}]`
            );
            const { email, password } = req.body;
            this.validateAccessTokenData(email, password);
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);
            await this.validadePassword(password, user.password);
            const authUser = { id: user.id, name: user.name, email: user.email };
            const accessToken = jwt.sign({ authUser }, secrets.API_SECRET, { expiresIn: "1d" });
            console.info(
                `Response to POST login with data ${JSON.stringify(
                  response
                )} | [transactionID: ${transactionid} | serviceID: ${serviceid}]`
              );
            return {
                status: httpStatus .SUCCESS,
                accessToken,
            };
        } catch (error) {
            return {
                status: error.status ? error.status : httpStatus .INTERNAL_SERVER_ERROR,
                message: error.message,
            };
        }
    }

    validateUserNotFound(user) {
        if (!user) {
            throw new UserException(httpStatus .BAD_REQUEST, "User was not found");
        }
    }

    validateAccessTokenData(email, password) {
        if (!email || !password) {
            throw new UserException(
                httpStatus .UNAUTHORIZED,
                "Email and password must be informed"
            );
        }
    }

    validarDadosRequisicao(email) {
        if (!email) {
            throw new UserException(httpStatus .BAD_REQUEST, "User email was not informed.");
        }
    }

    validadeAuthenticatedUser(user, authUser) {
        if (!authUser || user.id != authUser.id) {
          throw new UserException(httpStatus .FORBIDDEN, "You cannot see this user data")  
        }
    }

    async validadePassword(password, hashPassword) {
        if (!await bcrypt.compare(password, hashPassword)) {
            throw new UserException(httpStatus .UNAUTHORIZED, "Password doesn't valid!");
        }
    }
    
}

export default new UserService();
