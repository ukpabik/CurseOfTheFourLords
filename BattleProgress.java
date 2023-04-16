package data;

public class BattleProgress {
	public static boolean demonLordKilled,iceLordKilled,thunderLordKilled,grassLordKilled;
	
	public static boolean allBossesDefeated() {
		if (demonLordKilled == true && iceLordKilled == true &&
				thunderLordKilled == true && grassLordKilled == true) {
			return true;
		}
		else {
			return false;
		}
	}
}
