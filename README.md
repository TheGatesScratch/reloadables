# Reloadables

Reloadables is a very small library minecraft plugin for use in my personal projects.

## Why

While creating a custom item plugin, where the items were dynamically loaded ( Using a command ) from a configuration file ( Skiller ), I came across an issue.

I reloaded the items like this:
- Load all items from configuration
- Check each inventory for custom items.
- Delete custom item.
- Replace with new item with the newly loaded settings.

This works fine for static items that are all the same, but doesn't when applying **per item data**, e.g;

- Enchantments
- Plugin nbt data

This is because it will just override the changes done to the old item...

## How

That's why I created the Transferrer and TransferrerCollection system. 
The plugin that reloads the items / entities etc. defines a TransferCollection, and applies it to every 'thing' when it requires a reload, **supplying the old and new state**.
You can then add as many Transferrers as you want, e.g to *transfer* the custom item id, ( custom ) enchantments, and what not..!
