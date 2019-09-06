package facades;

import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Movie getMovieByID(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Movie.class, id);
        } finally {
            em.close();
        }
    }

    public List<Movie> getAllMovies() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query = em.createQuery("SELECT m FROM movie m", Movie.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    
    public void fillMovies() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(new Movie(2004, "TogC", new String[]{"Torben", "Chris"}));
            em.persist(new Movie(1999, "IogB", new String[]{"Ib", "Bo"}));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    public List<Movie> getMovieByTitle(String title) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query = em.createQuery("SELECT m FROM movie m WHERE title=: title", Movie.class)
                    .setParameter("title", title);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Movie> getMovieByYear(int year) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query = em.createQuery("SELECT m FROM movie m WHERE year=: year", Movie.class)
                    .setParameter("year", year);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Movie addMovie(int year, String name, String[] actors) {
        EntityManager em = emf.createEntityManager();
        Movie m = new Movie(year, name, actors);
        try {
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            return m;
        } finally {
            em.close();
        }
    }

    public int getNumberOfMovies() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery q = em.createQuery("select m from Movie m", Movie.class);
            return q.getResultList().size();
        } finally {
            em.close();
        }
    }
//
//    public static void main(String[] args) {
//        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
//        MovieFacade mf = MovieFacade.getMovieFacade(emf);
//        String[] actors = {"Vin Diesel", "Lektor Blomme"};
//        
//        System.out.println(mf.addMovie(1998, "Det forsømte forår", actors));
//        System.out.println(mf.addMovie(2087, "The Fast and the Furios 235", actors));
//    }
}