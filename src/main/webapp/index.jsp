<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 28.12.2022
  Time: 21:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Aston JDBC Servlet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<div class="container p-4">
<table class="table table-hover shadow rounded">
    <thead class="table-dark">
    <tr>
        <th></th>
        <th scope="col">Playground</th>
        <th scope="col">Game</th>
        <th scope="col">Price</th>
        <th scope="col">Amount</th>
    </tr>
    </thead>
    <tbody id="table-body">
    </tbody>
</table>
    <button type="button" id="add-button" class="btn btn-lg btn-success shadow" data-bs-toggle="modal" data-bs-target="#add-play-modal">
        Add
    </button>
</div>

<!-- Modal -->
<div class="modal fade" id="add-play-modal" tabindex="-1" aria-labelledby="addNewGame" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addNewGame">Add new game</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="input-group mb-3">
                    <input id="new-game-name" type="text" class="form-control" placeholder="Name of the game" aria-label="Username" aria-describedby="basic-addon1">
                    <input id="game-id-hidden" type="hidden">
                    <select class="form-select" id="choose-station">
                        <option selected>Choose station</option>
                    </select>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text">$</span>
                    <input id="new-game-price" type="number" placeholder="Price" class="form-control" aria-label="Dollar amount (with dot and two decimal places)">
                    <input id="new-game-amount" type="number" placeholder="Amount" class="form-control">
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="true" id="new-game-exclusive">
                    <label class="form-check-label" for="new-game-exclusive">
                        Exclusive
                    </label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button id="save-game-button" type="button" class="btn btn-success">Save game</button>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="static/js.js"></script>

</body>
</html>

