/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.ufjf.dcc.dcc025.dcc025;

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
                    // GeraJogoAleatorio
                }
                case 2 ->
                {
                    // Definir o próprio jogo
                }
                case 3 ->
                {
                    // Sair
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
