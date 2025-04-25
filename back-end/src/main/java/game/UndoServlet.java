package game;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class UndoServlet extends NanoHTTPD {

    private Game currentGame;

    public UndoServlet(int port, Game initialGame) throws IOException {
        super(port);
        this.currentGame = initialGame;
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {

        if ("/undo".equals(session.getUri())) {
            currentGame = currentGame.undo();

            String jsonResponse = currentGame.toString();
            return newFixedLengthResponse(Response.Status.OK, "application/json", jsonResponse);
        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not Found");
    }

    public static void main(String[] args) {
        try {
            Game initialGame = new Game();
            new UndoServlet(8090, initialGame);
            System.out.println("Server started on port 8090");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
