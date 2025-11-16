# ITSONTinder

ITSONTinder es una aplicación de escritorio, estilo Tinder, diseñada para estudiantes de ITSON. Permite a los usuarios registrarse, explorar perfiles, reaccionar con "Me gusta" o "No me interesa", y chatear con sus matches.

Este proyecto fue creado desde cero, programando toda la interfaz gráfica (UI) en **Java Swing** puro (sin editores visuales) y conectándola a una base de datos MySQL usando **JPA (Hibernate)** para la persistencia de datos.

## Características Principales

* **Registro y Login:** Sistema de autenticación de usuarios con validación de campos.
* **Explorar (Swiping):** Panel para ver perfiles de otros estudiantes (uno por uno) y reaccionar.
* **Sistema de Match:** Notificación instantánea cuando un "Me gusta" es mutuo.
* **Lista de Matches:** Panel que se actualiza automáticamente para mostrar todos tus matches.
* **Chat:** Ventana emergente (`JDialog`) para enviar y recibir mensajes con tus matches.
* **Edición de Perfil:** Panel para actualizar tu información personal, hobbies e intereses (manejando relaciones `@ManyToMany` de JPA).
* **Panel de Admin:** Una vista simple para probar los métodos `listar()` y `eliminar()` del CRUD de Estudiantes.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java (JDK 17+)
* **Interfaz Gráfica (UI):** Java Swing
* **Persistencia de Datos (ORM):** JPA (Hibernate)
* **Base de Datos:** MySQL
* **Gestión de Dependencias:** Maven

## ⚙️ Configuración del Proyecto
Para ejecutar este proyecto localmente, sigue estos pasos:

**1. Clonar el Repositorio:**
```bash
git clone [https://github.com/potroEduardo/ITSONTinder.git](https://github.com/potroEduardo/ITSONTinder.git)
cd ITSONTinder
````

**2. Configurar la Base de Datos MySQL:**

  * Asegúrate de tener un servidor MySQL corriendo (ej. MySQL Workbench o XAMPP).
  * Crea una nueva base de datos (schema). En este proyecto se usó el nombre: `itson_tinder_db`.

**3. Configurar la Conexión (persistence.xml):**

  * Abre el archivo: `src/main/resources/META-INF/persistence.xml`.
  * Ajusta las propiedades `jakarta.persistence.jdbc.url`, `user` y `password` para que coincidan con tu configuración local de MySQL.

<!-- end list -->

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/itson_tinder_db"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="TU_CONTRSEÑA_AQUI"/> 
```

**4. (CRÍTICO) Poblar Hobbies e Intereses:**
La primera vez que ejecutes, las tablas `HOBBY` e `INTERES` estarán vacías. El panel "Editar Perfil" no mostrará nada.

  * Usa MySQL Workbench para ejecutar el siguiente SQL y añadir datos de prueba:

<!-- end list -->

```sql
USE itson_tinder_db;

-- Hobbies
INSERT INTO HOBBY (nombre) VALUES ('Programar'), ('Videojuegos'), ('Ver series'), ('Leer'), ('Escuchar música'), ('Hacer ejercicio');
-- Intereses
INSERT INTO INTERES (nombre) VALUES ('Amistad'), ('Una relación'), ('Compañeros de estudio'), ('Nada serio');
```

## ▶️ Cómo Ejecutar

1.  Abre el proyecto en tu IDE (NetBeans, IntelliJ, etc.).
2.  Realiza un "Clean and Build" para asegurar que todas las dependencias estén cargadas y que los cambios en `persistence.xml` sean detectados.
3.  Busca el archivo `vistas/FrameLogin.java` y ejecútalo (Run File).
4.  Registra un par de usuarios de prueba, inicia sesión y listo.

<!-- end list -->

```
```
<img width="942" height="756" alt="image" src="https://github.com/user-attachments/assets/75e4f5a8-1bbf-46f8-baea-0c0ef5dd73dc" />
