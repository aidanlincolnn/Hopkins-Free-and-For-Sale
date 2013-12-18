package oose.group13.hffs.data;
/**
 * ItemTypeUtility to get the item type based on an int position for the client
 * @author Lucas
 *
 */
public class ItemTypeUtil{
	public static ItemType getItemType(int position) {
		switch(position) {
		 	case 0:
		 		return ItemType.BOOKS;
		 	case 1:
		 		return ItemType.FURNITURE;
		 	case 2:
		 		return ItemType.ELECTRONICS;
		 	case 3:
		 		return ItemType.CLOTHES;
		 	case 4:
		 		return ItemType.MISC;
		 	case 5:
		 		return ItemType.GAMES;
		 	default:
		 		return ItemType.BOOKS;
		 		
		}
	}
}