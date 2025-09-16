import utils.Utils;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static int opcao;

    public static void main(String[] args) {
        inicio();
    }

    public static void inicio() {
        boolean sair = false;

        do {
            Utils.mostrarMenuPrincipal();
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> Utils.menuCadastros(sc);
                case 2 -> Utils.menuPedidosEntregas(sc);
                case 3 -> Utils.menuRelatorios(sc);
                case 4 -> Utils.menuExclusoes(sc);
                case 0 -> {
                    sair = true;
                    System.out.println("\nSaindo do Sistema. Até a Próxima!");
                }
                default -> System.out.println("\nOpção Inválida! Tente Novamente.");
            }
        } while (!sair);
    }
}