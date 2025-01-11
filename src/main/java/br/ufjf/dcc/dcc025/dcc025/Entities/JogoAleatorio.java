package br.ufjf.dcc.dcc025.dcc025.Entities;

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
    
    private int quantidadeNumerosUsuarioDesejaSortear(Scanner scanner) 
    {
        gerarMensagemNumerosParaSortear();
        int numerosParaSortear = scanner.nextInt();
        if (!numerosParaSortearEhValido(numerosParaSortear))
            throw new IllegalArgumentException("O número digitado para sortear é inválido!");
        
        return numerosParaSortear;
    }
    
    private void gerarMensagemNumerosParaSortear()
    {
        System.out.println("""
                        ===================================================
                        Criando o jogo...

                        - Quantos números deseja sortear?
                        """);
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
                origemValores[linha][coluna] = "automatico"; // só vou poder remover os manuais
                preenchidos++;
            }
        }
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
