package model.dao;

import model.entities.Category;
import model.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryDAO {

    Connection conn;

    public CategoryDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean create(String name) {
        String sql = "INSERT INTO category(name) VALUES (?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        }catch (SQLException e) {
            System.out.println("Erro ao criar categoria. Causado por: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        System.out.println("Categoria criado com sucesso!");
        return true;
    }

    public List<Category> list() throws SQLException {
        List<Category> categories = new ArrayList<>();
//        "SELECT id, name, descricao FROM produto;";
        String sql = "SELECT id, name FROM category";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while(resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    Category category = new Category(id, name);
                    categories.add(category);
                }
            }
        }
        return categories;
    }

    public boolean update(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setLong(2, category.getId());
            preparedStatement.execute();
        }catch (SQLException e) {
            System.out.println("Erro ao atualizar categoria. Causado por: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        System.out.println("Categoria " + category.getName() +" criado com sucesso!");
        return true;
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM category WHERE id = ?";
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

    public Category getById(int id) {
        String sql = "SELECT id, name FROM category WHERE id = ?";
        Category category = null;
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                if(resultSet.next()) {
                    String name = resultSet.getString("name");
                    category = new Category(id, name);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao selecionar categoria: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
        System.out.println("Categoria localizada com sucesso");
        return category;
    }

    public List<Category> getByName(String name) {
        String sql = "SELECT id, name FROM category WHERE name LIKE '?' ";
        List<Category> categories = new ArrayList<>();
        Category category = null;
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while(resultSet.next()) {
                    category = new Category(
                            resultSet.getLong("id"),
                            resultSet.getString("name")
                    );
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao selecionar categoria: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
        System.out.println("Categoria(s) localizada com sucesso");
        return categories;
    }

    private void addCategory(Category category, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, category.getName());
        preparedStatement.execute();
    }
}
