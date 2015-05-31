package com.example.dean.bigbangmk2;

/**
 * Created by Dean on 25/03/2015.
 */
public interface ComparisonStrategy {
    public int compare(int otPlayer);


    class LizardComparisionStratergy implements ComparisonStrategy {
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

    class PaperComparisionStratergy implements ComparisonStrategy {
        public int compare(int selected) {
            switch (selected) {
                case GameHub.PAPER:
                    return GameHub.TIE;

                case GameHub.ROCK:
                case GameHub.SPOCK:
                    return GameHub.WIN;

                default:
                    return GameHub.LOSS;

            }

        }
    }

    class RockComparisionStratergy implements ComparisonStrategy {

        public int compare(int selected) {
            switch (selected) {
                case GameHub.ROCK:
                    return GameHub.TIE;

                case GameHub.SICSSOR:
                    return GameHub.WIN;

                case GameHub.LIZARD:
                    return GameHub.WIN;

                default:
                    return GameHub.LOSS;

            }

        }
    }

    class SpockComparisonStratergy implements ComparisonStrategy {

        public int compare(int selected) {
            switch (selected) {
                case GameHub.SPOCK:
                    return GameHub.TIE;

                case GameHub.SICSSOR:
                    return GameHub.WIN;

                case GameHub.ROCK:
                    return GameHub.WIN;

                default:
                    return GameHub.LOSS;

            }

        }
    }

    class SicssorComparisionStratergy implements ComparisonStrategy {
        public int compare(int selected) {
            switch (selected) {
                case GameHub.SICSSOR:
                    return GameHub.TIE;

                case GameHub.PAPER:
                    return GameHub.WIN;

                case GameHub.LIZARD:
                    return GameHub.WIN;

                default:
                    return GameHub.LOSS;

            }
        }
    }
}