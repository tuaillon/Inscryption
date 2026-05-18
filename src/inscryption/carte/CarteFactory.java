package inscryption.carte;

import java.util.Random;

public class CarteFactory
{
    private CarteFactory() {};

    //une méthode pour tous les animaux
    public static CarteAnimal creerCarteAnimal(TypeAnimal type)
    {
        switch ( type )
        {
            case CHAT:
                return new CarteAnimal(
                        "Chat",
                        0,
                        1,
                        1,
                        0,
                        false);

            case GRIZZLY:
                return new CarteAnimal(
                        "Grizzly",
                        4,
                        6,
                        3,
                        0,
                        false);

            case COYOTE:
                return new CarteAnimal(
                        "Coyote",
                        2,
                        1,
                        0,
                        4,
                        false);

            case MOINEAU:
                return new CarteAnimal(
                        "Moineau",
                        1,
                        2,
                        1,
                        0,
                        true);

            case CORBEAU:
                return new CarteAnimal(
                        "Corbeau",
                        2,
                        3,
                        2,
                        0,
                        true);

            case ECUREUIL:
                return new CarteAnimal(
                        "Ecureuil",
                        0,
                        1,
                        0,
                        0,
                        false);

            case HERMINE:
                return new CarteAnimal(
                        "Hermine",
                        1,
                        3,
                        1,
                        0,
                        false);

            case LOUVETEAU:
                return new CarteAnimal(
                        "Louveteau",
                        1,
                        1,
                        1,
                        0,
                        false);

            case LOUP:
                return new CarteAnimal(
                        "Loup",
                        3,
                        2,
                        2,
                        0,
                        false);

            case PUNAISE:
                return new CarteAnimal(
                        "Punaise",
                        1,
                        2,
                        0,
                        2,
                        false);
            default:
                return null; //jamais on arrivera ici

        }
    }

    public static CarteObstacle creerCarteObstacle(TypeObstacle type)
    {
        switch ( type )
        {
            case ROCHER:
                return new CarteObstacle(
                        "Rocher",
                        5);

            case SAPIN:
                return new CarteObstacle(
                        "Sapin",
                        3);

            default:
                return null;
        }
    }

    public static CarteAnimal creerCarteAnimalRandom()
    {
        TypeAnimal[] types = TypeAnimal.values();
        Random random = new Random();

        TypeAnimal typeAleatoire = types[random.nextInt(types.length)];

        return creerCarteAnimal(typeAleatoire);
    }

    public static CarteObstacle creerCarteObstacleRandom()
    {
        TypeObstacle[] types = TypeObstacle.values();
        Random random = new Random();

        TypeObstacle typeAleatoire = types[random.nextInt(types.length)];

        return creerCarteObstacle(typeAleatoire);
    }
}
