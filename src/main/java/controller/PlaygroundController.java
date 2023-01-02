package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ConnectionPool;
import service.PlayService;
import service.PlaygroundService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/api/all_playgrounds")
public class PlaygroundController extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            PlaygroundService playgroundService = new PlaygroundService(connection);
            String allPlaygrounds = mapper.writeValueAsString(playgroundService.getAll());
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(allPlaygrounds);
        } catch (PropertyVetoException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
