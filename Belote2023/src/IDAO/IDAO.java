package IDAO;
import java.util.List;

public interface IDAO<T> {
    public void add(T obj);
    public void delete (int id);
    public T getOne(int id);
    public List<T> getAll();
}
