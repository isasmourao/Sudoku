package br.ufjf.dcc.dcc025.dcc025.Entities;

import static br.ufjf.dcc.dcc025.dcc025.Constants.Constants.TAMANHO;
import br.ufjf.dcc.dcc025.dcc025.Entities.Interfaces.Jogo;
import java.util.Scanner;

public abstract class JogoSudoku implements Jogo {

    protected String[][] origemValores = new String[TAMANHO][TAMANHO];
    protected int[][] tabuleiro = new int[TAMANHO][TAMANHO];
 
    protected boolean podeInserirNumero(int linha, int coluna, int numero) 
    {
        return !existeNumeroNaLinha(linha, numero) &&
               !existeNumeroNaColuna(coluna, numero) &&
               !existeNumeroNoQuadrado(linha, coluna, numero);
    }

    private boolean existeNumeroNaLinha(int linha, int numero) 
    {
        for (int coluna = 0; coluna < 9; coluna++) 
        {
            if (tabuleiro[linha][coluna] == numero)            
                return true;           
        }
        return false;
    }
   
    private boolean existeNumeroNaColuna(int coluna, int numero) 
    {
        for (int linha = 0; linha < 9; linha++) 
        {
            if (tabuleiro[linha][coluna] == numero)            
                return true;          
        }
        return false;
    }

    private boolean existeNumeroNoQuadrado(int linha, int coluna, int numero) 
    {
        int inicioLinha = (linha / 3) * 3;
        int inicioColuna = (coluna / 3) * 3;

        for (int lin = inicioLinha; lin < inicioLinha + 3; lin++) 
        {
            for (int col = inicioColuna; col < inicioColuna + 3; col++) 
            {
                if (tabuleiro[lin][col] == numero)                
                    return true;               
            }
        }
        return false;
    }

    protected void imprimirTabuleiro() 
    {
        for (int[] linha : tabuleiro) 
        {
            for (int numero : linha)             
                System.out.print(numero + " ");
            
            System.out.println();
        }
    }
    
    protected void adicionarJogada(String jogada) 
    {
        // Verifica o formato da jogada
        if (!jogada.matches("\\(\\d{1},\\d{1},\\d{1}\\)")) 
        {
            System.out.println("Formato inválido! A entrada deve ser no formato (linha,coluna,valor) sem espaços.");
            return;
        }

        // Limpa os parênteses e divide a string em componentes
        jogada = jogada.replace("(", "").replace(")", "");
        String[] valores = jogada.split(",");

        // Converte para inteiros
        int linha = Integer.parseInt(valores[0]) - 1; // Ajuste para índice 0
        int coluna = Integer.parseInt(valores[1]) - 1; // Ajuste para índice 0
        int valor = Integer.parseInt(valores[2]);

        // Validação dos dados
        if (linha < 0 || linha >= 9 || coluna < 0 || coluna >= 9 || valor < 1 || valor > 9) {
            System.out.println("Valores fora do intervalo! A linha e a coluna devem ser entre 1 e 9, e o valor entre 1 e 9.");
            return;
        }

        // Verifica se a posição já está preenchida
        if (tabuleiro[linha][coluna] != 0) {
            System.out.printf("A entrada (%d,%d,%d) não foi inserida, pois já possui um valor atribuído.%n", linha + 1, coluna + 1, valor);
            return;
        }

        // Insere o valor no tabuleiro
        tabuleiro[linha][coluna] = valor;
        System.out.printf("Valor %d inserido na posição (%d,%d).%n", valor, linha + 1, coluna + 1);
        
        imprimirTabuleiro();
    }

    public boolean jogoEstaCompleto()
    {
        for (int i = 0; i < 9; i++) 
        {
            for (int j = 0; j < 9; j++) 
            {
                if (tabuleiro[i][j] == 0) 
                    return false; // se encontrar um 0, o jogo não terminou
            }
        }
        return true;
    }
    
    public void gerarMensagemJogoCompleto()
    {
        System.out.println("""
                           ===================================================
                                            JOGO COMPLETO!
                           ===================================================
                           """);
    }
    
