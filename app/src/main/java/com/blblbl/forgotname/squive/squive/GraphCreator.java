package com.blblbl.forgotname.squive.squive;

import android.util.Log;

import com.blblbl.forgotname.moteur.Vect;


public class GraphCreator {

    public final static int POISSON = 1;
    public final static int ORIGINAL = 0;
    public final static int PYRAMIDE = 2;
    public final static int ELEPHANT = 3;
    public final static int PAPILLON = 4;
    public final static int COEUR = 5;
    public final static int FLOCON = 6;
    public final static int TRIANGLE = 7;
    public final static int CARRE = 8;
    public final static int CHAT = 9;
    public final static int TOTORO = 10;
    public final static int ORIGINAL2 = 11;
    public final static int PHALLUS = 12;


    public static int scoreToNiveau(int score) {
        if (score %101 <= 0) {
            return TRIANGLE;
        } else if (score <= 2) {
            return CARRE;
        } else if (score %100  <= 5) {
            return ORIGINAL;
        } else if (score %100 <= 7) {
            return ELEPHANT;
        } else if (score %100 <= 9) {
            return PAPILLON;
        } else if (score %100 <= 11) {
            return FLOCON;
        } else if (score %100 <= 13) {
            return POISSON;
        } else if (score <= 14){
            return CHAT;
        } else if (score %100 <= 17) {
            return PYRAMIDE;
        } else if (score <= 19) {
            return TOTORO;
        } else if (score %100 <= 25) {
            return COEUR;
        }else if (score %100 <= 38){
            return ORIGINAL;
        } else if (score %100 <= 42){
            return COEUR;
        } else if (score%100 == 69) {
            return PHALLUS; // on le sort ou pas? LOL
        } else {
            return ORIGINAL2;
        }
    }

    public static Graphe create_graph(int niveau) throws Exception {
            Graphe ret = new Graphe(getNbSommets(niveau), getNbArretes(niveau));
            charger_sommets(niveau, ret);
            charger_arretes(niveau, ret);
            charger_matrice_tir(niveau, ret);
            charger_sommet_gap(niveau, ret);
            ret.load_angles();
            if (!ret.fully_loaded(getNbSommets(niveau) + getNbArretes(niveau)))
                throw new Exception("erreur: nombre de drawables incorrect, niveau " + Integer.toString(niveau)
                        + "nombre drawable: "
                        + Integer.toString(ret.getNbDrawable()) + "contre:"+
                        Integer.toString(getNbSommets(niveau))+", "+ Integer.toString(getNbArretes(niveau)));
            return ret;
    }

    private static int getNbSommets(int niveau) {
        switch(niveau) {
            case ORIGINAL:
                return 16;
            case 1:
                return 9;
            case 2:
                return 10;
            case 3:
                return 22;
            case 4:
                return 20;
            case 5:
                return 19;
            case 6:
                return 25;
            case 7:
                return 3;
            case 8:
                return 4;
            case 9:
                return 47;
            case 10:
                return 25;
            case 11:
                return 16;
            case PHALLUS:
                return 20;
        }
        return 0; //ERROR
    }

    private static int getNbArretes(int niveau) {
        switch(niveau) {
            case ORIGINAL:
                return 24;
            case 1:
                return 11;
            case 2:
                return 15;
            case 3:
                return 26;
            case 4:
                return 30;
            case 5:
                return 30;
            case 6:
                return 30;
            case 7:
                return 3;
            case 8:
                return 4;
            case 9:
                return 54;
            case 10:
                return 27;
            case 11:
                return 28;
            case PHALLUS:
                return 26;
        }
        return 0; //ERROR

    }

    private static void charger_sommet_gap(int niveau, Graphe graphe) {
        switch(niveau) {
            case ORIGINAL:
                graphe.setSommet_gap(12);
                break;
            case 1:
                graphe.setSommet_gap(6);
                break;
            case 2:
                graphe.setSommet_gap(7);
                break;
            case 3:
                graphe.setSommet_gap(16);
                break;
            case 4:
                graphe.setSommet_gap(13);
                break;
            case 5:
                graphe.setSommet_gap(13);
                break;
            case 6:
                graphe.setSommet_gap(12);
                break;
            case 7:
                graphe.setSommet_gap(2);
                break;
            case 8:
                graphe.setSommet_gap(1);
                break;
            case 9:
                graphe.setSommet_gap(18);
                break;
            case 10:
                graphe.setSommet_gap(7);
                break;
            case 11:
                graphe.setSommet_gap(12);
                break;
            case PHALLUS:
                graphe.setSommet_gap(4);
        }
    }

