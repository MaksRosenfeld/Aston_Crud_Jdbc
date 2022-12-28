package controller;

import service.GameService;
import service.PlayService;
import service.PlaygroundService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/name")
public class PlayController extends HttpServlet {

//    private final GameService gameService;
//    private final PlaygroundService playgroundService;
//    private final PlayService playService;
//
//
//    public PlayController() {
//        this.gameService = new GameService()
//
//    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

    }
}
