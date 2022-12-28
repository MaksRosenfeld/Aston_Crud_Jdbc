$(document).ready(function(){
    $("#test-button").click(() => {
        $.get( "ajax/test.html", function( data ) {
            $( ".result" ).html( data );
            alert( "Load was performed." );
        });
    })


})