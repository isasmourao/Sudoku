package br.ufjf.dcc.dcc025.dcc025.Entities;

import br.ufjf.dcc.dcc025.dcc025.Constants.Constants;
import java.util.Random;
import java.util.Scanner;

public class JogoAleatorio extends JogoSudoku  
{
    @Override
    public void criarJogo()
    {
        Scanner scanner = new Scanner(System.in);
        try
        {
            int quantidadeNumerosParaSortear = quantidadeNumerosUsuarioDesejaSortear(scanner);
            
            preencherTabuleiroAleatorio(quantidadeNumerosParaSortear);
            
            imprimirTabuleiro();
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
    
    @Override
    public void IniciarJogo() 
    {
        try
        {
            iniciarJogo();     
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
    
    // <editor-fold desc="Métodos Auxiliares">
    private int quantidadeNumerosUsuarioDesejaSortear(Scanner scanner) 
    {
        System.out.println("""
                ===================================================
                CRIANDO O JOGO""");
       
        int tentativas = 0;
        try
        {
            while (tentativas < Constants.MAX_TENTATIVAS) 
            {
                gerarMensagemNumerosParaSortear();
                int numerosParaSortear = scanner.nextInt();

                if (numerosParaSortearEhValido(numerosParaSortear)) 
                {
                    return numerosParaSortear;
                } 
                else 
                {
                    tentativas++;
                    System.out.println("O número digitado para sortear é inválido!");
                    if (tentativas < Constants.MAX_TENTATIVAS)
                    {
                        System.out.println("Tente novamente."); 
                        System.out.print(">> ");
                    }                                        
                }
            }
            throw new IllegalStateException("Número máximo de tentativas atingido. Não foi possível obter um valor válido.");
        }
        catch (IllegalStateException e) 
        {
            throw e;
        } 
        catch (Exception e) 
        {
            throw e;
        }     
    }
    
    private void gerarMensagemNumerosParaSortear()
    {
        System.out.println("""
                        Quantos números deseja sortear?
                        """);
        System.out.print(">> ");
    }
    
    private boolean numerosParaSortearEhValido(int numerosParaSortear)
    {
        return numerosParaSortear < 81 && numerosParaSortear > 0;
    }
    
    private void preencherTabuleiroAleatorio(int quantidade) 
    {
        Random random = new Random();
        int preenchidos = 0;

        while (preenchidos < quantidade) 
        {
            int linha = random.nextInt(9);
            int coluna = random.nextInt(9);
            int numero = random.nextInt(9) + 1; // números de 1 a 9

            if (tabuleiro[linha][coluna] == 0 && podeInserirNumero(linha, coluna, numero)) 
            {
                tabuleiro[linha][coluna] = numero;
                origemValores[linha][coluna] = "automatico";
                preenchidos++;
            }
        }
    }
    // </editor-fold>
}
