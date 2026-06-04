# Login System with JWT & SQL

## Descripción
Sistema de autenticación robusto desarrollado en Java con el framework Spring Boot. Este proyecto implementa un flujo de login seguro utilizando JSON Web Tokens (JWT) para la gestión de sesiones sin estado (stateless) y persistencia de datos mediante SQL.

## Tecnologías Utilizadas
* Java 17+
* Spring Boot 3.x
* Spring Security (Configuración de filtros y seguridad)
* JJWT / Auth0 Java-JWT (Generación y validación de tokens)
* MySQL/PostgreSQL (Gestión de usuarios y credenciales)
* Maven/Gradle (Gestión de dependencias)

## Características Técnicas
* Autenticación Stateless: Uso de tokens JWT eliminando la necesidad de sesiones en el servidor.
* Seguridad de Contraseñas: Encriptación de credenciales mediante BCryptPasswordEncoder.
* Protección de Endpoints: Acceso restringido a rutas protegidas mediante filtros de seguridad.
* Gestión de Excepciones: Manejo centralizado de errores de autenticación y autorización.

## Configuración del Entorno
Para ejecutar este proyecto, asegúrate de tener configurado el archivo application.properties (o application.yml) con los siguientes parámetros:

# Configuración de Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/nombre_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# Configuración JWT
jwt.secret=tu_clave_secreta_aqui
jwt.expiration=86400000

## Instalación y Ejecución
Sigue estos pasos para poner el proyecto en marcha en tu entorno local:

1. Clonar el repositorio:
git clone <url-del-repositorio>
cd nombre-del-proyecto

2. Configurar la base de datos:
Asegúrate de tener un servidor MySQL/PostgreSQL activo y crea la base de datos definida en tu archivo application.properties.

3. Construir el proyecto:
mvn clean install

4. Ejecutar la aplicación:
mvn spring-boot:run

5. Probar los endpoints:
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"tu_usuario", "password":"tu_password"}'

## Flujo de Trabajo
1. Login: El usuario envía sus credenciales (username y password).
2. Validación: El sistema verifica las credenciales contra la base de datos SQL.
3. Tokenización: Si son correctas, el servidor genera un JWT firmado y lo devuelve al cliente.
4. Acceso: El cliente envía el JWT en el encabezado Authorization: Bearer <token> para consumir los recursos protegidos.
