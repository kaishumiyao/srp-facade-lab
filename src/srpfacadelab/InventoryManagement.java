package srpfacadelab;

import java.util.ArrayList;
import java.util.List;

public class InventoryManagement {

    private RpgPlayer player;
    private final IGameEngine gameEngine;

    public InventoryManagement(IGameEngine gameEngine, RpgPlayer player){
        this.gameEngine = gameEngine;
        this.player = player;
    }

    public boolean pickUpItem(Item item) {
        int weight = calculateInventoryWeight();
        if (weight + item.getWeight() > player.getCarryingCapacity())
            return false;

        if (item.isUnique() && checkIfItemExistsInInventory(item))
            return false;

        // Don't pick up items that give health, just consume them.
        if (item.getHeal() > 0) {
            player.setHealth(player.getHealth() + item.getHeal());

            if (player.getHealth() > player.getMaxHealth())
                player.setHealth(player.getMaxHealth());

            if (item.getHeal() > 500) {
                gameEngine.playSpecialEffect("green_swirly");
            }

            return true;
        }

        if (item.isRare()){
            if(item.isUnique()){
                gameEngine.playSpecialEffect("blue-swirly");
            }
            else{
                gameEngine.playSpecialEffect("cool_swirly_particles");
            }
        }
            

        player.getInventory().add(item);

        calculateStats();

        return true;
    }
    private int calculateInventoryWeight() {
        int sum=0;
        for (Item i: player.getInventory()) {
            sum += i.getWeight();
        }
        return sum;
    }

    private boolean checkIfItemExistsInInventory(Item item) {
        for (Item i: player.getInventory()) {
            if (i.getId() == item.getId())
                return true;
        }
        return false;
    }
    
    private void calculateStats() {
        for (Item i: player.getInventory()) {
            player.setArmour(player.getArmour() + i.getArmour());
        }
    }
    public void takeDamage(int damage) {
        if (player.getCarryingCapacity()/2 > calculateInventoryWeight()){
            damage = (int)(0.75 * damage);
        }
        if (damage < player.getArmour()) {
            gameEngine.playSpecialEffect("parry");
        }
        

        int damageToDeal = damage - player.getArmour();
        player.setHealth(player.getHealth()-damageToDeal);

        gameEngine.playSpecialEffect("lots_of_gore");
    }
}