    private static void charger_sommets(int niveau, Graphe graphe) {
        switch(niveau) {
            case ORIGINAL:
                for (int i=0; i<4; ++i) {
                    int signe_y = 2*(i%2) - 1;//{-1, 1, -1, 1}
                    int signe_x = 2*(i/2) - 1;//{-1, -1, 1, 1}

                    graphe.addSommet(3, new Vect(signe_x*0.9f, signe_y*0.9f)); // vers le coin de l'écran
                    graphe.addSommet(3, new Vect(signe_x*0.9f, signe_y*0.4f));
                    graphe.addSommet(3, new Vect(signe_x*0.4f, signe_y*0.9f));
                    graphe.addSommet(3, new Vect(signe_x*0.4f, signe_y*0.4f)); // vers le centre de l'écran
                }
                break;
            case 1:
                graphe.addSommet(2, -0.9f, 0);// 0
                graphe.addSommet(3, -0.6f, 0.6f);
                graphe.addSommet(3, -0.6f, -0.6f);
                graphe.addSommet(2, -0.2f, 0); // 3
                graphe.addSommet(2, 0.35f, 0.6f);
                graphe.addSommet(2, 0.35f, -0.6f);
                graphe.addSommet(4, 0.6f, 0); //6
                graphe.addSommet(2, 0.9f, 0.4f);
                graphe.addSommet(2, 0.9f, -0.4f);
                break;

            case 2:
                graphe.addSommet(2,-0.9f, -0.9f);
                graphe.addSommet(2,-0.3f, -0.9f);
                graphe.addSommet(2,0.3f, -0.9f);
                graphe.addSommet(2,0.9f, -0.9f);

                graphe.addSommet(4,-0.6f, -0.3f);
                graphe.addSommet(4,0, -0.3f);
                graphe.addSommet(4,0.6f, -0.3f);

                graphe.addSommet(4,-0.3f, 0.3f);
                graphe.addSommet(4,0.3f, 0.3f);

                graphe.addSommet(2,0, 0.9f);
                break;

            case 3:
                graphe.addSommet(2,-0.9f, -0.1f);
                graphe.addSommet(2,-0.9f, 0.6f);
                graphe.addSommet(2,-0.6f, -0.4f);
                graphe.addSommet(2,-0.6f, 0.9f);
                graphe.addSommet(3,-0.3f, -0.1f);
                graphe.addSommet(3,-0.3f, 0.6f); // 5, oreille gauche

                graphe.addSommet(3,-0.1f, -0.25f);//6
                graphe.addSommet(2,-0.1f, 0.7f);
                graphe.addSommet(3,0.1f, -0.25f);
                graphe.addSommet(2,0.1f, 0.7f);

                graphe.addSommet(3,0.3f, -0.1f);//10
                graphe.addSommet(3,0.3f, 0.6f);
                graphe.addSommet(2,0.6f, -0.4f);
                graphe.addSommet(2,0.6f, 0.9f);
                graphe.addSommet(2,0.9f, -0.1f);
                graphe.addSommet(2,0.9f, 0.6f);//15, oreille droite

                graphe.addSommet(2, -0.1f, 0);
                graphe.addSommet(2, 0.1f, 0);
                graphe.addSommet(3, -0.1f, -0.6f);
                graphe.addSommet(3, 0.1f, -0.6f);
                graphe.addSommet(2, 0, -0.9f);
                graphe.addSommet(2, 0.18f, -0.9f);
                break;

            case 4:
                graphe.addSommet(1,-0.2f, 0.9f);
                graphe.addSommet(1,0.2f, 0.9f);
                graphe.addSommet(2,-0.6f, 0.6f);//2

                graphe.addSommet(4, 0 , 0.6f);
                graphe.addSommet(2, 0.6f, 0.6f);

                graphe.addSommet(2,-0.2f, 0.5f);//5
                graphe.addSommet(2,0.2f, 0.5f);

                graphe.addSommet(3,-0.9f, 0.2f);//7
                graphe.addSommet(4,-0.6f, -0.15f);
                graphe.addSommet(5,-0.2f, 0.2f);//9
                graphe.addSommet(5,0.2f, 0.2f);
                graphe.addSommet(4,0.6f, -0.15f);
                graphe.addSommet(3,0.9f, 0.2f); //12

                graphe.addSommet(3,-0.9f, -0.5f);
                graphe.addSommet(2,-0.6f, -0.9f);
                graphe.addSommet(5,-0.2f, -0.5f);//15
                graphe.addSommet(5,0.2f, -0.5f);
                graphe.addSommet(2,0.6f, -0.9f);
                graphe.addSommet(3,0.9f, -0.5f);

                graphe.addSommet(2,0, -0.9f);
                break;

            case 5:
                graphe.addSommet(3,-0.67f, 0.9f);
                graphe.addSommet(2,-0.23f, 0.9f);
                graphe.addSommet(2,0.23f, 0.9f);
                graphe.addSommet(3,0.67f, 0.9f);

                graphe.addSommet(2,-0.9f, 0.54f);//4
                graphe.addSommet(3,-0.45f, 0.54f);
                graphe.addSommet(6,0, 0.54f);
                graphe.addSommet(3,0.45f, 0.54f);
                graphe.addSommet(2,0.9f, 0.54f);

                graphe.addSommet(4,-0.67f, 0.18f);
                graphe.addSommet(3,-0.23f, 0.18f);
                graphe.addSommet(3,0.23f, 0.18f);
                graphe.addSommet(4,0.67f, 0.18f);

                graphe.addSommet(3,-0.45f, -0.18f);
                graphe.addSommet(6,0f, -0.18f);
                graphe.addSommet(3,0.45f, -0.18f);

                graphe.addSommet(3, -0.23f, -0.54f);
                graphe.addSommet(3, 0.23f, -0.54f);

                graphe.addSommet(2,0, -0.9f);

                break;
            case 6://flocon

                graphe.addSommet(1,-0.2f, 0.9f);//0
                graphe.addSommet(1,0.2f, 0.9f);

                graphe.addSommet(1,0.68f, 0.62f);
                graphe.addSommet(1,0.88f, 0.28f);

                graphe.addSommet(1,0.88f, -0.28f);
                graphe.addSommet(1,0.68f, -0.62f);

                graphe.addSommet(1,0.2f, -0.9f);
                graphe.addSommet(1,-0.2f, -0.9f);//7
                graphe.addSommet(1,-0.68f, -0.62f);
                graphe.addSommet(1,-0.88f, -0.28f);
                graphe.addSommet(1,-0.88f, 0.28f);
                graphe.addSommet(1,-0.68f, 0.62f); // 11

                graphe.addSommet(3,0, 0.7f);
                graphe.addSommet(3,0.606f, 0.35f);
                graphe.addSommet(3,0.606f, -0.35f);
                graphe.addSommet(3,0, -0.7f);
                graphe.addSommet(3,-0.606f, -0.35f);
                graphe.addSommet(3,-0.606f, 0.35f);

                graphe.addSommet(4,0, 0.5f);
                graphe.addSommet(4,0.433f, 0.25f);
                graphe.addSommet(4,0.433f, -0.25f);
                graphe.addSommet(4,0, -0.5f);
                graphe.addSommet(4,-0.433f, -0.25f);
                graphe.addSommet(4,-0.433f, 0.25f);
                graphe.addSommet(6,0, 0);

                break;

            case 7:
                graphe.addSommet(2, -0.9f, 0.7f);
                graphe.addSommet(2, 0.9f, 0.7f);
                graphe.addSommet(2, 0, -0.9f);
                break;
            case 8:
                graphe.addSommet(2, 0.9f, 0.9f);
                graphe.addSommet(2, -0.9f, 0.9f);
                graphe.addSommet(2, 0.9f, -0.9f);
                graphe.addSommet(2, -0.9f, -0.9f);
                break;

            case 9:
                graphe.addSommet(2, -0.28f, -0.76f);//0
                graphe.addSommet(2, -0.56f, -0.56f);
                graphe.addSommet(4, -0.69f, -0.28f);
                graphe.addSommet(4, -0.75f, -0.1f);
                graphe.addSommet(4, -0.75f, 0.06f);
                graphe.addSommet(2, -0.73f, 0.54f);
                graphe.addSommet(2, -0.9f, 0.84f);
                graphe.addSommet(2, -0.5f, 0.64f);
                graphe.addSommet(2, -0.22f, 0.74f); //8
                graphe.addSommet(2, 0.22f, 0.74f); // 9
                graphe.addSommet(2, 0.5f, 0.64f);
                graphe.addSommet(2, 0.9f, 0.84f);
                graphe.addSommet(2, 0.73f, 0.54f);
                graphe.addSommet(4, 0.75f, 0.06f);
                graphe.addSommet(4, 0.75f, -0.1f);
                graphe.addSommet(4, 0.69f, -0.28f);// 15
                graphe.addSommet(2, 0.56f, -0.56f);
                graphe.addSommet(2, 0.28f, -0.76f);
                graphe.addSommet(0, 0, -0.84f);//point inutile mais flemme de décaler les indices


                graphe.addSommet(2, -0.5f, 0.22f);
                graphe.addSommet(2, -0.25f, 0.36f);//20, oeil
                graphe.addSommet(2, -0.12f, 0.16f);
                graphe.addSommet(0, -0.26f, 0.22f);
                graphe.addSommet(0, 0.26f, 0.22f);
                graphe.addSommet(2, 0.12f, 0.16f);
                graphe.addSommet(2, 0.25f, 0.36f);
                graphe.addSommet(2, 0.5f, 0.22f);//26

                graphe.addSommet(1, -1.5f, -0.2f);
                graphe.addSommet(1, -1.5f, -0.04f);
                graphe.addSommet(1, -1.5f, 0.12f);
                graphe.addSommet(6, 0, -0.2f); // 30
                graphe.addSommet(1, 1.5f, 0.12f);
                graphe.addSommet(1, 1.5f, -0.04f);
                graphe.addSommet(1, 1.5f, -0.2f);

                graphe.addSommet(2, -0.53f, -0.34f);
                graphe.addSommet(3, -0.37f, -0.5f);
                graphe.addSommet(3, -0.37f, -0.36f);
                graphe.addSommet(3, -0.23f, -0.36f);//37
                graphe.addSommet(3, -0.23f, -0.56f);
                graphe.addSommet(3, 0, -0.4f);
                graphe.addSommet(3, 0, -0.6f);
                graphe.addSommet(3, 0.23f, -0.56f);
                graphe.addSommet(3, 0.23f, -0.36f);//42, sourire
                graphe.addSommet(3, 0.37f, -0.36f);
                graphe.addSommet(3, 0.37f, -0.5f);
                graphe.addSommet(2, 0.53f, -0.34f);//45

                graphe.addSommet(2, 0, -0.84f);
                break;

            case 10:
                graphe.addSommet(2, 0.9f, 0);//0
                graphe.addSommet(3, 0.6f, -0.08f);//1
                graphe.addSommet(3, 0.6f, -0.42f);//2
                graphe.addSommet(3, 0.3f, -0.16f);//3
                graphe.addSommet(3, 0.3f, -0.58f);//4
                graphe.addSommet(3, 0, -0.18f);//5
                graphe.addSommet(3, 0, -0.74f);//6

                graphe.addSommet(3, -0.3f, -0.58f);//7
                graphe.addSommet(3, -0.3f, -0.16f);//8
                graphe.addSommet(3, -0.6f, -0.42f);//9
                graphe.addSommet(3, -0.6f, -0.08f);//10

                graphe.addSommet(0, 0, 0.26f);//11
                graphe.addSommet(0, 0.5f, 0.7f);//12
                graphe.addSommet(0, -0.5f, 0.7f);//13

                graphe.addSommet(2, 0.32f, 0.62f);//14
                graphe.addSommet(2, 0.57f, 0.56f);//15
                graphe.addSommet(2, 0.66f, 0.74f);//16
                graphe.addSommet(2, 0.55f, 0.88f);//17
                graphe.addSommet(2, 0.37f, 0.82f);//18

                graphe.addSommet(2, -0.32f, 0.62f);//19
                graphe.addSommet(2, -0.57f, 0.56f);//20
                graphe.addSommet(2, -0.66f, 0.74f);//21
                graphe.addSommet(2, -0.55f, 0.88f);//22
                graphe.addSommet(2, -0.37f, 0.82f);//23


                graphe.addSommet(2, -0.9f, 0);//24
                break;

            case ORIGINAL2:
                for (int i=0; i<4; ++i) {
                    int signe_y = 2*(i%2) - 1;//{-1, 1, -1, 1}
                    int signe_x = 2*(i/2) - 1;//{-1, -1, 1, 1}

                    graphe.addSommet(3, new Vect(signe_x*0.9f, signe_y*0.9f)); // vers le coin de l'écran
                    graphe.addSommet(4, new Vect(signe_x*0.9f, signe_y*0.4f));
                    graphe.addSommet(4, new Vect(signe_x*0.4f, signe_y*0.9f));
                    graphe.addSommet(3, new Vect(signe_x*0.4f, signe_y*0.4f)); // vers le centre de l'écran
                }
                break;

            case PHALLUS:
                graphe.addSommet(3, 0, 0.9f);
                graphe.addSommet(3, -0.3f, 0.7f);
                graphe.addSommet(3, 0, 0.7f);
                graphe.addSommet(3, 0.3f, 0.7f);//3
                graphe.addSommet(3, -0.3f, 0.2f);
                graphe.addSommet(3, 0.3f, 0.2f);//5

                graphe.addSommet(3, -0.4f, -0.05f);
                graphe.addSommet(3, -0.1f, -0.05f);
                graphe.addSommet(3, 0.1f, -0.05f);
                graphe.addSommet(3, 0.4f, -0.05f);//9 (avant 11)


                graphe.addSommet(2, -0.75f, -0.3f);
                graphe.addSommet(2, -0.75f, -0.7f);
                graphe.addSommet(2, -0.5f, -0.9f);
                graphe.addSommet(2, -0.2f, -0.9f);
                graphe.addSommet(2, 0.75f, -0.3f);
                graphe.addSommet(2, 0.75f, -0.7f);
                graphe.addSommet(2, 0.5f, -0.9f);
                graphe.addSommet(2, 0.2f, -0.9f);

                graphe.addSommet(3, 0, -0.65f);
                graphe.addSommet(3, 0, -0.3f);

                break;
            default:
        }

    }

