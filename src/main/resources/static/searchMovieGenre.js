function search() {
    $.getJSON(
        "http://localhost:8080/api/v1/movies/genre/" + $("#search").val(),

        function(data) {
            table = $("<table><th>id</th><th>title</th><th>release year</th><th>genre</th><th>director</th><th>rating</th><th>no of ratings</th></table>")
                    .attr("id", "result");

            data.forEach(function(movie) {

                row = $("<tr></tr>")
                .append($("<td></td>").text(movie.id))
                .append($("<td></td>").text(movie.title))
                .append($("<td></td>").text(movie.releaseYear))
                .append($("<td></td>").text(movie.genre))
                .append($("<td></td>").text(movie.director))
                .append($("<td></td>").text(movie.rating))
                .append($("<td></td>").text(movie.numberOfRatings));

                table.append(row);
            })
            $("#result").replaceWith(table);
        }
    )
};