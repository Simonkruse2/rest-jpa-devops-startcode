package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("movie")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/movie",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final MovieFacade FACADE = MovieFacade.getMovieFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllMovies() {
        List<Movie> movies = FACADE.getAllMovies();
        return GSON.toJson(movies);
    }

    @Path("fill")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String populate() {
        FACADE.fillMovies();
        return "{\"msg\":\"done!\"}";
    }
   
    @GET
    @Path("title/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieByTitle(@PathParam("title") String title) {
        return GSON.toJson(FACADE.getMovieByTitle(title));
    }

    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getNumberOfMovies() {
        return "{\"count\":\"" + FACADE.getNumberOfMovies() + "\"}";
    }

    @GET
    @Path("Year/{Year}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieByReleaseYear(@PathParam("Year") int Year) {
        return GSON.toJson(FACADE.getMovieByYear(Year));
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieById(@PathParam("id") int id) {
        return GSON.toJson(FACADE.getMovieByID(id));

    }

}
