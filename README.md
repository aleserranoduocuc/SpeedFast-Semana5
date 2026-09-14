# SpeedFast - Semana 5

## Objetivo

Implementar un sistema concurrente para SpeedFast que sincronice el acceso a una zona de carga compartida, evitando condiciones de carrera y garantizando que cada pedido sea retirado por un único repartidor.

## Estructura del proyecto
SpeedFast-Semana5/
├── src/
│ ├── modelo/
│ │ ├── EstadoPedido.java
│ │ ├── Pedido.java
│ │ └── ZonaDeCarga.java
│ ├── repartidor/
│ │ └── Repartidor.java
│ └── Main.java
└── README.md

text

## Clases principales

### `EstadoPedido` (enum)
Define los estados posibles de un pedido: `PENDIENTE`, `EN_REPARTO`, `ENTREGADO`.

### `Pedido`
Representa un pedido con atributos `id`, `direccionEntrega` y `estado`.

### `ZonaDeCarga`
Recurso compartido protegido con `synchronized`. Utiliza `wait()` y `notifyAll()` para coordinar el acceso concurrente.

### `Repartidor` (implementa `Runnable`)
Cada repartidor es un hilo independiente que retira pedidos de la zona de carga y simula su entrega con `Thread.sleep()`.

## Concurrencia y sincronización

- **`synchronized`**: Protege los métodos `agregarPedido()` y `retirarPedido()`.
- **`wait()`**: Hace esperar a los hilos cuando no hay pedidos o la zona está llena.
- **`notifyAll()`**: Despierta a los hilos cuando hay cambios en la zona de carga.
- **`ExecutorService`**: Gestiona un pool de 3 hilos para los repartidores.
- **`InterruptedException`**: Se maneja con `Thread.currentThread().interrupt()` y `return` para finalizar el hilo correctamente.

## Instrucciones para ejecutar

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/SpeedFast-Semana5.git
Abre el proyecto en IntelliJ IDEA.

Ejecuta la clase Main.java.

Observa la salida por consola.

Autor
Alejandro Serrano - Duoc UC
