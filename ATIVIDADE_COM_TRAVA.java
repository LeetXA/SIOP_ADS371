import java.io.FileWriter;
import java.io.IOException;

public class ATIVIDADE_COM_TRAVA {

    static final int QTDE_THREADS = 20;
    static final int LINHAS_POR_THREAD = 500;

    static final Object lock = new Object();

    public static void main(String[] args) throws Exception {

        Thread[] threads = new Thread[QTDE_THREADS];

        for (int i = 0; i < QTDE_THREADS; i++) {
            final int threadId = i;

            threads[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < LINHAS_POR_THREAD; j++) {

                        //SEÇÃO PROTEGIDA
                        synchronized (lock) {
                            FileWriter writer = new FileWriter("arquivo_com_trava.txt", true);
                            writer.write("Thread " + threadId + " - registro " + j + "\n");
                            writer.close();
                        }

                        try { Thread.sleep(1); } catch (InterruptedException e) {}
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Finalizado COM trava (esperado: 10000 linhas)");
    }
}