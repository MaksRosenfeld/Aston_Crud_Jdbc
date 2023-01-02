package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ConnectionPool;
import entities.Game;
import entities.Station;
import service.GameService;
import service.PlayService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/api/save_game")
public class GameController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int playgroundId = Integer.parseInt(req.getParameter("playground"));
        double price = Double.parseDouble(req.getParameter("price"));
        int amount = Integer.parseInt(req.getParameter("amount"));
        boolean exclusive = Boolean.parseBoolean(req.getParameter("exclusive"));
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            GameService gameService = new GameService(connection);
            PlayService playService = new PlayService(connection);
            gameService.add(name, exclusive);
            Game game = gameService.getAll().stream().filter(g -> g.getName().equals(name)).findFirst().get();
            playService.add(playgroundId, game.getId(), price, amount);
            resp.setStatus(200);
        } catch (PropertyVetoException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
//            PlayService playService = new PlayService(connection);
//            String allGames = mapper.writeValueAsString(playService.getAll());
//            resp.setContentType("application/json; charset=UTF-8");
//            resp.getWriter().write(allGames);
//        } catch (PropertyVetoException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
