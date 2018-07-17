function search() {
    $.getJSON(
        "http://localhost:8080/api/v1/movies/" + $("#search").val(),

        function(data) {
            table = $("<table><th>id</th><th>title</th><th>release year</th><th>genre</th><th>director</th><th>rating</th><th>no of ratings</th></table>")
                    .attr("id", "result");

            row = $("<tr></tr>")
            .append($("<td></td>").text(data.id))
            .append($("<td></td>").text(data.title))
            .append($("<td></td>").text(data.releaseYear))
            .append($("<td></td>").text(data.genre))
            .append($("<td></td>").text(data.director))
            .append($("<td></td>").text(data.rating))
            .append($("<td></td>").text(data.numberOfRatings));

            table.append(row);

            $("#result").replaceWith(table);
        }
    )
};




  //   window.location = "http://localhost:8080/api/v1/movies/" + $("#search").val();


