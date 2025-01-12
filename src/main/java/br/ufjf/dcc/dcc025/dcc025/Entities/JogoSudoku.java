package br.ufjf.dcc.dcc025.dcc025.Entities;

import br.ufjf.dcc.dcc025.dcc025.Constants.Constants;
import static br.ufjf.dcc.dcc025.dcc025.Constants.Constants.TAMANHO;
import java.util.Scanner;
import br.ufjf.dcc.dcc025.dcc025.Entities.Interfaces.JogoInterface;

public abstract class JogoSudoku implements JogoInterface {

    protected String[][] origemValores = new String[TAMANHO][TAMANHO];
    protected int[][] tabuleiro = new int[TAMANHO][TAMANHO];
 
    protected boolean podeInserirNumero(int linha, int coluna, int numero) 
    {
        return ehPossivelInserirNumero(linha, coluna, numero);
    }

    protected void imprimirTabuleiro() 
    {
        System.out.println("""
                           ===================================================
                           TABULEIRO ATUALIZADO                          
                           """);
        for (int[] linha : tabuleiro) 
        {
            for (int numero : linha)             
                System.out.print(numero + " ");
            
            System.out.println();
        }
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
            
            if (jogoEstaCorreto())
            {
                gerarMensagemVitoria();
            }
            
            if (jogoEstaCompleto()) {
                System.out.println(" ########### Parabéns! Você completou o jogo.  ###########");
            }
            
            imprimirTabuleiro();
        }
    }
    
    // <editor-fold desc="Métodos Auxiliares">
    // verifica se o número é único na linha, coluna e no quadrado 3x3
    private boolean verificarSeNumeroEhUnico(int linha, int coluna, int numero) 
    {
        for (int i = 0; i < TAMANHO; i++) 
        {
            if (tabuleiro[linha][i] == numero || tabuleiro[i][coluna] == numero)                 
                return false;               
        }

        int inicioLinha = (linha / 3) * 3;
        int inicioColuna = (coluna / 3) * 3;
        for (int lin = inicioLinha; lin < inicioLinha + 3; lin++) 
        {
            for (int col = inicioColuna; col < inicioColuna + 3; col++) 
            {
                if (tabuleiro[lin][col] == numero)                
                    return false;                
            }
        }
        return true;
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
    
    private void gerarMensagemVitoria() 
    {
        System.out.println("""
                           ===================================================
                                        PARABÉNS VOCÊ VENCEU!!
                           ===================================================
                           """);
    }
    
    public void reiniciarTabuleiro() 
    {
        for (int i = 0; i < TAMANHO; i++) 
        {
            for (int j = 0; j < TAMANHO; j++) 
            {
                tabuleiro[i][j] = 0; 
                origemValores[i][j] = null;
            }
        }
    }
        
    private boolean verificarLinha(int linha) 
    {
        boolean[] numeros = new boolean[9];
        for (int j = 0; j < 9; j++) 
        {
            int valor = tabuleiro[linha][j];
            if (valor < 1 || valor > 9) continue; 
            if (numeros[valor - 1])            
                return false;
            
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
            if (valor < 1 || valor > 9) continue; // ignora o 0
            if (numeros[valor - 1])           
                return false; // sem número duplicado
            
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
                if (valor < 1 || valor > 9) continue;
                if (numeros[valor - 1])                
                    return false;
                
                numeros[valor - 1] = true;
            }
        }
        return true;
    }
    
    private void gerarMensagemIniciandoJogo()
    {
        System.out.print("""
                ===================================================
                                INICIANDO O JOGO          
                """);
    }
    
    protected boolean ehPossivelInserir(int linha, int coluna, int valor) 
    {
        for (int i = 0; i < TAMANHO; i++) 
        {
            if (tabuleiro[linha][i] == valor || tabuleiro[i][coluna] == valor)            
                return false;            
        }

        int linhaInicio = (linha / 3) * 3;
        int colunaInicio = (coluna / 3) * 3;
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
                if (tabuleiro[linhaInicio + i][colunaInicio + j] == valor)                
                    return false;               
            }
        }
        return true;
    }
    
    protected boolean ehPossivelInserirNumero(int linha, int coluna, int valor) 
    {
        return tabuleiro[linha][coluna] == 0 && verificarSeNumeroEhUnico(linha, coluna, valor);
    }
    
    private boolean estaDentroDosLimites(int linha, int coluna, int valor)
    {
        return linha < 1 || linha > 9 || coluna < 1 || coluna > 9 || valor < 1 || valor > 9;
    }
    
    private boolean estaDentroDosLimites(int linha, int coluna)
    {
        return linha < 1 || linha > 9 || coluna < 1 || coluna > 9;
    }
    
    private boolean posicaoEstaPreenchida(int linha, int coluna)
    {
        return tabuleiro[linha][coluna] != 0;
    }
    
    private void inserirValorTabuleiro(int linha, int coluna, int valor)
    {
        tabuleiro[linha][coluna] = valor;
        System.out.printf("Valor %d inserido na posição (%d,%d).%n", valor, linha + 1, coluna + 1);
    }
    
    private int[] posicaoEhValida(String posicao) 
    {
        try 
        {
            int maximoTentativas = Constants.MAX_TENTATIVAS;
            for (int tentativa = 0; tentativa <= maximoTentativas; tentativa++)
            {
                String[] valores = posicao.replace("(", "").replace(")", "").split(",");
                if (valores.length != 2 && tentativa == maximoTentativas)               
                    throw new IllegalArgumentException("Formato inválido. Use o formato (linha,coluna).\nExemplo: (3,4)");
                
                int linha = Integer.parseInt(valores[0].trim()) - 1;
                int coluna = Integer.parseInt(valores[1].trim()) - 1;

                if ((linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO)
                                && tentativa == maximoTentativas)               
                    throw new IllegalArgumentException("Posição fora dos limites do tabuleiro. Valores devem estar entre 1 e 9.");
                
                return new int[]{linha, coluna};
            }
            throw new IllegalArgumentException("Número máximo de tentativas atingido. Formato inválido ou posição fora dos limites.");
        } 
        catch (IllegalArgumentException e)
        {
            throw e;
        }
    }
    
    private String obterValoresPossiveis(int linha, int coluna) 
    {
        StringBuilder valoresPossiveis = new StringBuilder();

        for (int valor = 1; valor <= TAMANHO; valor++)
        {
            if (ehPossivelInserir(linha, coluna, valor)) 
            {
                if (valoresPossiveis.length() > 0)                 
                    valoresPossiveis.append(", ");
                
                valoresPossiveis.append(valor);
            }
            else
            {
                System.out.printf("Não é possível inserir %d na posição (%d,%d).%n", valor, linha + 1, coluna + 1);
            }
        }
        return valoresPossiveis.toString();
    }
    
    private void removerJogada(int linha, int coluna)
    {
        tabuleiro[linha - 1][coluna - 1] = 0; 
        origemValores[linha - 1][coluna - 1] = null; 
        System.out.printf("Jogada removida da posição (%d,%d).%n", linha, coluna);
    }
    
    private boolean jogoEstaCorreto() 
    {
        if (!jogoEstaCompleto())
            return false;
        
        for (int i = 0; i < 9; i++) 
            if (!verificarLinha(i))            
                return false;

        for (int j = 0; j < 9; j++)
            if (!verificarColuna(j))
                return false;          
        
        for (int linha = 0; linha < 9; linha += 3)
            for (int coluna = 0; coluna < 9; coluna += 3)
                if (!verificarSubgrade(linha, coluna))
                    return false;                                  

        return true;
    }

    // </editor-fold>
    
    // <editor-fold desc="Métodos Menu">
    private void mostrarMenu() 
    {
        System.out.println("""
                           ===================================================
                                       X=X=X MENU DE JOGO X=X=X
                           ==> Selecione uma opção:
                           
                           1. Adicionar jogada.
                           2. Remover jogada.
                           3. Verificar.
                           4. Dica.
                           5. Sair.                           
                           """);   
        System.out.print(">> ");
    }
    
    protected void removerJogada(String posicoes) 
    {
        int maximoTentativas = Constants.MAX_TENTATIVAS;
        try 
        {
            for (int tentativa = 0; tentativa < maximoTentativas; tentativa++) 
            {
                // verificar se é mais que uma entrada
                if (!posicoes.matches("(\\(\\d{1},\\d{1}\\))+$")) 
                {
                    System.out.println("Tentativa " + (tentativa + 1) + " falhou. Formato inválido! Tente novamente.");
                    System.out.print(">> Digite a posição para remover (linha,coluna): ");
                    posicoes = new Scanner(System.in).nextLine();
                    continue;
                }

                // se +1 jogada adiciona parênteses em torno dela para unificar o formato
                if (!posicoes.startsWith("("))
                    posicoes = "(" + posicoes + ")";               

                String[] jogadas = posicoes.split("\\)\\(");

                for (int i = 0; i < jogadas.length; i++)                
                    jogadas[i] = jogadas[i].replace("(", "").replace(")", "");
                              
                for (String jogadaIndividual : jogadas) 
                {
                    String[] partes = jogadaIndividual.split(",");

                    int linha = Integer.parseInt(partes[0]);
                    int coluna = Integer.parseInt(partes[1]);
                    
                    if (estaDentroDosLimites(linha, coluna)) 
                    {
                        System.out.println("Tentativa " + (tentativa + 1) + " falhou. Valores fora do intervalo.");
                        continue;
                    }
                    
                    if (origemValores[linha - 1][coluna - 1] != null && origemValores[linha - 1][coluna - 1].equals("automatico"))
                    {
                        System.out.printf("Tentativa %d falhou. Não é possível remover o valor na posição (%d,%d), inserido automaticamente.%n", tentativa + 1, linha, coluna);
                        continue;
                    } 
                    else if (tabuleiro[linha - 1][coluna - 1] == 0) // posição vazia
                    {                      
                        System.out.printf("Tentativa %d falhou. Não há jogada para remover na posição (%d,%d).%n", tentativa + 1, linha, coluna);
                        continue;
                    }
                 
                    removerJogada(linha, coluna);
                }

                return;
            }

            throw new IllegalArgumentException("Número máximo de tentativas atingido. Não foi possível remover a jogada.");
        } 
        catch (IllegalArgumentException e) 
        {
            throw e;
        }
    }
    
    public String verificarJogo() 
    {
        StringBuilder relatorio = new StringBuilder();
        boolean jogoValido = true;

        for (int i = 0; i < 9; i++) 
        {
            if (!verificarLinha(i)) 
            {
                jogoValido = false;
                relatorio.append(String.format("A linha %d contém números duplicados ou inválidos.%n", i + 1));
            }
        }

        for (int j = 0; j < 9; j++) 
        {
            if (!verificarColuna(j)) 
            {
                jogoValido = false;
                relatorio.append(String.format("A coluna %d contém números duplicados ou inválidos.%n", j + 1));
            }
        }

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
    
    protected void adicionarJogada(String jogada)
    {
        int maximoTentativas = Constants.MAX_TENTATIVAS;
        try 
        {
            for (int tentativa = 0; tentativa < maximoTentativas; tentativa++) 
            {
                if (!jogada.matches("(\\(\\d{1},\\d{1},\\d{1}\\))+$")) 
                {
                    System.out.println("Tentativa " + (tentativa + 1) + " falhou. Formato inválido.");
                    continue;
                }

                if (!jogada.startsWith("("))                
                    jogada = "(" + jogada + ")";

                String[] jogadas = jogada.split("\\)\\(");
               
                for (int i = 0; i < jogadas.length; i++)                
                    jogadas[i] = jogadas[i].replace("(", "").replace(")", "");
                               
                for (String jogadaIndividual : jogadas) 
                {
                    String[] valores = jogadaIndividual.split(",");

                    int linha = Integer.parseInt(valores[0]); 
                    int coluna = Integer.parseInt(valores[1]); 
                    int valor = Integer.parseInt(valores[2]);

                    if (estaDentroDosLimites(linha, coluna, valor))
                    {
                        System.out.println("Tentativa " + (tentativa + 1) + " falhou. Valores fora do intervalo.");
                        continue;
                    }

                    if (posicaoEstaPreenchida(linha - 1, coluna - 1)) 
                    { 
                        System.out.printf("Tentativa %d falhou. A posição (%d,%d,%d) já está preenchida.%n", tentativa + 1, linha, coluna, valor);
                        continue;
                    }

                    inserirValorTabuleiro(linha - 1, coluna - 1, valor);
                }

                return;
            }

            throw new IllegalArgumentException("Número máximo de tentativas atingido. Não foi possível inserir a jogada.");
        } 
        catch (IllegalArgumentException e)
        {
            throw e;
        }
    }

    protected String darDica(String posicao) 
    {
        try 
        {
            if (posicao == null || posicao.isBlank()) // validando se a posição é válida  
                return "Posição inválida. Use o formato (linha,coluna).";
        
            int[] coordenadas = posicaoEhValida(posicao);
            int linha = coordenadas[0];
            int coluna = coordenadas[1];

            if (tabuleiro[linha][coluna] != 0) // validando se a posição já possui um valor   
                return String.format("A posição (%d,%d) já possui um valor.", linha + 1, coluna + 1);
            
            String valoresPossiveis = obterValoresPossiveis(linha, coluna);
            return valoresPossiveis.isEmpty()
                ? String.format("Nenhum valor pode ser inserido na posição (%d,%d).", linha + 1, coluna + 1)
                : "Valores possíveis: " + valoresPossiveis;

        } 
        catch (Exception e)
        {
            throw e;
        }
    }
    // </editor-fold>
}

