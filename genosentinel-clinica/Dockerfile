#proceso de compilacion
FROM node:20-alpine AS builder

WORKDIR /app

COPY package.json package-lock.json ./

#instala las dependencias de los packages
RUN npm ci
#copiar configuraciones typescript y Nest para compilar
COPY tsconfig.json tsconfig.build.json nest-cli.json ./
#copiar todo el codigo fuente del proyecto
COPY src ./src

# Compila el proyecto TypeScript y genera la carpeta dist para produccion
#pasa los archivos .ts a js para compilar
RUN npm run build

#proceso de ejecuccion
FROM node:20-alpine AS runner

#define la variable de entorno para produccion
ENV NODE_ENV=production

WORKDIR /app

COPY package.json package-lock.json ./

#instala solo lo necesario para ejecucion no compilacion
RUN npm ci --omit=dev

# Copia los archivos ya compilados desde la etapa builder a la imagen final
COPY --from=builder /app/dist ./dist

EXPOSE 3000
ENV PORT=3000

#usa node como puerto de ejecuccion
USER node
CMD ["node", "dist/main.js"]
