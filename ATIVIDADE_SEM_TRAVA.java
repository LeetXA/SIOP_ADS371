import java.io.FileWriter;
import java.io.IOException;

public class ATIVIDADE_SEM_TRAVA {

    static final int QTDE_THREADS = 20;
    static final int LINHAS_POR_THREAD = 500;

    public static void main(String[] args) throws Exception {

        Thread[] threads = new Thread[QTDE_THREADS];

        for (int i = 0; i < QTDE_THREADS; i++) {
            final int threadId = i;

            threads[i] = new Thread(() -> {
                try {
                    FileWriter writer = new FileWriter("arquivo_sem_trava.txt", true);

                    for (int j = 0; j < LINHAS_POR_THREAD; j++) {

                        //SEÇÃO DESPROTEGIDA
                        writer.write("Thread " + threadId + " - registro " + j + "\n");

                        // Força troca de contexto
                        try { Thread.sleep(1); } catch (InterruptedException e) {}
                    }

                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Finalizado SEM trava (esperado: 10000 linhas)");
    }
}