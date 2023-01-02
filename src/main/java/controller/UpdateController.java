package controller;

import dao.ConnectionPool;
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

@WebServlet(urlPatterns = "/api/update_play")
public class UpdateController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int gameId = Integer.parseInt(req.getParameter("gameId"));
        int playgroundId = Integer.parseInt(req.getParameter("playground"));
        double price = Double.parseDouble(req.getParameter("price"));
        int amount = Integer.parseInt(req.getParameter("amount"));
        boolean exclusive = Boolean.parseBoolean(req.getParameter("exclusive"));
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            PlayService playService = new PlayService(connection);
            GameService gameService = new GameService(connection);
            gameService.update(gameId, name, exclusive);
            playService.update(playId, playgroundId, gameId, price, amount);
            resp.setStatus(200);
        } catch (PropertyVetoException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
