package br.ufjf.dcc.dcc025.dcc025.Entities;

import br.ufjf.dcc.dcc025.dcc025.Constants.Constants;
import java.util.Scanner;

public class JogoPersonalizado extends JogoSudoku 
{
    @Override
    public void criarJogo() 
    {
        Scanner scanner = new Scanner(System.in);
        
        gerarMensagemExplicandoComoInserirNumeros(); 
        
        try
        {
            while (true) 
            {
                System.out.println("Insira os valores no formato (linha,coluna,valor) ou (-1,-1,-1) para sair:");
                String valorParaInserir = scanner.nextLine();

                if (valorParaInserir.trim().equals("(-1,-1,-1)")) 
                {
                    System.out.println(" ## Código de parada inserido ##");
                    break;
                }

                String[] posicoes = valorParaInserir.split("\\)\\(");
                for (String posicao : posicoes) 
                {
                    posicao = posicao.replace("(", "").replace(")", "");

                    String[] valores = posicao.split(",");
                    if (valores.length != 3) {
                        System.out.println("Formato inválido. Use o formato (linha,coluna,valor).");
                        continue;
                    }

                    int[] indicesAjustados = ajustarIndices(valores);
                    int linha = indicesAjustados[0];
                    int coluna = indicesAjustados[1];
                    int valor = indicesAjustados[2];

                    int maximoTentativas = Constants.MAX_TENTATIVAS;
                    boolean insercaoBemSucedida = false;

                    for (int tentativa = 0; tentativa < maximoTentativas; tentativa++) {
                        if (!valoresSaoValidos(linha, coluna, valor)) 
                        {
                            System.out.println("Valores inválidos. Insira novamente:");
                            valorParaInserir = scanner.nextLine();

                            if (valorParaInserir.trim().equals("(-1,-1,-1)")) {
                                System.out.println(" ## Código de parada inserido ##");
                                return;
                            }

                            // Atualiza os valores com a nova entrada
                            posicao = valorParaInserir.replace("(", "").replace(")", "");
                            valores = posicao.split(",");
                            if (valores.length != 3) {
                                System.out.println("Formato inválido. Use o formato (linha,coluna,valor).");
                                continue;
                            }

                            indicesAjustados = ajustarIndices(valores);
                            linha = indicesAjustados[0];
                            coluna = indicesAjustados[1];
                            valor = indicesAjustados[2];
                            continue; // Recomeça a tentativa
                        }

                        if (ehPossivelInserir(linha, coluna, valor)) 
                        {
                            tabuleiro[linha][coluna] = valor;
                            System.out.printf("Valor %d inserido na posição (%d,%d) com sucesso.%n", valor, linha + 1, coluna + 1);
                            origemValores[linha][coluna] = "automatico"; // não posso remover eles
                            insercaoBemSucedida = true;
                            break; // Sai do loop de tentativas
                        } 
                        else 
                        {
                            System.out.printf(
                                    "Não foi possível inserir o valor %d na posição (%d,%d). Regras do Sudoku violadas ou posição já preenchida.%n",
                                    valor, linha + 1, coluna + 1);
                        }
                    }

                    if (!insercaoBemSucedida) 
                        System.out.println("Tentativas máximas excedidas. Passando para o próximo valor..."); 
                }
                imprimirTabuleiro();
            }
        }
        catch (IllegalArgumentException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    
    private void gerarMensagemExplicandoComoInserirNumeros()
    {
        System.out.println("""
                    ===================================================
                    Como inserir os números?
                    
                        1. Definir os valores na seguinte ordem
                           (linha,coluna,valor)
                        
                        Por exemplo, (1,3,4) representa linha = 1, coluna = 3 e valor = 4
                        
                        2. É possível adicionar mais que um valor!
                           Como assim?
                           Por exemplo, (2,4,5)(4,4,4)(1,2,3)
                           
                        3. Para encerrar a inserção, basta digitar:
                           (-1,-1,-1)
                        
                        Obs.: Os valores devem estar dentro do intervalo [1,9] para serem considerados válidos!                                 
                    """);
    }
    
    private int[] ajustarIndices(String[] valores) 
    {
        int linha = Integer.parseInt(valores[0]) - 1; 
        int coluna = Integer.parseInt(valores[1]) - 1; 
        int valor = Integer.parseInt(valores[2]);
        
        return new int[] { linha, coluna, valor };
    }
    
    private boolean valoresSaoValidos(int linha, int coluna, int valor)
    {
        // Verifica se os índices estão no intervalo válido para o Sudoku
        if (linha < 0 || linha >= 9 || coluna < 0 || coluna >= 9 || valor < 1 || valor > 9) {
            return false;
        }
        // Verifica se a posição no tabuleiro está vazia
        return tabuleiro[linha][coluna] == 0;
    }
    
    private boolean ehPossivelInserir(int linha, int coluna, int valor)
    {
        return tabuleiro[linha][coluna] == 0 && podeInserirNumero(linha, coluna, valor);
    }

    @Override
    public void IniciarJogo() 
    {
        try
        {
            iniciarJogo();     
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
