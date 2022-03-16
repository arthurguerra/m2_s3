package view;

import connection.FactoryConnection;
import model.dao.CategoryDAO;
import model.entities.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menus {

    private static Scanner sc = new Scanner(System.in);

    public static Connection conectarBancoDeDados() throws SQLException {
        System.out.println("Bem vindo ao MarketPlace!");
        System.out.println("Insira as credenciais de acesso ao banco de dados");
        System.out.print("Usuário: ");
        String usuario = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        FactoryConnection factoryConnection = new FactoryConnection(usuario, senha);
        return factoryConnection.openConnection();
    }

    public static void menuCrud(Connection conn) throws SQLException {
        CategoryDAO categoryDAO = new CategoryDAO(conn);
        int opcaoOperacao;
        do {
            System.out.println("----------------------------------------------");
            System.out.println("[ 1 ] - CREATE: Criar nova entidade");
            System.out.println("[ 2 ] - READ:   Listar entidades");
            System.out.println("[ 3 ] - UPDATE: Atualizar entidade");
            System.out.println("[ 4 ] - DELETE: Excluir entidade");
            System.out.println("[ 5 ] - GET BY ID: Escolher uma entidade pelo seu ID");
            System.out.println("[ 6 ] - GET BY NAME: Escolher uma entidade pelo seu NOME");
            System.out.println("[ 9 ] - SAIR");
            System.out.println("----------------------------------------------");
            System.out.print("Opção: ");
            opcaoOperacao = Integer.parseInt(sc.nextLine());
            System.out.println("[ 1 ] - Produto");
            System.out.println("[ 2 ] - Categoria");
            System.out.print("Opção: ");
            int opcaoEntidade = Integer.parseInt(sc.nextLine());
            switch (opcaoEntidade) {
                case 1:
                    switch (opcaoOperacao) {
                        case 1:
                            System.out.print("Nome do Produto: ");
                            String nome = sc.nextLine();
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 9:
                            System.out.println("Encerrando o sistema...");
                            break;
                        default:
                            System.err.println("Opção inválida!");
                            break;
                    }
                    break;
                case 2:
                    List<Category> categories;
                    Category category;
                    switch (opcaoOperacao) {
                        case 1:
                            System.out.print("Nome da Categoria: ");
                            String nome = sc.nextLine();
                            categoryDAO.create(nome);
                            break;
                        case 2:
                            categories = categoryDAO.list();
                            categories.forEach(System.out::println);
                            break;
                        case 3:
                            System.out.println("------------------------------------");
                            categories = categoryDAO.list();
                            categories.forEach(System.out::println);
                            System.out.println("------------------------------------");
                            System.out.print("ID da categoria para alteração: ");
                            int opcaoCategoria = Integer.parseInt(sc.nextLine());
                            category = categoryDAO.getById(opcaoCategoria);
                            System.out.println(category);
                            System.out.print("Novo Nome da Categoria: ");
                            nome = sc.nextLine();
                            category.setName(nome);
                            categoryDAO.update(category);
                            break;
                        case 4:
                            System.out.println("------------------------------------");
                            categories = categoryDAO.list();
                            categories.forEach(System.out::println);
                            System.out.println("------------------------------------");
                            System.out.print("ID da categoria que deseja excluir: ");
                            opcaoCategoria = Integer.parseInt(sc.nextLine());
                            categoryDAO.delete(opcaoCategoria);
                            break;
                        case 5:
                            System.out.print("ID da categoria: ");
                            int opcaoId = Integer.parseInt(sc.nextLine());
                            category = categoryDAO.getById(opcaoId);
                            System.out.println("-- Categoria selecionada --");
                            System.out.println(category);
                            break;
                        case 6:
                            System.out.print("Buscar categoria por nome: ");
                            categories = categoryDAO.getByName(sc.nextLine());
                            System.out.println("-- Categorias encontradas -- ");
                            categories.forEach(System.out::println);
                            break;
                        case 9:
                            System.out.println("Encerrando o sistema...");
                            break;
                        default:
                            System.err.println("Opção inválida!");
                            break;
                    }
                    break;
                default:
                    System.err.println("Opção inválida!");
                    break;
            }
        } while(opcaoOperacao != 9);
    }
}
