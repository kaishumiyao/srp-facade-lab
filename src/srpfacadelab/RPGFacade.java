package srpfacadelab;

public class RPGFacade{

    private InventoryManagement inventory;
    private PlayerAction actions;

    public RPGFacade(IGameEngine gameEngine, RpgPlayer player){
        inventory = new InventoryManagement(gameEngine,player);
        actions = new PlayerAction(gameEngine, player);
    }

    public void useItem(Item item){
        actions.useItem(item);
    }

    public boolean pickUpItem(Item item){
        return inventory.pickUpItem(item);
    }

    public void takeDamage(int damage){
        inventory.takeDamage(damage);
    }


}