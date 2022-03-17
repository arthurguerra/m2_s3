package model.dao;

import connection.FactoryConnection;
import model.entities.Category;
import model.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO{

    Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean create(String name, String description, int category_id) {
        String sql = "INSERT INTO product (name, description, category_id) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, category_id);
        } catch (SQLException e) {
            System.err.println("Erro ao criar produto: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
        System.out.println("Produto criado com sucesso!");
        return true;
    }

    public List<Product> list() {
        List<Product> products = new ArrayList<>();
//        "SELECT id, name, descricao FROM produto;";
        String sql = "SELECT p.id as p.id, p.name as p.namee, p.description as p.description, p.category_id as p.category_id, c.id as c.id, c.name as c.name FROM product p" +
                "INNER JOIN category c ON c.id = p.category_id";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while(resultSet.next()) {
                    long id = resultSet.getLong("p.id,");
                    String name = resultSet.getString("p.name");
                    String description = resultSet.getString("p.description");
                    long category_id = resultSet.getLong("p.category_id");
                    Category category = new Category(category_id, resultSet.getString("c.name"));
                    Product product = new Product(id, name, description, category);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar Produtos: "+e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    public boolean update(Product product) {
        String sql = "UPDATE product SET name = ?, description = ?, category_id = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setLong(3, product.getCategory().getId());
            preparedStatement.setLong(4, product.getId());
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
        System.out.println("Produto atualizado com sucesso!");
        return true;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() == 0) {
                System.out.println("Produto não encontrado no banco. Não deletado!");
                return false;
            } else {
                System.out.println("Produto deletado com sucesso");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar categoria: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Product getById(int id) {
        String sql = "SELECT product.id as p.id, name, description, category_id, c.name as c.name FROM product INNER JOIN c ON product.category_id = c.id WHERE p.id = ?";
        Product product = null;
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                if(resultSet.next()) {
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    long category_id = resultSet.getLong("category_id");
                    String category_name = resultSet.getString("c.name");
                    Category category = new Category(category_id, category_name);
                    product = new Product(id, name, description, category);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao selecionar categoria por ID: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
        System.out.println("Categoria localizada com sucesso");
        return product;
    }

    public Product getByName(String name) {
        String sql = "SELECT p.id as p.id, p.name as p.name, p.description as p.description, p.category_id as p.category_id, c.name as c.name FROM product as p " +
                "INNER JOIN category as c ON c.id = p.category_id WHERE name LIKE '?'";
        Product product = null;
        Category category = null;
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                if(resultSet.next()) {
                    category = new Category(resultSet.getLong("p.category_id"),
                            resultSet.getString("c.name"));
                    product = new Product(
                            resultSet.getLong("p.id"),
                            resultSet.getString("p.name"),
                            resultSet.getString("p.description"),
                            category);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao selecionar produto: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
        System.out.println("Produto localizado com sucesso");
        return product;
    }

}
