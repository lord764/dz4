
import java.util.Random;

public class Main {

    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int[] heroesHealth = {260, 250, 270, 220, 300, 200, 210};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 15, 10};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk"};
    public static int roundNumber;
    public static int golem = 0;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            round();
        }
    }

    public static void medic() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] != heroesHealth[3] && heroesHealth[3] > 0 && heroesHealth[i] <= 100) {
                Random random = new Random();
                int healPoints = random.nextInt(31);
                heroesHealth[i] += healPoints;
                System.out.println("The Medic helped: " + heroesAttackType[i] + "+" + healPoints);
                break;
            }
        }
    }

    public static void golem() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] != heroesHealth[4] && heroesHealth[4] > 0) {
                golem = bossDamage / 5;
                heroesHealth[i] += bossDamage / 5;
                heroesHealth[4] -= golem;
            }
        }
        System.out.println("Голем компенсировал 1/5 урона");
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);// 0,1,2
        for (int i = randomIndex; i < heroesAttackType.length; i++) {
            if (randomIndex == 3) {
                continue;
            } else {
                bossDefenceType = heroesAttackType[randomIndex];
            }
        }
        System.out.println("Boss chose: " + bossDefenceType);
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        medic();
        golem();
        printStatistics();
    }

    public static void bossHits() {
        Random random = new Random();
        boolean lucky = random.nextBoolean();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] == heroesHealth[5] && heroesHealth[5] > 0 && lucky) {
                System.out.println("Lucky " + lucky);
                continue;
            } else {
                if (bossHealth > 0 && heroesHealth[i] > 0) {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (heroesHealth[i] == 6) {
                    bossHealth = bossHealth - bossDamage / 2 * heroesDamage[i];
                }
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i]; //  bossHealth -= heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println(roundNumber + " ROUND -------------");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
    }
}
