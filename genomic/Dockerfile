# Etapa base: imagen oficial de Python
FROM python:3.12-slim

# Crear directorio de la app
WORKDIR /app

# Copiar dependencias primero (mejora cache)
COPY requirements.txt .

# Instalar dependencias
RUN pip install --no-cache-dir -r requirements.txt

# Copiar el proyecto completo
COPY . .

# Exponer el puerto del servidor Django
EXPOSE 8000

# Comando para ejecutar el servidor (modo desarrollo)
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]
