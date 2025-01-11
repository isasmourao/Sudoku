/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufjf.dcc.dcc025.dcc025.Entities;

import java.util.Scanner;

/**
 *
 * @author isabe
 */
public class JogoAleatorio extends JogoSudoku  
{
    public JogoAleatorio(int numerosParaSortear)
    {
        super(numerosParaSortear);
    }
    
    
    @Override
    public int quantidadeNumerosUsuarioDesejaSortear() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public void criarJogo()
    {
        Scanner scanner = new Scanner(System.in);
        try
        {
            gerarMensagemNumerosParaSortear();
            int numerosParaSortear = scanner.nextInt();
            if (!numerosParaSortearEhValido(numerosParaSortear))
                throw new IllegalArgumentException("O número digitado para sortear é inválido!");
            
            
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
}
