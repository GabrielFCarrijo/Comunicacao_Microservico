class UserException extends Error {

    constructor(status, msg) {
        super(msg)
        this.status = status;
        this.message = msg;
        this.name = this.constructor.name;
        Error.captureStackTrace(this.constructor)
    }
}

export default UserException;