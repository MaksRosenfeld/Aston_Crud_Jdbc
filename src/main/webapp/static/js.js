let idToUpdate;

function showAllStations() {
    if ($("#choose-station option").length === 1) {
        $.getJSON("api/all_playgrounds", function (stations) {
            $.each(stations, function (idx, station) {
                $("#choose-station").append(`
            <option value="${station["id"]}">${station["name"]}</option>
            `)
            })
        })
    }
}

$(document).ready(function () {

    $.getJSON("api/all_plays", function (plays) {
        $.each(plays, function (idx, play) {
            $("#table-body").append(`
            <tr>
            <td>
            <button value="${play["id"]}" type="button" class="btn btn-warning btn-sm shadow-p update-button">Update</button>
            <button value="${play["id"]}" type="button" class="btn btn-danger btn-sm shadow-p delete-button">Delete</button>
            </td>
            <td id="playground-${play["id"]}">${play["playground"]["name"]}</td>
            <th id="game-${play["id"]}" scope="row">${play["game"]["name"]}</th>
            <td id="price-${play["id"]}">${play["price"]}$</td>
            <td id="amount-${play["id"]}">${play["amount"]}</td>
            
</tr>
            `)

        })
    })
    $("#add-button").click(() => {
        $("#addNewGame").text("Add new game")
        $("#save-game-button").text("Save game")
        $("#new-game-name").val('')
        $("#choose-station").val(0)
        $("#new-game-price").val('');
        $("#new-game-amount").val('');
        showAllStations();

    })

    $("#save-game-button").click(() => {
        let gameName = $("#new-game-name").val();
        let pgName = $("#choose-station option:selected").val()
        let price = $("#new-game-price").val();
        let amount = $("#new-game-amount").val();
        let exclusive = !!$("#new-game-exclusive").is(":checked");
        if ($("#save-game-button").text() === "Save game") {
            if (!gameName || pgName === 0 || !price || !amount) {
                alert("Some fields are empty");
            } else {
                $.ajax("api/save_game", {
                    method: "POST",
                    data: {
                        name: gameName,
                        playground: pgName,
                        price: price,
                        amount: amount,
                        exclusive: exclusive
                    }

                })
            }
            location.reload();
        } else {
            let gameId = $("#game-id-hidden").val();
            if (!gameName || pgName === 0 || !price || !amount) {
                alert("Some fields are empty");
            } else {
                $.post('api/update_play', {
                    id: idToUpdate,
                    gameId: gameId,
                    name: gameName,
                    playground: pgName,
                    price: price,
                    amount: amount,
                    exclusive: exclusive
                })
            }
            location.reload();


        }
    })
})

$(document).on("click", ".delete-button", function () {
    $.ajax("api/delete_play", {
        method: "DELETE",
        data: {
            id: $(this).val()
        }

    })
    location.reload();
})

$(document).on("click", ".update-button", function () {
    idToUpdate = $(this).val();
    let gameName = $("#new-game-name");
    let station = $("#choose-station");
    let gamePrice = $("#new-game-price");
    let gameAmount = $("#new-game-amount");
    let gameId = $("#game-id-hidden");
    showAllStations();
    $("#save-game-button").text("Update game")
    $.get("api/get_play", {id: idToUpdate})
        .done(function (play) {
            $("#addNewGame").text("Update the game")
            $("#add-play-modal").modal("show");
            gameId.val(play["game"]["id"])
            gameName.val(play["game"]["name"])
            station.val(play["playground"]["id"]).change()
            gamePrice.val(play["price"])
            gameAmount.val(play["amount"])
        });


})

