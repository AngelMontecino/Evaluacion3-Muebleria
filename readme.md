# Mueblería Los Muebles Hermanos S.A - Sistema Web Dockerizado

Este repositorio contiene la **Evaluación 3** de Ingeniería de Software. Es una evolución del sistema de gestión de mueblería, migrado de una aplicación de consola a una arquitectura web distribuida y contenerizada.

El proyecto implementa una API REST con **Spring Boot** (Backend), una interfaz web nativa con **HTML/JS** (Frontend) y una base de datos **MySQL**, todo orquestado mediante **Docker Compose**.

##  Características

* **Arquitectura de Microservicios:** Separación clara entre Backend, Frontend y Base de Datos.
* **Dockerización Completa:** Despliegue automatizado con un solo comando.
* **Interfaz Gráfica (GUI):**
    * **Perfil Cliente:** Catálogo visual, carrito de compras y generación de cotizaciones.
    * **Perfil Administrador (Gustavo):** Dashboard de inventario con alertas de stock, gestión de variantes y control de ventas.
* **Diseño Nativo:** Frontend desarrollado sin frameworks pesados (Bootstrap/Tailwind), utilizando CSS Grid/Flexbox y JavaScript puro (Vanilla JS) para máximo rendimiento.

##  Tecnologías Utilizadas

* **Backend:** Java 21, Spring Boot 3.5.7, Spring Data JPA.
* **Frontend:** HTML5, CSS3, JavaScript (Fetch API).
* **Base de Datos:** MySQL 8.0.
* **Infraestructura:** Docker, Docker Compose.

##  Guía de Instalación y Ejecución

Sigue estos pasos para levantar el proyecto en tu máquina local.

### Prerrequisitos
* Tener instalado **Docker** y **Docker Compose**.
* Tener el puerto **3000** (Frontend), **8080** (Backend) y **3307** (MySQL externo) libres.

### Paso 1: Clonar el Repositorio
```bash
git clone <URL_DEL_REPOSITORIO>
cd <NOMBRE_DE_LA_CARPETA>
```

### Paso 2: Configurar Variables de Entorno
Revisar `.env` en la raíz del proyecto (al mismo nivel que `docker-compose.yml`) y pega el siguiente contenido:

```ini
MYSQL_URL=jdbc:mysql://mysqldb:3306/muebleria_db
MYSQL_USER=root
MYSQL_PASS=rootpassword
MYSQL_ROOT_PASSWORD=rootpassword
MYSQL_DATABASE=muebleria_db
```


### Paso 3: Desplegar con Docker
Abre una terminal en la raíz del proyecto y ejecuta:
```bash
docker-compose up --build
```
Docker compilará el código Java, configurará la base de datos y levantará los servidores web. Espera hasta ver los logs de "Started MuebleriaApiApplication".