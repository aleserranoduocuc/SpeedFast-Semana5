package repartidor;

import modelo.Pedido;
import modelo.ZonaDeCarga;
import modelo.EstadoPedido;

public class Repartidor implements Runnable {
    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
        System.out.println("[Repartidor - " + nombre + "] Iniciando jornada...");

        while (true) {
            Pedido pedido = null;

            synchronized (zonaDeCarga) {
                if (!zonaDeCarga.hayPedidos()) {
                    System.out.println("[Repartidor - " + nombre + "] No hay mas pedidos. Finalizando jornada.");
                    break;
                }
                pedido = zonaDeCarga.retirarPedido();
            }

            if (pedido == null) {
                break;
            }

            try {
                pedido.setEstado(EstadoPedido.EN_REPARTO);
                System.out.println("[Repartidor - " + nombre + "] Retirando pedido #" + pedido.getId() + "...");
                System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());

                int tiempoEntrega = 1000 + (int)(Math.random() * 2000);
                System.out.println("[Repartidor - " + nombre + "] Entregando pedido #" + pedido.getId() + "...");
                Thread.sleep(tiempoEntrega);

                pedido.setEstado(EstadoPedido.ENTREGADO);
                System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());
                System.out.println("[Repartidor - " + nombre + "] Pedido #" + pedido.getId() + " entregado correctamente.");

            } catch (InterruptedException e) {
                System.out.println("[Repartidor - " + nombre + "] Entrega interrumpida.");
                Thread.currentThread().interrupt();
                return;
            } catch (Exception e) {
                System.out.println("[Repartidor - " + nombre + "] Error inesperado: " + e.getMessage());
            }
        }

        System.out.println("[Repartidor - " + nombre + "] Jornada finalizada.");
    }
}