    private static void charger_arretes(int niveau, Graphe graphe) {
        try {
            switch (niveau) {
                case ORIGINAL:
                    for (int carre = 0; carre < 4; ++carre) { // chaque carré est un sous_graphe complet
                        for (int i = 0; i < 4; ++i) {
                            int v = 0;
                            for (int u = i + 1; u < 4; ++u) {
                                if (u != i && !(u == 1 && i == 2 || u == 2 && i == 1))
                                    graphe.addArrete(4 * carre + i, 4 * carre + u);
                            }
                        }
                    }
                    graphe.addArrete(1, 4 * 1 + 1);
                    graphe.addArrete(4 * 1 + 2, 4 * 3 + 2);
                    graphe.addArrete(4 * 2 + 1, 4 * 3 + 1);
                    graphe.addArrete(4 * 2 + 2, 2);
                    break;
                case 1:
                    graphe.addArrete(0, 1);
                    graphe.addArrete(0, 2);
                    graphe.addArrete(1, 3);
                    graphe.addArrete(2, 3);
                    graphe.addArrete(1, 4);
                    graphe.addArrete(2, 5);
                    graphe.addArrete(5, 6);
                    graphe.addArrete(4, 6);
                    graphe.addArrete(6, 8);
                    graphe.addArrete(6, 7);
                    graphe.addArrete(7, 8);
                    break;

                case 2:
                    graphe.addArrete(0, 1);
                    graphe.addArrete(2, 3);
                    graphe.addArrete(0, 4);
                    graphe.addArrete(1, 4);
                    graphe.addArrete(2, 6);
                    graphe.addArrete(3, 6);
                    graphe.addArrete(4, 5);
                    graphe.addArrete(5, 6);
                    graphe.addArrete(4, 7);
                    graphe.addArrete(5, 7);
                    graphe.addArrete(5, 8);
                    graphe.addArrete(6, 8);
                    graphe.addArrete(7, 8);
                    graphe.addArrete(7, 9);
                    graphe.addArrete(8, 9);
                    break;

                case 3:
                    graphe.addArrete(0, 1);
                    graphe.addArrete(1, 3);
                    graphe.addArrete(0, 2);
                    graphe.addArrete(2, 4);
                    graphe.addArrete(3, 5);
                    graphe.addArrete(4, 5);

                    graphe.addArrete(5, 7);
                    graphe.addArrete(7, 9);
                    graphe.addArrete(9, 11);
                    graphe.addArrete(4, 6);
                    graphe.addArrete(8, 10);

                    graphe.addArrete(11, 13);
                    graphe.addArrete(13, 15);
                    graphe.addArrete(15, 14);
                    graphe.addArrete(14, 12);
                    graphe.addArrete(12, 10);
                    graphe.addArrete(11, 10);

                    graphe.addArrete(16, 17);
                    graphe.addArrete(16, 6);
                    graphe.addArrete(17, 8);

                    graphe.addArrete(6, 18);
                    graphe.addArrete(19, 8);
                    graphe.addArrete(18, 19);

                    graphe.addArrete(18, 20);
                    graphe.addArrete(19, 21);
                    graphe.addArrete(20, 21);
                    break;
                case 4:
                    graphe.addArrete(0, 3);
                    graphe.addArrete(1, 3);
                    graphe.addArrete(5, 3);
                    graphe.addArrete(3, 6);
                    graphe.addArrete(5, 9);
                    graphe.addArrete(10, 6);
                    graphe.addArrete(9, 2);
                    graphe.addArrete(7, 2);
                    graphe.addArrete(10, 4);
                    graphe.addArrete(4, 12);
                    graphe.addArrete(7, 8);
                    graphe.addArrete(8, 9);
                    graphe.addArrete(9, 10);
                    graphe.addArrete(10, 11);
                    graphe.addArrete(11, 12);
                    graphe.addArrete(7, 13);
                    graphe.addArrete(13, 8);
                    graphe.addArrete(8, 15);
                    graphe.addArrete(9, 15);

                    graphe.addArrete(10, 16);
                    graphe.addArrete(16, 11);
                    graphe.addArrete(18, 11);
                    graphe.addArrete(12, 18);

                    graphe.addArrete(13, 14);
                    graphe.addArrete(15, 14);
                    graphe.addArrete(15, 16);
                    graphe.addArrete(16, 17);
                    graphe.addArrete(17, 18);

                    graphe.addArrete(15, 19);
                    graphe.addArrete(19, 16);
                    break;

                case 5:
                    graphe.addArrete(18, 16);
                    graphe.addArrete(18, 17);
                    graphe.addArrete(13, 16);
                    graphe.addArrete(15, 17);
                    graphe.addArrete(14, 16);
                    graphe.addArrete(14, 17);

                    graphe.addArrete(14, 13);
                    graphe.addArrete(14, 15);
                    graphe.addArrete(13, 9);
                    graphe.addArrete(14, 10);
                    graphe.addArrete(14, 11);
                    graphe.addArrete(15, 12);

                    graphe.addArrete(9, 10);
                    graphe.addArrete(11, 12);

                    graphe.addArrete(9, 4);
                    graphe.addArrete(5, 9);
                    graphe.addArrete(10, 6);
                    graphe.addArrete(11, 6);
                    graphe.addArrete(12, 7);
                    graphe.addArrete(12, 8);

                    graphe.addArrete(5, 6);
                    graphe.addArrete(7, 6);
                    graphe.addArrete(4, 0);
                    graphe.addArrete(0, 5);
                    graphe.addArrete(1, 6);
                    graphe.addArrete(6, 2);
                    graphe.addArrete(7, 3);
                    graphe.addArrete(8, 3);

                    graphe.addArrete(0, 1);
                    graphe.addArrete(2, 3);

                    break;
                case 6:
                    graphe.addArrete(0, 12);
                    graphe.addArrete(1, 12);
                    graphe.addArrete(2, 13);
                    graphe.addArrete(3, 13);
                    graphe.addArrete(4, 14);
                    graphe.addArrete(5, 14);
                    graphe.addArrete(6, 15);
                    graphe.addArrete(7, 15);
                    graphe.addArrete(8, 16);
                    graphe.addArrete(9, 16);
                    graphe.addArrete(10, 17);
                    graphe.addArrete(11, 17);

                    graphe.addArrete(18, 12);
                    graphe.addArrete(19, 13);
                    graphe.addArrete(20, 14);
                    graphe.addArrete(21, 15);
                    graphe.addArrete(22, 16);
                    graphe.addArrete(23, 17);

                    graphe.addArrete(18, 24);
                    graphe.addArrete(19, 24);
                    graphe.addArrete(20, 24);
                    graphe.addArrete(21, 24);
                    graphe.addArrete(22, 24);
                    graphe.addArrete(23, 24);

                    graphe.addArrete(18, 19);
                    graphe.addArrete(19, 20);
                    graphe.addArrete(20, 21);
                    graphe.addArrete(21, 22);
                    graphe.addArrete(23, 22);
                    graphe.addArrete(23, 18);


                    break;
                case 7:
                    graphe.addChaineArretes(0,1,2,0);
                    break;
                case 8:
                    graphe.addChaineArretes(0,1,3,2,0);
                    break;
                case 9:
                    graphe.addChaineArretes(46,0,1,2,3,4,5,6,7,8,9,10,11,12,13,
                            14,15,16,17,46); //19
                    graphe.addChaineArretes(27, 2, 30, 15, 33);
                    graphe.addChaineArretes(28, 3, 30, 14, 32);
                    graphe.addChaineArretes(29, 4, 30, 13, 31);
                    graphe.addChaineArretes(19,20,21,19);
                    graphe.addChaineArretes(24,25,26,24);
                    graphe.addChaineArretes(45,44,43,45);
                    graphe.addChaineArretes(43,42,41,44);
                    graphe.addChaineArretes(41,40,39,42);
                    graphe.addChaineArretes(39,37,38,40);
                    graphe.addChaineArretes(37,36,35,38);
                    graphe.addChaineArretes(36,34,35);
                    break;
                case 10:
                    graphe.addChaineArretes(14,15,16,17,18,14);
                    graphe.addChaineArretes(19,20,21,22,23,19);
                    graphe.addChaineArretes(0,1,2,0);
                    graphe.addChaineArretes(1,3,4,2);
                    graphe.addChaineArretes(3,5,6,4);
                    graphe.addChaineArretes(6,7,8,5);
                    graphe.addChaineArretes(7,9,10,8);
                    graphe.addChaineArretes(9,24,10);
                    break;

                case ORIGINAL2:
                    for (int carre = 0; carre < 4; ++carre) { // chaque carré est un sous_graphe complet
                        for (int i = 0; i < 4; ++i) {
                            int v = 0;
                            for (int u = i + 1; u < 4; ++u) {
                                graphe.addArrete(4 * carre + i, 4 * carre + u);
                            }
                        }
                    }
                    graphe.addArrete(1, 4 * 1 + 1);
                    graphe.addArrete(4 * 1 + 2, 4 * 3 + 2);
                    graphe.addArrete(4 * 2 + 1, 4 * 3 + 1);
                    graphe.addArrete(4 * 2 + 2, 2);
                    break;
                case PHALLUS:
                    graphe.addChaineArretes(0,1,4,6,10,11,12,13,18,17,16,15,14,9,8,19,18);
                    graphe.addChaineArretes(0,2,3,5,8);
                    graphe.addArrete(0,3);
                    graphe.addArrete(1,2);
                    graphe.addChaineArretes(4,7,19);
                    graphe.addArrete(6,7);
                    graphe.addArrete(5,9);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

    private static void charger_matrice_tir(int niveau, Graphe graphe) {
        int nombre_sommet = getNbSommets(niveau);
        switch(niveau) {
            case ORIGINAL:
                for (int sommet1 = 0; sommet1 < nombre_sommet; ++sommet1) {
                    for (int sommet2 = sommet1+1; sommet2 < nombre_sommet; ++sommet2) {

                        if (sommet1 / 4 == sommet2 / 4) { // si les sommets appartiennent au même carré
                            graphe.add_arrete_tirable(sommet1, sommet2);
                        } else {
                            Vect pos1 = graphe.getSommet(sommet1).getVect();
                            Vect pos2 = graphe.getSommet(sommet2).getVect();
                            if (Math.abs(pos1.getmX() - pos2.getmX()) < 0.1f || Math.abs(pos1.getmY() - pos2.getmY()) < 0.1f) {
                                graphe.add_arrete_tirable(sommet1, sommet2);
                            }
                        }
                    }
                }
                break;
            case POISSON:
                graphe.add_arrete_tirable(0,3);
                graphe.add_arrete_tirable(6,3);
                graphe.add_arrete_tirable(5,4);
                graphe.add_arrete_tirable(0,6);
                graphe.add_arrete_tirable(5,7);
                graphe.add_arrete_tirable(4,8);
                break;

            case 2:
                graphe.add_arrete_tirable(4,6);

                graphe.add_arrete_tirable(0,7);
                graphe.add_arrete_tirable(4,9);
                graphe.add_arrete_tirable(0,9);

                graphe.add_arrete_tirable(3,9);
                graphe.add_arrete_tirable(3,8);
                graphe.add_arrete_tirable(6,9);
                break;

            case 3:
                graphe.add_arrete_tirable(8,6);

                graphe.add_arrete_tirable(12,21);
                graphe.add_arrete_tirable(12,19);
                graphe.add_arrete_tirable(2,18);
                graphe.add_arrete_tirable(2,20);

                graphe.add_arrete_tirable(16,5);
                graphe.add_arrete_tirable(16,7);
                graphe.add_arrete_tirable(16,9);
                graphe.add_arrete_tirable(17,7);
                graphe.add_arrete_tirable(17,9);
                graphe.add_arrete_tirable(17,11);

                graphe.add_arrete_tirable(0,5);
                graphe.add_arrete_tirable(1,4);
                graphe.add_arrete_tirable(2,3);

                graphe.add_arrete_tirable(11,14);
                graphe.add_arrete_tirable(12,13);
                graphe.add_arrete_tirable(10,15);

                graphe.add_arrete_tirable(16,4);
                graphe.add_arrete_tirable(17,10);
                graphe.add_arrete_tirable(19,6);
                graphe.add_arrete_tirable(18,8);

                break;

            case 4:
                graphe.add_arrete_tirable(1, 0);
                graphe.add_arrete_tirable(7, 15);
                graphe.add_arrete_tirable(13, 9);
                graphe.add_arrete_tirable(10, 18);
                graphe.add_arrete_tirable(16, 12);

                graphe.add_arrete_tirable(14, 19);
                graphe.add_arrete_tirable(19, 17);
                graphe.add_arrete_tirable(14, 17);

                graphe.add_arrete_tirable(0, 2);
                graphe.add_arrete_tirable(1, 4);

                graphe.add_arrete_tirable(9,16);
                graphe.add_arrete_tirable(15,10);

                graphe.add_arrete_tirable(9,6);
                graphe.add_arrete_tirable(5,10);

                graphe.add_arrete_tirable(2,8);
                graphe.add_arrete_tirable(8,14);
                graphe.add_arrete_tirable(2,14);

                graphe.add_arrete_tirable(4,11);
                graphe.add_arrete_tirable(17,11);
                graphe.add_arrete_tirable(4,17);

                break;

            case 5:
                graphe.addAlignementTirable(4,9,13,16,18);
                graphe.addAlignementTirable(18,17,15,12,8);
                graphe.addAlignementTirable(0,1,2,3);
                graphe.addAlignementTirable(0,5,10,14,17);
                graphe.addAlignementTirable(3,7,11,14,16);
                graphe.addAlignementTirable(2,6,10,13);
                graphe.addAlignementTirable(1,6,11,15);
                graphe.addAlignementTirable(2,7,12);
                graphe.addAlignementTirable(1,5,9);

                graphe.addAlignementTirable(0,1,2,3);
                graphe.addAlignementTirable(4,5,6,7,8);
                graphe.addAlignementTirable(9,10,11,12);
                graphe.addAlignementTirable(13,14,15);
                graphe.add_arrete_tirable(16,17);
                break;

            case 6:
                graphe.addAlignementTirable(12, 18, 24, 21, 15);
                graphe.addAlignementTirable(17,23,24,20,14);
                graphe.addAlignementTirable(16,22,24,19,13);
                graphe.addChaineTirable(0,1,2,3,4,5,6,7,8,9,10,11);
                graphe.addChaineTirable(19, 20, 21, 19);
                break;

            case 9:
                graphe.addAlignementTirable(2,30,15);
                graphe.addAlignementTirable(3,30,14);
                graphe.addAlignementTirable(4,30,13);
                graphe.addAlignementTirable(4,3,2);
                graphe.addAlignementTirable(15,14,13);
                break;
            case 10:
                graphe.addAlignementTirable(0,1,3,8,10,24);
                break;

            case 11:
                for (int sommet1 = 0; sommet1 < nombre_sommet; ++sommet1) {
                    for (int sommet2 = sommet1+1; sommet2 < nombre_sommet; ++sommet2) {

                        if (sommet1 / 4 == sommet2 / 4) { // si les sommets appartiennent au même carré
                            graphe.add_arrete_tirable(sommet1, sommet2);
                        } else {
                            Vect pos1 = graphe.getSommet(sommet1).getVect();
                            Vect pos2 = graphe.getSommet(sommet2).getVect();
                            if (Math.abs(pos1.getmX() - pos2.getmX()) < 0.1f || Math.abs(pos1.getmY() - pos2.getmY()) < 0.1f) {
                                graphe.add_arrete_tirable(sommet1, sommet2);
                            }
                        }
                    }
                }
                //grosse diagonale:
                graphe.addAlignementTirable(0,3, 12,15);
                graphe.addAlignementTirable(4,7, 8,11);
                break;
        }
    }

    private static final String TAG = "GraphCreator";
}
