# Usando a versão correta do Node.js
FROM node:16.20.1

# Copiando os arquivos package.json e yarn.lock para o container
COPY package*.json ./

# Instalando as dependências
RUN yarn install

# Copiando todos os arquivos da aplicação para o container
COPY . .

# Expondo a porta 8080 para o serviço
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["node", "app.js"]
