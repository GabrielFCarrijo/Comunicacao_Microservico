import { Sequelize } from "sequelize";

const sequelize = new Sequelize("auth-db", "postgres", "postgres", {
    host: "localhost",
    port: 5434, // Porta do PostgreSQL, se diferente da padrão 5432
    dialect: "postgres",
    quoteIdentifiers: false,
    define: {
        syncOnAssociation: true,
        timestamps: false,
        underscored: true,
        underscoredAll: true,
        freezeTableName: true
    }
});

sequelize.authenticate()
    .then(() => {
        console.info("Connection has been established successfully.");
    })
    .catch((err) => {
        console.error("Unable to connect to the database:", err);
    });

export default sequelize;
