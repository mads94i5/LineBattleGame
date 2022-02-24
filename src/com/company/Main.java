package com.company;

import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public int playerField = 11;
    public int enemyField = -11;
    public int playerSoldiers = 25;
    public int enemySoldiers = 25;
    public int playerBullets = 2500;
    public int enemyBullets = 2500;
    public int fieldsAway;

    public boolean debugMessages = false;

    public static void main(String[] args) {
	// write your code here
        new Main().runGame();
    }
    public void resetVariables()
    {
        playerField = 11;
        enemyField = -11;
        playerSoldiers = 25;
        enemySoldiers = 25;
        playerBullets = 2500;
        enemyBullets = 2500;
    }
    public void runGame()
    {
        System.out.println(ANSI_BLUE + "Welcome to the Line Battle game!" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "First the player will roll a dice and move that amount of spots forward on the board." + ANSI_RESET);
        int playerFirstDice = throwDice(6);
        playerMoveForward(playerFirstDice);
        System.out.println(ANSI_GREEN + "Now the enemy will roll a dice and move that amount of spots forward on the board." + ANSI_RESET);
        int enemyFirstDice = throwDice(6);
        enemyMoveForward(enemyFirstDice);
        playerTurn();
    }
    public int throwDice(int sides)
    {
        int diceThrow = ThreadLocalRandom.current().nextInt(1, sides+1);
        if (debugMessages)
        {
            System.out.println("A " + sides + "-sided dice has been thrown! It landed on a " + diceThrow + ".");
        }
        return diceThrow;
    }
    public void playerScout()
    {
        fieldsAway = Math.abs(playerField - enemyField);
        if (debugMessages)
        {
            System.out.println("Enemy is " + fieldsAway + " fields away.");
        }
        if (playerField == enemyField)
        {
            System.out.printf(ANSI_BLUE + "The scout " + ANSI_RED + "is calling red alert! The enemy is here!%n%n" + ANSI_RESET);
        }
        else if ((playerField - 2 <= enemyField) && (playerField > enemyField))
        {
            if (fieldsAway == 1)
            {
                System.out.printf(ANSI_BLUE + "The scout " + ANSI_YELLOW + "has spotted the enemy at " + fieldsAway + " field in front!%n%n" + ANSI_RESET);
            }
            else
            {
                System.out.printf(ANSI_BLUE + "The scout " + ANSI_YELLOW + "has spotted the enemy at " + fieldsAway + " fields in front!%n%n" + ANSI_RESET);
            }
        }
        else if ((playerField + 3 >= enemyField) && (playerField < enemyField))
        {
            if (fieldsAway == 1)
            {
                System.out.printf(ANSI_BLUE + "The scout " + ANSI_YELLOW + "has spotted the enemy at " + fieldsAway + " field behind us!%n%n" + ANSI_RESET);
            }
            else
            {
                System.out.printf(ANSI_BLUE + "The scout " + ANSI_YELLOW + "has spotted the enemy at " + fieldsAway + " fields behind us!%n%n" + ANSI_RESET);
            }
        }
        else
        {
            System.out.printf(ANSI_BLUE + "The scout " + ANSI_GREEN + "can not see anyone, bring out the rations!%n%n" + ANSI_RESET);
        }
    }
    public void playerTurn()
    {
        System.out.printf(ANSI_GREEN + "It's now your turn, what do you want to do?%n%n");
        System.out.println(ANSI_RED + "Enter:" + ANSI_BLUE + " Action");
        System.out.println(ANSI_RED + "M:" + ANSI_BLUE + " Move forward");
        System.out.println(ANSI_RED + "R:" + ANSI_BLUE + " Retreat");
        System.out.println(ANSI_RED + "A:" + ANSI_BLUE + " Attack");
        Scanner sc = new Scanner(System.in);
        boolean hasSelectedChoice = false;
        while (!hasSelectedChoice)
        {
            System.out.print(ANSI_RED + "Enter" + ANSI_RESET + " your " + ANSI_BLUE + "action" + ANSI_RESET + ": ");
            String playerInput = sc.nextLine();
            if ((playerInput.equals("M")) || (playerInput.equals("m")))
            {
                hasSelectedChoice = true;
                int diceThrow = throwDice(6);
                if (diceThrow % 2 == 0)
                {
                    playerMoveForward(2);
                    enemyTurn();
                }
                else
                {
                    playerMoveForward(1);
                    enemyTurn();
                }
            }
            else if ((playerInput.equals("R")) || (playerInput.equals("r")))
            {
                hasSelectedChoice = true;
                int diceThrow = throwDice(6);
                if ((diceThrow == 1) || (diceThrow == 2))
                {
                    playerRetreat(1);
                }
                else if ((diceThrow == 3) || (diceThrow == 4))
                {
                    playerRetreat(2);
                }
                else if ((diceThrow == 5) || (diceThrow == 6))
                {
                    playerRetreat(3);
                }
            }
            else if ((playerInput.equals("A")) || (playerInput.equals("a")))
            {
                hasSelectedChoice = true;
                int diceThrow = throwDice(6);
                playerAttack(diceThrow);
            }
            else
            {
                System.out.println(ANSI_YELLOW + "You can only select '" + ANSI_RED + "M" + ANSI_YELLOW + "', '" + ANSI_RED + "R" + ANSI_YELLOW + "' or '" + ANSI_RED + "A" + ANSI_YELLOW + "'. You entered: " + ANSI_RED + playerInput + ANSI_YELLOW + ". Try again." + ANSI_RESET);
            }
        }
    }
    public void playerMoveForward(int moveFields)
    {
        System.out.print(ANSI_GREEN + "Player moving forward ");
        if (playerField - moveFields > -11)
        {
            playerField -= moveFields;
            if (moveFields == 1)
            {
                System.out.printf(ANSI_BLUE + moveFields + ANSI_GREEN + " field!%n%n" + ANSI_RESET);
            }
            else
            {
                System.out.printf(ANSI_BLUE + moveFields + ANSI_GREEN + " fields!%n%n" + ANSI_RESET);
            }
        }
        else
        {
            int previousField = playerField;
            playerField = -10;
            int playerMovedFields = Math.abs(previousField - playerField);
            if (playerMovedFields == 1)
            {
                System.out.printf(ANSI_BLUE + playerMovedFields + ANSI_GREEN + " field!%n%n" + ANSI_RESET);
            }
            else
            {
                System.out.printf(ANSI_BLUE + playerMovedFields + ANSI_GREEN + " fields!%n%n" + ANSI_RESET);
            }
        }
        playerScout();
        printStatusMessages();
    }
    public void playerRetreat(int moveFields)
    {
        System.out.print(ANSI_GREEN + "Player retreating ");
        if (playerField + moveFields < 11)
        {
            playerField += moveFields;
            if (moveFields == 0)
            {
                System.out.println(ANSI_BLUE + moveFields + ANSI_GREEN + " fields. We are cornered, so we didn't find any ammo!" + ANSI_RESET);
            }
            else if (moveFields == 1)
            {
                System.out.print(ANSI_BLUE + moveFields + ANSI_GREEN + " field. " + ANSI_RESET);
                int gainedAmmo = moveFields * 250;
                playerBullets += gainedAmmo;
                System.out.printf(ANSI_GREEN + "And gained " + ANSI_BLUE + gainedAmmo + ANSI_GREEN + " ammo!%n%n");
            }
            else
            {
                System.out.print(ANSI_BLUE + moveFields + ANSI_GREEN + " fields. " + ANSI_RESET);
                int gainedAmmo = moveFields * 250;
                playerBullets += gainedAmmo;
                System.out.printf(ANSI_GREEN + "And gained " + ANSI_BLUE + gainedAmmo + ANSI_GREEN + " ammo!%n%n");
            }
        }
        else
        {
            int previousField = playerField;
            playerField = 10;
            int playerMovedFields = Math.abs(previousField - playerField);
            if (playerMovedFields == 0)
            {
                System.out.println(ANSI_BLUE + playerMovedFields + ANSI_GREEN + " fields. We are cornered, so we didn't find any ammo!" + ANSI_RESET);
            }
            else if (playerMovedFields == 1)
            {
                System.out.print(ANSI_BLUE + playerMovedFields + ANSI_GREEN + " field. " + ANSI_RESET);
                int gainedAmmo = moveFields * 250;
                playerBullets += gainedAmmo;
                System.out.printf(ANSI_GREEN + "And gained " + ANSI_BLUE + gainedAmmo + ANSI_GREEN + " ammo!%n%n");
            }
            else
            {
                System.out.print(ANSI_BLUE + playerMovedFields + ANSI_GREEN + " fields. " + ANSI_RESET);
                int gainedAmmo = moveFields * 250;
                playerBullets += gainedAmmo;
                System.out.printf(ANSI_GREEN + "And gained " + ANSI_BLUE + gainedAmmo + ANSI_GREEN + " ammo!%n%n");
            }
        }
        playerScout();
        printStatusMessages();
        enemyTurn();
    }
    public void playerAttack(int ammoMultiplier)
    {
        System.out.println(ANSI_GREEN + "Trying to attack the enemy!" + ANSI_RESET);
        if (playerBullets > ammoMultiplier * 100)
        {
            fieldsAway = Math.abs(playerField - enemyField);
            if (fieldsAway <= 6)
            {
                playerBullets -= ammoMultiplier * 100;
                int soldiersKilled = 6 - fieldsAway;
                enemySoldiers -= soldiersKilled;
                if (enemySoldiers < 1)
                {
                    enemySoldiers = 0;
                    System.out.printf(ANSI_GREEN + "You killed " + ANSI_RED + soldiersKilled + ANSI_GREEN + " of the enemy soldiers!%n%n" + ANSI_RESET);
                    printStatusMessages();
                    gameOver("You");
                }
                else
                {
                    System.out.printf(ANSI_GREEN + "You killed " + ANSI_RED + soldiersKilled + ANSI_GREEN + " of the enemy soldiers!%n%n" + ANSI_RESET);
                    printStatusMessages();
                    enemyTurn();
                }
            }
            else
            {
                System.out.printf(ANSI_RED + "Can not reach the enemy, they are too far away!%n%n" + ANSI_RESET);
                printStatusMessages();
                enemyTurn();
            }
        }
        else
        {
            System.out.printf(ANSI_RED + "Not enough ammo to attack, retreat to get some more!%n%n" + ANSI_RESET);
            printStatusMessages();
            enemyTurn();
        }
    }
    public void enemyTurn()
    {
        System.out.println(ANSI_RED + "It's now the enemy's turn." + ANSI_RESET);
        if ((enemyBullets < 250) && (enemyField > -10))
        {
            int diceThrow = throwDice(6);
            if ((diceThrow == 1) || (diceThrow == 2))
            {
                enemyRetreat(1);
            }
            else if ((diceThrow == 3) || (diceThrow == 4))
            {
                enemyRetreat(2);
            }
            else if ((diceThrow == 5) || (diceThrow == 6))
            {
                enemyRetreat(3);
            }
        }
        else if ((fieldsAway <= 6) && (enemyBullets >= 250))
        {
            int diceThrow = throwDice(6);
            enemyAttack(diceThrow);
        }
        else if (enemyField < playerField)
        {
            int diceThrow = throwDice(6);
            if (diceThrow % 2 == 0)
            {
                enemyMoveForward(2);
                playerTurn();
            }
            else
            {
                enemyMoveForward(1);
                playerTurn();
            }
        }
        else
        {
            int diceThrow = throwDice(6);
            if ((diceThrow == 1) || (diceThrow == 2))
            {
                if (diceThrow % 2 == 0)
                {
                    enemyMoveForward(2);
                    playerTurn();
                }
                else
                {
                    enemyMoveForward(1);
                    playerTurn();
                }
            }
            else if ((diceThrow == 3) || (diceThrow == 4))
            {
                enemyRetreat(2);
            }
            else if ((diceThrow == 5) || (diceThrow == 6))
            {
                enemyAttack(diceThrow);
            }
        }
    }
    public void enemyMoveForward(int moveFields)
    {
        System.out.printf(ANSI_RED + "The enemy is moving forward!%n%n" + ANSI_RESET);
        if (enemyField + moveFields < 11)
        {
            enemyField += moveFields;
        }
        else
        {
            enemyField = 10;
        }
        playerScout();
        printStatusMessages();
    }
    public void enemyRetreat(int moveFields)
    {
        System.out.printf(ANSI_RED + "The enemy is retreating!%n%n" + ANSI_RESET);
        if (enemyField - moveFields < 11)
        {
            enemyField -= moveFields;
        }
        else
        {
            enemyField = -10;
        }
        enemyBullets += moveFields * 250;
        playerScout();
        printStatusMessages();
        playerTurn();
    }
    public void enemyAttack(int ammoMultiplier)
    {
        System.out.println(ANSI_RED + "The enemy is trying to attack!" + ANSI_RESET);
        if (enemyBullets > ammoMultiplier * 100)
        {
            fieldsAway = Math.abs(playerField - enemyField);
            if (fieldsAway <= 6)
            {
                enemyBullets -= ammoMultiplier * 100;
                int soldiersKilled = 6 - fieldsAway;
                playerSoldiers -= soldiersKilled;
                if (playerSoldiers < 1)
                {
                    playerSoldiers = 0;
                    System.out.printf(ANSI_RED + "The enemy killed " + ANSI_GREEN + soldiersKilled + ANSI_RED + " of your soldiers! You have " + ANSI_GREEN + playerSoldiers + ANSI_RED + " remaining!%n%n" + ANSI_RESET);
                    printStatusMessages();
                    gameOver("enemy");
                }
                else
                {
                    System.out.printf(ANSI_RED + "The enemy killed " + ANSI_GREEN + soldiersKilled + ANSI_RED + " of your soldiers! You have " + ANSI_GREEN + playerSoldiers + ANSI_RED + " remaining!%n%n" + ANSI_RESET);
                    printStatusMessages();
                    playerTurn();
                }
            }
            else
            {
                System.out.printf(ANSI_RED + "The enemy can not reach you, your troops are too far away!%n%n" + ANSI_RESET);
                printStatusMessages();
                playerTurn();
            }
        }
        else
        {
            System.out.printf(ANSI_RED + "Enemy does not have enough ammo to attack!%n%n" + ANSI_RESET);
            printStatusMessages();
            playerTurn();
        }
    }
    public void gameOver(String winner)
    {
        if (winner.equals("You"))
        {
            System.out.println(ANSI_GREEN + "Game over! " + winner + " win!" + ANSI_RESET);
            System.out.printf(ANSI_BLUE + winner + " might have gotten lucky this time. Don't get cocky.%n%n" + ANSI_RESET);
        }
        else
        {
            System.out.println(ANSI_RED + "Game over, the " + winner + " wins!" + ANSI_RESET);
            System.out.printf(ANSI_RED + "Can't believe you actually lost to a glorified calculator. What a dummy!%n%n" + ANSI_RESET);
        }
        playAgain();
    }
    public void playAgain()
    {
        boolean answeredYesNo = false;
        while (!answeredYesNo)
        {
            // Initialize scanner
            Scanner keyboard = new Scanner(System.in).useLocale(Locale.ENGLISH);
            System.out.print(ANSI_BLUE + "Do you wish to play again? (" + ANSI_RED + "Y" + ANSI_BLUE + "/" + ANSI_RED + "N" + ANSI_BLUE + "): " + ANSI_RESET);
            String yesNo = keyboard.next();
            if ((yesNo.equals("Y")) || (yesNo.equals("y")))
            {
                System.out.println(ANSI_BLUE + "Ok. Restarting game." + ANSI_RESET);
                answeredYesNo = true;
                resetVariables();
                runGame();
            }
            else if ((yesNo.equals("N")) || (yesNo.equals("n")))
            {
                System.out.println(ANSI_RED + "Me too, this is not exactly the most intriguing game. Let's GTFO." + ANSI_RESET);
                answeredYesNo = true;
            }
            if (!answeredYesNo)
            {
                System.out.println(ANSI_YELLOW + "You can only enter '" + ANSI_RED + "Y" + ANSI_YELLOW + "' or '" + ANSI_RED + "N" + ANSI_YELLOW + "'. You entered: " + ANSI_RED + yesNo + ANSI_YELLOW + ". Try again." + ANSI_RESET);
            }
        }
    }
    public void printStatusMessages()
    {
        if (debugMessages)
        {
            System.out.println("Status:");
            System.out.println("fieldsAway: " + fieldsAway);
            System.out.println("playerField: " + playerField);
            System.out.println("enemyField: " + enemyField);
            System.out.println("playerSoldiers: " + playerSoldiers);
            System.out.println("enemySoldiers: " + enemySoldiers);
            System.out.println("playerBullets: " + playerBullets);
            System.out.printf("enemyBullets: " + enemyBullets + "%n%n");
        }
    }
}
