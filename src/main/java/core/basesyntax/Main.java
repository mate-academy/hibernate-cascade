package core.basesyntax;

import core.basesyntax.lib.Injector;
import org.hibernate.SessionFactory;

public class Main {
    private static final Injector injector = Injector
            .getInstance("core.basesyntax");

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        /*CountryService countryService = (CountryService) injector
        .getInstance(CountryService.class);

        Country usa = new Country("USA");
        countryService.add(usa);

        Actor vinDiesel = new Actor("Vin Diesel");
        actorService.add(vinDiesel);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setActors(new ArrayList<>());
        fastAndFurious.getActors().add(actorService.get(vinDiesel.getId()));
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));*/
    }
}
