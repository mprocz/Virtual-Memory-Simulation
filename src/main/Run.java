package main;
import disk.*;
import ram.*;
import cpu.*;

public class Run {
    public static void main(String[] args){
        System.out.println(ConsoleColors.RED_BRIGHT + "Iniciando dispositivo...\n" + ConsoleColors.RESET);
        CPU cpu = new CPU();

        int row = 0;
        int column = 0;
        cpu.startProcess(row, column);

        System.out.println(ConsoleColors.RED_BRIGHT + "Dispositivo encerrando...\n" + ConsoleColors.RESET);
    }
}
