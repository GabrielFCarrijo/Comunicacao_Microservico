import bcrypt from "bcrypt";
import User from "../modules/user/model/User.js";
export async function createInicialData() {
    try {
        await User.sync({ force: true });

        const password = await bcrypt.hash("123456", 10);

        await User.create({
            name: 'User Test',
            email: 'testeuser@gmail.com',
            password: password,
        });
    } catch (error) {
        console.error(error);
    }
}
