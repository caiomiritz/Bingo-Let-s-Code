import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        String[] players = initialize();
        int[][] cartelas = cartelasPlayer(players);
        apresentacao(players, cartelas);
        play(players, cartelas);
    }

    public static String[] initialize (){
        Scanner scanner = new Scanner(System.in);
        System.out.println(" ");
        System.out.println("---------Bem vindo ao Bingo da Let's Code !!---------");
        System.out.println(" ");
        System.out.print("Por favor, digite quantos jogadores irão fazer parte do jogo: ");
        int num = scanner.nextInt();

        String[] players = new String[num];

        System.out.print("Por favor, digite o nome dos jogadores separados por hífen (-): ");
        players = scanner.next().split("-");

        return players;
    }

    public static int[][] cartelasPlayer (String[] players){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int[][] cartelasP = new int[players.length][5];
        String[] inputcartela = new String[5];
        int[] cartela = new int[5];
        int[] cartelaRand = new int[5];

        for(int i = 0; i < players.length; i++){
            System.out.printf("%s, Quer sua cartela gerada manualmente ou aleatoriamente ?\n", players[i]);
            System.out.println("Para manual, digite MANUAL, para aleatório, digite RAMDOM: ");
            String option = scanner.next();

            if(option.equals("MANUAL")){
                System.out.println("Por favor, digite os 5 números da cartela (de 0 a 60), sem repetição, separados por vírgula (,): ");

                inputcartela = scanner.next().split(",");

                for(int j = 0; j < 5; j++){
                    cartela[j] = Integer.parseInt(inputcartela[j]);
                    cartelasP[i][j] = cartela[j];
                }
            }

            else if(option.equals("RANDOM")){
                int[] aux = gerarCartela();
                for(int j = 0; j < aux.length; j++){
                    cartelasP[i][j] = aux[j];
                }
            }
        }
        return cartelasP;
    }

    public static void apresentacao (String[] players, int[][] cartelas){
        System.out.println("------------Os Respectivos jogadores(as) e suas cartelas são: ------------");
        System.out.println(" ");

        for(int i = 0; i < players.length; i++){
            System.out.printf("Jogador %d: %s, com sua respectiva cartela: ", i+1, players[i]);

            for(int j = 0; j < 5; j++){
                System.out.printf("|%d| ",cartelas[i][j]);
            }

            System.out.println(" ");
        }
    }

    public static int[] gerarCartela(){
        int[] valores = new int[5];
        Random random = new Random();

        int i = 0;
        while(i < valores.length){
            valores[i] = random.nextInt(60);
            boolean colide = false;

            for(int j = 0; j < i; j++){
                if(valores[i] == valores[j]){
                    colide = true;
                    break;
                }
            }
            if(!colide){
                i++;
            }
        }
        return valores;
    }

    public static void play(String[] players, int[][] cartelas){
        Scanner scanner = new Scanner(System.in);
        String option;
        int[][] cartelaZerada = zerar(players);
        boolean flag = false;
        int rounds = 0;

        System.out.println(" ");
        System.out.println("-----------------------------------------------------");
        System.out.println(" ");
        System.out.print("Se quizer o sorteio feito manualmente, digite MANUAL, se quizer aleatório, digite RANDOM: ");
        option = scanner.next();
        System.out.println(" ");

        if(option.equals("MANUAL")){
            sorteio(players, cartelas, scanner, cartelaZerada, option);
        }
        else if(option.equals("RANDOM")){
            sorteio(players, cartelas, scanner, cartelaZerada, option);
        }
    }

    public static int[][] zerar(String[] players){
        int[][] aux = new int[players.length][5];

        for(int i = 0; i < players.length; i++){
            for(int j = 0; j < 5; j++){
                aux[i][j] = 0;
            }
        }
        return aux;
    }

    public static void sorteio(String[] players, int[][] cartelasPlayers, Scanner scanner, int[][] cartelaZerada, String option){
        boolean flag = false;
        String[] inputcartela = new String[5];
        int[] cartelaSorteada = new int[5];
        int[][] acertados = cartelaZerada;
        int rounds = 1;
        int ganhador = 0;
        int[][] numAcertos = new int[players.length][1];
        int quntNumerosS = 0;
        int prim, seg, ter;


        while(flag == false){

            if(option.equals("MANUAL")){
                System.out.print("Por favor digite os 5 números sorteados (de 0 a 60) sem repetí-los uma única vez, separados por vírgula (,): ");
                inputcartela = scanner.next().split(",");

                for(int j = 0; j < 5; j++){
                    cartelaSorteada[j] = Integer.parseInt(inputcartela[j]);
                    quntNumerosS++;
                }
            }
            else if(option.equals("RANDOM")){
                cartelaSorteada = gerarCartela();
                quntNumerosS += 5;
            }

            for(int i = 0; i < players.length; i++){                       //percorrendo cada um dor jogadores
                for(int k = 0; k < 5; k++){                                //percorrendo cada numero da cartela do jogador
                    for(int l = 0; l < 5; l++){                           //comparando cada numero da cartela sorteada
                        if(cartelasPlayers[i][k] == cartelaSorteada[l]){
                            acertados[i][k] = 1;
                            numAcertos[i][0]++;
                        }
                    }
                }

                if(numAcertos[i][0] == 5){
                    flag = true;
                    ganhador = i;
                }
            }


            System.out.printf("------------ROUND: %d------------\n", rounds);
            System.out.printf("A cartela sorteada foi: ");
            for(int i = 0; i < 5; i++){
                System.out.printf("|%d|", cartelaSorteada[i]);
            }

            System.out.println(" ");
            System.out.println("---------------MENU--------------");
            System.out.println(" ");
            System.out.println("Para continuar o jogo digite 1.");
            System.out.println("Para finalizar o jogo digite 2.");

            if(scanner.next().equals("1")){
                rounds++;
            }
            else if(scanner.next().equals("2")){
                break;
            }
        }

        System.out.println("--------------------------------------------------");
        System.out.println(" ");

        System.out.printf("O jogador %d ganhou o bingo !!!! Parabéns %s\n", ganhador+1, players[ganhador]);
        System.out.print("A cartela premiada foi: ");
        for(int i = 0; i < 5; i++){
            System.out.printf("| %d |", cartelasPlayers[ganhador][i]);
        }
        System.out.println(" ");
        System.out.printf("Número de rounds: %d\n", rounds-1);
        System.out.printf("Quantidade de numeros sorteados: %d\n", quntNumerosS);
    }

}