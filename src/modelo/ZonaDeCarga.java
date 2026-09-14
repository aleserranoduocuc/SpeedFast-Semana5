package modelo;

import java.util.LinkedList;
import java.util.List;

public class ZonaDeCarga {
    private final List<Pedido> pedidos;
    private final int capacidadMaxima;

    public ZonaDeCarga(int capacidadMaxima) {
        this.pedidos = new LinkedList<>();
        this.capacidadMaxima = capacidadMaxima;
    }

    public synchronized void agregarPedido(Pedido p) {
        while (pedidos.size() >= capacidadMaxima) {
            try {
                System.out.println("[Zona de carga] Capacidad maxima alcanzada. Esperando...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Zona de carga] Agregado interrumpido.");
                return;
            }
        }
        pedidos.add(p);
        System.out.println("[Zona de carga] " + p + " agregado.");
        notifyAll();
    }

    public synchronized Pedido retirarPedido() {
        while (pedidos.isEmpty()) {
            try {
                System.out.println("[Zona de carga] No hay pedidos disponibles. Esperando...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Zona de carga] Retiro interrumpido.");
                return null;
            }
        }
        Pedido p = pedidos.remove(0);
        System.out.println("[Zona de carga] Pedido #" + p.getId() + " retirado por un repartidor.");
        notifyAll();
        return p;
    }

    public synchronized int getCantidadPedidos() {
        return pedidos.size();
    }

    public synchronized boolean hayPedidos() {
        return !pedidos.isEmpty();
    }
}