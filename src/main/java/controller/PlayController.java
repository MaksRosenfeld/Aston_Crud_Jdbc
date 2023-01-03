package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ConnectionPool;
import entities.Play;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {
        "/api/all_plays",
        "/api/delete_play",
        "/api/get_play",
        "/api/update_play"})
public class PlayController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            PlayService playService = new PlayService(connection);
            String id = req.getParameter("id");
            String result;
            if (id == null) {
                result = mapper.writeValueAsString(playService.getAll());
            } else {
                Play play = playService.get(Integer.parseInt(id));
                result = mapper.writeValueAsString(play);
            }
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(result);
        } catch (PropertyVetoException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getReader().lines().map(p -> p.split("=")).findFirst().get()[1]);
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            PlayService playService = new PlayService(connection);
            playService.delete(id);
            resp.setStatus(200);
        } catch (PropertyVetoException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