    protected void removerJogada(String posicao) 
    {
        String[] partes = posicao.replace("(", "").replace(")", "").split(",");
        int linha = Integer.parseInt(partes[0]) - 1; 
        int coluna = Integer.parseInt(partes[1]) - 1; 
        
        if (linha >= 0 && linha < 9 && coluna >= 0 && coluna < 9) {
            // Checa se o valor foi inserido automaticamente ou manualmente
            if (origemValores[linha][coluna] != null && origemValores[linha][coluna].equals("automatico")) {
                System.out.println("Não é possível remover o valor inserido automaticamente ou manualmente.");
            } 
            else 
            {
                tabuleiro[linha][coluna] = 0; 
                origemValores[linha][coluna] = null;
                System.out.println("Jogada removida da posição (" + (linha+1) + "," + (coluna+1) + ")");
            }
        } 
        else 
        {
            System.out.println("Posição inválida.");
        }   
    }

    public String verificarJogo() 
    {
        StringBuilder relatorio = new StringBuilder();
        boolean jogoValido = true;

        // Verificar linhas
        for (int i = 0; i < 9; i++) 
        {
            if (!verificarLinha(i)) 
            {
                jogoValido = false;
                relatorio.append(String.format("A linha %d contém números duplicados ou inválidos.%n", i + 1));
            }
        }

        // Verificar colunas
        for (int j = 0; j < 9; j++) 
        {
            if (!verificarColuna(j)) 
            {
                jogoValido = false;
                relatorio.append(String.format("A coluna %d contém números duplicados ou inválidos.%n", j + 1));
            }
        }

        // Verificar subgrades 3x3
        for (int linha = 0; linha < 9; linha += 3) 
        {
            for (int coluna = 0; coluna < 9; coluna += 3) 
            {
                if (!verificarSubgrade(linha, coluna)) 
                {
                    jogoValido = false;
                    relatorio.append(String.format(
                            "A subgrade começando na posição (%d,%d) contém números duplicados ou inválidos.%n",
                            linha + 1, coluna + 1));
                }
            }
        }

        if (jogoValido) 
        {
            return "O jogo está correto até o momento.";
        } 
        else 
        {
            return relatorio.toString();
        }
    }

    private boolean verificarLinha(int linha) 
    {
        boolean[] numeros = new boolean[9];
        for (int j = 0; j < 9; j++) 
        {
            int valor = tabuleiro[linha][j];
            if (valor < 1 || valor > 9) continue; // Ignora células vazias
            if (numeros[valor - 1]) 
            {
                return false; // Número duplicado encontrado
            }
            numeros[valor - 1] = true;
        }
        return true;
    }

    private boolean verificarColuna(int coluna) 
    {
        boolean[] numeros = new boolean[9];
        for (int i = 0; i < 9; i++) 
        {
            int valor = tabuleiro[i][coluna];
            if (valor < 1 || valor > 9) continue; // Ignora células vazias
            if (numeros[valor - 1])           
                return false; // Número duplicado encontrado
            
            numeros[valor - 1] = true;
        }
        return true;
    }

