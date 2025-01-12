package br.ufjf.dcc.dcc025.dcc025.Entities;
import br.ufjf.dcc.dcc025.dcc025.Constants.Constants;
import java.util.Scanner;
import br.ufjf.dcc.dcc025.dcc025.Entities.Interfaces.Menulnterface;

public class MenuInicial implements Menulnterface 
{
    @Override
    public void executarMensagemInicial()
    {
        System.out.println("""
                           ===================================================
                           Bem vindo ao jogo Sudoku!"
                           
                           Regras:
                           1. Não repetir números na horizontal (linha).
                           2. Não repetir números na vertical (colunas).
                           3. Não repetir números nos quadrados de tamanho 3x3.
                           ===================================================""");
    }
    
    @Override
    public int solicitarEscolhaUsuario()
    {
        Scanner scanner = new Scanner(System.in);
        try
        {
            gerarMenuOpcoesUsuario();
            
            int opcaoEscolhidaUsuario = scanner.nextInt();
            if (!verificarOpcaoEscolhidaEhValida(opcaoEscolhidaUsuario) )
            {
                int tentativasMaximas = Constants.MAX_TENTATIVAS;
                for (int tentativa = 3; tentativa >= tentativasMaximas; tentativa--)
                {
                    gerarMensagemOpcaoInvalidaTenteNovamente(tentativa);
                    gerarMenuOpcoesUsuario();
                    opcaoEscolhidaUsuario = scanner.nextInt();

                    if (verificarOpcaoEscolhidaEhValida(opcaoEscolhidaUsuario))
                        break;

                    if (tentativa == 1)
                        throw new IllegalArgumentException("Número de tentativas excedido. Programa será finalizado!");                    
                }
            }

            return opcaoEscolhidaUsuario;
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
    
    private boolean verificarOpcaoEscolhidaEhValida(int opcaoEscolhida)
    {
        return opcaoEscolhida >= 1 && opcaoEscolhida <= 3;
    }
    
    private void gerarMenuOpcoesUsuario()
    {
        System.out.println("""
                           Escolha uma das opções abaixo:
                           
                           1. Gerar um jogo aleatório.
                           2. Definir o próprio jogo.
                           3. Sair.
                           """);
        System.out.print(">> ");
    }
    
    private void gerarMensagemOpcaoInvalidaTenteNovamente(int tentativa)
    {
        System.out.println("""
                    >> Opção selecionada inválida! Por favor, tente novamente
                    - Tentativa 1 de """ + tentativa); 
    }
    
    public void gerarMensagemSair()
    {
        System.out.println("""
                    >> Finalizando o programa!"""); 
    } 
    
    public boolean desejaJogarNovamente() 
    {
        System.out.println("""
                           ===================================================
                                        Deseja jogar novamente?
                                        1. Sim
                                        2. Não
                           ===================================================
                           """);
        
        System.out.print(">> ");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1)              
            return true;        
        else 
        {
            System.out.println("Obrigado por jogar! Até a próxima :D");
            return false;
        }
    }
}
