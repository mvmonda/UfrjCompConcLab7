import java.util.Random;

class Soma {
    public int soma;
    public Soma() { 
        this.soma = 0; 
    }

    public synchronized void soma(int valor) { 
        this.soma+=valor; 
    }
}

class ThreadSomaArray extends Thread {
    private int tid;
    private Soma soma;
    private int[] array;
    public ThreadSomaArray(int tid, Soma soma, int[] array) { 
       this.tid = tid; 
       this.soma = soma;
       this.array = array;
    }
 
    public void run() {
        int inicioSessao = this.tid * 1000;
        int soma = 0;
       for(int i = inicioSessao; i< inicioSessao + 1000; i++) {
           soma+= array[i];
       }

       this.soma.soma(soma);
       System.out.println("Valor da thread " + tid + " é " + soma ); 
    }
 }

class SomaVetorParalelo {
    static final int N = 10;
    static int[] Vetor = new int[N * 1000];
    static Soma Soma = new Soma();
    public static void main (String[] args) {
        Random rd = new Random();
        for (int i = 0; i < Vetor.length; i++) {
            Vetor[i] = rd.nextInt(10000);
        }

        Thread[] threads = new Thread[N];

        for (int i=0; i<threads.length; i++) {
            threads[i] = new ThreadSomaArray(i, Soma, Vetor);
        }

        for (int i=0; i<threads.length; i++) {
            threads[i].start();
        }

        for (int i=0; i<threads.length; i++) {
            try { threads[i].join(); } catch (InterruptedException e) { return; }
        }
        System.out.println("Valor de s = " + Soma.soma); 
    }
}