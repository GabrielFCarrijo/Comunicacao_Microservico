import { v4 as uuidv4 } from "uuid";
import * as httpStatus from './constants/httpStatus.js';

export default (req, res, next) => {
  let { transactionid } = req.headers;
  if (!transactionid) {
    return res.status(httpStatus.BAD_REQUEST).json({
      status: httpStatus.BAD_REQUEST,
      message: "The transactionid header is required.",
    });
  }
  req.headers.serviceid = uuidv4();
  return next();
};