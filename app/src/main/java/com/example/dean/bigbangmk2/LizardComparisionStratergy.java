package com.example.dean.bigbangmk2;

/**
 * Created by Dean on 25/03/2015.
 */
public class LizardComparisionStratergy implements ComparisonStrategy {
    public int compare(int selected) {
        switch (selected) {
            case GameHub.LIZARD:
                return GameHub.TIE;

            case GameHub.SPOCK:
                return GameHub.WIN;

            case GameHub.PAPER:
                return GameHub.WIN;

            default:
                return GameHub.LOSS;

        }

    }
}
