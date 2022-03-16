package model.dao;

import connection.FactoryConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class ProductDAO implements TemplateDAO{

    Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean create(String name, String description, int category_id) {
        return false;
    }

    @Override
    public List<Object> list() {
        return null;
    }

    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Object getById(int id) {
        return null;
    }

    @Override
    public Object getByName(String name) {
        return null;
    }
}
