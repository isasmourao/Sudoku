package br.ufjf.dcc.dcc025.dcc025;

import br.ufjf.dcc.dcc025.dcc025.Entities.JogoAleatorio;
import br.ufjf.dcc.dcc025.dcc025.Entities.JogoPersonalizado;
import br.ufjf.dcc.dcc025.dcc025.Entities.JogoSudoku;
import br.ufjf.dcc.dcc025.dcc025.Entities.MenuInicial;

/**
 * @author Isabella Mourão dos Santos Dias
 */
public class DCC025 
{
    public static void main(String[] args) 
    {
        System.out.println("Hello World!");
        
        try
        {
            MenuInicial menu = new MenuInicial();
            menu.executarMensagemInicial();
            int escolhaUsuario = menu.solicitarEscolhaUsuario();
            
            JogoSudoku jogo = null;
            switch(escolhaUsuario)
            {
                case 1 -> 
                {
                    System.out.println(">> Escolha do usuário: GERAR JOGO ALEATORIO");
                    jogo = new JogoAleatorio();
                }
                case 2 ->
                {
                    System.out.println(">> Escolha do usuário: DEFINIR O PRÓPRIO JOGO");
                    jogo = new JogoPersonalizado();
                }
                case 3 ->
                {
                    System.out.println(">> Escolha do usuário: SAIR");
                    menu.gerarMensagemSair();
                    return;
                }
                default -> 
                {
                    System.out.println(">> Escolha inválida!");
                    return;
                }
            }
            
            if (jogo != null)
            {
                jogo.criarJogo();
                jogo.IniciarJogo();
            }
            else
                throw new Exception(" ### Não foi possível criar o jogo, por favor inicie novamente! ###");
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());        
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage()); 
        }      
    }
}