    private boolean verificarSubgrade(int linhaInicio, int colunaInicio) 
    {
        boolean[] numeros = new boolean[9];
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
                int valor = tabuleiro[linhaInicio + i][colunaInicio + j];
                if (valor < 1 || valor > 9) continue; // Ignora células vazias
                if (numeros[valor - 1]) 
                {
                    return false; // Número duplicado encontrado
                }
                numeros[valor - 1] = true;
            }
        }
        return true;
    }
  
    public void iniciarJogo() 
    {
        Scanner scanner = new Scanner(System.in);
        gerarMensagemIniciandoJogo();
        
        while (!jogoEstaCompleto()) 
        {
            mostrarMenu();
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            imprimirTabuleiro();
            
            switch (opcao) 
            {              
                case 1 -> 
                {
                    System.out.print(">> Digite a jogada no formato (linha,coluna,valor): ");
                    String jogada = scanner.nextLine();
                    adicionarJogada(jogada);
                    break;
                }
                case 2 -> 
                {
                    System.out.print(">> Digite a posição da jogada a ser removida (linha,coluna): ");
                    String jogada = scanner.nextLine();
                    removerJogada(jogada);
                    break;
                }
                case 3 -> 
                {
                    System.out.println(verificarJogo());
                    break;
                }
                case 4 ->
                {
                    System.out.print(">> Digite a posição (linha,coluna): ");
                    String jogada = scanner.nextLine();
                    System.out.println(darDica(jogada));
                    break;
                }
                case 5 -> 
                {
                    System.out.println("Saindo do jogo...");
                    return;
                }
                default -> System.out.println(" ### Opção inválida. Tente novamente. ###");
            }           
            
            if (jogoEstaCompleto()) {
                System.out.println(" ########### Parabéns! Você completou o jogo.  ###########");
            }
            
            imprimirTabuleiro();
        }
    }
    
    private void gerarMensagemIniciandoJogo()
    {
        System.out.println("""
                ===================================================
                Iniciando o jogo...
                ===================================================
                """);
    }
    
    private void mostrarMenu() 
    {
        System.out.println("""
                           ===================================================
                           Menu de jogadas! Selecione uma opção:
                           
                           1. Adicionar jogada.
                           2. Remover jogada.
                           3. Verificar.
                           4. Dica.
                           5. Sair.
                           ===================================================
                           """);   
    }
    
    protected String darDica(String posicao) {
        if (posicao == null || posicao.isBlank()) {
            return "Posição inválida. Use o formato (linha,coluna).";
        }

        try {
            int[] coordenadas = parsePosicao(posicao);
            int linha = coordenadas[0];
            int coluna = coordenadas[1];

            if (tabuleiro[linha][coluna] != 0) {
                return String.format("A posição (%d,%d) já possui um valor.", linha + 1, coluna + 1);
            }

            String valoresPossiveis = obterValoresPossiveis(linha, coluna);
            return valoresPossiveis.isEmpty()
                ? String.format("Nenhum valor pode ser inserido na posição (%d,%d).", linha + 1, coluna + 1)
                : "Valores possíveis: " + valoresPossiveis;

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private int[] parsePosicao(String posicao) {
        try {
            String[] valores = posicao.replace("(", "").replace(")", "").split(",");
            if (valores.length != 2) {
                throw new IllegalArgumentException("Formato inválido. Use o formato (linha,coluna).\nExemplo: (3,4)");
            }

            int linha = Integer.parseInt(valores[0].trim()) - 1;
            int coluna = Integer.parseInt(valores[1].trim()) - 1;

            if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
                throw new IllegalArgumentException("Posição fora dos limites do tabuleiro. Valores devem estar entre 1 e 9.");
            }

            return new int[]{linha, coluna};

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido. Use números inteiros no formato (linha,coluna).", e);
        }
    }

    private String obterValoresPossiveis(int linha, int coluna) {
        StringBuilder valoresPossiveis = new StringBuilder();

        for (int valor = 1; valor <= TAMANHO; valor++) {
            if (ehPossivelInserir(linha, coluna, valor)) {
                if (valoresPossiveis.length() > 0) {
                    valoresPossiveis.append(", ");
                }
                valoresPossiveis.append(valor);
            }
        }

        return valoresPossiveis.toString();
    }

    private boolean ehPossivelInserir(int linha, int coluna, int valor) {
        // Verifica linha e coluna simultaneamente
        for (int i = 0; i < TAMANHO; i++) {
            if (tabuleiro[linha][i] == valor || tabuleiro[i][coluna] == valor) {
                return false;
            }
        }

        // Verifica a subgrade 3x3
        int linhaInicio = (linha / 3) * 3;
        int colunaInicio = (coluna / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[linhaInicio + i][colunaInicio + j] == valor) {
                    return false;
                }
            }
        }

        return true;
    }
}

