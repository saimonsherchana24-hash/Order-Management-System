package aptProject.dao.Interface;

import aptProject.model.MenuItem;
import java.util.List;

public interface MenuItemDAOInterface {
    List<MenuItem> getAllItems();
    List<MenuItem> getItemsByCategory(String category);
    MenuItem getItemById(int id);
    boolean addItem(MenuItem item);
    boolean updateItem(MenuItem item);
    boolean deleteItem(int id);
}
