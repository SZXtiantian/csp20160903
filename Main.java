package csp20160903;


import java.util.LinkedList;
import java.util.Scanner;

class Role{
	int health;
	int attack;
	LinkedList<Role> summon;

	public Role() {
		health = 30;
		attack = 0;
		summon = new LinkedList<>();
	}

	public Role(int ack, int hp) {
		health = hp;
		attack = ack;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
}
/**
 * 
* @Description
* @author TimTim Email:754595995@qq.com
* @version
* @date 2022年10月20日下午2:13:42
*
 */
public class Main {
	public static void attack(Role Hero1, Role Hero2, int attacker, int defender) {
		attacker -= 1;
		if (defender == 0) {
			Hero2.setHealth(Hero2.getHealth() - Hero1.summon.get(attacker).getAttack());
			if (Hero1.summon.get(attacker).getHealth() <= 0) {
				Hero1.summon.remove(attacker);
			}
		} else {
			defender -= 1;
			Hero2.summon.get(defender)
					.setHealth(Hero2.summon.get(defender).getHealth() - Hero1.summon.get(attacker).getAttack());
			Hero1.summon.get(attacker)
					.setHealth(Hero1.summon.get(attacker).getHealth() - Hero2.summon.get(defender).getAttack());
			if (Hero1.summon.get(attacker).getHealth() <= 0) {
				Hero1.summon.remove(attacker);
			}
			if (Hero2.summon.get(defender).getHealth() <= 0) {
				Hero2.summon.remove(defender);
			}
		}
	}

	public static void callSummon(Role Hero, int pos, int ack, int hp) {
		if (Hero.summon.size() == 7) {
			return;
		}
		Hero.summon.add(pos - 1, new Role(ack, hp));
	}

	public static int winner(Role Hero1, Role Hero2) {
		return Hero1.getHealth() < Hero2.getHealth() ? -1 : 1;
	}
	public static boolean GameOver(Role Hero1, Role Hero2) {
		return Hero1.getHealth() <= 0 || Hero2.getHealth() <= 0 ? true : false;
	}
	public static void print(Role Hero) {
		System.out.println(Hero.getHealth());
		System.out.print(Hero.summon.size());
		//排序！！！
		//Hero.summon.sort(null);
		for (int i = 0; i < Hero.summon.size(); i++) {
			System.out.print(" " + Hero.summon.get(i).getHealth());
		}
		System.out.println();
	}
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		Role Hero1 = new Role();
		Role Hero2 = new Role();
		int flag = 0;
		for (int i = 1; i <= n; i++) {
			String act = scanner.next();
			if (flag == 0) {
				if (act.equals("attack")) {
					int attacker = scanner.nextInt();
					int defender = scanner.nextInt();
					attack(Hero1, Hero2, attacker, defender);
				} else if (act.equals("summon")) {
					int pos = scanner.nextInt();
					int ack = scanner.nextInt();
					int hp = scanner.nextInt();
					callSummon(Hero1, pos, ack, hp);
				} else if (act.equals("end")) {
					flag = 1;
					continue;
				}
				
			}else if (flag == 1) {
				if (act.equals("attack")) {
					int attacker = scanner.nextInt();
					int defender = scanner.nextInt();
					attack(Hero2, Hero1, attacker, defender);
				} else if (act.equals("summon")) {
					int pos = scanner.nextInt();
					int ack = scanner.nextInt();
					int hp = scanner.nextInt();
					callSummon(Hero2, pos, ack, hp);
				} else if (act.equals("end")) {
					flag = 0;
					continue;
				}
			}
			if(GameOver(Hero1, Hero2))break;
		}
		if (GameOver(Hero1, Hero2)) {
			System.out.println(winner(Hero1, Hero2));
		}else {
			System.out.println(0);
		}
		print(Hero1);
		print(Hero2);
		scanner.close();
	}
}
