# TecnoStore Console System

Sistema de consola en Java para la gestión de ventas de celulares, clientes e inventario, usando Programación Orientada a Objetos, JDBC, Stream API y generación de reportes.

---

## 📌 Descripción del proyecto

TecnoStore es una aplicación de consola desarrollada en Java que permite:

- Gestionar celulares (inventario)
- Gestionar clientes
- Registrar ventas con detalle
- Calcular IVA automáticamente
- Generar reportes con Stream API
- Exportar ventas a archivo `.txt`

El sistema utiliza MySQL para persistencia y sigue una arquitectura por capas.

---

## 🏗️ Estructura del proyecto

```
modelo/
    celular.java
    cliente.java
    venta.java
    detalleVenta.java

controlador/
    GestionarCelularImpl.java
    GestionarClienteImpl.java
    GestionarVentaImpl.java
    conexion.java

vista/
    menuCelular.java
    menuCliente.java
    menuVenta.java
    menuReportes.java

utilidades/
    ReporteUtils.java
```

---

## ⚙️ Funcionalidades

### Gestión de celulares
- Registrar, actualizar, eliminar y listar
- Validación de precio y stock

### Gestión de clientes
- Registro con validación de correo
- Identificación única

### Gestión de ventas
- Venta con múltiples ítems
- Cálculo automático de IVA (19%)
- Actualización de stock
- Persistencia en MySQL

### Reportes (Stream API)
- Celulares con stock bajo
- Top 3 más vendidos
- Ventas por mes
- Exportación a `reporte_ventas.txt`

---

## 🗄️ Base de datos

Nombre sugerido:

```
tecnostore
```

Tablas:

```
celular
cliente
venta
detalle_venta
marca
```

Ejecutar el script:

```
tecnostore_db.sql
```

---

## ▶️ Ejemplo de ejecución

```
======= MENÚ PRINCIPAL =======
1. Celulares
2. Clientes
3. Ventas
4. Reportes
5. Salir
```

---

## 🔌 Conexión MySQL

Configurar en:

```
conexion.java
```

Ejemplo:

```java
String url = "jdbc:mysql://localhost:3306/tecnostore";
String user = "root";
String password = "tu_password";
```

---

## 🧠 Tecnologías usadas

- Java
- JDBC
- MySQL
- Stream API
- JFileChooser
- Programación Orientada a Objetos

---

## 👨‍💻 Principios aplicados

- Encapsulamiento
- Composición
- Arquitectura por capas
- Manejo de excepciones
- Try-with-resources

---

## 📄 Archivo generado

El sistema genera:

```
reporte_ventas.txt
```

con el resumen completo de ventas.

---

## 📌 Autor

Santiago Uribe Duarte

