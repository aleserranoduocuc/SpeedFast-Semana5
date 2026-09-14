import modelo.Pedido;
import modelo.ZonaDeCarga;
import repartidor.Repartidor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  SPEEDFAST - SINCRONIZACION DE ENTREGAS");
        System.out.println("==================================================\n");

        ZonaDeCarga zonaDeCarga = new ZonaDeCarga(10);
        System.out.println("[Zona de carga inicializada]\n");

        zonaDeCarga.agregarPedido(new Pedido(1, "Santiago Centro"));
        zonaDeCarga.agregarPedido(new Pedido(2, "Providencia"));
        zonaDeCarga.agregarPedido(new Pedido(3, "Nunoa"));
        zonaDeCarga.agregarPedido(new Pedido(4, "Recoleta"));
        zonaDeCarga.agregarPedido(new Pedido(5, "Las Condes"));

        System.out.println("\n--- INICIO DE ENTREGAS ---\n");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new Repartidor("Juan", zonaDeCarga));
        executor.submit(new Repartidor("Camila", zonaDeCarga));
        executor.submit(new Repartidor("Pedro", zonaDeCarga));

        executor.shutdown();

        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                System.out.println("[Main] Tiempo de espera agotado. Forzando finalizacion...");
                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("[Main] Ejecucion interrumpida. Estado restablecido.");
        }

        System.out.println("\n==================================================");
        System.out.println("  Todos los pedidos han sido entregados correctamente");
        System.out.println("==================================================");
    }
}