package model.dao;

import java.util.List;

public interface TemplateDAO {

    public List<Object> list();

    public boolean update(Object o);

    public boolean delete(int id);

    public Object getById(int id);

    public Object getByName(String name);
}
