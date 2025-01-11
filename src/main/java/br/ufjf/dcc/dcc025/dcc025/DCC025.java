/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.ufjf.dcc.dcc025.dcc025;

import br.ufjf.dcc.dcc025.dcc025.Entities.JogoAleatorio;
import br.ufjf.dcc.dcc025.dcc025.Entities.Menu;

/**
 *
 * @author Isabella Dias
 */
public class DCC025 
{
    public static void main(String[] args) 
    {
        System.out.println("Hello World!");
        
        try
        {
            Menu menu = new Menu();
            menu.executarMensagemInicial();
            int escolhaUsuario = menu.solicitarEscolhaUsuario();
            
            switch(escolhaUsuario)
            {
                case 1 -> 
                {
                    System.out.println(">> Escolha do usuário: GERAR JOGO ALEATORIO");
                    JogoAleatorio jogoAleatorio = new JogoAleatorio();
                    jogoAleatorio.criarJogo();
                }
                case 2 ->
                {
                    System.out.println(">> Escolha do usuário: DEFINIR O PRÓPRIO JOGO");
                    // Definir o próprio jogo
                }
                case 3 ->
                {
                    System.out.println(">> Escolha do usuário: SAIR");
                    menu.gerarMensagemSair();
                    return;
                }
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());        
        }
        catch(Exception e)
        {
        
        }

    }
}
