package knight_game;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Generator {
    private static List<Knight> knightList;
    private static List<Equipment> equipmentList;

    public Generator() {
    }

    public static List<Knight> createKnightsList(){
        knightList = new ArrayList<>();
        knightList.add(new Knight(0,"Bruno Barbarian",23,8,18,15));
        knightList.add(new Knight(1,"Willow the Warrior",15,12,17,20));
        knightList.add(new Knight(2,"Terry the Thieve",10,16,13,25));
        knightList.add(new Knight(3,"Avenger Archibald",17,13,20,14));
        knightList.add(new Knight(4,"Crazy Chris",12,15,7,30));
        knightList.add(new Knight(5,"Paladin Peter",17,15,22,10));
        knightList.add(new Knight(6,"Riddick the Rouge",16,16,16,16));

        return knightList;
    }
    public static List<Equipment> createEquipmentList(){
        equipmentList = new ArrayList<>();
        equipmentList.add(new Weapon("Wooden stick",18,2,290));
        equipmentList.add(new Weapon("Silver dagger",21,4,350));
        equipmentList.add(new Weapon("Short sword",27,5,390));
        equipmentList.add(new Weapon("Iron Spear",29,6,420));
        equipmentList.add(new Weapon("Long sword",33,7,475));
        equipmentList.add(new Weapon("Smash Hammer",48,15,520));
        equipmentList.add(new Weapon("Mighty Axe",40,10,600));

        equipmentList.add(new Armour("Fabric shirt",16,3,350));
        equipmentList.add(new Armour("Leather jacket",20,4,385));
        equipmentList.add(new Armour("Leather armour",24,5,400));
        equipmentList.add(new Armour("Plate armour",28,6,425));
        equipmentList.add(new Armour("Steel plate armour",31,10,480));
        equipmentList.add(new Armour("Golden Armour",35,7,580));
        equipmentList.add(new Armour("Mithril shirt",38,5,660));

        return equipmentList;
    }


}
