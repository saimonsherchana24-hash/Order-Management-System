package aptProject.dao.Interface;

import aptProject.model.MenuItem;
import java.util.List;

/**
 * MenuItemDAOInterface — contract for all menu item database operations.
 * <p>Defines the CRUD operations available on the {@code menu_items} table.
 * Implemented by {@code MenuItemDAO}. Using this interface allows servlets
 * and services to depend on the abstraction rather than the concrete class.</p>
 */
public interface MenuItemDAOInterface {

    /**
     * Retrieves every menu item, ordered by category then name.
     * @return a list of all {@link MenuItem} objects (may be empty, never {@code null})
     */
    List<MenuItem> getAllItems();

    /**
     * Retrieves all menu items that belong to a specific category.
     * @param category the category to filter by (e.g. "food", "drinks", "dessert")
     * @return a list of matching {@link MenuItem} objects (may be empty, never {@code null})
     */
    List<MenuItem> getItemsByCategory(String category);

    /**
     * Retrieves a single menu item by its primary key.
     * @param id the ID of the menu item to fetch
     * @return the matching {@link MenuItem}, or {@code null} if not found
     */
    MenuItem getItemById(int id);

    /**
     * Inserts a new menu item into the database.
     * @param item the {@link MenuItem} to persist (id field is ignored; auto-generated)
     * @return {@code true} if the item was created successfully; {@code false} otherwise
     */
    boolean addItem(MenuItem item);

    /**
     * Updates an existing menu item's details.
     * @param item the {@link MenuItem} with updated values (id must match an existing row)
     * @return {@code true} if the row was updated; {@code false} otherwise
     */
    boolean updateItem(MenuItem item);

    /**
     * Deletes a menu item by its primary key.
     * @param id the ID of the menu item to remove
     * @return {@code true} if the row was deleted; {@code false} otherwise
     */
    boolean deleteItem(int id);
}
