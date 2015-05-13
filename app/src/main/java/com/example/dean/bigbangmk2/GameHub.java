package com.example.dean.bigbangmk2;

/**
 * Created by Dean on 25/03/2015.
 */

public class GameHub {

    public static final int NO_SELECTION = 0;
    public static final int ROCK = 1;
    public static final int PAPER = 2;
    public static final int SICSSOR = 3;
    public static final int LIZARD = 4;
    public static final int SPOCK = 5;


    public static int RESULT = NO_SELECTION;

    public static final int TIE = 0;
    public static final int WIN = 1;
    public static final int LOSS = 2;

    protected static int LOWESTNUM = 1;
    protected static int HIGHESTNUM = 5;

    protected static ComparisonStrategy strategy;

    protected static int userChoice = NO_SELECTION;

    public GameHub(){

        }

    //check that there is a strategy applied default is ROCK
    protected static ComparisonStrategy getStrategy(){
        if (strategy == null){
            strategy = new RockComparisionStratergy();
        }
        return strategy;
    }

    public static int getUserChoice(){
        return userChoice;
    }

    public static void setUserChoice(int NewuserChoice) {
        userChoice = NewuserChoice;
        switch (userChoice){
            case ROCK:
                setStrategy(new RockComparisionStratergy());
                break;
            case PAPER:
                setStrategy(new PaperComparisionStratergy());
                break;
            case SICSSOR:
                setStrategy(new SicssorComparisionStratergy());
        }


    }

    protected static void setStrategy(ComparisonStrategy Newstratergy) {
        strategy = Newstratergy;
    }

    public static void compare(int otherPlayer){
        RESULT = getStrategy().compare(otherPlayer);
    }


    public static int AiGuess() {
        int AiRandomNum = (int) (Math.random() * (HIGHESTNUM - LOWESTNUM)) + LOWESTNUM;
        int Choice = NO_SELECTION;

        if (AiRandomNum == 1) {
            Choice = ROCK;

        } else if (AiRandomNum == 2) {
            Choice = PAPER;

        } else if (AiRandomNum == 3) {
            Choice = SICSSOR;
        }
        return Choice;

    }

}
